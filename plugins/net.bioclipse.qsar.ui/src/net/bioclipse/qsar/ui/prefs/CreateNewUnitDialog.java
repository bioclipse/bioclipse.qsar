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
package net.bioclipse.qsar.ui.prefs;

import net.bioclipse.qsar.QsarPackage;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class CreateNewUnitDialog extends TitleAreaDialog{

    private AdapterFactoryEditingDomain editingDomain;

    public CreateNewUnitDialog(Shell parentShell, AdapterFactoryEditingDomain editingDomain) {
        super( parentShell );
        this.editingDomain=editingDomain;
    }
    
    @Override
    protected Control createDialogArea( Composite parent ) {
        
        // create the top level composite for the dialog area
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setFont(parent.getFont());
        // Build the separator line
        Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL
            | SWT.SEPARATOR);
        titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        layout.numColumns = 2;
        
        
        //Create our model object
        
        

        //Name
        Label lblName = new Label(composite, SWT.NONE);
        GridData gd2 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblName.setLayoutData(gd2);

        Text txtName = new Text(composite, SWT.NONE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint=16;
        txtName.setLayoutData(gd);
        lblName.setText( "Name" );
 
        //Shortname
        Label lblShortName = new Label(composite, SWT.NONE);
        GridData gd3 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblShortName.setLayoutData(gd3);
        lblShortName.setText( "Shortname" );

        Text txtShortName = new Text(composite, SWT.NONE);
        GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
        gd4.heightHint=16;
        txtShortName.setLayoutData(gd4);

        //Shortname
        Label lblDescription = new Label(composite, SWT.NONE);
        GridData gd5 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblDescription.setLayoutData(gd5);
        lblDescription.setText( "Description" );

        Text txtDescription = new Text(composite, SWT.NONE);
        GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
        gd6.heightHint=16;
        txtDescription.setLayoutData(gd6);

        //Shortname
        Label lblURL = new Label(composite, SWT.NONE);
        GridData gd7 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblURL.setLayoutData(gd7);
        lblURL.setText( "URL" );

        Text txtURL = new Text(composite, SWT.NONE);
        GridData gd8 = new GridData(GridData.FILL_HORIZONTAL);
        gd8.heightHint=16;
        txtURL.setLayoutData(gd8);
        
        return composite;
        
    }

}
