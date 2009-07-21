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
package net.bioclipse.qsar.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DescriptorproviderType;
import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.DescriptorresultlistsType;
import net.bioclipse.qsar.DescriptorvalueType;
import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.MetadataType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.PreprocessingStepType;
import net.bioclipse.qsar.PreprocessingType;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ResponsesListType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.StructurelistType;
import net.bioclipse.qsar.TypeType;
import net.bioclipse.qsar.util.QsarAdapterFactory;
import net.bioclipse.qsar.util.QsarResourceFactoryImpl;
import net.sf.bibtexml.ArticleType;
import net.sf.bibtexml.BibTeXMLEntryType;
import net.sf.bibtexml.BibtexmlFactory;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

public class TestWrite {
	public static void main(String[] args) {

		System.out.println("start");

		QsarAdapterFactory factory=new QsarAdapterFactory();
		EditingDomain editingDomain=new AdapterFactoryEditingDomain(factory, new BasicCommandStack());

		//Create the super types
		DocumentRoot root=QsarFactory.eINSTANCE.createDocumentRoot();
		
		//Collect commands here
		CompoundCommand cCmd = new CompoundCommand();
		Command cmd;

		//Create editor type to hold everything else
		QsarType qsar=QsarFactory.eINSTANCE.createQsarType();

//		//could not get SetCommand to work for root element, add directly 
		root.setQsar(qsar);

		//Add QSAR root elements
		//======================
    MetadataType meta=QsarFactory.eINSTANCE.createMetadataType();
    meta.setDatasetname( "olas dataset" );
    meta.setAuthors( "Ola Spjuth" );
    meta.setDescription( "A dataset describing a lot of thins. Pretty much everything." );
    meta.setLicense( "Creative Blah License" );
    meta.setURL( "http://www.bioclipse.net" );
    cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__METADATA, meta);
    cCmd.append(cmd);

    //Add a narticle as reference in bibtexml
    BibTeXMLEntryType entry = BibtexmlFactory.eINSTANCE.createBibTeXMLEntryType();
    entry.setId( "article1" );
    
    ArticleType article=BibtexmlFactory.eINSTANCE.createArticleType();
    article.setAuthor( "Spjuth, Ola and Helmus, Tobias and Willighagen, Egon and Kuhn, Stefan and Eklund, Martin and Wagener, Johannes and Murray-Rust, Peter and Steinbeck, Christoph and Wikberg, Jarl" );
    article.setTitle( "Bioclipse: an open source workbench for chemo- and bioinformatics" );
    article.setJournal( "BMC Bioinformatics" );
    article.setVolume( "8" );

    try {
        DatatypeFactory dt = DatatypeFactory.newInstance();
        XMLGregorianCalendar cal = dt.newXMLGregorianCalendar();
        cal.setYear( 2007 );
        article.setYear( cal );
    } catch ( DatatypeConfigurationException e ) {
        e.printStackTrace();
        return;
    }
    article.setNumber( "1" );
    article.setPages( "59" );
    article.setDoi( "10.1186/1471-2105-8-59" );
    article.setUrl( "http://www.biomedcentral.com/1471-2105/8/59" );

    entry.setArticle( article );

    cmd=AddCommand.create(editingDomain, meta, QsarPackage.Literals.METADATA_TYPE__REFERENCE, entry);
    cCmd.append(cmd);

    //Add descriptor types
		DescriptorproviderType cdk=QsarFactory.eINSTANCE.createDescriptorproviderType();
		cdk.setId("cdk");
		cdk.setURL("http://cdk.sourceforge.net");
		cdk.setVendor("Chemistry Development Kit");
		cdk.setName("Chemistry Development Kit");
		cdk.setVersion("1.1.0.20080808");
		cmd=AddCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORPROVIDERS, cdk);
		cCmd.append(cmd);
		
		DescriptorproviderType dragon=QsarFactory.eINSTANCE.createDescriptorproviderType();
		dragon.setId("dragon");
		dragon.setURL( "http://talete.mi.it");
		dragon.setVendor("Talete");
		dragon.setName("Dragon descriptor");
		dragon.setVersion("5.14");
		cmd=AddCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORPROVIDERS, dragon);
		cCmd.append(cmd);

		StructurelistType structlist=QsarFactory.eINSTANCE.createStructurelistType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__STRUCTURELIST, structlist);
		cCmd.append(cmd);

		DescriptorlistType desclist=QsarFactory.eINSTANCE.createDescriptorlistType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORLIST, desclist);
		cCmd.append(cmd);

		PreprocessingType prelist=QsarFactory.eINSTANCE.createPreprocessingType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__PREPROCESSING, prelist);
		cCmd.append(cmd);
		
		ResponsesListType reslist=QsarFactory.eINSTANCE.createResponsesListType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__RESPONSELIST, reslist);
		cCmd.append(cmd);

		DescriptorresultlistsType descreslist=QsarFactory.eINSTANCE.createDescriptorresultlistsType();
    cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORRESULTLIST, descreslist);
    cCmd.append(cmd);


		//Add resources and structures
		//======================
		ResourceType res1=QsarFactory.eINSTANCE.createResourceType();
		res1.setId("mol1");
		res1.setName("polycarpol");
		res1.setFile("polycarpol.mol");
    res1.setType( TypeType.TEXT );
    res1.setChecksum( "N/A" );
		cmd=AddCommand.create(editingDomain, structlist, QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES, res1);
		cCmd.append(cmd);

		//Add structure for resource1
		StructureType struct1 = QsarFactory.eINSTANCE.createStructureType();
    struct1.setId( "struct1" );
    struct1.setResourceindex( 0 );
    struct1.setInchi( "N/A" );
    cmd=AddCommand.create(editingDomain, res1, QsarPackage.Literals.RESOURCE_TYPE__STRUCTURE, struct1);
    cCmd.append(cmd);


		ResourceType res2=QsarFactory.eINSTANCE.createResourceType();
		res2.setId("mol2");
		res2.setName("smallCollection");
		res2.setFile("smallCollection.sdf");
    res2.setType( TypeType.TEXT );
    res2.setChecksum( "N/A" );
    cmd=AddCommand.create(editingDomain, structlist, QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES, res2);
		cCmd.append(cmd);

		//Add structures for resource2
    StructureType struct2 = QsarFactory.eINSTANCE.createStructureType();
    struct2.setId( "struct2" );
    struct2.setResourceindex( 0 );
    struct2.setInchi( "N/A" );
    cmd=AddCommand.create(editingDomain, res2, QsarPackage.Literals.RESOURCE_TYPE__STRUCTURE, struct2);
    cCmd.append(cmd);


		ResourceType res3=QsarFactory.eINSTANCE.createResourceType();
		res3.setId("res3");
		res3.setName("reserpine");
		res3.setURL( "http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=5770&disopt=SaveXML");
		res3.setType( TypeType.XML );
    cmd=AddCommand.create(editingDomain, structlist, QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES, res3);
		cCmd.append(cmd);
		
    //Add structures for resource3
    StructureType struct3 = QsarFactory.eINSTANCE.createStructureType();
    struct3.setId( "struct3" );
    struct3.setResourceindex( 0 );
    struct3.setInchi( "N/A" );
    cmd=AddCommand.create(editingDomain, res3, QsarPackage.Literals.RESOURCE_TYPE__STRUCTURE, struct3);
    cCmd.append(cmd);


		//Add descriptors with parameters
		//======================
		//Desc1
		DescriptorType desc1=QsarFactory.eINSTANCE.createDescriptorType();
		desc1.setId( "descriptor1" );
		desc1.setOntologyid( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#xlogP");
		desc1.setProvider( cdk.getId() );
		cmd=AddCommand.create(editingDomain, desclist, QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS, desc1);
		cCmd.append(cmd);

    //Desc1 params
		ParameterType param1=QsarFactory.eINSTANCE.createParameterType();
		param1.setKey("checkAromaticity");
		param1.setValue("true");
		cmd=AddCommand.create(editingDomain, desc1, QsarPackage.Literals.DESCRIPTOR_TYPE__PARAMETER, param1);
		cCmd.append(cmd);
		ParameterType param2=QsarFactory.eINSTANCE.createParameterType();
		param2.setKey("salicylFlag");
		param2.setValue("false");
		cmd=AddCommand.create(editingDomain, desc1, QsarPackage.Literals.DESCRIPTOR_TYPE__PARAMETER, param2);
		cCmd.append(cmd);

		//Desc2
		DescriptorType desc2=QsarFactory.eINSTANCE.createDescriptorType();
		desc2.setId( "descriptor2" );
		desc2.setOntologyid("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#atomCount");
		desc2.setProvider( dragon.getId() );

    //Desc2 provider
//    cmd=SetCommand.create(editingDomain, desc2, QsarPackage.Literals.DESCRIPTOR_TYPE__PROVIDER,dragon);
//    cCmd.append(cmd);

    //Desc2 params
		ParameterType param3=QsarFactory.eINSTANCE.createParameterType();
		param3.setKey("elementName");
		param3.setValue("C");
		cmd=AddCommand.create(editingDomain, desc2, QsarPackage.Literals.DESCRIPTOR_TYPE__PARAMETER, param3);
		cCmd.append(cmd);

		//Desc2
		DescriptorType desc3=QsarFactory.eINSTANCE.createDescriptorType();
    desc3.setId( "descriptor3" );
		desc3.setOntologyid( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#atomCount");
		desc3.setProvider( cdk.getId() );
		cmd=AddCommand.create(editingDomain, desclist, QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS, desc3);
		cCmd.append(cmd);

		//Add preprocessing steps
		//=======================
		PreprocessingStepType calc3D=QsarFactory.eINSTANCE.createPreprocessingStepType();
		calc3D.setId("Smi23d");
		calc3D.setName("Generate 3D coordinates with smi23d");
		calc3D.setOrder("1");
		calc3D.setNamespace("http://www.chembiogrid.org/cheminfo/smi23d/");
		cmd=AddCommand.create(editingDomain, prelist, QsarPackage.Literals.PREPROCESSING_TYPE__PREPROCESSING_STEP, calc3D);
		cCmd.append(cmd);

		PreprocessingStepType att=QsarFactory.eINSTANCE.createPreprocessingStepType();
		att.setId("org.openscience.cdk.atomtype.sybyl");
		att.setName("Sybyl Atom Types");
		att.setOrder("2");
		att.setNamespace("http://cdk.sf.net");
		cmd=AddCommand.create(editingDomain, prelist, QsarPackage.Literals.PREPROCESSING_TYPE__PREPROCESSING_STEP, att);
		cCmd.append(cmd);
		
		//Add a response unit
		ResponseunitType unit1=QsarFactory.eINSTANCE.createResponseunitType();
		unit1.setId( "ic50" );
		unit1.setName( "half maximal inhibitory concentration (IC50)" );
		unit1.setShortname( "IC50" );
		unit1.setDescription( "Measure of the effectiveness of a compound in inhibiting biological or biochemical function");
		unit1.setURL( "http://en.wikipedia.org/wiki/IC50" );
    cmd=AddCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__RESPONSEUNIT, unit1);
    cCmd.append(cmd);

		//Add responses
		//=======================
		ResponseType response1=QsarFactory.eINSTANCE.createResponseType();
		response1.setStructureID( res1.getId());
		response1.setValue("11.45");
		response1.setUnit( unit1.getId() );
    //Add to responselist
    cmd=AddCommand.create(editingDomain, reslist, QsarPackage.Literals.RESPONSES_LIST_TYPE__RESPONSE, response1);
		cCmd.append(cmd);

		ResponseType response2=QsarFactory.eINSTANCE.createResponseType();
    response2.setStructureID( res2.getId());
		response2.setValue("15.45");
    response2.setUnit( unit1.getId() );
    //Add to responselist
		cmd=AddCommand.create(editingDomain, reslist, QsarPackage.Literals.RESPONSES_LIST_TYPE__RESPONSE, response2);
		cCmd.append(cmd);

		ResponseType response3=QsarFactory.eINSTANCE.createResponseType();
		response3.setStructureID( res3.getId());
		response3.setValue( "12.56,23.45,34.56");
    response3.setUnit( unit1.getId() );
    //Add to responselist
		cmd=AddCommand.create(editingDomain, reslist, QsarPackage.Literals.RESPONSES_LIST_TYPE__RESPONSE, response3);
		cCmd.append(cmd);

		
		//Add some bogus descriptor results
		DescriptorresultType descres1=QsarFactory.eINSTANCE.createDescriptorresultType();
		descres1.setDescriptorid( desc1.getId() );
		descres1.setStructureid( struct1.getId() );
    cmd=AddCommand.create(editingDomain, descreslist, QsarPackage.Literals.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT, descres1);
    cCmd.append(cmd);
		
		DescriptorvalueType dvalue1=QsarFactory.eINSTANCE.createDescriptorvalueType();
		dvalue1.setIndex( 0 );
    dvalue1.setLabel( "desc1_col1" );
    dvalue1.setValue( "19.564" );
    cmd=AddCommand.create(editingDomain, descres1, QsarPackage.Literals.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE, dvalue1);
    cCmd.append(cmd);

    DescriptorvalueType dvalue2=QsarFactory.eINSTANCE.createDescriptorvalueType();
    dvalue2.setIndex( 1 );
    dvalue2.setLabel( "desc1_col2" );
    dvalue2.setValue( "76.2" );
    cmd=AddCommand.create(editingDomain, descres1, QsarPackage.Literals.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE, dvalue2);
    cCmd.append(cmd);

    DescriptorresultType descres2=QsarFactory.eINSTANCE.createDescriptorresultType();
    descres2.setDescriptorid( desc2.getId() );
    descres2.setStructureid( struct1.getId() );
    cmd=AddCommand.create(editingDomain, descreslist, QsarPackage.Literals.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT, descres2);
    cCmd.append(cmd);
    
    DescriptorvalueType dvalue12=QsarFactory.eINSTANCE.createDescriptorvalueType();
    dvalue12.setIndex( 0 );
    dvalue12.setLabel( "desc2label" );
    dvalue12.setValue( "19.564" );
    cmd=AddCommand.create(editingDomain, descres2, QsarPackage.Literals.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE, dvalue12);
    cCmd.append(cmd);

    
		//Execute the CompoundCommand
		editingDomain.getCommandStack().execute(cCmd); 		

//		for (StructureResourceType mol : qsar.getMoleculelist().getMoleculeResource()){
//			System.out.println("mol: " + mol.getName());
//		}
		
		
		//Debug out
		serializeToSTDOUT(root);
		
	}

	private static void serializeToSTDOUT(DocumentRoot root) {
		ResourceSet resourceSet=new ResourceSetImpl();
		URI fileURI;
		try {
			fileURI = URI.createFileURI(new File("myQSAR2.xml").getAbsolutePath());
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml", new QsarResourceFactoryImpl());

			Resource resource=resourceSet.createResource(fileURI);
			resource.getContents().add(root);

			Map opts=new HashMap();
			opts.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			opts.put(XMLResource.OPTION_ENCODING, "UTF-8");
			
			//Save to file
			resource.save(opts);

			//Serialize to byte[] and print to sysout
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			resource.save(os, opts);
			System.out.println(new String(os.toByteArray()));

			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
