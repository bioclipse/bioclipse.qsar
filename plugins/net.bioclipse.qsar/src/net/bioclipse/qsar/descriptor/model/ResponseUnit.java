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

import net.bioclipse.core.api.domain.BioObject;

/**
 * 
 * @author ola
 *
 */
public class ResponseUnit extends BioObject{

    String id;
    String name;
    String shortname;
    String description;
    String url;
    
    public ResponseUnit(String id, String name) {
        this.id=id;
        this.name=name;
    }

    public String getId() {
    
        return id;
    }
    
    public void setId( String id ) {
    
        this.id = id;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName( String name ) {
    
        this.name = name;
    }
    
    public String getShortname() {
    
        return shortname;
    }
    
    public void setShortname( String shortname ) {
    
        this.shortname = shortname;
    }
    
    public String getDescription() {
    
        return description;
    }
    
    public void setDescription( String description ) {
    
        this.description = description;
    }
    
    public String getUrl() {
    
        return url;
    }
    
    public void setUrl( String url ) {
    
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "ResponseUnit: id=" + getId() + ", name=" + getName() 
        + ", shortname=" + getShortname() + ", url=" + getUrl() 
        + ", descr=" + getDescription();
    }
    
}
