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

import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.init.Activator;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DescriptorProviderPropertySource extends BaseEPObjectPropertySource{

    protected static final String PROPERTY_TYPE = "Type";
    protected static final String PROPERTY_NO_DESCR = "# descriptors";

    IQsarManager qsar;
    
	public DescriptorProviderPropertySource(BaseEPObject item) {
		super(item);
		qsar=Activator.getDefault().getJavaQsarManager();
	}
	
	@Override
	public void addPropertiesWithValues() {

		//Add id and name
		super.addPropertiesWithValues();
		
		//Add type
        PropertyDescriptor descriptor;
        descriptor = new TextPropertyDescriptor(PROPERTY_TYPE,"Type");
        descriptor.setCategory("General");
        getProperties().add(descriptor);
    	//Add type value
        getValueMap().put(PROPERTY_TYPE,"Provider");

		//Add type
        PropertyDescriptor descriptor2;
        descriptor2 = new TextPropertyDescriptor(PROPERTY_NO_DESCR,"# descriptors");
        descriptor2.setCategory("General");
        getProperties().add(descriptor2);
    	//Add type value
        getValueMap().put(PROPERTY_NO_DESCR,qsar.getFullDescriptorImpls((DescriptorProvider)getItem()).size());

	}

}
