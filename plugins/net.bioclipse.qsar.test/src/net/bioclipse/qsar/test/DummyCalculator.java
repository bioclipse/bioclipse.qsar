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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import net.bioclipse.core.api.domain.IMolecule;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.descriptor.DescriptorResult;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;

public class DummyCalculator implements IDescriptorCalculator {

    private ArrayList<String> supported;

    /**
     * For each molecule and descriptor, return an array of values
     */
    @Deprecated
    public Map<IMolecule, List<IDescriptorResult>> calculateDescriptor(
                                                                       List<? extends IMolecule> molecules, 
                                                                       List<DescriptorType> descriptorTypes, IProgressMonitor monitor){

        Map<IMolecule, List<IDescriptorResult>> allResults = 
            new HashMap<IMolecule, List<IDescriptorResult>>();

        //Loop over all molecules
        for (IMolecule mol : molecules){

            List<IDescriptorResult> molresults=new ArrayList<IDescriptorResult>();

            //Loop over all descriptors
            for (DescriptorType descType : descriptorTypes){

                //Dummy result
                DescriptorResult res=new DescriptorResult();

                //If descriptor is supported, add content
                if (getSupportedDescriptorIDs().contains(descType.getOntologyid())){

                    if (descType.getOntologyid().equals("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#aromaticBondsCount")){
                        //Return error result, fake a calculation has gone wrong
                        res.setErrorMessage("Failed to calculate descriptor " +
                                            "'" + descType.getOntologyid() + 
                        "'. Reason: Unknown");
                    }
                    else{
                        //Return correct result
                        res.setDescriptor( descType );
                        Float[] returnvalue=new Float[]{new Float(15.2456), new Float(47.01), new Float(-6.44)};
                        res.setValues(returnvalue);
                        String[] labels=new String[]{"label1","label2","label3"};
                        res.setLabels(labels);

                    }
                }

                //If this descriptor is not supported, add error msg
                //This should not happen
                else{
                    res.setErrorMessage("DescriptorID '" + descType.getOntologyid() + 
                    "' is not supported by DummyProvider.");
                }

                //Add to results for this molecule
                molresults.add(res);

            }

            //Put this molecules result in map to return
            allResults.put(mol, molresults);

        }

        return allResults;
    }

    private List<String> getSupportedDescriptorIDs(){

        if (supported==null){
            supported=new ArrayList<String>();

            supported.add("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#chiChain");
            supported.add("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#BCUT");
            supported.add("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#aromaticBondsCount");

        }
        return supported;
    }

    public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
           Map<IMolecule, List<DescriptorType>> moldesc,
           IProgressMonitor monitor ) {



        Map<IMolecule, List<IDescriptorResult>> allResults = 
            new HashMap<IMolecule, List<IDescriptorResult>>();

        //Loop over all molecules
        for (IMolecule mol : moldesc.keySet()){

            List<IDescriptorResult> molresults=new ArrayList<IDescriptorResult>();

            //Loop over all descriptors
            for (DescriptorType descType : moldesc.get( mol )){

                //Dummy result
                DescriptorResult res=new DescriptorResult();

                //If descriptor is supported, add content
                if (getSupportedDescriptorIDs().contains(descType.getOntologyid())){

                    if (descType.getOntologyid().equals("http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#aromaticBondsCount")){
                        //Return error result, fake a calculation has gone wrong
                        res.setErrorMessage("Failed to calculate descriptor " +
                                            "'" + descType.getOntologyid() + 
                        "'. Reason: Unknown");
                    }
                    else{
                        //Return correct result
                        res.setDescriptor( descType );
                        Float[] returnvalue=new Float[]{new Float(15.2456), new Float(47.01), new Float(-6.44)};
                        res.setValues(returnvalue);
                        String[] labels=new String[]{"label1","label2","label3"};
                        res.setLabels(labels);

                    }
                }

                //If this descriptor is not supported, add error msg
                //This should not happen
                else{
                    res.setErrorMessage("DescriptorID '" + descType.getOntologyid() + 
                    "' is not supported by DummyProvider.");
                }

                //Add to results for this molecule
                molresults.add(res);

            }

            //Put this molecules result in map to return
            allResults.put(mol, molresults);

        }

        return allResults;

    }


}
