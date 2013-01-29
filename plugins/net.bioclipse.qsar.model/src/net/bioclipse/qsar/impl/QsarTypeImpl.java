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

import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DescriptorproviderType;
import net.bioclipse.qsar.DescriptorresultlistsType;
import net.bioclipse.qsar.MetadataType;
import net.bioclipse.qsar.PreprocessingType;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResponsesListType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.StructurelistType;

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
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getStructurelist <em>Structurelist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getDescriptorlist <em>Descriptorlist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getDescriptorproviders <em>Descriptorproviders</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getPreprocessing <em>Preprocessing</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getResponselist <em>Responselist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getResponseunit <em>Responseunit</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.QsarTypeImpl#getDescriptorresultlist <em>Descriptorresultlist</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QsarTypeImpl extends EObjectImpl implements QsarType {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached value of the '{@link #getStructurelist() <em>Structurelist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStructurelist()
	 * @generated
	 * @ordered
	 */
    protected StructurelistType structurelist;

    /**
	 * The cached value of the '{@link #getDescriptorlist() <em>Descriptorlist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorlist()
	 * @generated
	 * @ordered
	 */
    protected DescriptorlistType descriptorlist;

    /**
	 * The cached value of the '{@link #getDescriptorproviders() <em>Descriptorproviders</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorproviders()
	 * @generated
	 * @ordered
	 */
    protected EList<DescriptorproviderType> descriptorproviders;

    /**
	 * The cached value of the '{@link #getPreprocessing() <em>Preprocessing</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPreprocessing()
	 * @generated
	 * @ordered
	 */
    protected PreprocessingType preprocessing;

    /**
	 * The cached value of the '{@link #getResponselist() <em>Responselist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResponselist()
	 * @generated
	 * @ordered
	 */
    protected ResponsesListType responselist;

    /**
	 * The cached value of the '{@link #getResponseunit() <em>Responseunit</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResponseunit()
	 * @generated
	 * @ordered
	 */
    protected EList<ResponseunitType> responseunit;

    /**
	 * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMetadata()
	 * @generated
	 * @ordered
	 */
    protected MetadataType metadata;

    /**
	 * The cached value of the '{@link #getDescriptorresultlist() <em>Descriptorresultlist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptorresultlist()
	 * @generated
	 * @ordered
	 */
    protected DescriptorresultlistsType descriptorresultlist;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected QsarTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return QsarPackage.Literals.QSAR_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public StructurelistType getStructurelist() {
		return structurelist;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStructurelist(StructurelistType newStructurelist, NotificationChain msgs) {
		StructurelistType oldStructurelist = structurelist;
		structurelist = newStructurelist;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__STRUCTURELIST, oldStructurelist, newStructurelist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setStructurelist(StructurelistType newStructurelist) {
		if (newStructurelist != structurelist) {
			NotificationChain msgs = null;
			if (structurelist != null)
				msgs = ((InternalEObject)structurelist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__STRUCTURELIST, null, msgs);
			if (newStructurelist != null)
				msgs = ((InternalEObject)newStructurelist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__STRUCTURELIST, null, msgs);
			msgs = basicSetStructurelist(newStructurelist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__STRUCTURELIST, newStructurelist, newStructurelist));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorlistType getDescriptorlist() {
		return descriptorlist;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDescriptorlist(DescriptorlistType newDescriptorlist, NotificationChain msgs) {
		DescriptorlistType oldDescriptorlist = descriptorlist;
		descriptorlist = newDescriptorlist;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__DESCRIPTORLIST, oldDescriptorlist, newDescriptorlist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescriptorlist(DescriptorlistType newDescriptorlist) {
		if (newDescriptorlist != descriptorlist) {
			NotificationChain msgs = null;
			if (descriptorlist != null)
				msgs = ((InternalEObject)descriptorlist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__DESCRIPTORLIST, null, msgs);
			if (newDescriptorlist != null)
				msgs = ((InternalEObject)newDescriptorlist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__DESCRIPTORLIST, null, msgs);
			msgs = basicSetDescriptorlist(newDescriptorlist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__DESCRIPTORLIST, newDescriptorlist, newDescriptorlist));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<DescriptorproviderType> getDescriptorproviders() {
		if (descriptorproviders == null) {
			descriptorproviders = new EObjectContainmentEList<DescriptorproviderType>(DescriptorproviderType.class, this, QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS);
		}
		return descriptorproviders;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PreprocessingType getPreprocessing() {
		return preprocessing;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPreprocessing(PreprocessingType newPreprocessing, NotificationChain msgs) {
		PreprocessingType oldPreprocessing = preprocessing;
		preprocessing = newPreprocessing;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__PREPROCESSING, oldPreprocessing, newPreprocessing);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPreprocessing(PreprocessingType newPreprocessing) {
		if (newPreprocessing != preprocessing) {
			NotificationChain msgs = null;
			if (preprocessing != null)
				msgs = ((InternalEObject)preprocessing).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__PREPROCESSING, null, msgs);
			if (newPreprocessing != null)
				msgs = ((InternalEObject)newPreprocessing).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__PREPROCESSING, null, msgs);
			msgs = basicSetPreprocessing(newPreprocessing, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__PREPROCESSING, newPreprocessing, newPreprocessing));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResponsesListType getResponselist() {
		return responselist;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetResponselist(ResponsesListType newResponselist, NotificationChain msgs) {
		ResponsesListType oldResponselist = responselist;
		responselist = newResponselist;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__RESPONSELIST, oldResponselist, newResponselist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setResponselist(ResponsesListType newResponselist) {
		if (newResponselist != responselist) {
			NotificationChain msgs = null;
			if (responselist != null)
				msgs = ((InternalEObject)responselist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__RESPONSELIST, null, msgs);
			if (newResponselist != null)
				msgs = ((InternalEObject)newResponselist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__RESPONSELIST, null, msgs);
			msgs = basicSetResponselist(newResponselist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__RESPONSELIST, newResponselist, newResponselist));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<ResponseunitType> getResponseunit() {
		if (responseunit == null) {
			responseunit = new EObjectContainmentEList<ResponseunitType>(ResponseunitType.class, this, QsarPackage.QSAR_TYPE__RESPONSEUNIT);
		}
		return responseunit;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MetadataType getMetadata() {
		return metadata;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMetadata(MetadataType newMetadata, NotificationChain msgs) {
		MetadataType oldMetadata = metadata;
		metadata = newMetadata;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__METADATA, oldMetadata, newMetadata);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMetadata(MetadataType newMetadata) {
		if (newMetadata != metadata) {
			NotificationChain msgs = null;
			if (metadata != null)
				msgs = ((InternalEObject)metadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__METADATA, null, msgs);
			if (newMetadata != null)
				msgs = ((InternalEObject)newMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__METADATA, null, msgs);
			msgs = basicSetMetadata(newMetadata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__METADATA, newMetadata, newMetadata));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorresultlistsType getDescriptorresultlist() {
		return descriptorresultlist;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDescriptorresultlist(DescriptorresultlistsType newDescriptorresultlist, NotificationChain msgs) {
		DescriptorresultlistsType oldDescriptorresultlist = descriptorresultlist;
		descriptorresultlist = newDescriptorresultlist;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST, oldDescriptorresultlist, newDescriptorresultlist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescriptorresultlist(DescriptorresultlistsType newDescriptorresultlist) {
		if (newDescriptorresultlist != descriptorresultlist) {
			NotificationChain msgs = null;
			if (descriptorresultlist != null)
				msgs = ((InternalEObject)descriptorresultlist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST, null, msgs);
			if (newDescriptorresultlist != null)
				msgs = ((InternalEObject)newDescriptorresultlist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST, null, msgs);
			msgs = basicSetDescriptorresultlist(newDescriptorresultlist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST, newDescriptorresultlist, newDescriptorresultlist));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QsarPackage.QSAR_TYPE__STRUCTURELIST:
				return basicSetStructurelist(null, msgs);
			case QsarPackage.QSAR_TYPE__DESCRIPTORLIST:
				return basicSetDescriptorlist(null, msgs);
			case QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS:
				return ((InternalEList<?>)getDescriptorproviders()).basicRemove(otherEnd, msgs);
			case QsarPackage.QSAR_TYPE__PREPROCESSING:
				return basicSetPreprocessing(null, msgs);
			case QsarPackage.QSAR_TYPE__RESPONSELIST:
				return basicSetResponselist(null, msgs);
			case QsarPackage.QSAR_TYPE__RESPONSEUNIT:
				return ((InternalEList<?>)getResponseunit()).basicRemove(otherEnd, msgs);
			case QsarPackage.QSAR_TYPE__METADATA:
				return basicSetMetadata(null, msgs);
			case QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST:
				return basicSetDescriptorresultlist(null, msgs);
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
			case QsarPackage.QSAR_TYPE__STRUCTURELIST:
				return getStructurelist();
			case QsarPackage.QSAR_TYPE__DESCRIPTORLIST:
				return getDescriptorlist();
			case QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS:
				return getDescriptorproviders();
			case QsarPackage.QSAR_TYPE__PREPROCESSING:
				return getPreprocessing();
			case QsarPackage.QSAR_TYPE__RESPONSELIST:
				return getResponselist();
			case QsarPackage.QSAR_TYPE__RESPONSEUNIT:
				return getResponseunit();
			case QsarPackage.QSAR_TYPE__METADATA:
				return getMetadata();
			case QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST:
				return getDescriptorresultlist();
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
			case QsarPackage.QSAR_TYPE__STRUCTURELIST:
				setStructurelist((StructurelistType)newValue);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORLIST:
				setDescriptorlist((DescriptorlistType)newValue);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS:
				getDescriptorproviders().clear();
				getDescriptorproviders().addAll((Collection<? extends DescriptorproviderType>)newValue);
				return;
			case QsarPackage.QSAR_TYPE__PREPROCESSING:
				setPreprocessing((PreprocessingType)newValue);
				return;
			case QsarPackage.QSAR_TYPE__RESPONSELIST:
				setResponselist((ResponsesListType)newValue);
				return;
			case QsarPackage.QSAR_TYPE__RESPONSEUNIT:
				getResponseunit().clear();
				getResponseunit().addAll((Collection<? extends ResponseunitType>)newValue);
				return;
			case QsarPackage.QSAR_TYPE__METADATA:
				setMetadata((MetadataType)newValue);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST:
				setDescriptorresultlist((DescriptorresultlistsType)newValue);
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
			case QsarPackage.QSAR_TYPE__STRUCTURELIST:
				setStructurelist((StructurelistType)null);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORLIST:
				setDescriptorlist((DescriptorlistType)null);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS:
				getDescriptorproviders().clear();
				return;
			case QsarPackage.QSAR_TYPE__PREPROCESSING:
				setPreprocessing((PreprocessingType)null);
				return;
			case QsarPackage.QSAR_TYPE__RESPONSELIST:
				setResponselist((ResponsesListType)null);
				return;
			case QsarPackage.QSAR_TYPE__RESPONSEUNIT:
				getResponseunit().clear();
				return;
			case QsarPackage.QSAR_TYPE__METADATA:
				setMetadata((MetadataType)null);
				return;
			case QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST:
				setDescriptorresultlist((DescriptorresultlistsType)null);
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
			case QsarPackage.QSAR_TYPE__STRUCTURELIST:
				return structurelist != null;
			case QsarPackage.QSAR_TYPE__DESCRIPTORLIST:
				return descriptorlist != null;
			case QsarPackage.QSAR_TYPE__DESCRIPTORPROVIDERS:
				return descriptorproviders != null && !descriptorproviders.isEmpty();
			case QsarPackage.QSAR_TYPE__PREPROCESSING:
				return preprocessing != null;
			case QsarPackage.QSAR_TYPE__RESPONSELIST:
				return responselist != null;
			case QsarPackage.QSAR_TYPE__RESPONSEUNIT:
				return responseunit != null && !responseunit.isEmpty();
			case QsarPackage.QSAR_TYPE__METADATA:
				return metadata != null;
			case QsarPackage.QSAR_TYPE__DESCRIPTORRESULTLIST:
				return descriptorresultlist != null;
		}
		return super.eIsSet(featureID);
	}

} //QsarTypeImpl
