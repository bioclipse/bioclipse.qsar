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
package net.bioclipse.cdk.qsar.xmpp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.base.CMLElements;
import org.xmlcml.cml.element.CMLProperty;
import org.xmlcml.cml.element.CMLScalar;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cml.managers.IJavaValidateCMLManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.descriptor.DescriptorResult;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.xws.client.adhoc.IFunction;
import net.bioclipse.xws.client.adhoc.IService;
import net.bioclipse.xws.exceptions.XmppException;
import net.bioclipse.xws.exceptions.XwsException;
import net.bioclipse.xws4j.business.IXwsManager;
import net.bioclipse.xws4j.exceptions.Xws4jException;
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
public class CdkXMPPDescriptorCalculator implements IDescriptorCalculator {
    
    private static final Logger logger = Logger.getLogger(CdkXMPPDescriptorCalculator.class);

    private static final String XMPP_SERVICE_NAME = "descriptor.ws1.bmc.uu.se";

    private ICDKManager cdk;
    private IXwsManager xmpp;
    private IJavaValidateCMLManager cml;

    private Map<String, String> ontologyMap;
    
    public CdkXMPPDescriptorCalculator() {
        cdk=Activator.getDefault().getJavaCDKManager();
        initialize();
    }


    /**
     * Set up list from ontology ID to XMPP function
     */
    private void initialize() {

        ontologyMap=new HashMap<String, String>();
        ontologyMap.put( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT", "BCUT" );
        ontologyMap.put( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#lipinskifailures", "LipinskiRuleOfFive" );
        ontologyMap.put( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#tpsa", "TPSA" );
        ontologyMap.put( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#xlogP", "XLOGP" );

    }

    /**
     * Calculate descriptors using XMPP services. Requires a valid Jabber 
     * account properly set up in Bioclipse preferences.
     * @throws BioclipseException 
     */
   public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
             Map<IMolecule, List<DescriptorType>> moldesc,
             IProgressMonitor monitor ) throws BioclipseException {

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();

        cml = net.bioclipse.cml.managers.Activator
                .getDefault().getJavaManager();

        xmpp = net.bioclipse.xws4j.Activator
                .getDefault().getXwsManager();

        IService service=null;
        
        // connect to the XMPP hive
            try {
                xmpp.connect();
            } catch ( Exception e ) {
                throw new BioclipseException("Could not connect to XMPP network.");
            }

            // we are going to call the TPSA function of the descriptor service
            try{
                service = xmpp.getService(XMPP_SERVICE_NAME);
                service.discoverSync(10000);
                service.getFunctions();
            } catch ( Exception e ) {
                throw new BioclipseException("Could not connect to service: " 
                                             + XMPP_SERVICE_NAME);
            }

        //OK, we are connected.
        //For each molecule, call XMPP function
        int molindex=1;
        for (IMolecule mol : moldesc.keySet()){
            try {
                
                //We need the SMILES for the REST descriptors
                String molCML=cdk.asCDKMolecule( mol ).toCML();
                
                List<IDescriptorResult> retlist=
                                             new ArrayList<IDescriptorResult>();

                for (DescriptorType desc : moldesc.get( mol )){
                    
                    String ontologyID=desc.getOntologyid();
                    
                    //Look up function name in map
                    String xmppFunction=ontologyMap.get(ontologyID);
                    if (xmppFunction==null)
                        throw new BioclipseException("Descriptor: " + ontologyID 
                             + " does not have a corresponding XMPP function.");
                    
                    //Call XMPP service
                    try {
                    IDescriptorResult res=invokeXMPP(xmppFunction, molCML, service, desc);
                    retlist.add (res);
                    } catch ( Exception e ) {
                        logger.error("Problems invoking XMPP function: " 
                                     + xmppFunction 
                                     + " for descriptor " + ontologyID + "\n" + e.getMessage());
                        
                    }
                    
                    monitor.worked( 1 );

                    //Go get next descriptor
                }
                
                //Store results for this mol
                allResults.put(mol, retlist);


            } catch (BioclipseException e) {
                logger.error("Unable to create CDKMolecule from Imolecule. " +
                "Skipping descriptor calculation for this mol.");
                LogUtils.debugTrace(logger, e);
            }

            molindex++;
            //Get next molecule
        }

        return allResults;
    }


    private IDescriptorResult invokeXMPP( String xmppFunction, String molCML, IService service, DescriptorType desc ) throws XmppException, XwsException, InterruptedException, BioclipseException {

        IFunction f = service.getFunction(xmppFunction);

        logger.debug("Invoking XMPP service: " + service.getJid() +" for function: " + xmppFunction);
        org.w3c.dom.Element result = f.invokeSync(molCML, 10000);
        String cmlReturned = xmpp.toString(result);
         
        // convert the returned CML into CMLXOM
        CMLElement propertyList = cml.fromString(cmlReturned);
        
        
        for (CMLElement ele : propertyList.getChildCMLElements()){
            CMLProperty property =(CMLProperty)ele;
            
            for (CMLScalar cmle : property.getScalarElements()){
                String name=cmle.getDictRef();
                String val=cmle.getValue();
                
                System.out.println("XMPP result: " + name + " = " + val);
            }

            
        }
        
        //TODO: We need to put the results in an IDescriptorResult.

        
        
        /*
        
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
        
        */

        return null;
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
