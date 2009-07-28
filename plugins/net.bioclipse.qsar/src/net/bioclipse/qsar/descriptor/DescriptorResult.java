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
package net.bioclipse.qsar.descriptor;

import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.init.Activator;

/**
 * Base implementation of a descriptor result.
 * @author ola
 *
 */
public class DescriptorResult implements IDescriptorResult{

	DescriptorType descriptor;
	String[] labels;
	Float[] values;

	String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	//Constructor
	public DescriptorResult() {
	}


	public String[] getLabels() {
		return labels;
	}


	public void setLabels(String[] labels) {
		this.labels = labels;
	}


	public Float[] getValues() {
		return values;
	}


	public void setValues(Float[] values) {
		this.values = values;
	}
	
	@Override
    public String toString() {
	    
	    if (labels==null || labels.length<=0) return "null";
	    IQsarManager qsar=Activator.getDefault().getQsarManager();
	    
	    String provider=qsar.getProviderByID( descriptor.getProvider() ).getShortName();
	    String paramstr="";
	    for (ParameterType param : descriptor.getParameter()){
	        paramstr=paramstr+param.getKey()+"="+param.getValue() + ", ";
	    }
	    if (paramstr.length()>3){
	        paramstr=paramstr.substring( 0, paramstr.length()-2 );
	    }
	    
	    String ret="Descriptor=" 
	        + qsar.toShortOntologyForm( descriptor.getOntologyid());
	    if (paramstr.length()>1){
	        ret=ret+" [" + paramstr + "]";
	    }
	    ret=ret + "; provider=" + provider +"; ";	    
	    for (int i=0; i< labels.length;i++){
	        ret=ret+labels[i] + "=" + values[i];
	    }

	    return ret;
    }
    
    public DescriptorType getDescriptor() {
        return descriptor;
    }
    public void setDescriptor( DescriptorType descriptor ) {
        this.descriptor = descriptor;
    }

}
