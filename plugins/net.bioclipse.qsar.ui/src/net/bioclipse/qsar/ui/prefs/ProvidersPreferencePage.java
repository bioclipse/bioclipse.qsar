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
import net.bioclipse.qsar.init.Activator;
import net.bioclipse.qsar.prefs.QSARPreferenceInitializer;
import net.bioclipse.qsar.prefs.QsarPreferenceHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * A preference page for ordering descriptorProviders
 * @author ola
 *
 */
public class ProvidersPreferencePage extends FieldEditorPreferencePage 
implements IWorkbenchPreferencePage {


	
	public ProvidersPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		
		// Set the preference store for the preference page.
		IPreferenceStore store =
			Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}
	
	@Override
	protected void createFieldEditors() {

		
		UpDownListEditor listeditor=new UpDownListEditor(QSARConstants.QSAR_PROVIDERS_ORDER_PREFERENCE,
				"&Order of DescriptorProviders", getFieldEditorParent()){

			@Override
			protected String createList(String[] items) {
				return QsarPreferenceHelper.createQsarPreferenceStringFromItems(items);
			}


			@Override
			protected String[] parseString(String stringList) {
				return QsarPreferenceHelper.parseQsarPreferenceString(stringList);
			}
			
		};
		
		addField(listeditor);
		GridData gd=new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=200;
		listeditor.getListControl(getFieldEditorParent()).setLayoutData(gd);

		
	}

	public void init(IWorkbench workbench) {
	}
	


}
