/*******************************************************************************
 *Copyright (c) 2010 The Bioclipse Team and others.
 *              2010 Egon Willighagen <egonw@users.sf.net>
 *
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    Ola Spjuth - initial API and implementation
 *******************************************************************************/
package net.bioclipse.qsar.ui.wizards;

import java.util.List;

import net.bioclipse.chembl.Activator;
import net.bioclipse.chembl.business.IChEMBLManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.rdf.model.IStringMatrix;

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
 * @author ola
 */
public class WizardChEMBLPage extends WizardPage {

	private Label targetName;
	private Label targetType;
	private Label targetOrganism;
	private Combo cboAutobuild;
	
	private IChEMBLManager chembl;
	
    protected WizardChEMBLPage(String pageName) {
        super( pageName );
        chembl = Activator.getDefault().getJavaChEMBLManager();
    }

    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        final GridLayout layout = new GridLayout(3, false);
        container.setLayout(layout);
        setControl(container);
        
        Label label = new Label(container, SWT.NONE);
        GridData gridData = new GridData(GridData.FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalSpan = 3;
        label.setLayoutData(gridData);
        label.setText("ChEMBL Target ID (Integer)");
        
        Text targetField = new Text(container, SWT.BORDER);
        targetField.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                updatePageComplete((Text)e.getSource());
            }
        });
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        targetField.setLayoutData(gridData);

        cboAutobuild = new Combo(container, SWT.NONE);
        GridData gdAutoBuild=new GridData();
        gdAutoBuild.widthHint=100;
        cboAutobuild.setLayoutData(gdAutoBuild);
        addDefaultActivities();
        cboAutobuild.select( 0 );

        cboAutobuild.addSelectionListener( new SelectionListener(){

            public void widgetDefaultSelected( SelectionEvent e ) {
            }

            public void widgetSelected( SelectionEvent e ) {
                Combo cbo=(Combo) e.getSource();
                String selectedType = cbo.getItem(cbo.getSelectionIndex());
                ((NewQSARProjectFromChEMBLWizard)getWizard()).setResponseType(
                	selectedType
                );
            }
        });
        
        label = new Label(container, SWT.NONE);
        GridData grLayout = new GridData(GridData.BEGINNING);
        grLayout.horizontalSpan = 1;
        label.setLayoutData(grLayout);
        label.setText("Name: ");
        targetName = new Label(container, SWT.BORDER);
        grLayout = new GridData(GridData.FILL_HORIZONTAL);
        grLayout.grabExcessHorizontalSpace = true;
        grLayout.horizontalSpan = 2;
        targetName.setLayoutData(grLayout);

        label = new Label(container, SWT.NONE);
        grLayout = new GridData(GridData.BEGINNING);
        grLayout.horizontalSpan = 1;
        label.setLayoutData(grLayout);
        label.setText("Type: ");
        targetType = new Label(container, SWT.BORDER);
        grLayout = new GridData(GridData.FILL_HORIZONTAL);
        grLayout.grabExcessHorizontalSpace = true;
        grLayout.horizontalSpan = 2;
        targetType.setLayoutData(grLayout);

        label = new Label(container, SWT.NONE);
        grLayout = new GridData(GridData.BEGINNING);
        grLayout.horizontalSpan = 1;
        label.setLayoutData(grLayout);
        label.setText("Organism: ");
        targetOrganism = new Label(container, SWT.BORDER);
        grLayout = new GridData(GridData.FILL_HORIZONTAL);
        grLayout.grabExcessHorizontalSpace = true;
        grLayout.horizontalSpan = 2;
        targetOrganism.setLayoutData(grLayout);

    }

	private void addDefaultActivities() {
		cboAutobuild.add( "IC50" );
        cboAutobuild.add( "KD" );
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

            // try to get some information on the target
            IStringMatrix matrix = chembl.getProperties(t);
            if (matrix != null && matrix.getRowCount() > 0) {
            	targetName.setText(matrix.get(1, "title"));
            	targetType.setText(
            	    type2String(matrix.get(1, "type"))
            	);
            	targetOrganism.setText(matrix.get(1, "organism"));
            } else {
            	// make fields empty if there is no hit
            	targetName.setText("");
            	targetType.setText("");
            	targetOrganism.setText("");
            	cboAutobuild.removeAll();
            	
            	setErrorMessage("No hits found.");
            	setPageComplete( false );
                getWizard().getContainer().updateButtons();
            }

            List<String> activities = chembl.getActivities(t);
            if (activities != null && activities.size() != 0) {
            	// add actual values for the given target
                cboAutobuild.removeAll();
                for (String act : activities) {
                	cboAutobuild.add(act);
                }
                cboAutobuild.select(0);

                // update the wizard
                ((NewQSARProjectFromChEMBLWizard)getWizard()).setResponseType(
                	cboAutobuild.getItem(0)
                );
            }

            // if all succeeded...
            setErrorMessage(null);
        } catch (NumberFormatException e) {
            setErrorMessage("The TargetID must be a number (Integer).");
            getWizard().getContainer().updateButtons();
            return;
        } catch (BioclipseException e) {
        	setErrorMessage("Could not update target information.");
		}

        setPageComplete( true );
        getWizard().getContainer().updateButtons();
    }
    
	private String type2String(String string) {
    	String result = string.substring(string.lastIndexOf('/') + 1);
    	return result.toLowerCase();
	}

	@Override
    public boolean isPageComplete() {
        if (getErrorMessage()!=null) return false;
        return super.isPageComplete();
    }

}
