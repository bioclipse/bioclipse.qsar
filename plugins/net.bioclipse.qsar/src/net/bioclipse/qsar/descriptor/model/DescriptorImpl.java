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
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Instances of this class are defined in manifest of providers plugins
 * @author ola
 *
 */
public class DescriptorImpl extends BaseEPObject{

	private DescriptorProvider provider;
	private boolean requires3D;
	private List<DescriptorParameter> parameters;
	private String definition;
	private String description;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public List<DescriptorParameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<DescriptorParameter> parameters) {
		this.parameters = parameters;
	}
	public boolean isRequires3D() {
		return requires3D;
	}
	public void setRequires3D(boolean requires3D) {
		this.requires3D = requires3D;
	}
	public DescriptorProvider getProvider() {
		return provider;
	}
	public void setProvider(DescriptorProvider provider) {
		this.provider = provider;
	}
	public DescriptorImpl(String id, String name) {
		super(id, name);
	}
	public DescriptorImpl(String id, String name, String icon_path) {
		super(id, name, icon_path);
	}

	DescriptorImpl(){
	}


	public Object getAdapter(Class adapter) {

		if (IPropertySource.class.equals(adapter)) {
			return new DescriptorImplPropertySource(this);
		}

		return super.getAdapter(adapter);
	}
	
	public DescriptorImpl newInstance(){
	    DescriptorImpl impl=new DescriptorImpl();
	    impl.setId( getId() );
	    impl.setName( getName() );
	    impl.setDefinition( getDefinition() );
	    impl.setDescription( getDescription() );
	    impl.setIcon( getIcon() );
	    impl.setNamesapce( getNamesapce() );
	    impl.setProvider( getProvider() );
	    impl.setRequires3D( isRequires3D() );
	    List<DescriptorParameter> params = new ArrayList<DescriptorParameter>();
	    if (getParameters()!=null){
	        for (DescriptorParameter p : getParameters()){
	            DescriptorParameter np=new DescriptorParameter(p.getKey(),p.getDefaultvalue());
	            np.setValue( new String(p.getValue()));
	            np.setListedvalues( p.getListedvalues() );
	            params.add( np );
	        }
	    }
      impl.setParameters( params );
	    
	    return impl;
	}
	
	
}
