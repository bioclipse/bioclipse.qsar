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
package net.bioclipse.qsar.descriptor.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.qsar.descriptor.IDescriptorResult;


public class DescriptorCalculationResult {
    
    private static final Logger logger = Logger.getLogger(DescriptorCalculationResult.class);

    public static final String ROW_SEPARATOR="\t";

    private Map<IMolecule, List<IDescriptorResult>> resultMap;

    
    public Map<IMolecule, List<IDescriptorResult>> getResultMap() {
    
        return resultMap;
    }

    
    public void setResultMap( Map<IMolecule, List<IDescriptorResult>> resultMap ) {
    
        this.resultMap = resultMap;
    }

    public DescriptorCalculationResult(
            Map<IMolecule, List<IDescriptorResult>> resultMap) {

        this.resultMap=resultMap;
    }
    
    @Override
    public String toString() {

        String res="DescriptorCalculationResult:\n";
        
        for (IMolecule mol : resultMap.keySet()){
            res=res+("Molecule: " + mol + "\n");
            List<IDescriptorResult> lst = resultMap.get( mol );
            for (IDescriptorResult dres : lst){
                res=res+ "  - " + dres + "\n";
            }
        }

        return res;
    }

    public String toMatrix() {
    	return toMatrix(null);
    }

    public String toMatrix(String responseProperty) {

        //===============================
        //Set up row and column labels + compute size of dataset
        //===============================

        StringBuffer complete=new StringBuffer();
        ICDKManager cdk = Activator.getDefault().getJavaCDKManager();

        //Set up column row
        String row= ROW_SEPARATOR;// + ROW_SEPARATOR + ROW_SEPARATOR;
        IMolecule fmol = (IMolecule) resultMap.keySet().toArray()[0];

        //Loop over all descriptors
        for (IDescriptorResult dres : resultMap.get( fmol )){
            for (String label : dres.getLabels()){
                row=row + label + ROW_SEPARATOR;
            }
        }
        if (null!=responseProperty)
            row=row+responseProperty;
        

        row=row+"\n";
        complete.append( row );

        //Set up the rest of the rows
        for (IMolecule mol : resultMap.keySet()){
            row="";
            //The row header
            row=row + mol.toString()+ ROW_SEPARATOR;
            //Loop over all descriptors
            for (IDescriptorResult dres : resultMap.get( mol )){
                for (Float val : dres.getValues()){
                    row=row + val + ROW_SEPARATOR;
                }
            }
            if (null!=responseProperty){
            	ICDKMolecule cdkmol;
				try {
					cdkmol = cdk.asCDKMolecule(mol);
	                row=row+cdkmol.getProperty(responseProperty, IMolecule.Property.USE_CACHED);
				} catch (BioclipseException e) {
					logger.error("Could not get property " + responseProperty + " from molecule: " + mol);
				}
            }

            row=row+"\n";
            complete.append( row );
        }

        return complete.toString();


    }

}
