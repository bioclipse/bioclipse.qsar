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
package net.bioclipse.cdk.qsar.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.business.QsarManager;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;

/**
 * 
 * @author ola
 *
 */
public class CDKDescriptorCalculator implements IDescriptorCalculator {
    
    private static final String REST_PROVIDER_ID="net.bioclipse.cdk.rest.descriptorprovider";
    private static final String BASE_URL="http://toposome.chemistry.drexel.edu:6666/cdk/descriptor/";

    private static final Logger logger = Logger.getLogger(QsarManager.class);

    ICDKManager cdk;

    public CDKDescriptorCalculator() {
        cdk=Activator.getDefault().getJavaCDKManager();
    }


    public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
             Map<IMolecule, List<DescriptorType>> moldesc,
             IProgressMonitor monitor ) {

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();

        IQsarManager qsar = net.bioclipse.qsar.init.Activator
        .getDefault().getQsarManager();

        int molindex=1;
        //For each molecule
        for (IMolecule mol : moldesc.keySet()){
            try {
                
                //We need the SMILES for the REST descriptors
                String smiles=cdk.calculateSMILES( mol );
                
                for (DescriptorType desc : moldesc.get( mol )){
                    
                    
                    DescriptorImpl dimpl = qsar.getDescriptorImpl( 
                                       desc.getOntologyid(), REST_PROVIDER_ID );
                    
                    //descriptor class
                    String descid=dimpl.getId();
                    
                    //We need to remove .rest to get classname
                    String classname=descid.replaceAll( ".rest", "" );
                    
                    monitor.subTask( "Callin CDK REST descriptor: " 
                                     + dimpl.getName() + " for molecule " 
                                     + molindex );
                    
                    //Call REST service
                    String retxml=runRest(classname, smiles);
                    
                    List<IDescriptorResult> retlist=parseResultingXML(retxml);

                    //Store results for this mol
                    allResults.put(mol, retlist);
                    
                    monitor.worked( 1 );

                    //Go get next descriptor
                }

            } catch (BioclipseException e) {
                logger.error("Unable to create CDKMolecule from Imolecule. " +
                "Skipping descriptor calculation for this mol.");
                LogUtils.debugTrace(logger, e);
            } catch ( MalformedURLException e ) {
                LogUtils.debugTrace(logger, e);
            } catch ( IOException e ) {
                LogUtils.debugTrace(logger, e);
            }

            molindex++;
            //Get next molecule
        }

        return allResults;
    }


    private String runRest( String classname, String smiles ) throws MalformedURLException, IOException {

        String url=BASE_URL+classname +"/" + smiles;
        
        logger.debug("Calling URL: " + url);
        
        BufferedReader r = new BufferedReader(
                           new InputStreamReader(
                           new URL(url).openStream()));

        String line=r.readLine();
        StringBuffer buffer=new StringBuffer();
        while ( line!=null ) {
            buffer.append( line );
            line=r.readLine();
        }

        logger.debug("Result from REST service: \n" + buffer.toString());

        return buffer.toString();
    }

    
    private List<IDescriptorResult> parseResultingXML( String retxml ) {

        // TODO Auto-generated method stub
        return null;
    }



    
    
    
    
}
