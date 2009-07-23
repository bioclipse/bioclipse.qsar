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
package net.bioclipse.qsar.ui.wizards;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


public class ExistingResourcesFilter extends ViewerFilter {

    private List<IResource> blacklist;

    public ExistingResourcesFilter(List<IResource> blacklist) {
        this.blacklist=blacklist;
    }

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {

        //Only filter out IResource
        if (!(element instanceof IResource)) {
            return true;
        }

        //Filter out if in blacklist
        IResource resource = (IResource) element;
        if (blacklist.contains( resource )) return false;
        else return true;
    }

}
