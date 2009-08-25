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
package net.bioclipse.qsar.ui.editors;

import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.model.BaseEPObject;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import net.bioclipse.qsar.ui.Activator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class DescriptorLabelProvider implements ITableLabelProvider, ILabelProvider {

	Image open, desc;
	IQsarManager qsar;
	
	public DescriptorLabelProvider() {
        qsar=net.bioclipse.qsar.init.Activator.getDefault().getJavaQsarManager();
	}

	public Image getColumnImage(Object element, int columnIndex) {

		if (element instanceof PendingObject) {
			return null;
		}
//		else if (element instanceof DescriptorInstance) {
//			if (desc==null) desc= Activator.getImageDescriptor(
//					"icons/descriptor.png").createImage();
//			return desc;
//		}
		
		else if (element instanceof DescriptorType) {
			if (desc==null) desc= Activator.getImageDescriptor(
					"icons/descriptor.png").createImage();
			return desc;
		}

		if (columnIndex==0){
			if (element instanceof DescriptorCategory) {
				if (open==null) open=Activator.getImageDescriptor(
						"icons/open.gif").createImage();
				return open;
			}
			else if (element instanceof Descriptor) {
				if (desc==null) desc= Activator.getImageDescriptor(
						"icons/descriptor.png").createImage();
				return desc;
			}

			else if (element instanceof BaseEPObject) {
				BaseEPObject epobj = (BaseEPObject) element;
				return epobj.getIcon();
			}
		}
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		
		if (element instanceof PendingObject) {
			PendingObject po = (PendingObject) element;
			if (columnIndex==0)
				return po.getPendingString();
			return "";
		}
//		else if (element instanceof DescriptorInstance) {
//			DescriptorInstance inst = (DescriptorInstance) element;
//			return inst.getName();
//		}
		else if (element instanceof DescriptorType) {
			DescriptorType desc=(DescriptorType)element;

			String descString=qsar.getDescriptorByID(desc.getId()).getName();

			String cpstr="";
			if (desc.getParameter()!=null && desc.getParameter().size()>0){
				for (ParameterType param : desc.getParameter()){
					String pstr=param.getKey() + "=" + param.getValue()+", ";
					cpstr=cpstr+pstr;
				}
				cpstr=cpstr.substring(0,cpstr.length()-2);
			}
			
			if (cpstr.length()>1){
				descString=descString+" [" + cpstr + "]";
			}
			
			return descString;
		}

		else if (element instanceof ResponseType) {
			ResponseType response=(ResponseType)element;
			
			if (columnIndex==0){
			    //TODO: changed, might need work: ols was: response.getResourceIndex()
				return response.getStructureID() + "-" + response.getStructureID();
			}
			if (columnIndex==1){
					return response.getValue();
			}

			return "????";
		}

		else if (element instanceof Descriptor) {
			Descriptor desc = (Descriptor) element;
			if (columnIndex==0){
				return desc.getName();
			}
			
			//Providers column
			else if (columnIndex==1){

				String retProv="";
				for (DescriptorImpl impl : qsar.getDescriptorImplsForDescriptor(desc.getId())){
					retProv=retProv + impl.getProvider().getShortName() +", ";
				}
				
				if (retProv.length()>1){
					//Remove last comma and space
					retProv=retProv.substring(0,retProv.length()-2);
				}
				
				return retProv;
			}
		}
		else if (element instanceof DescriptorCategory) {
			BaseEPObject epobj = (BaseEPObject) element;
			if (columnIndex==0){
				return epobj.getName();
			}
			return "";
		}
		
		return "NA";
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText(Object element) {
		return getColumnText(element, 0);
	}
	

}
