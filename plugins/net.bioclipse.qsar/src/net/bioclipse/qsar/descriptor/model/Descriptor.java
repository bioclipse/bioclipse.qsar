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

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Instances of this class are defined in BODO.
 * @author ola
 *
 */
public class Descriptor extends BaseEPObject{

	private String definition;
	private String description;
	private String date;
	private List<DescriptorCategory> categories;
	
	public Descriptor(String id, String name) {
		super(id, name);
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<DescriptorCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<DescriptorCategory> categories) {
		this.categories = categories;
	}
	
	
	public Object getAdapter(Class adapter) {

		if (IPropertySource.class.equals(adapter)) {
			return new DescriptorPropertySource(this);
		}

		return super.getAdapter(adapter);
	}
	

}
