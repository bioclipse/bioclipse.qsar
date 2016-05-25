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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.DescriptorResult;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * 
 * @author ola
 *
 */
public class CdkRESTDescriptorCalculator implements IDescriptorCalculator {
    
    private static final String REST_PROVIDER_ID="net.bioclipse.cdk.rest.descriptorprovider";
//    private static final String BASE_URL="http://toposome.chemistry.drexel.edu:6666/cdk/descriptor/";
    private static final String BASE_URL="http://ws1.bmc.uu.se:8182/cdk/descriptor/";
    
        
    private static final Logger logger = Logger.getLogger(CdkRESTDescriptorCalculator.class);

    ICDKManager cdk;

    public CdkRESTDescriptorCalculator() {
        cdk=Activator.getDefault().getJavaCDKManager();
    }

   public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
             Map<IMolecule, List<DescriptorType>> moldesc,
             IProgressMonitor monitor ) throws BioclipseException {

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();

        IQsarManager qsar = net.bioclipse.qsar.init.Activator
        .getDefault().getJavaQsarManager();
        
        //The workload for this provider is mols x their descs
        int workload=0;
        for (IMolecule mol : moldesc.keySet()){
            workload=workload+moldesc.get( mol ).size();
        }
        
        monitor.beginTask( "Calculating descriptors with CDK REST" , workload );

        //Verify REST server before processing molecules
        try {
            verifyRestServer();
        } catch ( Exception e ) {
            throw new BioclipseException("Could not contact rest server: " 
                                         + BASE_URL);
        }
        
        int molindex=1;
        int molSize=moldesc.keySet().size();
        //For each molecule
        for (IMolecule mol : moldesc.keySet()){
            try {
                
                //We need the SMILES for the REST descriptors
            	ICDKMolecule cdkMol = cdk.asCDKMolecule(mol);
            	IAtomContainer atomContainer = cdkMol.getAtomContainer();
                // TODO Whitch generator to use see:
                // https://github.com/cdk/cdk/wiki/1.5.4-Release-Notes#smipar
                SmilesGenerator generator = SmilesGenerator.generic();
                IAtomContainer molecule = atomContainer;
                String smiles = null;
                try {
                    smiles = generator.create( molecule );
                } catch ( CDKException ex ) {
                    logger.error( "Could not generate SMILES for mol: " + mol
                                  + ". Returning ERROR for all descriptors for this mol.", ex );
                }
                
                if ( smiles == null || smiles.equals( "" ) ) {
                    logger.error( "Could not generate SMILES for mol: " + mol
                                  + ". Returning ERROR for all descriptors for this mol." );
                }
                List<IDescriptorResult> retlist=
                                             new ArrayList<IDescriptorResult>();


                for (DescriptorType desc : moldesc.get( mol )){

                	if (smiles==null || smiles.equals("")){
                		//We have an error, unable to create SMILES ande hence cannot use REST
            			IDescriptorResult res = new DescriptorResult();
            			res.setDescriptor(desc);
            			res.setErrorMessage("Could not generate SMILES for molecule.");
            			retlist.add (res);
                	}
                	else{
                		//Call REST for this SMILES

                		DescriptorImpl dimpl = qsar.getDescriptorImpl( 
                				desc.getOntologyid(), REST_PROVIDER_ID );

                		//descriptor class
                		String descid=dimpl.getId();

                		//We need to remove .rest to get classname
                		String classname=descid.replaceAll( ".rest", "" );

                		if (monitor.isCanceled())
                			throw new OperationCanceledException();

                		monitor.subTask( "Molecule " + molindex + "/" + molSize + "\nCDK REST Descriptor:"
                				+ dimpl.getName());

                		//Call REST service
                		String retxml=runRest(classname, smiles);

                		try {
                			IDescriptorResult res = parseResultingXML(retxml, desc, classname);
                			retlist.add (res);
                		} catch ( Exception e ) {
                			logger.error("Problems parsing values from CDK REST:\n"
                					+ retxml);
                		}

                		monitor.worked( 1 );

                	}


                    //Go get next descriptor
                }
                
                //Store results for this mol
                allResults.put(mol, retlist);


            } catch (BioclipseException e) {
                logger.error("Unable to create CDKMolecule from IMolecule. " +
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


    private void verifyRestServer() throws MalformedURLException, IOException {

        String url=BASE_URL;
        
        BufferedReader r = new BufferedReader(
                           new InputStreamReader(
                           new URL(url).openStream()));

        String line=r.readLine();
        StringBuffer buffer=new StringBuffer();
        while ( line!=null ) {
            buffer.append( line );
            line=r.readLine();
        }
    
}

    private String runRest( String classname, String smiles ) throws 
                                            MalformedURLException, IOException {
    	
    	String escapedSmiles=escapeSMILES(smiles);

        String url=BASE_URL+classname +"/" + escapedSmiles;
        
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


    /**
     * % must be excaped as %25 in URLs.
     * See http://www.december.com/html/spec/esccodes.html
     * @param smiles SMILES to be escaped.
     * @return escaped SMILES
     */
    private String escapeSMILES(String smiles) {
    	smiles=smiles.replace("%", "%25");
    	smiles=smiles.replace("[", "%5B");
    	smiles=smiles.replace("]", "%5D");
    	smiles=smiles.replace("#", "%23");
    	smiles=smiles.replace("@", "%40");
    	smiles=smiles.replace(":", "%3A");
    	smiles=smiles.replace("+", "%2B");
    	smiles=smiles.replace("=", "%3D");

    	return smiles;
    }

	private IDescriptorResult parseResultingXML( String retxml, 
                                                 DescriptorType desc, 
                                                 String classname ) 
                       throws ValidityException, ParsingException, IOException {

        ByteArrayInputStream bis=new ByteArrayInputStream(retxml.getBytes());
        Builder parser = new Builder();
        Document doc = parser.build(bis);

        Element root = doc.getRootElement();
        assert("DescriptorList".equals( root.getQualifiedName()));

        //Store results in this, to return
        IDescriptorResult descriptorResult=new DescriptorResult();
        descriptorResult.setDescriptor( desc );
        List<String> labels=new ArrayList<String>();
        List<Float> values=new ArrayList<Float>();


        for (int i = 0; i < root.getChildCount(); i++) {
            Node child = root.getChild(i);
            if ( child instanceof Element ) {
                Element childelement = (Element) child;
                
                assert("Descriptor".equals( childelement.getQualifiedName()));
                assert(childelement.getAttributeCount()==3);

                String rParent=childelement.getAttributeValue( "parent" );
                String rname = childelement.getAttributeValue( "name" );
                String rval = childelement.getAttributeValue( "value" );

                if (rParent!=null && rname!=null && rval!=null){

                    //Confirm the same descriptor we asked for
                    if (classname.endsWith( rParent)){
                        //All is well
                        labels.add(rname);

                        //Parse value
                        if (rval.equalsIgnoreCase( "false" ))
                            values.add(new Float(0));
                        else if (rval.equalsIgnoreCase( "true" ))
                            values.add(new Float(1));
                        else{
                            //Try to parse as float
                            try{
                                Float valueToAdd=Float.parseFloat( rval );
                                values.add(valueToAdd);
                            }catch(NumberFormatException e){
                                //Not a float. Cannot handle this
                                descriptorResult.setErrorMessage( "Could not " +
                                                 "parse result value: " + rval);
                            }
                        }
                        
                    }else{
                        logger.error("Expected results from descriptor: " 
                             + desc.getId() + " but got results " +
                             "from descriptor: " 
                             + childelement.getQualifiedName());
                        descriptorResult.setErrorMessage( "Expected results " +
                        		"from descriptor: " 
                             + desc.getId() + " but got results " +
                             "from descriptor: " 
                             + childelement.getQualifiedName());                    

                    }
                    
                    
                }else{
                    logger.error("Parsed values were null!");
                    descriptorResult.setErrorMessage(
                                                    "Parsed values were null!");
                }
                
            }
        }            
        descriptorResult.setLabels( labels.toArray( new String[0] ) );
        descriptorResult.setValues( values.toArray( new Float[0] ) );

        return descriptorResult;
    }

}
