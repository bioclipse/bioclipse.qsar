/**
 *  Copyright (c) 2009 Ola Spjuth
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */
package net.bioclipse.qsar.impl;

import java.util.Collection;

import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.DescriptorresultlistsType;
import net.bioclipse.qsar.QsarPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Descriptorresultlists Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorresultlistsTypeImpl#getDescriptorresult <em>Descriptorresult</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptorresultlistsTypeImpl extends EObjectImpl implements DescriptorresultlistsType {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached value of the '{@link #getDescriptorresult() <em>Descriptorresult</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorresult()
	 * @generated
	 * @ordered
	 */
    protected EList<DescriptorresultType> descriptorresult;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected DescriptorresultlistsTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return QsarPackage.Literals.DESCRIPTORRESULTLISTS_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<DescriptorresultType> getDescriptorresult() {
		if (descriptorresult == null) {
			descriptorresult = new EObjectContainmentEList<DescriptorresultType>(DescriptorresultType.class, this, QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT);
		}
		return descriptorresult;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT:
				return ((InternalEList<?>)getDescriptorresult()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT:
				return getDescriptorresult();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT:
				getDescriptorresult().clear();
				getDescriptorresult().addAll((Collection<? extends DescriptorresultType>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT:
				getDescriptorresult().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT:
				return descriptorresult != null && !descriptorresult.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DescriptorresultlistsTypeImpl
