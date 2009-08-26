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
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.qsar.descriptor.model.DescriptorParameter;
import net.bioclipse.qsar.descriptor.model.DescriptorProvider;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.prefs.QsarPreferenceHelper;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;


public class QsarHelper {

    private static final Logger logger = Logger.getLogger(QsarHelper.class);


    /**
     * Read all descriptor providers and their implementations from EP.
     */
    public static List<DescriptorProvider> readProvidersAndDescriptorImplsfromEP() {

        List<DescriptorProvider> provlist=new ArrayList<DescriptorProvider>();

        //Initialize implementations via extension points
        IExtensionRegistry registry = Platform.getExtensionRegistry();

        if ( registry == null )
            throw new RuntimeException("Registry is null, no services can " +
            "be read. Workbench not started?");
        // it likely means that the Eclipse workbench has not
        // started, for example when running tests

        /*
         * service objects
         */
        IExtensionPoint serviceObjectExtensionPoint = registry
        .getExtensionPoint(QSARConstants.DESCRIPTOR_EXTENSION_POINT);

        IExtension[] serviceObjectExtensions
        = serviceObjectExtensionPoint.getExtensions();


        for(IExtension extension : serviceObjectExtensions) {
            for( IConfigurationElement element
                    : extension.getConfigurationElements() ) {

                if (element.getName().equals(QSARConstants.PROVIDER_ELEMENT_NAME)){

                    try {
                        String pid=element.getAttribute("id");
                        String pname=element.getAttribute("name");

                        DescriptorProvider provider=new DescriptorProvider(pid, pname);
                        String picon=element.getAttribute("icon");
                        provider.setIcon_path(picon);

                        String pshortname=element.getAttribute("shortName");
                        provider.setShortName(pshortname);

                        String pvendor=element.getAttribute("vendor");
                        provider.setVendor(pvendor);

                        String pvers=element.getAttribute("version");
                        provider.setVersion(pvers);

                        String pns=element.getAttribute("namespace");
                        provider.setNamespace(pns);

                        IDescriptorCalculator calculator;
                        calculator = (IDescriptorCalculator) 
                        element.createExecutableExtension("calculator");
                        provider.setCalculator(calculator);

                        String cml=element.getAttribute("acceptsCml");
                        if (cml!=null){
                            if (cml.equalsIgnoreCase("true")){
                                provider.setAcceptsCml(true);
                            }
                            else{
                                //If not explicitly true, then false
                                provider.setAcceptsCml(false);
                            }
                        }

                        String molfile=element.getAttribute("acceptsMolfile");
                        if (molfile!=null){
                            if (molfile.equalsIgnoreCase("true")){
                                provider.setAcceptsMolfile(true);
                            }
                            else{
                                //If not explicitly true, then false
                                provider.setAcceptsMolfile(false);
                            }
                        }

                        String smiles=element.getAttribute("acceptsSmiles");
                        if (smiles!=null){
                            if (smiles.equalsIgnoreCase("true")){
                                provider.setAcceptsSmiles(true);
                            }
                            else{
                                //If not explicitly true, then false
                                provider.setAcceptsSmiles(false);
                            }
                        }

                        //Get descriptor children
                        provider.setDescriptorImpls(new ArrayList<DescriptorImpl>());
                        for( IConfigurationElement providerChild
                                : element.getChildren(QSARConstants.DESCRIMPL_ELEMENT_NAME) ) {

                            String did=providerChild.getAttribute("id");
                            String dname=providerChild.getAttribute("name");

                            DescriptorImpl descImpl=new DescriptorImpl(did, dname);
                            String dicon=providerChild.getAttribute("icon");
                            descImpl.setIcon_path(dicon);

                            String ddef=providerChild.getAttribute("definition");
                            descImpl.setDefinition(ddef);
                            
                            String ddesc=providerChild.getAttribute("description");
                            descImpl.setDescription(ddesc);

                            String dns=element.getAttribute("namespace");
                            descImpl.setNamespace(dns);


                            String req3d=providerChild.getAttribute("requires3D");
                            if (req3d!=null){
                                if (req3d.equalsIgnoreCase("true")){
                                    descImpl.setRequires3D(true);
                                }
                                else{
                                    //If not explicitly true, then false
                                    descImpl.setRequires3D(false);
                                }
                            }

                            //                        String dcat=providerChild.getAttribute("category");
                            //                        DescriptorCategory foundcat=null;
                            //                        for (DescriptorCategory cat : getFullCategories()){
                            //                          if (cat.getId().equals(dcat)){
                            //                            foundcat=cat;
                            //                          }
                            //                        }
                            //                        if (foundcat!=null){
                            //                          desc.setCategory(foundcat);
                            //                        }else {
                            //                          logger.error("Descriptor category: " + dcat + 
                            //                          " for the descriptor: " + did + "could not be found");
                            //                        }

                            //Get descriptor children=parameters
                            List<DescriptorParameter> pparams=new ArrayList<DescriptorParameter>();
                            for( IConfigurationElement param
                                    : providerChild.getChildren(QSARConstants.PARAMETER_ELEMENT_NAME) ) {

                                String pakey=param.getAttribute("key");
                                String padef=param.getAttribute("defaultvalue");
                                DescriptorParameter dparam=new DescriptorParameter(pakey, padef);

                                String padescr=param.getAttribute("description");
                                dparam.setDescription(padescr);
                                
                                for( IConfigurationElement listedvalue
                                        : param.getChildren(QSARConstants.PARAMETER_LISTED_VALUES) ) {

                                    String val=listedvalue.getAttribute( "value" );
                                    dparam.addListedValue(val);
                                }

                                pparams.add(dparam);
                            }
                            if (pparams.size()>0)
                                descImpl.setParameters(pparams);



                            //Add parent provider to descriptor
                            descImpl.setProvider(provider);

                            provider.getDescriptorImpls().add(descImpl);
                            logger.debug("  Added descriptor impl: " + dname + " for provider: " + pshortname);

                        }

                        provlist.add(provider);
                        logger.debug("Finished adding descriptor provider: " + pname);

                    } catch (CoreException e) {
                        logger.error("Could not initialize EP. Reason: " + e.getMessage());
                        e.printStackTrace();
                    }



                }

            }
        }

        return provlist;
    }


    public static List<ResponseUnit> readUnitsFromEPAndPreferences() {

        List<ResponseUnit> responses=new ArrayList<ResponseUnit>();

        //Initialize implementations via extension points
        IExtensionRegistry registry = Platform.getExtensionRegistry();

        if ( registry == null )
            throw new RuntimeException("Registry is null, no services can " +
            "be read. Workbench not started?");
        // it likely means that the Eclipse workbench has not
        // started, for example when running tests

        /*
         * service objects
         */
        IExtensionPoint serviceObjectExtensionPoint = registry
        .getExtensionPoint(QSARConstants.RESPONSEUNITS_EXTENSION_POINT);

        IExtension[] serviceObjectExtensions
        = serviceObjectExtensionPoint.getExtensions();


        for(IExtension extension : serviceObjectExtensions) {
            for( IConfigurationElement element
                    : extension.getConfigurationElements() ) {

                if (element.getName().equals(QSARConstants.RESPONSEUNITS_ELEMENT_NAME)){

                    //The required values
                    String pid=element.getAttribute("id");
                    String pname=element.getAttribute("name");

                    ResponseUnit unit=new ResponseUnit(pid, pname);

                    String pshortname=element.getAttribute("shortname");
                    unit.setShortname(pshortname);

                    String purl=element.getAttribute("url");
                    unit.setUrl( purl);

                    String pdesc=element.getAttribute("description");
                    unit.setDescription( pdesc);

                    responses.add(unit);
                    logger.debug("Added response unit: " + unit);

                }

            }
        }
        
        //Ok, move on to preferences
        responses.addAll( QsarPreferenceHelper.getAvailableUnitsFromPrefs() );
        

        return responses;
    }


    public static void addDescriptorDefinitionsFromFiles( DescriptorModel model ) {

        //Get files
        List<URL> urls=QsarPreferenceHelper.getAvailableDescriptorDefinitionFilesFromPrefs();

        for (URL url : urls){
            try {
                model = OntologyHelper.addDescriptorHierarchy(model, url);
            } catch ( Exception e ) {
                logger.error("Error adding descriptors from url: " + url 
                             + ". Reason: " + e.getMessage());
            }
        }
        
    }

}
