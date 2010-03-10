/*******************************************************************************
 *Copyright (c) 2010 The Bioclipse Team and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    Ola Spjuth - initial API and implementation
 *******************************************************************************/
package net.bioclipse.qsar.ui.wizards;

import net.bioclipse.qsar.ui.QsarHelper;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author ola
 *
 */
public class WizardChEMBLPage extends WizardPage {

    protected WizardChEMBLPage(String pageName) {

        super( pageName );
    }

    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        final GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        container.setLayout(layout);
        setControl(container);
        
        final Label label = new Label(container, SWT.NONE);
        final GridData gridData = new GridData();
        gridData.horizontalSpan = 3;
        label.setLayoutData(gridData);
        label.setText("ChEMBL Target ID (Integer)");
        
        Text targetField = new Text(container, SWT.BORDER);
        targetField.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                updatePageComplete((Text)e.getSource());
            }
        });
        targetField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Combo cboAutobuild = new Combo(container, SWT.NONE);
        GridData gdAutoBuild=new GridData();
        gdAutoBuild.widthHint=70;
        cboAutobuild.setLayoutData(gdAutoBuild);
        cboAutobuild.add( "IC50" );
        cboAutobuild.add( "KD" );
        cboAutobuild.select( 0 );

        cboAutobuild.addSelectionListener( new SelectionListener(){

            public void widgetDefaultSelected( SelectionEvent e ) {
            }

            public void widgetSelected( SelectionEvent e ) {
                Combo cbo=(Combo) e.getSource();
                if (cbo.getSelectionIndex()==0){
                    ((NewQSARProjectFromChEMBLWizard)getWizard()).setResponseType( "IC50" );
                }
                else{
                    ((NewQSARProjectFromChEMBLWizard)getWizard()).setResponseType( "KD" );
                }
            }
        });
        


        
    }

    private void updatePageComplete(Text field) {
        String targetString = field.getText();
        if (targetString.length() <= 0) {
            setErrorMessage("The TargetID is a required field.");
            getWizard().getContainer().updateButtons();
            return;
        }

        try{
            Integer t=Integer.parseInt( targetString );
            ((NewQSARProjectFromChEMBLWizard)getWizard()).setTargetID( t );
        }catch (NumberFormatException e){
            setErrorMessage("The TargetID must be a number (Integer).");
            getWizard().getContainer().updateButtons();
            return;
        }
        setErrorMessage(null);
        setPageComplete( true );
        getWizard().getContainer().updateButtons();
    }
    
    @Override
    public boolean isPageComplete() {
        if (getErrorMessage()!=null) return false;
        return super.isPageComplete();
    }

}
