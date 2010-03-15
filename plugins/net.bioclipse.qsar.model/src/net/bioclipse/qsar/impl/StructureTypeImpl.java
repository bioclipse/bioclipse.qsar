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
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#isHas2d <em>Has2d</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.StructureTypeImpl#isHas3d <em>Has3d</em>}</li>
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
     * The default value of the '{@link #isHas2d() <em>Has2d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isHas2d()
     * @generated
     * @ordered
     */
    protected static final boolean HAS2D_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isHas2d() <em>Has2d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isHas2d()
     * @generated
     * @ordered
     */
    protected boolean has2d = HAS2D_EDEFAULT;

    /**
     * This is true if the Has2d attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean has2dESet;

    /**
     * The default value of the '{@link #isHas3d() <em>Has3d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isHas3d()
     * @generated
     * @ordered
     */
    protected static final boolean HAS3D_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isHas3d() <em>Has3d</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isHas3d()
     * @generated
     * @ordered
     */
    protected boolean has3d = HAS3D_EDEFAULT;

    /**
     * This is true if the Has3d attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean has3dESet;

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
    public boolean isHas2d() {
        return has2d;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHas2d(boolean newHas2d) {
        boolean oldHas2d = has2d;
        has2d = newHas2d;
        boolean oldHas2dESet = has2dESet;
        has2dESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__HAS2D, oldHas2d, has2d, !oldHas2dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetHas2d() {
        boolean oldHas2d = has2d;
        boolean oldHas2dESet = has2dESet;
        has2d = HAS2D_EDEFAULT;
        has2dESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.STRUCTURE_TYPE__HAS2D, oldHas2d, HAS2D_EDEFAULT, oldHas2dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetHas2d() {
        return has2dESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isHas3d() {
        return has3d;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHas3d(boolean newHas3d) {
        boolean oldHas3d = has3d;
        has3d = newHas3d;
        boolean oldHas3dESet = has3dESet;
        has3dESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.STRUCTURE_TYPE__HAS3D, oldHas3d, has3d, !oldHas3dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetHas3d() {
        boolean oldHas3d = has3d;
        boolean oldHas3dESet = has3dESet;
        has3d = HAS3D_EDEFAULT;
        has3dESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.STRUCTURE_TYPE__HAS3D, oldHas3d, HAS3D_EDEFAULT, oldHas3dESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetHas3d() {
        return has3dESet;
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
            case QsarPackage.STRUCTURE_TYPE__HAS2D:
                return isHas2d();
            case QsarPackage.STRUCTURE_TYPE__HAS3D:
                return isHas3d();
            case QsarPackage.STRUCTURE_TYPE__ID:
                return getId();
            case QsarPackage.STRUCTURE_TYPE__INCHI:
                return getInchi();
            case QsarPackage.STRUCTURE_TYPE__RESOURCEID:
                return getResourceid();
            case QsarPackage.STRUCTURE_TYPE__RESOURCEINDEX:
                return getResourceindex();
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
            case QsarPackage.STRUCTURE_TYPE__HAS2D:
                setHas2d((Boolean)newValue);
                return;
            case QsarPackage.STRUCTURE_TYPE__HAS3D:
                setHas3d((Boolean)newValue);
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
                setResourceindex((Integer)newValue);
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
            case QsarPackage.STRUCTURE_TYPE__HAS2D:
                unsetHas2d();
                return;
            case QsarPackage.STRUCTURE_TYPE__HAS3D:
                unsetHas3d();
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
            case QsarPackage.STRUCTURE_TYPE__HAS2D:
                return isSetHas2d();
            case QsarPackage.STRUCTURE_TYPE__HAS3D:
                return isSetHas3d();
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
        result.append(", has2d: ");
        if (has2dESet) result.append(has2d); else result.append("<unset>");
        result.append(", has3d: ");
        if (has3dESet) result.append(has3d); else result.append("<unset>");
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
