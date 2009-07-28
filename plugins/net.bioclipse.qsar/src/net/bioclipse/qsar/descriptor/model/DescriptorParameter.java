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

import java.util.ArrayList;
import java.util.List;

public class DescriptorParameter {

	String key;
	String defaultvalue;
	String description;
	String value;
	private List<String> listedvalues;
	
	
	/**
	 * If null, return default value.
	 * @return
	 */
	public String getValue() {
		if (value==null) return defaultvalue;
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DescriptorParameter(String key, String defaultValue) {
		this.key=key;
		this.defaultvalue=defaultValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public DescriptorParameter clone(){
		DescriptorParameter newParam=new DescriptorParameter(this.key, this.defaultvalue);
		newParam.setDescription(this.description);
		newParam.setListedvalues( getListedvalues() );
		return newParam;
	}

	/**
	 *Allowed values for the parameter
	 */
	public void addListedValue( String val ) {

	    if (getListedvalues()==null) setListedvalues( new ArrayList<String>() );
	    getListedvalues().add( val );

	}

    public void setListedvalues( List<String> listedvalues ) {

        this.listedvalues = listedvalues;
    }

    public List<String> getListedvalues() {

        return listedvalues;
    }

	

}
