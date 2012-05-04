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

import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.ParameterType;
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
 * An implementation of the model object '<em><b>Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorTypeImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorTypeImpl#getOntologyid <em>Ontologyid</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.DescriptorTypeImpl#getProvider <em>Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptorTypeImpl extends EObjectImpl implements DescriptorType {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
    protected EList<ParameterType> parameter;

    /**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected static final String ID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected String id = ID_EDEFAULT;

    /**
	 * The default value of the '{@link #getOntologyid() <em>Ontologyid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOntologyid()
	 * @generated
	 * @ordered
	 */
    protected static final String ONTOLOGYID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getOntologyid() <em>Ontologyid</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOntologyid()
	 * @generated
	 * @ordered
	 */
    protected String ontologyid = ONTOLOGYID_EDEFAULT;

    /**
	 * The default value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
    protected static final String PROVIDER_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
    protected String provider = PROVIDER_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected DescriptorTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return QsarPackage.Literals.DESCRIPTOR_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<ParameterType> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<ParameterType>(ParameterType.class, this, QsarPackage.DESCRIPTOR_TYPE__PARAMETER);
		}
		return parameter;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getId() {
		return id;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTOR_TYPE__ID, oldId, id));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getOntologyid() {
		return ontologyid;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOntologyid(String newOntologyid) {
		String oldOntologyid = ontologyid;
		ontologyid = newOntologyid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTOR_TYPE__ONTOLOGYID, oldOntologyid, ontologyid));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getProvider() {
		return provider;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.DESCRIPTOR_TYPE__PROVIDER, oldProvider, provider));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QsarPackage.DESCRIPTOR_TYPE__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
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
			case QsarPackage.DESCRIPTOR_TYPE__PARAMETER:
				return getParameter();
			case QsarPackage.DESCRIPTOR_TYPE__ID:
				return getId();
			case QsarPackage.DESCRIPTOR_TYPE__ONTOLOGYID:
				return getOntologyid();
			case QsarPackage.DESCRIPTOR_TYPE__PROVIDER:
				return getProvider();
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
			case QsarPackage.DESCRIPTOR_TYPE__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends ParameterType>)newValue);
				return;
			case QsarPackage.DESCRIPTOR_TYPE__ID:
				setId((String)newValue);
				return;
			case QsarPackage.DESCRIPTOR_TYPE__ONTOLOGYID:
				setOntologyid((String)newValue);
				return;
			case QsarPackage.DESCRIPTOR_TYPE__PROVIDER:
				setProvider((String)newValue);
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
			case QsarPackage.DESCRIPTOR_TYPE__PARAMETER:
				getParameter().clear();
				return;
			case QsarPackage.DESCRIPTOR_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case QsarPackage.DESCRIPTOR_TYPE__ONTOLOGYID:
				setOntologyid(ONTOLOGYID_EDEFAULT);
				return;
			case QsarPackage.DESCRIPTOR_TYPE__PROVIDER:
				setProvider(PROVIDER_EDEFAULT);
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
			case QsarPackage.DESCRIPTOR_TYPE__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case QsarPackage.DESCRIPTOR_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case QsarPackage.DESCRIPTOR_TYPE__ONTOLOGYID:
				return ONTOLOGYID_EDEFAULT == null ? ontologyid != null : !ONTOLOGYID_EDEFAULT.equals(ontologyid);
			case QsarPackage.DESCRIPTOR_TYPE__PROVIDER:
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", ontologyid: ");
		result.append(ontologyid);
		result.append(", provider: ");
		result.append(provider);
		result.append(')');
		return result.toString();
	}

} //DescriptorTypeImpl
