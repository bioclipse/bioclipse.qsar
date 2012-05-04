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
 * A representation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getStructure <em>Structure</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#isContainsErrors <em>Contains Errors</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#isExcluded <em>Excluded</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getFile <em>File</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getId <em>Id</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getName <em>Name</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getNo2d <em>No2d</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getNo3d <em>No3d</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getNoMols <em>No Mols</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getType <em>Type</em>}</li>
 *   <li>{@link net.bioclipse.qsar.ResourceType#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.bioclipse.qsar.QsarPackage#getResourceType()
 * @model extendedMetaData="name='resourceType' kind='elementOnly'"
 * @generated
 */
public interface ResourceType extends EObject {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * Returns the value of the '<em><b>Structure</b></em>' containment reference list.
	 * The list contents are of type {@link net.bioclipse.qsar.StructureType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structure</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Structure</em>' containment reference list.
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Structure()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='structure' namespace='##targetNamespace'"
	 * @generated
	 */
    EList<StructureType> getStructure();

    /**
	 * Returns the value of the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Checksum</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Checksum</em>' attribute.
	 * @see #setChecksum(String)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Checksum()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='checksum'"
	 * @generated
	 */
    String getChecksum();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getChecksum <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checksum</em>' attribute.
	 * @see #getChecksum()
	 * @generated
	 */
    void setChecksum(String value);

    /**
	 * Returns the value of the '<em><b>Contains Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Contains Errors</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains Errors</em>' attribute.
	 * @see #isSetContainsErrors()
	 * @see #unsetContainsErrors()
	 * @see #setContainsErrors(boolean)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_ContainsErrors()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='containsErrors'"
	 * @generated
	 */
    boolean isContainsErrors();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#isContainsErrors <em>Contains Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contains Errors</em>' attribute.
	 * @see #isSetContainsErrors()
	 * @see #unsetContainsErrors()
	 * @see #isContainsErrors()
	 * @generated
	 */
    void setContainsErrors(boolean value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#isContainsErrors <em>Contains Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetContainsErrors()
	 * @see #isContainsErrors()
	 * @see #setContainsErrors(boolean)
	 * @generated
	 */
    void unsetContainsErrors();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#isContainsErrors <em>Contains Errors</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Contains Errors</em>' attribute is set.
	 * @see #unsetContainsErrors()
	 * @see #isContainsErrors()
	 * @see #setContainsErrors(boolean)
	 * @generated
	 */
    boolean isSetContainsErrors();

    /**
	 * Returns the value of the '<em><b>Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Excluded</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Excluded</em>' attribute.
	 * @see #isSetExcluded()
	 * @see #unsetExcluded()
	 * @see #setExcluded(boolean)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Excluded()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='excluded'"
	 * @generated
	 */
    boolean isExcluded();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#isExcluded <em>Excluded</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excluded</em>' attribute.
	 * @see #isSetExcluded()
	 * @see #unsetExcluded()
	 * @see #isExcluded()
	 * @generated
	 */
    void setExcluded(boolean value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#isExcluded <em>Excluded</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetExcluded()
	 * @see #isExcluded()
	 * @see #setExcluded(boolean)
	 * @generated
	 */
    void unsetExcluded();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#isExcluded <em>Excluded</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Excluded</em>' attribute is set.
	 * @see #unsetExcluded()
	 * @see #isExcluded()
	 * @see #setExcluded(boolean)
	 * @generated
	 */
    boolean isSetExcluded();

    /**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_File()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='file'"
	 * @generated
	 */
    String getFile();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
    void setFile(String value);

    /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
    String getId();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

    /**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
    String getName();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
	 * Returns the value of the '<em><b>No2d</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>No2d</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>No2d</em>' attribute.
	 * @see #isSetNo2d()
	 * @see #unsetNo2d()
	 * @see #setNo2d(int)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_No2d()
	 * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" transient="true"
	 *        extendedMetaData="kind='attribute' name='no2d'"
	 * @generated
	 */
    int getNo2d();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getNo2d <em>No2d</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No2d</em>' attribute.
	 * @see #isSetNo2d()
	 * @see #unsetNo2d()
	 * @see #getNo2d()
	 * @generated
	 */
    void setNo2d(int value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#getNo2d <em>No2d</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetNo2d()
	 * @see #getNo2d()
	 * @see #setNo2d(int)
	 * @generated
	 */
    void unsetNo2d();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#getNo2d <em>No2d</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>No2d</em>' attribute is set.
	 * @see #unsetNo2d()
	 * @see #getNo2d()
	 * @see #setNo2d(int)
	 * @generated
	 */
    boolean isSetNo2d();

    /**
	 * Returns the value of the '<em><b>No3d</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>No3d</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>No3d</em>' attribute.
	 * @see #isSetNo3d()
	 * @see #unsetNo3d()
	 * @see #setNo3d(int)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_No3d()
	 * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" transient="true"
	 *        extendedMetaData="kind='attribute' name='no3d'"
	 * @generated
	 */
    int getNo3d();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getNo3d <em>No3d</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No3d</em>' attribute.
	 * @see #isSetNo3d()
	 * @see #unsetNo3d()
	 * @see #getNo3d()
	 * @generated
	 */
    void setNo3d(int value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#getNo3d <em>No3d</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetNo3d()
	 * @see #getNo3d()
	 * @see #setNo3d(int)
	 * @generated
	 */
    void unsetNo3d();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#getNo3d <em>No3d</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>No3d</em>' attribute is set.
	 * @see #unsetNo3d()
	 * @see #getNo3d()
	 * @see #setNo3d(int)
	 * @generated
	 */
    boolean isSetNo3d();

    /**
	 * Returns the value of the '<em><b>No Mols</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>No Mols</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>No Mols</em>' attribute.
	 * @see #isSetNoMols()
	 * @see #unsetNoMols()
	 * @see #setNoMols(int)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_NoMols()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" transient="true"
	 *        extendedMetaData="kind='attribute' name='noMols'"
	 * @generated
	 */
    int getNoMols();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getNoMols <em>No Mols</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No Mols</em>' attribute.
	 * @see #isSetNoMols()
	 * @see #unsetNoMols()
	 * @see #getNoMols()
	 * @generated
	 */
    void setNoMols(int value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#getNoMols <em>No Mols</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetNoMols()
	 * @see #getNoMols()
	 * @see #setNoMols(int)
	 * @generated
	 */
    void unsetNoMols();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#getNoMols <em>No Mols</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>No Mols</em>' attribute is set.
	 * @see #unsetNoMols()
	 * @see #getNoMols()
	 * @see #setNoMols(int)
	 * @generated
	 */
    boolean isSetNoMols();

    /**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link net.bioclipse.qsar.TypeType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see net.bioclipse.qsar.TypeType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(TypeType)
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_Type()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='type'"
	 * @generated
	 */
    TypeType getType();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see net.bioclipse.qsar.TypeType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
    void setType(TypeType value);

    /**
	 * Unsets the value of the '{@link net.bioclipse.qsar.ResourceType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(TypeType)
	 * @generated
	 */
    void unsetType();

    /**
	 * Returns whether the value of the '{@link net.bioclipse.qsar.ResourceType#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(TypeType)
	 * @generated
	 */
    boolean isSetType();

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
	 * @see net.bioclipse.qsar.QsarPackage#getResourceType_URL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='URL'"
	 * @generated
	 */
    String getURL();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.ResourceType#getURL <em>URL</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URL</em>' attribute.
	 * @see #getURL()
	 * @generated
	 */
    void setURL(String value);

} // ResourceType
