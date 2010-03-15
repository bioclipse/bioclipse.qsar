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

import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.TypeType;

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
 * An implementation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getStructure <em>Structure</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#isContainsErrors <em>Contains Errors</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#isExcluded <em>Excluded</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getFile <em>File</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getNo2d <em>No2d</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getNo3d <em>No3d</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getNoMols <em>No Mols</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.ResourceTypeImpl#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceTypeImpl extends EObjectImpl implements ResourceType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
     * The cached value of the '{@link #getStructure() <em>Structure</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStructure()
     * @generated
     * @ordered
     */
    protected EList<StructureType> structure;

    /**
     * The default value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChecksum()
     * @generated
     * @ordered
     */
    protected static final String CHECKSUM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChecksum()
     * @generated
     * @ordered
     */
    protected String checksum = CHECKSUM_EDEFAULT;

    /**
     * The default value of the '{@link #isContainsErrors() <em>Contains Errors</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isContainsErrors()
     * @generated
     * @ordered
     */
    protected static final boolean CONTAINS_ERRORS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isContainsErrors() <em>Contains Errors</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isContainsErrors()
     * @generated
     * @ordered
     */
    protected boolean containsErrors = CONTAINS_ERRORS_EDEFAULT;

    /**
     * This is true if the Contains Errors attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean containsErrorsESet;

    /**
     * The default value of the '{@link #isExcluded() <em>Excluded</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isExcluded()
     * @generated
     * @ordered
     */
    protected static final boolean EXCLUDED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isExcluded() <em>Excluded</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isExcluded()
     * @generated
     * @ordered
     */
    protected boolean excluded = EXCLUDED_EDEFAULT;

    /**
     * This is true if the Excluded attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean excludedESet;

    /**
     * The default value of the '{@link #getFile() <em>File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFile()
     * @generated
     * @ordered
     */
    protected static final String FILE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFile()
     * @generated
     * @ordered
     */
    protected String file = FILE_EDEFAULT;

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
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getNo2d() <em>No2d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNo2d()
     * @generated
     * @ordered
     */
    protected static final int NO2D_EDEFAULT = -1;

    /**
     * The cached value of the '{@link #getNo2d() <em>No2d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNo2d()
     * @generated
     * @ordered
     */
    protected int no2d = NO2D_EDEFAULT;

    /**
     * This is true if the No2d attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean no2dESet;

    /**
     * The default value of the '{@link #getNo3d() <em>No3d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNo3d()
     * @generated
     * @ordered
     */
    protected static final int NO3D_EDEFAULT = -1;

    /**
     * The cached value of the '{@link #getNo3d() <em>No3d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNo3d()
     * @generated
     * @ordered
     */
    protected int no3d = NO3D_EDEFAULT;

    /**
     * This is true if the No3d attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean no3dESet;

    /**
     * The default value of the '{@link #getNoMols() <em>No Mols</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoMols()
     * @generated
     * @ordered
     */
    protected static final int NO_MOLS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNoMols() <em>No Mols</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoMols()
     * @generated
     * @ordered
     */
    protected int noMols = NO_MOLS_EDEFAULT;

    /**
     * This is true if the No Mols attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean noMolsESet;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final TypeType TYPE_EDEFAULT = TypeType.XML;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected TypeType type = TYPE_EDEFAULT;

    /**
     * This is true if the Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean typeESet;

    /**
     * The default value of the '{@link #getURL() <em>URL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getURL()
     * @generated
     * @ordered
     */
    protected static final String URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getURL() <em>URL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getURL()
     * @generated
     * @ordered
     */
    protected String uRL = URL_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResourceTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QsarPackage.Literals.RESOURCE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<StructureType> getStructure() {
        if (structure == null) {
            structure = new EObjectContainmentEList<StructureType>(StructureType.class, this, QsarPackage.RESOURCE_TYPE__STRUCTURE);
        }
        return structure;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChecksum(String newChecksum) {
        String oldChecksum = checksum;
        checksum = newChecksum;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__CHECKSUM, oldChecksum, checksum));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isContainsErrors() {
        return containsErrors;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setContainsErrors(boolean newContainsErrors) {
        boolean oldContainsErrors = containsErrors;
        containsErrors = newContainsErrors;
        boolean oldContainsErrorsESet = containsErrorsESet;
        containsErrorsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS, oldContainsErrors, containsErrors, !oldContainsErrorsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetContainsErrors() {
        boolean oldContainsErrors = containsErrors;
        boolean oldContainsErrorsESet = containsErrorsESet;
        containsErrors = CONTAINS_ERRORS_EDEFAULT;
        containsErrorsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS, oldContainsErrors, CONTAINS_ERRORS_EDEFAULT, oldContainsErrorsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetContainsErrors() {
        return containsErrorsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isExcluded() {
        return excluded;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExcluded(boolean newExcluded) {
        boolean oldExcluded = excluded;
        excluded = newExcluded;
        boolean oldExcludedESet = excludedESet;
        excludedESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__EXCLUDED, oldExcluded, excluded, !oldExcludedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExcluded() {
        boolean oldExcluded = excluded;
        boolean oldExcludedESet = excludedESet;
        excluded = EXCLUDED_EDEFAULT;
        excludedESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__EXCLUDED, oldExcluded, EXCLUDED_EDEFAULT, oldExcludedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExcluded() {
        return excludedESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFile() {
        return file;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFile(String newFile) {
        String oldFile = file;
        file = newFile;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__FILE, oldFile, file));
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
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNo2d() {
        return no2d;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNo2d(int newNo2d) {
        int oldNo2d = no2d;
        no2d = newNo2d;
        boolean oldNo2dESet = no2dESet;
        no2dESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__NO2D, oldNo2d, no2d, !oldNo2dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNo2d() {
        int oldNo2d = no2d;
        boolean oldNo2dESet = no2dESet;
        no2d = NO2D_EDEFAULT;
        no2dESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__NO2D, oldNo2d, NO2D_EDEFAULT, oldNo2dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNo2d() {
        return no2dESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNo3d() {
        return no3d;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNo3d(int newNo3d) {
        int oldNo3d = no3d;
        no3d = newNo3d;
        boolean oldNo3dESet = no3dESet;
        no3dESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__NO3D, oldNo3d, no3d, !oldNo3dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNo3d() {
        int oldNo3d = no3d;
        boolean oldNo3dESet = no3dESet;
        no3d = NO3D_EDEFAULT;
        no3dESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__NO3D, oldNo3d, NO3D_EDEFAULT, oldNo3dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNo3d() {
        return no3dESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNoMols() {
        return noMols;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNoMols(int newNoMols) {
        int oldNoMols = noMols;
        noMols = newNoMols;
        boolean oldNoMolsESet = noMolsESet;
        noMolsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__NO_MOLS, oldNoMols, noMols, !oldNoMolsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNoMols() {
        int oldNoMols = noMols;
        boolean oldNoMolsESet = noMolsESet;
        noMols = NO_MOLS_EDEFAULT;
        noMolsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__NO_MOLS, oldNoMols, NO_MOLS_EDEFAULT, oldNoMolsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNoMols() {
        return noMolsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TypeType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(TypeType newType) {
        TypeType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        boolean oldTypeESet = typeESet;
        typeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__TYPE, oldType, type, !oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetType() {
        TypeType oldType = type;
        boolean oldTypeESet = typeESet;
        type = TYPE_EDEFAULT;
        typeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.RESOURCE_TYPE__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetType() {
        return typeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getURL() {
        return uRL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setURL(String newURL) {
        String oldURL = uRL;
        uRL = newURL;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.RESOURCE_TYPE__URL, oldURL, uRL));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case QsarPackage.RESOURCE_TYPE__STRUCTURE:
                return ((InternalEList<?>)getStructure()).basicRemove(otherEnd, msgs);
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
            case QsarPackage.RESOURCE_TYPE__STRUCTURE:
                return getStructure();
            case QsarPackage.RESOURCE_TYPE__CHECKSUM:
                return getChecksum();
            case QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS:
                return isContainsErrors();
            case QsarPackage.RESOURCE_TYPE__EXCLUDED:
                return isExcluded();
            case QsarPackage.RESOURCE_TYPE__FILE:
                return getFile();
            case QsarPackage.RESOURCE_TYPE__ID:
                return getId();
            case QsarPackage.RESOURCE_TYPE__NAME:
                return getName();
            case QsarPackage.RESOURCE_TYPE__NO2D:
                return getNo2d();
            case QsarPackage.RESOURCE_TYPE__NO3D:
                return getNo3d();
            case QsarPackage.RESOURCE_TYPE__NO_MOLS:
                return getNoMols();
            case QsarPackage.RESOURCE_TYPE__TYPE:
                return getType();
            case QsarPackage.RESOURCE_TYPE__URL:
                return getURL();
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
            case QsarPackage.RESOURCE_TYPE__STRUCTURE:
                getStructure().clear();
                getStructure().addAll((Collection<? extends StructureType>)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__CHECKSUM:
                setChecksum((String)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS:
                setContainsErrors((Boolean)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__EXCLUDED:
                setExcluded((Boolean)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__FILE:
                setFile((String)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__ID:
                setId((String)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__NAME:
                setName((String)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__NO2D:
                setNo2d((Integer)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__NO3D:
                setNo3d((Integer)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__NO_MOLS:
                setNoMols((Integer)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__TYPE:
                setType((TypeType)newValue);
                return;
            case QsarPackage.RESOURCE_TYPE__URL:
                setURL((String)newValue);
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
            case QsarPackage.RESOURCE_TYPE__STRUCTURE:
                getStructure().clear();
                return;
            case QsarPackage.RESOURCE_TYPE__CHECKSUM:
                setChecksum(CHECKSUM_EDEFAULT);
                return;
            case QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS:
                unsetContainsErrors();
                return;
            case QsarPackage.RESOURCE_TYPE__EXCLUDED:
                unsetExcluded();
                return;
            case QsarPackage.RESOURCE_TYPE__FILE:
                setFile(FILE_EDEFAULT);
                return;
            case QsarPackage.RESOURCE_TYPE__ID:
                setId(ID_EDEFAULT);
                return;
            case QsarPackage.RESOURCE_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case QsarPackage.RESOURCE_TYPE__NO2D:
                unsetNo2d();
                return;
            case QsarPackage.RESOURCE_TYPE__NO3D:
                unsetNo3d();
                return;
            case QsarPackage.RESOURCE_TYPE__NO_MOLS:
                unsetNoMols();
                return;
            case QsarPackage.RESOURCE_TYPE__TYPE:
                unsetType();
                return;
            case QsarPackage.RESOURCE_TYPE__URL:
                setURL(URL_EDEFAULT);
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
            case QsarPackage.RESOURCE_TYPE__STRUCTURE:
                return structure != null && !structure.isEmpty();
            case QsarPackage.RESOURCE_TYPE__CHECKSUM:
                return CHECKSUM_EDEFAULT == null ? checksum != null : !CHECKSUM_EDEFAULT.equals(checksum);
            case QsarPackage.RESOURCE_TYPE__CONTAINS_ERRORS:
                return isSetContainsErrors();
            case QsarPackage.RESOURCE_TYPE__EXCLUDED:
                return isSetExcluded();
            case QsarPackage.RESOURCE_TYPE__FILE:
                return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
            case QsarPackage.RESOURCE_TYPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case QsarPackage.RESOURCE_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case QsarPackage.RESOURCE_TYPE__NO2D:
                return isSetNo2d();
            case QsarPackage.RESOURCE_TYPE__NO3D:
                return isSetNo3d();
            case QsarPackage.RESOURCE_TYPE__NO_MOLS:
                return isSetNoMols();
            case QsarPackage.RESOURCE_TYPE__TYPE:
                return isSetType();
            case QsarPackage.RESOURCE_TYPE__URL:
                return URL_EDEFAULT == null ? uRL != null : !URL_EDEFAULT.equals(uRL);
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
        result.append(" (checksum: ");
        result.append(checksum);
        result.append(", containsErrors: ");
        if (containsErrorsESet) result.append(containsErrors); else result.append("<unset>");
        result.append(", excluded: ");
        if (excludedESet) result.append(excluded); else result.append("<unset>");
        result.append(", file: ");
        result.append(file);
        result.append(", id: ");
        result.append(id);
        result.append(", name: ");
        result.append(name);
        result.append(", no2d: ");
        if (no2dESet) result.append(no2d); else result.append("<unset>");
        result.append(", no3d: ");
        if (no3dESet) result.append(no3d); else result.append("<unset>");
        result.append(", noMols: ");
        if (noMolsESet) result.append(noMols); else result.append("<unset>");
        result.append(", type: ");
        if (typeESet) result.append(type); else result.append("<unset>");
        result.append(", uRL: ");
        result.append(uRL);
        result.append(')');
        return result.toString();
    }

} //ResourceTypeImpl
