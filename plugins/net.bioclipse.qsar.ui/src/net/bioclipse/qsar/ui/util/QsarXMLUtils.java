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
package net.bioclipse.qsar.ui.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.MetadataType;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.StructurelistType;
import net.bioclipse.qsar.util.QsarAdapterFactory;
import net.bioclipse.qsar.util.QsarResourceFactoryImpl;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

public class QsarXMLUtils {

	public static byte[] getEmptyContent(){

		//Create empty model
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

		//Add metadata
    MetadataType meta=QsarFactory.eINSTANCE.createMetadataType();
    cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__METADATA, meta);
    cCmd.append(cmd);

    //Add structurelist
		StructurelistType structlist=QsarFactory.eINSTANCE.createStructurelistType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__STRUCTURELIST, structlist);
		cCmd.append(cmd);

    //Add descriptorlist
		DescriptorlistType desclist=QsarFactory.eINSTANCE.createDescriptorlistType();
		cmd=SetCommand.create(editingDomain, qsar, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORLIST, desclist);
		cCmd.append(cmd);

		//Execute the CompoundCommand
		editingDomain.getCommandStack().execute(cCmd); 		


		//Serialize to byte[]
		ResourceSet resourceSet=new ResourceSetImpl();

		try {
			URI fileURI = URI.createFileURI(new File("myQSAR.xml").getAbsolutePath());
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml", new QsarResourceFactoryImpl());

			Resource resource=resourceSet.createResource(fileURI);
			resource.getContents().add(root);

			//Serialize to byte[] and print to sysout
			ByteArrayOutputStream os=new ByteArrayOutputStream();

			Map opts=new HashMap();
	      opts.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
	      opts.put(XMLResource.OPTION_ENCODING, "UTF-8");

			resource.save(os, opts);

			return os.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
