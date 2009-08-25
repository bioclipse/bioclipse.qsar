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

import java.util.List;

import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author ola
 *
 */
public class CreateNewUnitDialog extends TitleAreaDialog{

    ResponseUnit unitModel;

    private Text txtURL;
    private Text txtDescription;
    private Text txtShortName;
    private Text txtName;

    public CreateNewUnitDialog(Shell parentShell) {
        super( parentShell );
    }
    
    @Override
    protected Control createDialogArea( Composite parent ) {
        
        
        setTitle( "Add response unit" );
        
        
        // create the top level composite for the dialog area
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        layout.numColumns = 2;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setFont(parent.getFont());
        // Build the separator line
        Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL
            | SWT.SEPARATOR);
        GridData gdtr = new GridData(GridData.FILL_HORIZONTAL);
        gdtr.horizontalSpan=2;
        titleBarSeparator.setLayoutData(gdtr);

        
        
        //Create our model object
        
        

        //Name
        Label lblName = new Label(composite, SWT.NONE);
        GridData labelGD = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        labelGD.widthHint=100;
        labelGD.horizontalIndent=10;
        lblName.setLayoutData(labelGD);
        lblName.setText( "Name" );

        txtName = new Text(composite, SWT.BORDER);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//        gd.heightHint=16;
        txtName.setLayoutData(gd);
        txtName.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
            }
            public void keyReleased( KeyEvent e ) {
                updateStatus();
            }
        } );

        //Shortname
        Label lblShortName = new Label(composite, SWT.NONE);
        lblShortName.setLayoutData(labelGD);
        lblShortName.setText( "Shortname" );

        txtShortName = new Text(composite, SWT.BORDER);
        GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
        gd4.heightHint=16;
        txtShortName.setLayoutData(gd4);
        txtShortName.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
            }
            public void keyReleased( KeyEvent e ) {
                updateStatus();
            }
        } );

        //Shortname
        Label lblDescription = new Label(composite, SWT.NONE);
        lblDescription.setLayoutData(labelGD);
        lblDescription.setText( "Description" );

        txtDescription = new Text(composite, SWT.BORDER);
        GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
        gd6.heightHint=16;
        txtDescription.setLayoutData(gd6);
        txtDescription.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
            }
            public void keyReleased( KeyEvent e ) {
                updateStatus();
            }
        } );

        //Shortname
        Label lblURL = new Label(composite, SWT.NONE);
        lblURL.setLayoutData(labelGD);
        lblURL.setText( "URL" );

        txtURL = new Text(composite, SWT.BORDER);
        GridData gd8 = new GridData(GridData.FILL_HORIZONTAL);
        gd8.heightHint=16;
        txtURL.setLayoutData(gd8);
        txtURL.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
            }
            public void keyReleased( KeyEvent e ) {
                updateStatus();
            }
        } );


        return composite;
        
    }

    protected void updateStatus() {
        
        setErrorMessage( null );
        unitModel=null;

        String name=txtName.getText();
        String shortName=txtShortName.getText();
        String url=txtURL.getText();
        String desc = txtDescription.getText();
        
        if (name==null || name.length()<=0){
            setErrorMessage( "Name field must not be empty" );
            getButton(IDialogConstants.OK_ID).setEnabled( false);
            return;
        }
        
        if (shortName==null || shortName.length()<=0){
            setErrorMessage( "ShortName field must not be empty" );
            getButton(IDialogConstants.OK_ID).setEnabled( false);
            return;
        }
        
        //Make sure name and shortname are unique
        List<ResponseUnit> existingUnits = Activator.getDefault()
                                       .getJavaQsarManager().getFullResponseUnits();
        
        for (ResponseUnit eunit : existingUnits){
            if (eunit.getName().equals( name )){
                setErrorMessage( "The name: " + name + " already exist in " +
                		"another response unit" );
                getButton(IDialogConstants.OK_ID).setEnabled( false);
                return;
            }
            
            if (eunit.getShortname().equals( shortName)){
                setErrorMessage( "The short name: " + shortName+ " already exist in " +
                    "another response unit" );
                getButton(IDialogConstants.OK_ID).setEnabled( false);
                return;
            }
        }

        //Ok, valid
        unitModel=new ResponseUnit(shortName, name);
        unitModel.setShortname( shortName );
        unitModel.setUrl( url );
        unitModel.setDescription( desc );

        getButton(IDialogConstants.OK_ID).setEnabled( true);

    }

    public ResponseUnit getResponseUnit() {

        return unitModel;
    }
    
    @Override
    protected void okPressed() {
        //Make sure we have a valid responseunit, else return
        if (unitModel==null){
            updateStatus();
            return;
        }

        super.okPressed();
    }

}
