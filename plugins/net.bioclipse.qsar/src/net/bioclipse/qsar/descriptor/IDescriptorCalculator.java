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
package net.bioclipse.qsar.descriptor;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.qsar.DescriptorType;

public interface IDescriptorCalculator {

    /**
     * Calculate a set of descriptors for each molecule.
     * 
     * @param moldesc Map from IMolecule to a list of Descriptortypes
     * @param monitor Progressmonitor. Set worked++ for each molecule/descriptor combo calculated
     * @return Map<IMolecule, IDescriptorResult> results for each molecule
     * @throws BioclipseException 
     */
    public Map<? extends IMolecule, List<IDescriptorResult>> calculateDescriptor(
             Map<IMolecule, List<DescriptorType>> moldesc,
             IProgressMonitor monitor ) throws BioclipseException;
}
