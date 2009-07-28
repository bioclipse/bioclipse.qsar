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

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.init.Activator;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DescriptorPropertySource extends BaseEPObjectPropertySource{

    protected static final String PROPERTY_TYPE = "Type";
    protected static final String PROPERTY_DATE = "Date";
    protected static final String PROPERTY_DEFINITION = "Definition";
    protected static final String PROPERTY_DESCRIPTION = "Description";
    protected static final String PROPERTY_IMPLS = "Implementations";

    IQsarManager qsar;
    
	public DescriptorPropertySource(BaseEPObject item) {
		super(item);
	}
	
	@Override
	public void addPropertiesWithValues() {

		qsar=Activator.getDefault().getQsarManager();
		
		//Add id and name
		super.addPropertiesWithValues();

        Descriptor desc=(Descriptor)getItem();
        
		
		//Add type
        PropertyDescriptor descriptor;
        descriptor = new TextPropertyDescriptor(PROPERTY_TYPE,"Type");
        descriptor.setCategory("General");
        getProperties().add(descriptor);
    	//Add type value
        getValueMap().put(PROPERTY_TYPE,"Descriptor");

		//Add date
        PropertyDescriptor descriptor2;
        descriptor2 = new TextPropertyDescriptor(PROPERTY_DATE,"Date");
        descriptor2.setCategory("General");
        getProperties().add(descriptor2);
    	//Add date value
        getValueMap().put(PROPERTY_DATE,desc.getDate());

		//Add def
        PropertyDescriptor descriptor3;
        descriptor3 = new TextPropertyDescriptor(PROPERTY_DEFINITION,"Definition");
        descriptor3.setCategory("General");
        getProperties().add(descriptor3);
    	//Add def value
        getValueMap().put(PROPERTY_DEFINITION,desc.getDefinition());

		//Add desc
        PropertyDescriptor descriptor4;
        descriptor4 = new TextPropertyDescriptor(PROPERTY_DESCRIPTION,"Description");
        descriptor4.setCategory("General");
        getProperties().add(descriptor4);
    	//Add desc value
        getValueMap().put(PROPERTY_DESCRIPTION,desc.getDescription());

		//Add Impls
        PropertyDescriptor descriptor5;
        descriptor5 = new TextPropertyDescriptor(PROPERTY_IMPLS,"Implementations");
        descriptor5.setCategory("General");
        getProperties().add(descriptor5);

        //Get value from manager and transform to string
        List<String> impls;
        try {
            impls = qsar.getDescriptorImpls(desc.getId());
            String implsStr="";
            if (impls==null || impls.size()<=0){
                implsStr="None";
            }else{
                for (String str : impls){
                    implsStr=implsStr+str+", ";
                }
                if (implsStr.length()>3)
                    implsStr=implsStr.substring(0,implsStr.length()-2);
            }
            //Add impls value to properties
            getValueMap().put(PROPERTY_IMPLS,implsStr);

        } catch ( BioclipseException e ) {
            getValueMap().put(PROPERTY_IMPLS,"Error: " + e.getMessage());
        }


	}

}
