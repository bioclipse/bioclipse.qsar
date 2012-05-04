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
package net.bioclipse.qsar;

import net.sf.bibtexml.BibTeXMLEntriesClass;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadata Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getReference <em>Reference</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getAuthors <em>Authors</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getDatasetname <em>Datasetname</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getDescription <em>Description</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getLicense <em>License</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getResponseLabel <em>Response Label</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getResponsePlacement <em>Response Placement</em>}</li>
 *   <li>{@link net.bioclipse.qsar.MetadataType#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.bioclipse.qsar.QsarPackage#getMetadataType()
 * @model extendedMetaData="name='metadataType' kind='elementOnly'"
 * @generated
 */
public interface MetadataType extends EObject {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * Returns the value of the '<em><b>Reference</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.bibtexml.BibTeXMLEntriesClass}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reference</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' containment reference list.
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_Reference()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='reference' namespace='##targetNamespace'"
	 * @generated
	 */
    EList<BibTeXMLEntriesClass> getReference();

    /**
	 * Returns the value of the '<em><b>Authors</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Authors</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Authors</em>' attribute.
	 * @see #setAuthors(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_Authors()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='authors'"
	 * @generated
	 */
    String getAuthors();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getAuthors <em>Authors</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authors</em>' attribute.
	 * @see #getAuthors()
	 * @generated
	 */
    void setAuthors(String value);

    /**
	 * Returns the value of the '<em><b>Datasetname</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Datasetname</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Datasetname</em>' attribute.
	 * @see #setDatasetname(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_Datasetname()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='datasetname'"
	 * @generated
	 */
    String getDatasetname();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getDatasetname <em>Datasetname</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datasetname</em>' attribute.
	 * @see #getDatasetname()
	 * @generated
	 */
    void setDatasetname(String value);

    /**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='description'"
	 * @generated
	 */
    String getDescription();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
    void setDescription(String value);

    /**
	 * Returns the value of the '<em><b>License</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>License</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>License</em>' attribute.
	 * @see #setLicense(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_License()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='license'"
	 * @generated
	 */
    String getLicense();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getLicense <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>License</em>' attribute.
	 * @see #getLicense()
	 * @generated
	 */
    void setLicense(String value);

    /**
	 * Returns the value of the '<em><b>Response Label</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response Label</em>' attribute.
	 * @see #isSetResponseLabel()
	 * @see #unsetResponseLabel()
	 * @see #setResponseLabel(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_ResponseLabel()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='responseLabel'"
	 * @generated
	 */
	String getResponseLabel();

				/**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getResponseLabel <em>Response Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response Label</em>' attribute.
	 * @see #isSetResponseLabel()
	 * @see #unsetResponseLabel()
	 * @see #getResponseLabel()
	 * @generated
	 */
	void setResponseLabel(String value);

				/**
	 * Unsets the value of the '{@link net.bioclipse.qsar.MetadataType#getResponseLabel <em>Response Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResponseLabel()
	 * @see #getResponseLabel()
	 * @see #setResponseLabel(String)
	 * @generated
	 */
	void unsetResponseLabel();

				/**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.MetadataType#getResponseLabel <em>Response Label</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Response Label</em>' attribute is set.
	 * @see #unsetResponseLabel()
	 * @see #getResponseLabel()
	 * @see #setResponseLabel(String)
	 * @generated
	 */
	boolean isSetResponseLabel();

				/**
	 * Returns the value of the '<em><b>Response Placement</b></em>' attribute.
	 * The default value is <code>"first"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response Placement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response Placement</em>' attribute.
	 * @see #isSetResponsePlacement()
	 * @see #unsetResponsePlacement()
	 * @see #setResponsePlacement(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_ResponsePlacement()
	 * @model default="first" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='responsePlacement'"
	 * @generated
	 */
	String getResponsePlacement();

				/**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getResponsePlacement <em>Response Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response Placement</em>' attribute.
	 * @see #isSetResponsePlacement()
	 * @see #unsetResponsePlacement()
	 * @see #getResponsePlacement()
	 * @generated
	 */
	void setResponsePlacement(String value);

				/**
	 * Unsets the value of the '{@link net.bioclipse.qsar.MetadataType#getResponsePlacement <em>Response Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResponsePlacement()
	 * @see #getResponsePlacement()
	 * @see #setResponsePlacement(String)
	 * @generated
	 */
	void unsetResponsePlacement();

				/**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.MetadataType#getResponsePlacement <em>Response Placement</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Response Placement</em>' attribute is set.
	 * @see #unsetResponsePlacement()
	 * @see #getResponsePlacement()
	 * @see #setResponsePlacement(String)
	 * @generated
	 */
	boolean isSetResponsePlacement();

				/**
	 * Returns the value of the '<em><b>URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>URL</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>URL</em>' attribute.
	 * @see #setURL(String)
	 * @see net.bioclipse.qsar.QsarPackage#getMetadataType_URL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='URL'"
	 * @generated
	 */
    String getURL();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.MetadataType#getURL <em>URL</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URL</em>' attribute.
	 * @see #getURL()
	 * @generated
	 */
    void setURL(String value);

} // MetadataType
