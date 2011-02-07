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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IStringMatrix;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.rdf.Activator;
import net.bioclipse.rdf.business.IRDFManager;
import net.bioclipse.rdf.business.IRDFStore;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;


public class OntologyHelper {

    private static final Logger logger = Logger.getLogger(OntologyHelper.class);

    /**
     * Parse OWL using RDF plugin and build descriptor hierarchy
     * @return
     * @throws CoreException 
     * @throws BioclipseException 
     * @throws IOException 
     */
    public static DescriptorModel buildDescriptorHierarchy() throws IOException, BioclipseException, CoreException{

        //The new model
        DescriptorModel descriptorModel=new DescriptorModel();

        //Use hardcoded owl
        String owlFile="/ontology/descriptor-algorithms.owl";
        Bundle bundle = Platform.getBundle(net.bioclipse.qsar.init.Activator.PLUGIN_ID); 

        URL url=bundle.getEntry(owlFile);
        //        System.out.println("URL: " + url);
        URL furl=FileLocator.toFileURL(url);
        logger.debug("BODOntology as fileURL: " + furl);

        return addDescriptorHierarchy(descriptorModel, furl);
    }

    public static DescriptorModel addDescriptorHierarchy(
        DescriptorModel descriptorModel, URL url) throws
        IOException, BioclipseException, CoreException {

        //We need a new list of descriptor categories
        List<DescriptorCategory> categories = descriptorModel.getCategories();
        if (categories == null)
            categories = new ArrayList<DescriptorCategory>();

        IRDFManager rdf=Activator.getDefault().getJavaManager();
        IRDFStore owl = rdf.createInMemoryStore();

        rdf.importURL(owl, url.toString());

        // list all descriptor categories
        IStringMatrix cats = rdf.sparql(owl,
          "PREFIX qsar: <http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#> " +
          "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
          "PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
          "SELECT ?id ?label ?definition ?date WHERE { " +
          "  ?id qsar:isClassifiedAs qsar:descriptorCategories; " +
          "      rdfs:label ?label; " +
          "      qsar:definition ?definition; " +
          "      dc:date ?date. " +
          "}"
        );
        // iterate over all categories child of the category 'descriptorCategories'
        for (int i=1; i<=cats.getRowCount(); i++) {
            // the next follows the ?id ?label ?definition order in the SPARQL
            String identifier = cats.get(i, "id");
            String label = cats.get(i, "label");
            String definition = cats.get(i, "definition");
            String date = cats.get(i, "date");

            //Remove the starting qsar:
            identifier=identifier.substring( 5 );
            identifier=QSARConstants.BO_NAMESPACE + identifier;

            //Create model object and store in list
            DescriptorCategory dcat=new DescriptorCategory(identifier, label);
            dcat.setDate(date);
            categories.add( dcat );
        }

        // because we may be adding from a new OWL file, we have to also
        // now earlier added categories, so we reiterate
        for (DescriptorCategory dcat : categories) {
            String identifier = dcat.getId();
            // list of descriptors for this category; it takes advantage from the fact
            // that the identifier is namespace prefix, and we use the same here
            String sparql =
                "PREFIX qsar: <http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#> " +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "SELECT ?s ?label ?date ?definition WHERE { " +

// FIXME: Why does not the below work? See bug 1933.
// "SELECT ?s ?label ?date ?definition ?description WHERE { " +

                "  ?s ?p <" + identifier + ">;" +
                "     qsar:definition ?definition; " +
//                "     qsar:description ?description; " +
                "     dc:date ?date; " +
                "     rdfs:label ?label." +
                "}";
            IStringMatrix descriptors = rdf.sparql(owl, sparql);
            if (descriptors != null) {
                for (int j=1; j<descriptors.getRowCount(); j++) {
                    String descriptorID = descriptors.get(j, "s");
                    String label = descriptors.get(j, "label");
                    String date = descriptors.get(j, "date");
                    String definition = descriptors.get(j, "definition");
//                    String description = descriptor.get(4).trim();
                    
                    if (definition.indexOf("^^")>0)
                    	definition=definition.substring(0,definition.indexOf("^^"));
//                    if (description.indexOf("^^")>0)
//                    	description=description.substring(0,description.indexOf("^^"));
//                    logger.debug("  " + descriptorID + " = " + label + "\n");
                    
                    //Remove the starting qsar:
                    descriptorID=descriptorID.substring( 5 );
                    
                    Descriptor desc = new Descriptor(QSARConstants.BO_NAMESPACE + descriptorID,label);
                    desc.setNamespace(QSARConstants.BO_NAMESPACE);
                    desc.addCategory(dcat);
                    desc.setDate(date);
                    desc.setDefinition( definition);
//                    desc.setDescription(description);
                    
                    dcat.addDescriptor(desc);
                    
                }
            }
        }

        descriptorModel.setCategories(categories);

        return descriptorModel;
    }


}
