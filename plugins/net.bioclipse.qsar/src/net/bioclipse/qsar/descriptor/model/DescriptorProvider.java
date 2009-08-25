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

import net.bioclipse.qsar.descriptor.IDescriptorCalculator;

/**
 * 
 * @author ola
 *
 */
public class DescriptorProvider extends BaseEPObject{
    
	protected DescriptorProvider() {
        super();
    }

	
	public DescriptorProvider(String id, String name) {
	    super(id, name);
	}
	public DescriptorProvider(String id, String name, String icon_path) {
	    super(id, name, icon_path);
	}

	private List<DescriptorImpl> descriptorImpls;
	private IDescriptorCalculator calculator;
	private boolean acceptsSmiles;
	private boolean acceptsCml;
	private boolean acceptsMolfile;
	private String shortName;
	
	private String vendor;
	private String version;

	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public List<DescriptorImpl> getDescriptorImpls() {
		return descriptorImpls;
	}
	public void setDescriptorImpls(List<DescriptorImpl> descriptorImpls) {
		this.descriptorImpls = descriptorImpls;
	}
	public IDescriptorCalculator getCalculator() {
		return calculator;
	}
	public void setCalculator(IDescriptorCalculator calculator) {
		this.calculator = calculator;
	}
	public boolean isAcceptsSmiles() {
		return acceptsSmiles;
	}
	public void setAcceptsSmiles(boolean acceptsSmiles) {
		this.acceptsSmiles = acceptsSmiles;
	}
	public boolean isAcceptsCml() {
		return acceptsCml;
	}
	public void setAcceptsCml(boolean acceptsCml) {
		this.acceptsCml = acceptsCml;
	}
	public boolean isAcceptsMolfile() {
		return acceptsMolfile;
	}
	public void setAcceptsMolfile(boolean acceptsMolfile) {
		this.acceptsMolfile = acceptsMolfile;
	}
	
	public Object getAdapter(Class adapter) {

		if (IPropertySource.class.equals(adapter)) {
			return new DescriptorProviderPropertySource(this);
		}

		return super.getAdapter(adapter);
	}

	
}
