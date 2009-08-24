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
package net.bioclipse.qsar.prefs;

import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.business.QsarManager;
import net.bioclipse.qsar.init.Activator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;

public class QSARPreferenceInitializer extends AbstractPreferenceInitializer {

    private static final Logger logger = Logger.getLogger(QSARPreferenceInitializer.class);

	public QSARPreferenceInitializer() {
		super();
	}

    @Override
    public void initializeDefaultPreferences() {

    	//Read available providers from EP
		String defstr=QsarPreferenceHelper.getAvailableProvidersFromEP();
		
		logger.debug( "Read availiable descriptors from ExPo: " + defstr );

		
    IPreferenceStore store =
        Activator.getDefault().getPreferenceStore();
    
    store.setDefault( 
              QSARConstants.QSAR_PROVIDERS_ORDER_PREFERENCE, 
              defstr);

    logger.debug( "Set qsar providers order prefs to: " + defstr );

    }
    
    

}
