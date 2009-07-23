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
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * A wizard to select molecules and optionally a property that is used
 * as reponse value.
 * 
 * @author ola
 *
 */
public class AddMoleculeFilesWizard extends Wizard{

    private SelectMoleculesPage selectMolPage;
    private SelectPropertyAsResponsePage selectResponsePage;

    private List<IFile> selectedFiles;
    private Map<IFile, PropertyEntry> selectedProperties;
    private List<IResource> blacklist;
    
    public List<IResource> getBlacklist() {
        return blacklist;
    }

    public AddMoleculeFilesWizard(List<IResource> blacklistFilter) {
        this.blacklist=blacklistFilter;
    }

    public void setSelectedProperties( Map<IFile, PropertyEntry> selectedProperties ) {
        this.selectedProperties = selectedProperties;
    }

    public Map<IFile, PropertyEntry> getSelectedProperties() {
        return selectedProperties;
    }

    public void setSelectedFiles( List<IFile> selectedFiles ) {
        this.selectedFiles = selectedFiles;
    }

    public List<IFile> getSelectedFiles() {
        return selectedFiles;
    }


    @Override
    public void addPages() {

        //Page 1: Select molecular files
        selectMolPage=new SelectMoleculesPage("Select molecule files to add");
        addPage(selectMolPage);

        //Page 2: Select properties to use as response values for the molecules
        selectResponsePage=new SelectPropertyAsResponsePage("Select property as response");
        addPage( selectResponsePage );
    }

    /**
     * When moving from first to second page, we need to send selected resources
     */
    @Override
    public IWizardPage getNextPage( IWizardPage page ) {
        
        if (page==selectMolPage)
            selectResponsePage.setInput( selectedFiles );
    
        return super.getNextPage( page );
    }
    
    @Override
    public boolean canFinish() {
        //We require a valid molecules selection, and that's it
        if (selectedFiles==null || selectedFiles.size()<=0)
            return false;

        return true;
    }
    
    @Override
    public boolean performFinish() {

        //If no properties selected, add file with null propertyentry
        for (IFile file : selectedFiles){
            if (!(selectedProperties.keySet().contains( file )))
                selectedProperties.put( file, null );
        }
        
        return true;
    }

}
