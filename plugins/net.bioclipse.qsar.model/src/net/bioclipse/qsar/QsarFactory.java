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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.bioclipse.qsar.QsarPackage
 * @generated
 */
public interface QsarFactory extends EFactory {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    QsarFactory eINSTANCE = net.bioclipse.qsar.impl.QsarFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>Descriptorlist Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptorlist Type</em>'.
	 * @generated
	 */
    DescriptorlistType createDescriptorlistType();

    /**
	 * Returns a new object of class '<em>Descriptorprovider Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptorprovider Type</em>'.
	 * @generated
	 */
    DescriptorproviderType createDescriptorproviderType();

    /**
	 * Returns a new object of class '<em>Descriptorresultlists Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptorresultlists Type</em>'.
	 * @generated
	 */
    DescriptorresultlistsType createDescriptorresultlistsType();

    /**
	 * Returns a new object of class '<em>Descriptorresult Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptorresult Type</em>'.
	 * @generated
	 */
    DescriptorresultType createDescriptorresultType();

    /**
	 * Returns a new object of class '<em>Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptor Type</em>'.
	 * @generated
	 */
    DescriptorType createDescriptorType();

    /**
	 * Returns a new object of class '<em>Descriptorvalue Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptorvalue Type</em>'.
	 * @generated
	 */
    DescriptorvalueType createDescriptorvalueType();

    /**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
    DocumentRoot createDocumentRoot();

    /**
	 * Returns a new object of class '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metadata Type</em>'.
	 * @generated
	 */
    MetadataType createMetadataType();

    /**
	 * Returns a new object of class '<em>Parameter Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Type</em>'.
	 * @generated
	 */
    ParameterType createParameterType();

    /**
	 * Returns a new object of class '<em>Preprocessing Step Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preprocessing Step Type</em>'.
	 * @generated
	 */
    PreprocessingStepType createPreprocessingStepType();

    /**
	 * Returns a new object of class '<em>Preprocessing Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preprocessing Type</em>'.
	 * @generated
	 */
    PreprocessingType createPreprocessingType();

    /**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
    QsarType createQsarType();

    /**
	 * Returns a new object of class '<em>Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Type</em>'.
	 * @generated
	 */
    ResourceType createResourceType();

    /**
	 * Returns a new object of class '<em>Responses List Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Responses List Type</em>'.
	 * @generated
	 */
    ResponsesListType createResponsesListType();

    /**
	 * Returns a new object of class '<em>Response Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Response Type</em>'.
	 * @generated
	 */
    ResponseType createResponseType();

    /**
	 * Returns a new object of class '<em>Responseunit Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Responseunit Type</em>'.
	 * @generated
	 */
    ResponseunitType createResponseunitType();

    /**
	 * Returns a new object of class '<em>Structurelist Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structurelist Type</em>'.
	 * @generated
	 */
    StructurelistType createStructurelistType();

    /**
	 * Returns a new object of class '<em>Structure Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structure Type</em>'.
	 * @generated
	 */
    StructureType createStructureType();

    /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
    QsarPackage getQsarPackage();

} //QsarFactory
