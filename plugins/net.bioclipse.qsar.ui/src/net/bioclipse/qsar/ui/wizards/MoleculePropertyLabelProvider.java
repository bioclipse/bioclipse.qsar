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

import java.io.IOException;
import java.util.Map;

import net.bioclipse.chemoinformatics.util.ChemoinformaticUtils;
import net.bioclipse.qsar.ui.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author ola
 *
 */
public class MoleculePropertyLabelProvider implements ILabelProvider {

    private Image singleIcon;
    private Image multiIcon;
    private Image propIcon;
    private Map<IFile, PropertyEntry> selectedProperties;

    public MoleculePropertyLabelProvider(Map<IFile, PropertyEntry> selectedProperties) {

        //Init the images
        singleIcon=Activator
            .getImageDescriptor( "icons/benzene.gif" ).createImage();
        multiIcon=Activator
            .getImageDescriptor( "icons/moltable.png" ).createImage();
        propIcon=Activator
            .getImageDescriptor( "icons/property.gif" ).createImage();
        
        //We need to cache this to be able to look up the selected elements
        this.selectedProperties=selectedProperties;
    }

    public Image getImage( Object element ) {
        if ( element instanceof IFile ) {
            IFile file = (IFile) element;
            try {
                if (ChemoinformaticUtils.isMolecule( file ))
                    return singleIcon;
                if (ChemoinformaticUtils.isMultipleMolecule( file ))
                    return multiIcon;
            } catch ( CoreException e ) {
            } catch ( IOException e ) {
            }
        }
        
        if ( element instanceof PropertyEntry ) {
            return propIcon;
        }
        
        return null;
    }

    public String getText( Object element ) {
        
        if ( element instanceof IFile ) {
            return ((IFile) element).getName();
        }
        if ( element instanceof PropertyEntry ) {
            PropertyEntry prop = (PropertyEntry) element;
            String ret = prop.getPropObject().toString();
            if (selectedProperties.get( prop.getParent() ) != null){
                if (selectedProperties.get( prop.getParent() ).equals( prop )){
                    ret = ret + " [SELECTED]";
                }
            }
            return ret;
        }
        
        return "???";
    }

    public void addListener( ILabelProviderListener listener ) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty( Object element, String property ) {
        return false;
    }

    public void removeListener( ILabelProviderListener listener ) {
    }

}
