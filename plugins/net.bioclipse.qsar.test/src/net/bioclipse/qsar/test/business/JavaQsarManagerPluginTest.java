/*******************************************************************************
 * Copyright (c) 2011  Egon Willighagen <egon.willighagen@ki.se>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * www.eclipse.org—epl-v10.html <http://www.eclipse.org/legal/epl-v10.html>
 * 
 * Contact: http://www.bioclipse.net/    
 ******************************************************************************/
package net.bioclipse.qsar.test.business;

import net.bioclipse.managers.business.IBioclipseManager;

import org.junit.BeforeClass;

public class JavaQsarManagerPluginTest
    extends AbstractQsarManagerPluginTest {

    @BeforeClass public static void setup() {
        qsar = net.bioclipse.qsar.init.Activator.getDefault()
            .getJavaQsarManager();
    }

	@Override
	public IBioclipseManager getManager() {
		return qsar;
	}

}
