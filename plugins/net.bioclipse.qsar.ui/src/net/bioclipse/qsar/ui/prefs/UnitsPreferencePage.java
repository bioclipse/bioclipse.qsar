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

import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;
import net.bioclipse.qsar.prefs.QsarPreferenceHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * 
 * @author ola
 *
 */
public class UnitsPreferencePage extends FieldEditorPreferencePage 
                                         implements IWorkbenchPreferencePage {

    public UnitsPreferencePage() {
        super(FieldEditorPreferencePage.GRID);

        // Set the preference store for the preference page.
        IPreferenceStore store =
            Activator.getDefault().getPreferenceStore();
        setPreferenceStore(store);
    }
    
    
    @Override
    protected void createFieldEditors() {
        
        ListEditor filesEditor = new ListEditor(QSARConstants.QSAR_UNITS_PREFERENCE,
                                                "&Additional units", getFieldEditorParent()){

            @Override
            protected String createList( String[] items ) {
                
                return QsarPreferenceHelper.createQsarPreferenceStringFromItems(items);
            }
            
            

            @Override
            protected String getNewInputObject() {
                
                CreateNewUnitDialog dlg=new CreateNewUnitDialog(getShell());
                int ret=dlg.open();
                if (ret==Window.CANCEL)
                    return null;

                ResponseUnit newUnit=dlg.getResponseUnit();
                if (newUnit==null)
                return null;

                //Ok, we have a new unit, convert to string
                String str=QsarPreferenceHelper
                         .createQsarPreferenceStringFromResponseUnit( newUnit );
                
                return str;
            }

            @Override
            protected String[] parseString( String stringList ) {
                return QsarPreferenceHelper.parseQsarPreferenceString(stringList);
            }
            
        };

        addField(filesEditor);
        GridData gd=new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint=200;
        filesEditor.getListControl(getFieldEditorParent()).setLayoutData(gd);
        
    }

    public void init( IWorkbench workbench ) {
    }
    
    @Override
    public boolean performOk() {
        boolean ret = super.performOk();
        Activator.getDefault().getQsarManager().updateUnits();
        return ret;
    }

}
