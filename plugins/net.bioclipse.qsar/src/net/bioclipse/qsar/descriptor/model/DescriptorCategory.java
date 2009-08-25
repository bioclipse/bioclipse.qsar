/*******************************************************************************
 * Copyright (c) 2008 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ola Spjuth - core API and implementation
 *******************************************************************************/
package net.bioclipse.qsar.descriptor.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertySource;

public class DescriptorCategory extends BaseEPObject{

	private String date;
	private List<Descriptor> descriptors;
	
	public List<Descriptor> getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(List<Descriptor> descriptors) {
		this.descriptors = descriptors;
	}
	public DescriptorCategory(String id, String name) {
		super(id, name);
	}
	public DescriptorCategory(String id, String name, String icon_path) {
		super(id, name, icon_path);
	}

	protected DescriptorCategory() {
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Object getAdapter(Class adapter) {

		if (IPropertySource.class.equals(adapter)) {
			return new DescriptorCategoryPropertySource(this);
		}

		return super.getAdapter(adapter);
	}
    public void addDescriptor( Descriptor desc ) {
        if (descriptors==null) descriptors=new ArrayList<Descriptor>();
        descriptors.add(desc);
    }

	
}
