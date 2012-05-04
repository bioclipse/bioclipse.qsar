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
package net.bioclipse.qsar.util;

import java.util.List;

import net.bioclipse.qsar.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see net.bioclipse.qsar.QsarPackage
 * @generated
 */
public class QsarSwitch<T> extends Switch<T> {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached model package
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static QsarPackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public QsarSwitch() {
		if (modelPackage == null) {
			modelPackage = QsarPackage.eINSTANCE;
		}
	}

    /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

				/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    @Override
				protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case QsarPackage.DESCRIPTORLIST_TYPE: {
				DescriptorlistType descriptorlistType = (DescriptorlistType)theEObject;
				T result = caseDescriptorlistType(descriptorlistType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DESCRIPTORPROVIDER_TYPE: {
				DescriptorproviderType descriptorproviderType = (DescriptorproviderType)theEObject;
				T result = caseDescriptorproviderType(descriptorproviderType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DESCRIPTORRESULTLISTS_TYPE: {
				DescriptorresultlistsType descriptorresultlistsType = (DescriptorresultlistsType)theEObject;
				T result = caseDescriptorresultlistsType(descriptorresultlistsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DESCRIPTORRESULT_TYPE: {
				DescriptorresultType descriptorresultType = (DescriptorresultType)theEObject;
				T result = caseDescriptorresultType(descriptorresultType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DESCRIPTOR_TYPE: {
				DescriptorType descriptorType = (DescriptorType)theEObject;
				T result = caseDescriptorType(descriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DESCRIPTORVALUE_TYPE: {
				DescriptorvalueType descriptorvalueType = (DescriptorvalueType)theEObject;
				T result = caseDescriptorvalueType(descriptorvalueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.METADATA_TYPE: {
				MetadataType metadataType = (MetadataType)theEObject;
				T result = caseMetadataType(metadataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.PARAMETER_TYPE: {
				ParameterType parameterType = (ParameterType)theEObject;
				T result = caseParameterType(parameterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.PREPROCESSING_STEP_TYPE: {
				PreprocessingStepType preprocessingStepType = (PreprocessingStepType)theEObject;
				T result = casePreprocessingStepType(preprocessingStepType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.PREPROCESSING_TYPE: {
				PreprocessingType preprocessingType = (PreprocessingType)theEObject;
				T result = casePreprocessingType(preprocessingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.QSAR_TYPE: {
				QsarType qsarType = (QsarType)theEObject;
				T result = caseQsarType(qsarType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.RESOURCE_TYPE: {
				ResourceType resourceType = (ResourceType)theEObject;
				T result = caseResourceType(resourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.RESPONSES_LIST_TYPE: {
				ResponsesListType responsesListType = (ResponsesListType)theEObject;
				T result = caseResponsesListType(responsesListType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.RESPONSE_TYPE: {
				ResponseType responseType = (ResponseType)theEObject;
				T result = caseResponseType(responseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.RESPONSEUNIT_TYPE: {
				ResponseunitType responseunitType = (ResponseunitType)theEObject;
				T result = caseResponseunitType(responseunitType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.STRUCTURELIST_TYPE: {
				StructurelistType structurelistType = (StructurelistType)theEObject;
				T result = caseStructurelistType(structurelistType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case QsarPackage.STRUCTURE_TYPE: {
				StructureType structureType = (StructureType)theEObject;
				T result = caseStructureType(structureType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptorlist Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptorlist Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorlistType(DescriptorlistType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptorprovider Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptorprovider Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorproviderType(DescriptorproviderType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptorresultlists Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptorresultlists Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorresultlistsType(DescriptorresultlistsType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptorresult Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptorresult Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorresultType(DescriptorresultType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorType(DescriptorType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptorvalue Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptorvalue Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDescriptorvalueType(DescriptorvalueType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseMetadataType(MetadataType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseParameterType(ParameterType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Preprocessing Step Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preprocessing Step Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePreprocessingStepType(PreprocessingStepType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Preprocessing Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preprocessing Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePreprocessingType(PreprocessingType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseQsarType(QsarType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseResourceType(ResourceType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Responses List Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Responses List Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseResponsesListType(ResponsesListType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Response Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Response Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseResponseType(ResponseType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Responseunit Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Responseunit Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseResponseunitType(ResponseunitType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Structurelist Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structurelist Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseStructurelistType(StructurelistType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Structure Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structure Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseStructureType(StructureType object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    @Override
				public T defaultCase(EObject object) {
		return null;
	}

} //QsarSwitch
