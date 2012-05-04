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

import net.bioclipse.qsar.MetadataType;
import net.bioclipse.qsar.QsarPackage;

import net.sf.bibtexml.BibTeXMLEntriesClass;

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
 * An implementation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getAuthors <em>Authors</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getDatasetname <em>Datasetname</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getLicense <em>License</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getResponseLabel <em>Response Label</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getResponsePlacement <em>Response Placement</em>}</li>
 *   <li>{@link net.bioclipse.qsar.impl.MetadataTypeImpl#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetadataTypeImpl extends EObjectImpl implements MetadataType {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
    protected EList<BibTeXMLEntriesClass> reference;

    /**
	 * The default value of the '{@link #getAuthors() <em>Authors</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAuthors()
	 * @generated
	 * @ordered
	 */
    protected static final String AUTHORS_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getAuthors() <em>Authors</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAuthors()
	 * @generated
	 * @ordered
	 */
    protected String authors = AUTHORS_EDEFAULT;

    /**
	 * The default value of the '{@link #getDatasetname() <em>Datasetname</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDatasetname()
	 * @generated
	 * @ordered
	 */
    protected static final String DATASETNAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDatasetname() <em>Datasetname</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDatasetname()
	 * @generated
	 * @ordered
	 */
    protected String datasetname = DATASETNAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
	 * The default value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
    protected static final String LICENSE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
    protected String license = LICENSE_EDEFAULT;

    /**
	 * The default value of the '{@link #getResponseLabel() <em>Response Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String RESPONSE_LABEL_EDEFAULT = "";

				/**
	 * The cached value of the '{@link #getResponseLabel() <em>Response Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseLabel()
	 * @generated
	 * @ordered
	 */
	protected String responseLabel = RESPONSE_LABEL_EDEFAULT;

				/**
	 * This is true if the Response Label attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean responseLabelESet;

				/**
	 * The default value of the '{@link #getResponsePlacement() <em>Response Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsePlacement()
	 * @generated
	 * @ordered
	 */
	protected static final String RESPONSE_PLACEMENT_EDEFAULT = "first";

				/**
	 * The cached value of the '{@link #getResponsePlacement() <em>Response Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsePlacement()
	 * @generated
	 * @ordered
	 */
	protected String responsePlacement = RESPONSE_PLACEMENT_EDEFAULT;

				/**
	 * This is true if the Response Placement attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean responsePlacementESet;

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
    protected MetadataTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return QsarPackage.Literals.METADATA_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<BibTeXMLEntriesClass> getReference() {
		if (reference == null) {
			reference = new EObjectContainmentEList<BibTeXMLEntriesClass>(BibTeXMLEntriesClass.class, this, QsarPackage.METADATA_TYPE__REFERENCE);
		}
		return reference;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getAuthors() {
		return authors;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setAuthors(String newAuthors) {
		String oldAuthors = authors;
		authors = newAuthors;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__AUTHORS, oldAuthors, authors));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDatasetname() {
		return datasetname;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDatasetname(String newDatasetname) {
		String oldDatasetname = datasetname;
		datasetname = newDatasetname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__DATASETNAME, oldDatasetname, datasetname));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDescription() {
		return description;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__DESCRIPTION, oldDescription, description));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLicense() {
		return license;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLicense(String newLicense) {
		String oldLicense = license;
		license = newLicense;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__LICENSE, oldLicense, license));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResponseLabel() {
		return responseLabel;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponseLabel(String newResponseLabel) {
		String oldResponseLabel = responseLabel;
		responseLabel = newResponseLabel;
		boolean oldResponseLabelESet = responseLabelESet;
		responseLabelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__RESPONSE_LABEL, oldResponseLabel, responseLabel, !oldResponseLabelESet));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetResponseLabel() {
		String oldResponseLabel = responseLabel;
		boolean oldResponseLabelESet = responseLabelESet;
		responseLabel = RESPONSE_LABEL_EDEFAULT;
		responseLabelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.METADATA_TYPE__RESPONSE_LABEL, oldResponseLabel, RESPONSE_LABEL_EDEFAULT, oldResponseLabelESet));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetResponseLabel() {
		return responseLabelESet;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResponsePlacement() {
		return responsePlacement;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponsePlacement(String newResponsePlacement) {
		String oldResponsePlacement = responsePlacement;
		responsePlacement = newResponsePlacement;
		boolean oldResponsePlacementESet = responsePlacementESet;
		responsePlacementESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT, oldResponsePlacement, responsePlacement, !oldResponsePlacementESet));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetResponsePlacement() {
		String oldResponsePlacement = responsePlacement;
		boolean oldResponsePlacementESet = responsePlacementESet;
		responsePlacement = RESPONSE_PLACEMENT_EDEFAULT;
		responsePlacementESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT, oldResponsePlacement, RESPONSE_PLACEMENT_EDEFAULT, oldResponsePlacementESet));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetResponsePlacement() {
		return responsePlacementESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, QsarPackage.METADATA_TYPE__URL, oldURL, uRL));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QsarPackage.METADATA_TYPE__REFERENCE:
				return ((InternalEList<?>)getReference()).basicRemove(otherEnd, msgs);
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
			case QsarPackage.METADATA_TYPE__REFERENCE:
				return getReference();
			case QsarPackage.METADATA_TYPE__AUTHORS:
				return getAuthors();
			case QsarPackage.METADATA_TYPE__DATASETNAME:
				return getDatasetname();
			case QsarPackage.METADATA_TYPE__DESCRIPTION:
				return getDescription();
			case QsarPackage.METADATA_TYPE__LICENSE:
				return getLicense();
			case QsarPackage.METADATA_TYPE__RESPONSE_LABEL:
				return getResponseLabel();
			case QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT:
				return getResponsePlacement();
			case QsarPackage.METADATA_TYPE__URL:
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
			case QsarPackage.METADATA_TYPE__REFERENCE:
				getReference().clear();
				getReference().addAll((Collection<? extends BibTeXMLEntriesClass>)newValue);
				return;
			case QsarPackage.METADATA_TYPE__AUTHORS:
				setAuthors((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__DATASETNAME:
				setDatasetname((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__LICENSE:
				setLicense((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__RESPONSE_LABEL:
				setResponseLabel((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT:
				setResponsePlacement((String)newValue);
				return;
			case QsarPackage.METADATA_TYPE__URL:
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
			case QsarPackage.METADATA_TYPE__REFERENCE:
				getReference().clear();
				return;
			case QsarPackage.METADATA_TYPE__AUTHORS:
				setAuthors(AUTHORS_EDEFAULT);
				return;
			case QsarPackage.METADATA_TYPE__DATASETNAME:
				setDatasetname(DATASETNAME_EDEFAULT);
				return;
			case QsarPackage.METADATA_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case QsarPackage.METADATA_TYPE__LICENSE:
				setLicense(LICENSE_EDEFAULT);
				return;
			case QsarPackage.METADATA_TYPE__RESPONSE_LABEL:
				unsetResponseLabel();
				return;
			case QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT:
				unsetResponsePlacement();
				return;
			case QsarPackage.METADATA_TYPE__URL:
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
			case QsarPackage.METADATA_TYPE__REFERENCE:
				return reference != null && !reference.isEmpty();
			case QsarPackage.METADATA_TYPE__AUTHORS:
				return AUTHORS_EDEFAULT == null ? authors != null : !AUTHORS_EDEFAULT.equals(authors);
			case QsarPackage.METADATA_TYPE__DATASETNAME:
				return DATASETNAME_EDEFAULT == null ? datasetname != null : !DATASETNAME_EDEFAULT.equals(datasetname);
			case QsarPackage.METADATA_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case QsarPackage.METADATA_TYPE__LICENSE:
				return LICENSE_EDEFAULT == null ? license != null : !LICENSE_EDEFAULT.equals(license);
			case QsarPackage.METADATA_TYPE__RESPONSE_LABEL:
				return isSetResponseLabel();
			case QsarPackage.METADATA_TYPE__RESPONSE_PLACEMENT:
				return isSetResponsePlacement();
			case QsarPackage.METADATA_TYPE__URL:
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
		result.append(" (authors: ");
		result.append(authors);
		result.append(", datasetname: ");
		result.append(datasetname);
		result.append(", description: ");
		result.append(description);
		result.append(", license: ");
		result.append(license);
		result.append(", responseLabel: ");
		if (responseLabelESet) result.append(responseLabel); else result.append("<unset>");
		result.append(", responsePlacement: ");
		if (responsePlacementESet) result.append(responsePlacement); else result.append("<unset>");
		result.append(", uRL: ");
		result.append(uRL);
		result.append(')');
		return result.toString();
	}

} //MetadataTypeImpl
