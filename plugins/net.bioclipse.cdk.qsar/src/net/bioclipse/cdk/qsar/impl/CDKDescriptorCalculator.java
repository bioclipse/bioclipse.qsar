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
package net.bioclipse.cdk.qsar.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.NoSuchAtomException;
import org.openscience.cdk.graph.SpanningTree;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.qsar.DescriptorEngine;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IDescriptor;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IntegerArrayResult;
import org.openscience.cdk.qsar.result.IntegerResult;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.api.BioclipseException;
import net.bioclipse.core.api.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.DescriptorResult;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;

public class CDKDescriptorCalculator implements IDescriptorCalculator {

    private static final Logger logger = Logger.getLogger(CDKDescriptorCalculator.class);

    ICDKManager cdk;
    private DescriptorEngine engine = null;
    private Map<String, IMolecularDescriptor> descriptorInstances;
    IQsarManager qsar;

    public CDKDescriptorCalculator() {
        cdk=Activator.getDefault().getJavaCDKManager();
        qsar=net.bioclipse.qsar.init.Activator.getDefault().getJavaQsarManager();
    }

    public Map<String, IMolecularDescriptor> getDescriptorMap() {
        if (descriptorInstances == null){ 
            getEngine();
            descriptorInstances = new HashMap<String, IMolecularDescriptor>();

            List<IDescriptor> lst = engine.getDescriptorInstances();

            for (IDescriptor descriptor : lst) {
                IMolecularDescriptor mdesc=(IMolecularDescriptor)descriptor;
                descriptorInstances.put(mdesc.getSpecification().getSpecificationReference(), mdesc);
            }
        }

        return descriptorInstances;
    }

    private void getEngine() {
        if (engine == null) {
            List<String> descriptors = new ArrayList<String>();


            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ZagrebIndexDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.WienerNumbersDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.WHIMDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.WeightedPathDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.VAdjMaDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.TPSADescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.RuleOfFiveDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.RotatableBondsCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.PetitjeanShapeIndexDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.PetitjeanNumberDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.MomentOfInertiaDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.MDEDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.LongestAliphaticChainDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.LengthOverBreadthDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.LargestPiSystemDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.LargestChainDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.KierHallSmartsDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.KappaShapeIndicesDescriptor");

            //Why is this not added?
            //			descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.IPMolecularLearningDescriptor");

            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.HBondDonorCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.HBondAcceptorCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.GravitationalIndexDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.FragmentComplexityDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.EccentricConnectivityIndexDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.CPSADescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ChiPathDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ChiPathClusterDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ChiClusterDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ChiChainDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.CarbonTypesDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.BondCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.BCUTDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AutocorrelationDescriptorPolarizability");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AutocorrelationDescriptorMass");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AutocorrelationDescriptorCharge");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AtomCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AromaticBondsCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.AromaticAtomsCountDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.APolDescriptor");
            descriptors.add("org.openscience.cdk.qsar.descriptors.molecular.ALOGPDescriptor");

            engine  = new DescriptorEngine(descriptors);
            engine.getDescriptorInstances();
        }
    }









    public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
                                                                                 List<? extends IMolecule> molecules, 
                                                                                 List<DescriptorType> descriptorTypes, 
                                                                                 IProgressMonitor monitor) throws OperationCanceledException {

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();

        for (IMolecule mol : molecules){
            try {
                ICDKMolecule cdkmol=cdk.asCDKMolecule(mol);

                List<IDescriptorResult> retlist = doCalculate(cdkmol, descriptorTypes, monitor);
                allResults.put(mol, retlist);

            } catch (BioclipseException e) {
                logger.error("Unable to create CDKMolecule from Imolecule. " +
                "Skipping descriptor calculation for this mol.");
                LogUtils.debugTrace(logger, e);
            }

        }

        return allResults;
    }


    /**
     * Calculate descriptors for a single molecule
     * @param monitor 
     * @param mol Input molecule
     * @param descriptorInstances2 List<String> of descriptor IDs
     * @return IDescriptorResult with results of the calculation
     * @throws BioclipseException 
     */
    private List<IDescriptorResult> doCalculate(ICDKMolecule cdkmol, 
                                                List<DescriptorType> descriptorTypes, 
                                                IProgressMonitor monitor)  throws OperationCanceledException{

        //Get atomcontainer from IMolecule
        IAtomContainer container=cdkmol.getAtomContainer();

        //Make sure e have a Molecule, otherwise create it
        if (!(container instanceof org.openscience.cdk.interfaces.IMolecule))
            container = container.getBuilder().newInstance(
                     org.openscience.cdk.interfaces.IMolecule.class, container);

        //Store results here
        List<IDescriptorResult> results = new ArrayList<IDescriptorResult>();

        //Preprocess atomcontainer. Return null if all is well, else String with error
        //========================
        String atomcontainerError=doCDKPreprocessing(container);

        //Loop over all descriptors
        //=========================
        for (DescriptorType descType : descriptorTypes){

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

//            logger.debug("CDK calculating descriptor: " + descType.getOntologyid() + " for mol: " + cdkmol.getName());
            monitor.subTask("Molecule: " + cdkmol.getName() 
                            + "\nCDK Descriptor: " + descType.getOntologyid());
            monitor.worked(1);


            //This is where we store the result
            DescriptorResult res=new DescriptorResult();
            res.setDescriptor( descType );

            //Get descriptor by id
            IMolecularDescriptor cdkDescriptor = getDescriptorMap().get(descType.getOntologyid());

            //If we had errors in atomcontainer, add error here
            if (atomcontainerError!=null){
                res.setErrorMessage(atomcontainerError);
                logger.debug("Error message set to: " + res.getErrorMessage());
            }else if (cdkDescriptor==null){
                res.setErrorMessage("Descriptor not supported in cdk: " + descType.getOntologyid());
                logger.debug("Error message set to: " + res.getErrorMessage());
            }

            //We have a valid descriptorID and a good atomcontainer, do calculation
            else{

                //Do we have parameters available for this descriptorID in Map?
                if (descType.getParameter()!=null && descType.getParameter().size()>0){
//                    logger.debug("We have: " + descType.getParameter().size() 
//                                 + " params available.");

                    //OK, get it and set it to descriptor
                    EList<ParameterType> bcParams = descType.getParameter();

                    //Check that the CDK descriptor accepts parameters
                    if (cdkDescriptor.getParameterNames()==null && cdkDescriptor
                            .getParameterNames().length<=0){

                        logger.warn("Trying to set params for descrImpl: " + 
                                     descType.getOntologyid() + " but corresponding " +
                        "CDKDescriptor does not accept any params.");

                    }
                    else{
                        //These are the ones we should fill
                        Object[] cdkParams=cdkDescriptor.getParameters();

                        for (int i=0; i< cdkParams.length; i++){
                            String cdkName=cdkDescriptor.getParameterNames()[i];
//                            logger.debug("CDK descr accepts param: " + cdkName);

                            if (cdkName.equalsIgnoreCase("checkAromaticity")){
//                                logger.debug("Aromaticity detection set as false, " +
//                                "as this is already done");
                                cdkParams[i]=false;
                            }

                            else{
                                for (ParameterType param : bcParams){
                                    if (param.getKey().equals(cdkName)){

                                        //Match, convert from String to data type
                                        Object obj=cdkDescriptor.getParameterType(cdkName);

                                        //Integer
                                        if (obj instanceof Integer) {
                                            try{
                                                Integer ival=Integer.parseInt(param.getValue());
                                                cdkParams[i]=ival;
//                                                logger.debug("   - Set param: " + 
//                                                             cdkName + " to " + param.getValue());
                                            }catch (NumberFormatException e){
                                                logger.error("   - expected an Integer " +
                                                             "param for " + cdkName + 
                                                             " but found: "  + 
                                                             param.getValue());
                                            }
                                        }

                                        //Boolean
                                        else if (obj instanceof Boolean) {
                                            //Do conversion from String to Boolean
                                            if (param.getValue().equalsIgnoreCase("true")){
                                                cdkParams[i]=true;
//                                                logger.debug("   - Set param: " + 
//                                                             cdkName + " to " + param.getValue());
                                            }
                                            else if (param.getValue().equalsIgnoreCase("false")){
                                                cdkParams[i]=false;
//                                                logger.debug("   - Set param: " + 
//                                                             cdkName + " to " + param.getValue());
                                            }
                                            else{
                                                logger.error("   - expected a boolean " +
                                                             "param for " + cdkName + 
                                                             " but found: "  + 
                                                             param.getValue());
                                            }
                                        }

                                        //String
                                        else if (obj instanceof String) {
                                            cdkParams[i]=param.getValue();
//                                            logger.debug("   - Set param: " + 
//                                                         cdkName + " to " + param.getValue());
                                        }
                                    }
                                }
                            }
                        }
                        try {
                            cdkDescriptor.setParameters(cdkParams);
                        } catch (CDKException e) {
                            e.printStackTrace();
                        }

                    }

                }


                //Number of return values
                Float[] resultVals = new Float[cdkDescriptor.getDescriptorResultType().
                                               length() > 0 ? cdkDescriptor.getDescriptorResultType().
                                                       length() : 1];
                String[] resultLabels = null;
//                logger.debug("# result values: " + resultVals.length);

                //Calculate descriptor
                try {
                    DescriptorValue value = ((IMolecularDescriptor) cdkDescriptor).calculate(container);
                    resultLabels = value.getNames();
                    org.openscience.cdk.qsar.result.IDescriptorResult result = value.getValue();
                    if (result instanceof BooleanResult) {
                        if (((BooleanResult)result).booleanValue()) {
                            resultVals[0] = 1f;
//                            logger.debug("Calculated value: " + resultVals[0]);
                        } else {
                            resultVals[0] = 0f;
//                            logger.debug("Calculated value: " + resultVals[0]);
                        }
                    } else if (result instanceof DoubleResult) {
                        resultVals[0] = (float)((DoubleResult)result).doubleValue();
//                        logger.debug("Calculated value: " + resultVals[0]);
                    } else if (result instanceof IntegerResult) {
                        resultVals[0] = (float)((IntegerResult)result).intValue();
//                        logger.debug("Calculated value: " + resultVals[0]);
                    } else if (result instanceof IntegerArrayResult) {
                        resultVals = new Float[((IntegerArrayResult)result).length()];
                        for (int i=0; i<resultVals.length; i++) {
                            resultVals[i] = (float)((IntegerArrayResult)result).get(i);
//                            logger.debug("Calculated value " + i +": " + resultVals[i]);
                        }
                    } else if (result instanceof DoubleArrayResult) {
                        resultVals = new Float[((DoubleArrayResult)result).length()];
                        for (int i=0; i<resultVals.length; i++) {
                            resultVals[i] = (float)((DoubleArrayResult)result).get(i);
//                            logger.debug("Calculated value " + i +": " + resultVals[i]);
                        }
                    } else {
                        logger.error("Uknown descriptor result type: " + result.getClass().getName());
                        res.setErrorMessage("Uknown descriptor result type: " + result.getClass().getName());
                    }

                } catch (Throwable e) {
                    logger.error("Could not generate descriptor for molecule: " + 
                                 cdkmol.getName() + ". Reason: " + e.getMessage());
                    for (int i=0; i<resultVals.length; i++) {
                        resultVals[i] = Float.NaN;
                    }
                    res.setErrorMessage(e.getMessage());
                    //				e.printStackTrace();
                }

                //Check that we got back correct number of values
                if (resultLabels==null || resultVals==null){
                    logger.error("No resultValues or labels for CDK descriptor: " + descType.getOntologyid() + " for mol: " + cdkmol.getName());
                    res.setErrorMessage( "No resultValues or labels");
                }
                else if (resultLabels.length != resultVals.length) {
                    logger.error("#labels != #vals for " + descType.getOntologyid());
                    res.setErrorMessage( "#labels != #vals for " + descType.getOntologyid() );
                }

                res.setLabels(resultLabels);
                res.setValues(resultVals);
            }

            //Add results. Could be empty with just error msg.
            results.add(res);
        }


        return results;

    }

    /**
     * Preprocess molecules to set ISINRING, percieve atom types, and 
     * detect aromaticity
     * @param container the IAtomcontainer to work on
     * @return String with error message or null if OK
     */
    private String doCDKPreprocessing(IAtomContainer container) {


        //Add hydrogens
        try {

            CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(container.getBuilder());
            for (IAtom atom : container.atoms()) {
                IAtomType matched = matcher.findMatchingAtomType(container, atom);
                if (matched != null){
                    AtomTypeManipulator.configure(atom, matched);
                }else{
                    logger.error("Could not find matching atom type for atom: " + atom);
                }
            }

            CDKHydrogenAdder hAdder = CDKHydrogenAdder.getInstance(container.getBuilder());
            hAdder.addImplicitHydrogens(container);
            AtomContainerManipulator.convertImplicitToExplicitHydrogens(container);
        } catch (Exception e1) {
            String emsg="Error addding hydrogens : " + e1.getMessage();
            logger.error(emsg);
            return emsg;
        }

        //Mark rings as ISINRING
        IRingSet rs;
        try {
            rs = new SpanningTree(container).getBasicRings();
        } catch (NoSuchAtomException e) {
            String emsg="Could not detect rings: " + e.getMessage();
            logger.error(emsg);
            return emsg;
        }
        for (int i = 0; i < container.getAtomCount(); i++) {
            if (rs.contains(container.getAtom(i))) {
                container.getAtom(i).setFlag(CDKConstants.ISINRING, true);
            }
        }

        //Percieve atom types and aromaticity
        try {
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(container);
            CDKHueckelAromaticityDetector.detectAromaticity(container);
        } catch (CDKException e) {
            String emsg="Could not percieve atom types or aromaticity: " + e.getMessage();
            logger.error(emsg);
            return emsg;
        }

        return null;
    }

    public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
             Map<IMolecule, List<DescriptorType>> moldesc,
             IProgressMonitor monitor ){

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();
        
        //The workload for this provider is mols x their descs
        int workload=0;
        for (IMolecule mol : moldesc.keySet()){
            workload=workload+moldesc.get( mol ).size();
        }
        
        monitor.beginTask( "Calculating descriptors with CDK" , workload );

        for (IMolecule mol : moldesc.keySet()){
            try {
                ICDKMolecule cdkmol=cdk.asCDKMolecule(mol);

                List<IDescriptorResult> retlist = doCalculate(cdkmol, moldesc.get( mol ), monitor);
                allResults.put(mol, retlist);

            } catch (BioclipseException e) {
                logger.error("Unable to create CDKMolecule from Imolecule. " +
                "Skipping descriptor calculation for this mol.");
                LogUtils.debugTrace(logger, e);
            }

        }

        return allResults;
    }

}
