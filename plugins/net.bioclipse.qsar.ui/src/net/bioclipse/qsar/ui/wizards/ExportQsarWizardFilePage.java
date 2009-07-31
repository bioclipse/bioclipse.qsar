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


import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.internal.ide.dialogs.FileFolderSelectionDialog;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;


public class ExportQsarWizardFilePage extends WizardPage implements IWizardPage {

	private ExportQsarWizard wiz;
	private Text textFilePath;

	protected ExportQsarWizardFilePage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {

		wiz=(ExportQsarWizard)getWizard();
		
		Composite body = new Composite(parent, SWT.NONE);
		Layout l=new GridLayout(3, false);
		body.setLayout(l);
//		GridData gd= new GridData(GridData.FILL_BOTH);
//		body.setLayoutData(gd);
		
		Label lbl=new Label(body, SWT.NONE);
		lbl.setText("Filename: ");
		GridData gdlbl= new GridData();
		gdlbl.widthHint=100;
		lbl.setLayoutData(gdlbl);
		
		textFilePath=new Text(body, SWT.BORDER);
		GridData gdtxt= new GridData(GridData.FILL_HORIZONTAL);
		textFilePath.setLayoutData(gdtxt);

		Button browse = new Button(body, SWT.NONE);
		browse.setText("Browse...");
		GridData gdbtn= new GridData();
		browse.setLayoutData(gdbtn);
		gdbtn.widthHint=100;
		browse.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent e) {
				
			    FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.zip;", "*.*" });
			    dialog.setText("Export QSAR project to Archive");
			    String currentSourceString = getDestinationValue();
			    int lastSeparatorIndex = currentSourceString.lastIndexOf(File.separator);
			    if (lastSeparatorIndex != -1) {

              dialog.setFilterPath(currentSourceString.substring(0,
                                                                 lastSeparatorIndex));
			    }
			    String selectedFileName = dialog.open();

			    if (selectedFileName != null) {
			        setErrorMessage(null);
			        setDestinationValue(selectedFileName);
			    }
			    else if (textFilePath.getText()==null){
              setErrorMessage("Please specify a file");
			    }


				getWizard().getContainer().updateButtons();
				
			}
			
		});

		setControl(body);
		
	}

    public void setDestinationValue( String destinationText ) {

        if (destinationText.length() != 0
                && !destinationText.endsWith(File.separator)) {
            int dotIndex = destinationText.lastIndexOf('.');
            if (dotIndex != -1) {
                // the last path seperator index
                int pathSepIndex = destinationText.lastIndexOf(File.separator);
                if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
                    destinationText += ".zip";
                }
            } else {
                destinationText += ".zip";
            }
        }
        
        textFilePath.setText( destinationText );
        wiz.setFilename( destinationText );
    }

    public String getDestinationValue() {
        
        if ("".equals( textFilePath.getText())){
            if (System.getProperty( "user.home" )==null && 
                    !("".equals( System.getProperty( "user.home" ) )))
                return File.separator;
            return System.getProperty( "user.home" );
        }

        return textFilePath.getText();
    }

}
