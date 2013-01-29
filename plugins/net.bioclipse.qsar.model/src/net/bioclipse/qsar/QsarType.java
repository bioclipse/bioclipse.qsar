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
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.bioclipse.qsar.QsarType#getStructurelist <em>Structurelist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getDescriptorlist <em>Descriptorlist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getDescriptorproviders <em>Descriptorproviders</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getPreprocessing <em>Preprocessing</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getResponselist <em>Responselist</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getResponseunit <em>Responseunit</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link net.bioclipse.qsar.QsarType#getDescriptorresultlist <em>Descriptorresultlist</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.bioclipse.qsar.QsarPackage#getQsarType()
 * @model extendedMetaData="name='qsarType' kind='elementOnly'"
 * @generated
 */
public interface QsarType extends EObject {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * Returns the value of the '<em><b>Structurelist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structurelist</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Structurelist</em>' containment reference.
	 * @see #setStructurelist(StructurelistType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Structurelist()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='structurelist' namespace='##targetNamespace'"
	 * @generated
	 */
    StructurelistType getStructurelist();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getStructurelist <em>Structurelist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structurelist</em>' containment reference.
	 * @see #getStructurelist()
	 * @generated
	 */
    void setStructurelist(StructurelistType value);

    /**
	 * Returns the value of the '<em><b>Descriptorlist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptorlist</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptorlist</em>' containment reference.
	 * @see #setDescriptorlist(DescriptorlistType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Descriptorlist()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='descriptorlist' namespace='##targetNamespace'"
	 * @generated
	 */
    DescriptorlistType getDescriptorlist();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getDescriptorlist <em>Descriptorlist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Descriptorlist</em>' containment reference.
	 * @see #getDescriptorlist()
	 * @generated
	 */
    void setDescriptorlist(DescriptorlistType value);

    /**
	 * Returns the value of the '<em><b>Descriptorproviders</b></em>' containment reference list.
	 * The list contents are of type {@link net.bioclipse.qsar.DescriptorproviderType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptorproviders</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptorproviders</em>' containment reference list.
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Descriptorproviders()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='descriptorproviders' namespace='##targetNamespace'"
	 * @generated
	 */
    EList<DescriptorproviderType> getDescriptorproviders();

    /**
	 * Returns the value of the '<em><b>Preprocessing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Preprocessing</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Preprocessing</em>' containment reference.
	 * @see #setPreprocessing(PreprocessingType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Preprocessing()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='preprocessing' namespace='##targetNamespace'"
	 * @generated
	 */
    PreprocessingType getPreprocessing();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getPreprocessing <em>Preprocessing</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preprocessing</em>' containment reference.
	 * @see #getPreprocessing()
	 * @generated
	 */
    void setPreprocessing(PreprocessingType value);

    /**
	 * Returns the value of the '<em><b>Responselist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Responselist</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Responselist</em>' containment reference.
	 * @see #setResponselist(ResponsesListType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Responselist()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='responselist' namespace='##targetNamespace'"
	 * @generated
	 */
    ResponsesListType getResponselist();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getResponselist <em>Responselist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Responselist</em>' containment reference.
	 * @see #getResponselist()
	 * @generated
	 */
    void setResponselist(ResponsesListType value);

    /**
	 * Returns the value of the '<em><b>Responseunit</b></em>' containment reference list.
	 * The list contents are of type {@link net.bioclipse.qsar.ResponseunitType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Responseunit</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Responseunit</em>' containment reference list.
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Responseunit()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='responseunit' namespace='##targetNamespace'"
	 * @generated
	 */
    EList<ResponseunitType> getResponseunit();

    /**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Metadata</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata</em>' containment reference.
	 * @see #setMetadata(MetadataType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Metadata()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='metadata' namespace='##targetNamespace'"
	 * @generated
	 */
    MetadataType getMetadata();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getMetadata <em>Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata</em>' containment reference.
	 * @see #getMetadata()
	 * @generated
	 */
    void setMetadata(MetadataType value);

    /**
	 * Returns the value of the '<em><b>Descriptorresultlist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptorresultlist</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptorresultlist</em>' containment reference.
	 * @see #setDescriptorresultlist(DescriptorresultlistsType)
	 * @see net.bioclipse.qsar.QsarPackage#getQsarType_Descriptorresultlist()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='descriptorresultlist' namespace='##targetNamespace'"
	 * @generated
	 */
    DescriptorresultlistsType getDescriptorresultlist();

    /**
	 * Sets the value of the '{@link net.bioclipse.qsar.QsarType#getDescriptorresultlist <em>Descriptorresultlist</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Descriptorresultlist</em>' containment reference.
	 * @see #getDescriptorresultlist()
	 * @generated
	 */
    void setDescriptorresultlist(DescriptorresultlistsType value);

} // QsarType
