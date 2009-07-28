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
import net.bioclipse.qsar.StructureType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structure Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#getProblem <em>Problem</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#getInchi <em>Inchi</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#getResourceid <em>Resourceid</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#getResourceindex <em>Resourceindex</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StructureTypeImpl extends EObjectImpl implements StructureType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
     * The cached value of the '{@link #getProblem() <em>Problem</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProblem()
     * @generated
     * @ordered
     */
    protected EList<String> problem;

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
     * The default value of the '{@link #getInchi() <em>Inchi</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInchi()
     * @generated
     * @ordered
     */
    protected static final String INCHI_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInchi() <em>Inchi</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInchi()
     * @generated
     * @ordered
     */
    protected String inchi = INCHI_EDEFAULT;

    /**
     * The default value of the '{@link #getResourceid() <em>Resourceid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceid()
     * @generated
     * @ordered
     */
    protected static final String RESOURCEID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceid() <em>Resourceid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceid()
     * @generated
     * @ordered
     */
    protected String resourceid = RESOURCEID_EDEFAULT;

    /**
     * The default value of the '{@link #getResourceindex() <em>Resourceindex</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceindex()
     * @generated
     * @ordered
     */
    protected static final int RESOURCEINDEX_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getResourceindex() <em>Resourceindex</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceindex()
     * @generated
     * @ordered
     */
    protected int resourceindex = RESOURCEINDEX_EDEFAULT;

    /**
     * This is true if the Resourceindex attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean resourceindexESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StructureTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QsarPackage.Literals.STRUCTURE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getProblem() {
        if (problem == null) {
            problem = new EDataTypeEList<String>(String.class, this, QsarPackage.STRUCTURE_TYPE__PROBLEM);
        }
        return problem;
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
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getInchi() {
        return inchi;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInchi(String newInchi) {
        String oldInchi = inchi;
        inchi = newInchi;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__INCHI, oldInchi, inchi));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceid() {
        return resourceid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceid(String newResourceid) {
        String oldResourceid = resourceid;
        resourceid = newResourceid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__RESOURCEID, oldResourceid, resourceid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getResourceindex() {
        return resourceindex;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceindex(int newResourceindex) {
        int oldResourceindex = resourceindex;
        resourceindex = newResourceindex;
        boolean oldResourceindexESet = resourceindexESet;
        resourceindexESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX, oldResourceindex, resourceindex, !oldResourceindexESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetResourceindex() {
        int oldResourceindex = resourceindex;
        boolean oldResourceindexESet = resourceindexESet;
        resourceindex = RESOURCEINDEX_EDEFAULT;
        resourceindexESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX, oldResourceindex, RESOURCEINDEX_EDEFAULT, oldResourceindexESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetResourceindex() {
        return resourceindexESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case QsarPackage.STRUCTURE_TYPE__PROBLEM:
                return getProblem();
            case QsarPackage.STRUCTURE_TYPE__ID:
                return getId();
            case QsarPackage.STRUCTURE_TYPE__INCHI:
                return getInchi();
            case QsarPackage.STRUCTURE_TYPE__RESOURCEID:
                return getResourceid();
            case QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX:
                return new Integer(getResourceindex());
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
            case QsarPackage.STRUCTURE_TYPE__PROBLEM:
                getProblem().clear();
                getProblem().addAll((Collection<? extends String>)newValue);
                return;
            case QsarPackage.STRUCTURE_TYPE__ID:
                setId((String)newValue);
                return;
            case QsarPackage.STRUCTURE_TYPE__INCHI:
                setInchi((String)newValue);
                return;
            case QsarPackage.STRUCTURE_TYPE__RESOURCEID:
                setResourceid((String)newValue);
                return;
            case QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX:
                setResourceindex(((Integer)newValue).intValue());
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
            case QsarPackage.STRUCTURE_TYPE__PROBLEM:
                getProblem().clear();
                return;
            case QsarPackage.STRUCTURE_TYPE__ID:
                setId(ID_EDEFAULT);
                return;
            case QsarPackage.STRUCTURE_TYPE__INCHI:
                setInchi(INCHI_EDEFAULT);
                return;
            case QsarPackage.STRUCTURE_TYPE__RESOURCEID:
                setResourceid(RESOURCEID_EDEFAULT);
                return;
            case QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX:
                unsetResourceindex();
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
            case QsarPackage.STRUCTURE_TYPE__PROBLEM:
                return problem != null && !problem.isEmpty();
            case QsarPackage.STRUCTURE_TYPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case QsarPackage.STRUCTURE_TYPE__INCHI:
                return INCHI_EDEFAULT == null ? inchi != null : !INCHI_EDEFAULT.equals(inchi);
            case QsarPackage.STRUCTURE_TYPE__RESOURCEID:
                return RESOURCEID_EDEFAULT == null ? resourceid != null : !RESOURCEID_EDEFAULT.equals(resourceid);
            case QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX:
                return isSetResourceindex();
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
        result.append(" (problem: ");
        result.append(problem);
        result.append(", id: ");
        result.append(id);
        result.append(", inchi: ");
        result.append(inchi);
        result.append(", resourceid: ");
        result.append(resourceid);
        result.append(", resourceindex: ");
        if (resourceindexESet) result.append(resourceindex); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //StructureTypeImpl
