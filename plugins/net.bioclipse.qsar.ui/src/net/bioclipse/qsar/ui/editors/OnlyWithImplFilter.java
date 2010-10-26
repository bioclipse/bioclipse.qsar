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

import net.bioclipse.core.api.BioclipseException;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.model.Descriptor;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter to filter out descriptors that donŠt have an implementation
 * @author ola
 *
 */
public class OnlyWithImplFilter extends ViewerFilter {

	IQsarManager qsar;
	

	public OnlyWithImplFilter() {
	    qsar=net.bioclipse.qsar.init.Activator.getDefault().getJavaQsarManager();
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		//Only filter Descriptors
		if (!(element instanceof Descriptor)) {
			return true;
		}

		Descriptor desc = (Descriptor) element;
		
		try {
            if (qsar.getDescriptorImpls(desc.getId()).size()>0)
                return true;
        } catch ( BioclipseException e ) {
        }

		return false;
	}

}
