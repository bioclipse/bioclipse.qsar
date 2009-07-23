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

import org.eclipse.core.resources.IFile;


public class PropertyEntry {

    IFile parent;
    Object propObject;

    public IFile getParent() {
        return parent;
    }
    public Object getPropObject() {
        return propObject;
    }

    public PropertyEntry(IFile molfile, Object obj) {
        parent=molfile;
        propObject=obj;
    }
    
    @Override
    public boolean equals( Object obj ) {
        
        if ( obj instanceof PropertyEntry ) {
            PropertyEntry pe = (PropertyEntry) obj;
            if ((pe.getParent().equals( parent )) 
                  && 
                  pe.getPropObject().toString().equals( propObject.toString()))
                return true;
        }
    
        return false;
    }

}
