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

import net.bioclipse.qsar.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see net.bioclipse.qsar.QsarPackage
 * @generated
 */
public class QsarAdapterFactory extends AdapterFactoryImpl {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static QsarPackage modelPackage;

    /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public QsarAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = QsarPackage.eINSTANCE;
		}
	}

    /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
    @Override
    public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

    /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected QsarSwitch<Adapter> modelSwitch =
        new QsarSwitch<Adapter>() {
			@Override
			public Adapter caseDescriptorlistType(DescriptorlistType object) {
				return createDescriptorlistTypeAdapter();
			}
			@Override
			public Adapter caseDescriptorproviderType(DescriptorproviderType object) {
				return createDescriptorproviderTypeAdapter();
			}
			@Override
			public Adapter caseDescriptorresultlistsType(DescriptorresultlistsType object) {
				return createDescriptorresultlistsTypeAdapter();
			}
			@Override
			public Adapter caseDescriptorresultType(DescriptorresultType object) {
				return createDescriptorresultTypeAdapter();
			}
			@Override
			public Adapter caseDescriptorType(DescriptorType object) {
				return createDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseDescriptorvalueType(DescriptorvalueType object) {
				return createDescriptorvalueTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseMetadataType(MetadataType object) {
				return createMetadataTypeAdapter();
			}
			@Override
			public Adapter caseParameterType(ParameterType object) {
				return createParameterTypeAdapter();
			}
			@Override
			public Adapter casePreprocessingStepType(PreprocessingStepType object) {
				return createPreprocessingStepTypeAdapter();
			}
			@Override
			public Adapter casePreprocessingType(PreprocessingType object) {
				return createPreprocessingTypeAdapter();
			}
			@Override
			public Adapter caseQsarType(QsarType object) {
				return createQsarTypeAdapter();
			}
			@Override
			public Adapter caseResourceType(ResourceType object) {
				return createResourceTypeAdapter();
			}
			@Override
			public Adapter caseResponsesListType(ResponsesListType object) {
				return createResponsesListTypeAdapter();
			}
			@Override
			public Adapter caseResponseType(ResponseType object) {
				return createResponseTypeAdapter();
			}
			@Override
			public Adapter caseResponseunitType(ResponseunitType object) {
				return createResponseunitTypeAdapter();
			}
			@Override
			public Adapter caseStructurelistType(StructurelistType object) {
				return createStructurelistTypeAdapter();
			}
			@Override
			public Adapter caseStructureType(StructureType object) {
				return createStructureTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

    /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
    @Override
    public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorlistType <em>Descriptorlist Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorlistType
	 * @generated
	 */
    public Adapter createDescriptorlistTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorproviderType <em>Descriptorprovider Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorproviderType
	 * @generated
	 */
    public Adapter createDescriptorproviderTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorresultlistsType <em>Descriptorresultlists Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorresultlistsType
	 * @generated
	 */
    public Adapter createDescriptorresultlistsTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorresultType <em>Descriptorresult Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorresultType
	 * @generated
	 */
    public Adapter createDescriptorresultTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorType <em>Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorType
	 * @generated
	 */
    public Adapter createDescriptorTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DescriptorvalueType <em>Descriptorvalue Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DescriptorvalueType
	 * @generated
	 */
    public Adapter createDescriptorvalueTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.DocumentRoot
	 * @generated
	 */
    public Adapter createDocumentRootAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.MetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.MetadataType
	 * @generated
	 */
    public Adapter createMetadataTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.ParameterType
	 * @generated
	 */
    public Adapter createParameterTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.PreprocessingStepType <em>Preprocessing Step Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.PreprocessingStepType
	 * @generated
	 */
    public Adapter createPreprocessingStepTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.PreprocessingType <em>Preprocessing Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.PreprocessingType
	 * @generated
	 */
    public Adapter createPreprocessingTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.QsarType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.QsarType
	 * @generated
	 */
    public Adapter createQsarTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.ResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.ResourceType
	 * @generated
	 */
    public Adapter createResourceTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.ResponsesListType <em>Responses List Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.ResponsesListType
	 * @generated
	 */
    public Adapter createResponsesListTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.ResponseType <em>Response Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.ResponseType
	 * @generated
	 */
    public Adapter createResponseTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.ResponseunitType <em>Responseunit Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.ResponseunitType
	 * @generated
	 */
    public Adapter createResponseunitTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.StructurelistType <em>Structurelist Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.StructurelistType
	 * @generated
	 */
    public Adapter createStructurelistTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link net.bioclipse.qsar.StructureType <em>Structure Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.bioclipse.qsar.StructureType
	 * @generated
	 */
    public Adapter createStructureTypeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
    public Adapter createEObjectAdapter() {
		return null;
	}

} //QsarAdapterFactory
