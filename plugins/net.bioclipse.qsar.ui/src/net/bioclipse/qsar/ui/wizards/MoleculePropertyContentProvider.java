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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.chemoinformatics.util.ChemoinformaticUtils;
import net.bioclipse.core.api.BioclipseException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author ola
 *
 */
public class MoleculePropertyContentProvider implements ITreeContentProvider{

    private static final Object[] EMPTY_CHILDREN = new Object[0];

    public Object[] getChildren( Object parentElement ) {

        if (!( parentElement instanceof IFile ))
            return EMPTY_CHILDREN;
        
        IFile molfile=(IFile)parentElement;

        ICDKManager cdk = Activator.getDefault().getJavaCDKManager();
        ICDKMolecule cdkmol=null;

        try {
            //Handle multiple mols separately since might take a long time parse
            if (ChemoinformaticUtils.isMultipleMolecule( molfile)){
                Iterator<ICDKMolecule> it;
                it = cdk.createMoleculeIterator( molfile );
                cdkmol=it.next();
            }else{
                //Single mol, just parse
                cdkmol = cdk.loadMolecule( molfile );
            }
        } catch ( Exception e ) {
//            e.printStackTrace();
            return EMPTY_CHILDREN;
        }

        List<PropertyEntry> props = new ArrayList<PropertyEntry>();
        
        if (cdkmol.getAtomContainer().getProperties()!=null){
            for (Object obj : cdkmol.getAtomContainer().getProperties().keySet()){
                PropertyEntry prop=new PropertyEntry(molfile, obj);
                props.add( prop );
            }
        }

        return props.toArray();
        
    }

    public Object getParent( Object element ) {
        if ( element instanceof PropertyEntry ) {
            PropertyEntry prop = (PropertyEntry) element;
            return prop.getParent();
        }
        return null;
    }

    public boolean hasChildren( Object element ) {
        return getChildren( element ).length >0 ? true : false;
    }

    @SuppressWarnings("unchecked")
    public Object[] getElements( Object inputElement ) {
        List<IFile> reslist = (List<IFile>) inputElement;
        return reslist.toArray();
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    }

    public void dispose() {
    }

}
