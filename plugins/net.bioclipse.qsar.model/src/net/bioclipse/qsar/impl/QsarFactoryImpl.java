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

import net.bioclipse.qsar.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class QsarFactoryImpl extends EFactoryImpl implements QsarFactory {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static QsarFactory init() {
		try {
			QsarFactory theQsarFactory = (QsarFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.bioclipse.net/qsar"); 
			if (theQsarFactory != null) {
				return theQsarFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new QsarFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public QsarFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case QsarPackage.DESCRIPTORLIST_TYPE: return createDescriptorlistType();
			case QsarPackage.DESCRIPTORPROVIDER_TYPE: return createDescriptorproviderType();
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE: return createDescriptorresultlistsType();
			case QsarPackage.DESCRIPTORRESULT_TYPE: return createDescriptorresultType();
			case QsarPackage.DESCRIPTOR_TYPE: return createDescriptorType();
			case QsarPackage.DESCRIPTORVALUE_TYPE: return createDescriptorvalueType();
			case QsarPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case QsarPackage.METADATA_TYPE: return createMetadataType();
			case QsarPackage.PARAMETER_TYPE: return createParameterType();
			case QsarPackage.PREPROCESSING_STEP_TYPE: return createPreprocessingStepType();
			case QsarPackage.PREPROCESSING_TYPE: return createPreprocessingType();
			case QsarPackage.QSAR_TYPE: return createQsarType();
			case QsarPackage.RESOURCE_TYPE: return createResourceType();
			case QsarPackage.RESPONSES_LIST_TYPE: return createResponsesListType();
			case QsarPackage.RESPONSE_TYPE: return createResponseType();
			case QsarPackage.RESPONSEUNIT_TYPE: return createResponseunitType();
			case QsarPackage.STRUCTURELIST_TYPE: return createStructurelistType();
			case QsarPackage.STRUCTURE_TYPE: return createStructureType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case QsarPackage.TYPE_TYPE:
				return createTypeTypeFromString(eDataType, initialValue);
			case QsarPackage.TYPE_TYPE_OBJECT:
				return createTypeTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case QsarPackage.TYPE_TYPE:
				return convertTypeTypeToString(eDataType, instanceValue);
			case QsarPackage.TYPE_TYPE_OBJECT:
				return convertTypeTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorlistType createDescriptorlistType() {
		DescriptorlistTypeImpl descriptorlistType = new DescriptorlistTypeImpl();
		return descriptorlistType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorproviderType createDescriptorproviderType() {
		DescriptorproviderTypeImpl descriptorproviderType = new DescriptorproviderTypeImpl();
		return descriptorproviderType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorresultlistsType createDescriptorresultlistsType() {
		DescriptorresultlistsTypeImpl descriptorresultlistsType = new DescriptorresultlistsTypeImpl();
		return descriptorresultlistsType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorresultType createDescriptorresultType() {
		DescriptorresultTypeImpl descriptorresultType = new DescriptorresultTypeImpl();
		return descriptorresultType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorType createDescriptorType() {
		DescriptorTypeImpl descriptorType = new DescriptorTypeImpl();
		return descriptorType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DescriptorvalueType createDescriptorvalueType() {
		DescriptorvalueTypeImpl descriptorvalueType = new DescriptorvalueTypeImpl();
		return descriptorvalueType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MetadataType createMetadataType() {
		MetadataTypeImpl metadataType = new MetadataTypeImpl();
		return metadataType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ParameterType createParameterType() {
		ParameterTypeImpl parameterType = new ParameterTypeImpl();
		return parameterType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PreprocessingStepType createPreprocessingStepType() {
		PreprocessingStepTypeImpl preprocessingStepType = new PreprocessingStepTypeImpl();
		return preprocessingStepType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PreprocessingType createPreprocessingType() {
		PreprocessingTypeImpl preprocessingType = new PreprocessingTypeImpl();
		return preprocessingType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public QsarType createQsarType() {
		QsarTypeImpl qsarType = new QsarTypeImpl();
		return qsarType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResourceType createResourceType() {
		ResourceTypeImpl resourceType = new ResourceTypeImpl();
		return resourceType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResponsesListType createResponsesListType() {
		ResponsesListTypeImpl responsesListType = new ResponsesListTypeImpl();
		return responsesListType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResponseType createResponseType() {
		ResponseTypeImpl responseType = new ResponseTypeImpl();
		return responseType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResponseunitType createResponseunitType() {
		ResponseunitTypeImpl responseunitType = new ResponseunitTypeImpl();
		return responseunitType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public StructurelistType createStructurelistType() {
		StructurelistTypeImpl structurelistType = new StructurelistTypeImpl();
		return structurelistType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public StructureType createStructureType() {
		StructureTypeImpl structureType = new StructureTypeImpl();
		return structureType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TypeType createTypeTypeFromString(EDataType eDataType, String initialValue) {
		TypeType result = TypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String convertTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TypeType createTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTypeTypeFromString(QsarPackage.Literals.TYPE_TYPE, initialValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String convertTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTypeTypeToString(QsarPackage.Literals.TYPE_TYPE, instanceValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public QsarPackage getQsarPackage() {
		return (QsarPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static QsarPackage getPackage() {
		return QsarPackage.eINSTANCE;
	}

} //QsarFactoryImpl
