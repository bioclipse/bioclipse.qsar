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

import java.util.ArrayList;

import net.bioclipse.chemoinformatics.util.MoleculeContentTypeViewerFilter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A Wizard page to select one or many molecules from the workspace
 * 
 * @author ola
 *
 */
public class SelectMoleculesPage extends WizardPage implements ISelectionChangedListener {

    private TreeViewer viewer;
    private AddMoleculeFilesWizard wizard;
    private ArrayList<IFile> selectedFiles;

    protected SelectMoleculesPage(String pageName) {
        super( pageName );
    }

    public void createControl( Composite parent ) {
        
        wizard=(AddMoleculeFilesWizard) getWizard();
        
        setTitle( "Add files" );
        setDescription( "Select files with molecules to add to the QSAR project." );

        Composite comp = new Composite(parent, SWT.NONE);
        GridLayout layout=new GridLayout();
        comp.setLayout( layout );
        
        viewer = createViewer(comp);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.grabExcessHorizontalSpace=true;
        data.grabExcessVerticalSpace=true;
        data.heightHint = 400;
        data.widthHint = 300;
        viewer.getControl().setLayoutData(data);
        viewer.addSelectionChangedListener( this );
        
        setControl( comp );
    }
      
      protected TreeViewer createViewer(Composite parent) {
        TreeViewer viewer =
          new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        viewer.setUseHashlookup(true);
        
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider( new WorkbenchLabelProvider());
        viewer.addFilter( new MoleculeContentTypeViewerFilter() );
        viewer.addFilter( new ExistingResourcesFilter(wizard.getBlacklist()) );
        viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
        viewer.expandToLevel(2);
        return viewer;
      }

      /**
       * React on treeviewer changes to be able to return the selected resources
       */
    public void selectionChanged( SelectionChangedEvent event ) {

        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
        selectedFiles = new ArrayList<IFile>();
        for (Object obj : selection.toList()){
            if ( obj instanceof IFile ) {
                IFile file = (IFile) obj;
                selectedFiles.add(file);
            }
        }
        
        wizard.setSelectedFiles( selectedFiles );
        
        updateStatus();
    }

    private void updateStatus() {

        setErrorMessage( null );
        setPageComplete( true );

        if (selectedFiles==null || selectedFiles.size()<=0){
            setErrorMessage( "Please select at least one file" );
            setPageComplete( false );
        }
            
        getContainer().updateButtons();
    }
      
}
