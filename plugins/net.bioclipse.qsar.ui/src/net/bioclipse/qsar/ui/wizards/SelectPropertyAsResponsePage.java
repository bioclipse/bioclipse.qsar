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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bioclipse.chemoinformatics.util.MoleculeContentTypeViewerFilter;
import net.bioclipse.qsar.ui.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;

/**
 * A wizard page to optionally select one property for a molecule file.
 * 
 * @author ola
 *
 */
public class SelectPropertyAsResponsePage extends WizardPage {

    private TreeViewer viewer;
    private Action selectPropertyAction;
    
    Map<IFile, PropertyEntry> selectedProperties;
    private AddMoleculeFilesWizard wizard;

    protected SelectPropertyAsResponsePage(String pageName) {
        super( pageName );
    }

    /**
     * Create the property selection implemented as a treeviewer
     */
    public void createControl( Composite parent ) {

        //Store selected props here
        selectedProperties=new HashMap<IFile, PropertyEntry>();
        
        //Store selected props in the wizard as well
        wizard=(AddMoleculeFilesWizard) getWizard();
        wizard.setSelectedProperties( selectedProperties );

        //Set up page
        setTitle( "Select response property" );
        setDescription( "Select a property of the file to use as response. " +
        		"This step is optional." );
        setImageDescriptor( Activator.getImageDescriptor( "wizban/wiz_imp_mol.gif" ) );

        Composite comp = new Composite(parent, SWT.NONE);
        GridLayout layout=new GridLayout();
        comp.setLayout( layout );

        viewer = new TreeViewer(comp, 
                          SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        viewer.setUseHashlookup(true);
        viewer.setContentProvider(new MoleculePropertyContentProvider());
        viewer.setLabelProvider( new MoleculePropertyLabelProvider(selectedProperties));
        viewer.addFilter( new MoleculeContentTypeViewerFilter() );
        viewer.addDoubleClickListener( new IDoubleClickListener(){

            public void doubleClick( DoubleClickEvent event ) {
                selectPropertyAction.run();
            }
            
        });

        GridData data = new GridData(GridData.FILL_BOTH);
        data.grabExcessHorizontalSpace=true;
        data.grabExcessVerticalSpace=true;
        data.heightHint = 400;
        data.widthHint = 300;
        viewer.getControl().setLayoutData(data);

        makeActions();
        hookContextMenu();
        
        setControl( comp );
    }

    /**
     * The actions in the viewer
     */
    private void makeActions() {

        selectPropertyAction=new Action("Select as response property", 
                            Activator.getImageDescriptor( "icons/sight.gif" )) {
            @Override
            public void run() {
                IStructuredSelection sel=
                                   (IStructuredSelection) viewer.getSelection();
                
                //Should only be one since single selection mode
                Object obj = sel.getFirstElement();
                if ( obj instanceof PropertyEntry ) {
                    PropertyEntry prop = (PropertyEntry) obj;
                    
                    //Replace with this
                    selectedProperties.put( prop.getParent(), prop );
                    viewer.refresh();

                }
            }
        };
        
    }

    /**
     * A context menu
     */
    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                manager.removeAll();
                
                //Only show actions if propertyentry selected
                IStructuredSelection sel=
                    (IStructuredSelection) viewer.getSelection();

                if ( sel.getFirstElement() instanceof PropertyEntry ) {
                    manager.add(selectPropertyAction);
                    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
                }
            }

        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
    }


    /**
     * Called from wizard on nextPage to set input
     * @param selectedFiles
     */
    public void setInput(List<IFile> selectedFiles){
        viewer.setInput( selectedFiles );
        viewer.expandAll();
    }
    
    

}
