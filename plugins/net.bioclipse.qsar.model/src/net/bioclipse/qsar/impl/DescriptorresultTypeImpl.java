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
import net.bioclipse.qsar.DescriptorvalueType;
import net.bioclipse.qsar.QsarPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Descriptorresult Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorresultTypeImpl#getDescriptorvalue <em>Descriptorvalue</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorresultTypeImpl#getDescriptorid <em>Descriptorid</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorresultTypeImpl#getErrorString <em>Error String</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorresultTypeImpl#getStructureid <em>Structureid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptorresultTypeImpl extends EObjectImpl implements DescriptorresultType {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached value of the '{@link #getDescriptorvalue() <em>Descriptorvalue</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorvalue()
	 * @generated
	 * @ordered
	 */
    protected EList<DescriptorvalueType> descriptorvalue;

    /**
	 * The default value of the '{@link #getDescriptorid() <em>Descriptorid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorid()
	 * @generated
	 * @ordered
	 */
    protected static final String DESCRIPTORID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDescriptorid() <em>Descriptorid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorid()
	 * @generated
	 * @ordered
	 */
    protected String descriptorid = DESCRIPTORID_EDEFAULT;

    /**
	 * The default value of the '{@link #getErrorString() <em>Error String</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getErrorString()
	 * @generated
	 * @ordered
	 */
    protected static final String ERROR_STRING_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getErrorString() <em>Error String</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getErrorString()
	 * @generated
	 * @ordered
	 */
    protected String errorString = ERROR_STRING_EDEFAULT;

    /**
	 * The default value of the '{@link #getStructureid() <em>Structureid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStructureid()
	 * @generated
	 * @ordered
	 */
    protected static final String STRUCTUREID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getStructureid() <em>Structureid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStructureid()
	 * @generated
	 * @ordered
	 */
    protected String structureid = STRUCTUREID_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected DescriptorresultTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return QsarPackage.Literals.DESCRIPTORRESULT_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<DescriptorvalueType> getDescriptorvalue() {
		if (descriptorvalue == null) {
			descriptorvalue = new EObjectContainmentEList<DescriptorvalueType>(DescriptorvalueType.class, this, QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE);
		}
		return descriptorvalue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDescriptorid() {
		return descriptorid;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescriptorid(String newDescriptorid) {
		String oldDescriptorid = descriptorid;
		descriptorid = newDescriptorid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORID, oldDescriptorid, descriptorid));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getErrorString() {
		return errorString;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setErrorString(String newErrorString) {
		String oldErrorString = errorString;
		errorString = newErrorString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTORRESULT_TYPE__ERROR_STRING, oldErrorString, errorString));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getStructureid() {
		return structureid;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setStructureid(String newStructureid) {
		String oldStructureid = structureid;
		structureid = newStructureid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTORRESULT_TYPE__STRUCTUREID, oldStructureid, structureid));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE:
				return ((InternalEList<?>)getDescriptorvalue()).basicRemove(otherEnd, msgs);
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
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE:
				return getDescriptorvalue();
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORID:
				return getDescriptorid();
			case QsarPackage.DESCRIPTORRESULT_TYPE__ERROR_STRING:
				return getErrorString();
			case QsarPackage.DESCRIPTORRESULT_TYPE__STRUCTUREID:
				return getStructureid();
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
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE:
				getDescriptorvalue().clear();
				getDescriptorvalue().addAll((Collection<? extends DescriptorvalueType>)newValue);
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORID:
				setDescriptorid((String)newValue);
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__ERROR_STRING:
				setErrorString((String)newValue);
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__STRUCTUREID:
				setStructureid((String)newValue);
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
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE:
				getDescriptorvalue().clear();
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORID:
				setDescriptorid(DESCRIPTORID_EDEFAULT);
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__ERROR_STRING:
				setErrorString(ERROR_STRING_EDEFAULT);
				return;
			case QsarPackage.DESCRIPTORRESULT_TYPE__STRUCTUREID:
				setStructureid(STRUCTUREID_EDEFAULT);
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
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE:
				return descriptorvalue != null && !descriptorvalue.isEmpty();
			case QsarPackage.DESCRIPTORRESULT_TYPE__DESCRIPTORID:
				return DESCRIPTORID_EDEFAULT == null ? descriptorid != null : !DESCRIPTORID_EDEFAULT.equals(descriptorid);
			case QsarPackage.DESCRIPTORRESULT_TYPE__ERROR_STRING:
				return ERROR_STRING_EDEFAULT == null ? errorString != null : !ERROR_STRING_EDEFAULT.equals(errorString);
			case QsarPackage.DESCRIPTORRESULT_TYPE__STRUCTUREID:
				return STRUCTUREID_EDEFAULT == null ? structureid != null : !STRUCTUREID_EDEFAULT.equals(structureid);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (descriptorid: ");
		result.append(descriptorid);
		result.append(", errorString: ");
		result.append(errorString);
		result.append(", structureid: ");
		result.append(structureid);
		result.append(')');
		return result.toString();
	}

} //DescriptorresultTypeImpl
