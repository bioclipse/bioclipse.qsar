/*******************************************************************************
 * Copyright (c) 2009 Ola Spjuth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth - initial API and implementation
 ******************************************************************************/
package net.bioclipse.qsar.business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.qsar.init.Activator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class JenaReader {

	private static final String BO_NAMESPACE = "http://www.blueobelisk.org/" +
	"ontologies/chemoinformatics-algorithms";

	@Deprecated
	public static DescriptorModel populateHierarchy() throws IOException, 
															URISyntaxException{

		//The new model
        DescriptorModel descriptorModel=new DescriptorModel();

		//We need a new list of descriptor categories
		List<DescriptorCategory> categories=new ArrayList<DescriptorCategory>();
		
		
		//Try to get from URL
		//TODO
		
		//Use hardcoded owl
		String owlFile="/ontology/descriptor-algorithms.owl";
        Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID); 
		String uri=null;
		
		URL url=bundle.getEntry(owlFile);
		System.out.println("URL: " + url);
		URL furl=FileLocator.toFileURL(url);
		System.out.println("fileURL: " + furl);
		uri = furl.toURI().toString();
		System.out.println("URI: " + uri);

		//Load schema and data from same file (seems to work!)
		Model schema = FileManager.get().loadModel(uri);
		Model data = FileManager.get().loadModel(uri);

		//Init reasoner and inference model
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);

		//Resources from BO ontology OWL
		
		
		Resource catRes= infmodel.getResource("http://www.blueobelisk.org/" +
		"ontologies/chemoinformatics-algorithms/#descriptorCategories");

		Resource descRes= infmodel.getResource("http://www.blueobelisk.org/" +
		"ontologies/chemoinformatics-algorithms/#Descriptor");

		Property propIsClassifiedAs=schema.getProperty("http://www.blueobelisk.org/" +
		"ontologies/chemoinformatics-algorithms/#isClassifiedAs");

		Property propDescription=schema.getProperty("http://www.blueobelisk.org/" +
		"ontologies/chemoinformatics-algorithms/#description");
		
		Property propDefinition=schema.getProperty("http://www.blueobelisk.org/" +
		"ontologies/chemoinformatics-algorithms/#definition");
		
		Property propDate=schema.getProperty("http://dublincore.org/date");

		//Find all categories of type descriptorCategory
		//===================================
		for (StmtIterator i = infmodel.listStatements(null, propIsClassifiedAs, 
				catRes); i.hasNext(); ) {
			Statement stmt = i.nextStatement();

			Resource category=stmt.getSubject();
//			System.out.println(" * " + PrintUtil.print(category));
			
			String catid=category.getURI();
			String catname="";
			if (category.hasProperty(RDFS.label)){
				catname=category.getProperty(RDFS.label).getString();
			} else {
				catname=catid;
			}
			
			System.out.println(" ## Added category: " + catname + " with id: " + catid);
			DescriptorCategory descCat=new DescriptorCategory(catid, catname);
			
			categories.add(descCat);

		}
		
		//Set collected list of categories in model
		descriptorModel.setCategories(categories);
		
		//Read all resources of type Descriptor
		//=====================================
		for (StmtIterator ii = infmodel.listStatements(null, RDF.type, descRes); 
															   ii.hasNext(); ) {
			Statement stmt2 = ii.nextStatement();
			Resource descriptor=stmt2.getSubject();
			
			String descid=descriptor.getURI();
			String descname=descriptor.getProperty(RDFS.label).getString();
			Descriptor desc=new Descriptor(descid,descname);

			if (descriptor.hasProperty(propDescription)){
				String description=descriptor.getProperty(propDescription).getString();
				desc.setDescription(description);
//				System.out.println("        ++ " + PrintUtil.print(descriptor.getProperty(propDescription).getObject()));
			}
			if (descriptor.hasProperty(propDefinition)){
				String def=descriptor.getProperty(propDefinition).getString();
				desc.setDefinition(def);
//				System.out.println("        == " + PrintUtil.print(descriptor.getProperty(propDefinition).getObject()));
			}
			if (descriptor.hasProperty(propDate)){
				String date=descriptor.getProperty(propDate).getString();
				desc.setDate(date);
//				System.out.println("##**## Added date: " + date + 
//						" to descriptor: " + descid);
			}

			//All these descriptors have the BO namespace
			desc.setNamespace(BO_NAMESPACE);

//			System.out.println("## Found descriptor: " + descname + 
//					" with id: " + descid);
			
			
			//Read all categories for this descriptor
			//=======================================
			for (StmtIterator i = descriptor.listProperties(propIsClassifiedAs); 
																i.hasNext(); ) {

				Statement stmt = i.nextStatement();
				if (stmt.getObject().isResource()){
					Resource cat=(Resource) stmt.getObject();
					
					String processedCategoryID=addLastDash(cat.getURI());
					
//					System.out.println(" * " + cat);
					DescriptorCategory existingCategory=
						       findCategoryByID(descriptorModel, processedCategoryID);
					
//					System.out.println("### found existing cat: " + existingCategory);
					
					if (existingCategory==null){
						//Should not happen
						System.out.println("Descriptor category: " + 
								processedCategoryID +" not found in already " +
							       "added categories. Skipping this category.");
					}
					else{
						
						//Add this descriptor to the category
						if (existingCategory.getDescriptors()==null){
							existingCategory.setDescriptors(
									new ArrayList<Descriptor>());
						}
						existingCategory.getDescriptors().add(desc);
						
						//Add this category to descriptor for bidirectional mapping
						if (desc.getCategories()==null){
							desc.setCategories(
									new ArrayList<DescriptorCategory>());
						}
						desc.getCategories().add(existingCategory);
						
//						System.out.println(" ## Added descriptor: " + desc.getId() + " to category: " + existingCategory.getId());

						
					}
					
				}
			}
			
		}			

		return descriptorModel;
	}


	@Deprecated
	private static DescriptorCategory findCategoryByID(DescriptorModel descriptorModel, String categoryID) {

		for (DescriptorCategory cat:  descriptorModel.getCategories()){
			if (cat.getId().equals(categoryID)){
				return cat;
			}
		}
		return null;
	}


	/**
	 * If not a # in namespace, add it
	 */
	@Deprecated
	public static String addLastDash(String input){
		int lastSlashIX=input.lastIndexOf('/');
		
		if (input.charAt(lastSlashIX+1)=='#') return input;

		String newstr=input.substring(0,lastSlashIX+1);
		newstr=newstr+"#";
		newstr=newstr+input.substring(lastSlashIX+1);
		
		return newstr;
	}

	@Deprecated
	public static void printStatements(Model m, Resource s, Property p, Resource o) {
		for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
			Statement stmt = i.nextStatement();
			System.out.println(" - " + PrintUtil.print(stmt));
		}
	}
	
	
}
