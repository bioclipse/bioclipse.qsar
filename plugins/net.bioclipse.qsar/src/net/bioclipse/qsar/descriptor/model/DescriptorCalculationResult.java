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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.bioclipse.core.api.domain.IMolecule;
import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.DescriptorvalueType;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.descriptor.IDescriptorResult;


public class DescriptorCalculationResult {
    
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

        //===============================
        //Set up row and column labels + compute size of dataset
        //===============================

        StringBuffer complete=new StringBuffer();

        //Set up column row
        String row= ROW_SEPARATOR + ROW_SEPARATOR + ROW_SEPARATOR;
        IMolecule fmol = (IMolecule) resultMap.keySet().toArray()[0];

        //Loop over all descriptors
        for (IDescriptorResult dres : resultMap.get( fmol )){
            for (String label : dres.getLabels()){
                row=row + label + ROW_SEPARATOR;
            }
        }
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
            row=row+"\n";
            complete.append( row );
        }

        return complete.toString();


    }

}
