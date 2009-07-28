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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Descriptorresult Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.DescriptorresultType#getDescriptorvalue <em>Descriptorvalue</em>}</li>
 *   <li>{@link net.bioclipse.qsar.DescriptorresultType#getDescriptorid <em>Descriptorid</em>}</li>
 *   <li>{@link net.bioclipse.qsar.DescriptorresultType#getErrorString <em>Error String</em>}</li>
 *   <li>{@link net.bioclipse.qsar.DescriptorresultType#getStructureid <em>Structureid</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.bioclipse.qsar.QsarPackage#getDescriptorresultType()
 * @model extendedMetaData="name='descriptorresultType' kind='elementOnly'"
 * @generated
 */
public interface DescriptorresultType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
     * Returns the value of the '<em><b>Descriptorvalue</b></em>' containment reference list.
     * The list contents are of type {@link net.bioclipse.qsar.DescriptorvalueType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptorvalue</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Descriptorvalue</em>' containment reference list.
     * @see net.bioclipse.qsar.QsarPackage#getDescriptorresultType_Descriptorvalue()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='descriptorvalue' namespace='##targetNamespace'"
     * @generated
     */
    EList<DescriptorvalueType> getDescriptorvalue();

    /**
     * Returns the value of the '<em><b>Descriptorid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptorid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Descriptorid</em>' attribute.
     * @see #setDescriptorid(String)
     * @see net.bioclipse.qsar.QsarPackage#getDescriptorresultType_Descriptorid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='descriptorid'"
     * @generated
     */
    String getDescriptorid();

    /**
     * Sets the value of the '{@link net.bioclipse.qsar.DescriptorresultType#getDescriptorid <em>Descriptorid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Descriptorid</em>' attribute.
     * @see #getDescriptorid()
     * @generated
     */
    void setDescriptorid(String value);

    /**
     * Returns the value of the '<em><b>Error String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Error String</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Error String</em>' attribute.
     * @see #setErrorString(String)
     * @see net.bioclipse.qsar.QsarPackage#getDescriptorresultType_ErrorString()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='errorString'"
     * @generated
     */
    String getErrorString();

    /**
     * Sets the value of the '{@link net.bioclipse.qsar.DescriptorresultType#getErrorString <em>Error String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Error String</em>' attribute.
     * @see #getErrorString()
     * @generated
     */
    void setErrorString(String value);

    /**
     * Returns the value of the '<em><b>Structureid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structureid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Structureid</em>' attribute.
     * @see #setStructureid(String)
     * @see net.bioclipse.qsar.QsarPackage#getDescriptorresultType_Structureid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='structureid'"
     * @generated
     */
    String getStructureid();

    /**
     * Sets the value of the '{@link net.bioclipse.qsar.DescriptorresultType#getStructureid <em>Structureid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Structureid</em>' attribute.
     * @see #getStructureid()
     * @generated
     */
    void setStructureid(String value);

} // DescriptorresultType
