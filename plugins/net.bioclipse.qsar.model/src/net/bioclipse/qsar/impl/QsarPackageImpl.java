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

import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DescriptorproviderType;
import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.DescriptorresultlistsType;
import net.bioclipse.qsar.DescriptorvalueType;
import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.MetadataType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.PreprocessingStepType;
import net.bioclipse.qsar.PreprocessingType;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ResponsesListType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.StructurelistType;
import net.bioclipse.qsar.TypeType;

import net.sf.bibtexml.BibtexmlPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class QsarPackageImpl extends EPackageImpl implements QsarPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorlistTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorproviderTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorresultlistsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorresultTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptorvalueTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass metadataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass preprocessingStepTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass preprocessingTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qsarTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass responsesListTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass responseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass responseunitTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass structurelistTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass structureTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum typeTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType typeTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see net.bioclipse.qsar.QsarPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private QsarPackageImpl() {
        super(eNS_URI, QsarFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link QsarPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static QsarPackage init() {
        if (isInited) return (QsarPackage)EPackage.Registry.INSTANCE.getEPackage(QsarPackage.eNS_URI);

        // Obtain or create and register package
        QsarPackageImpl theQsarPackage = (QsarPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof QsarPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new QsarPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        BibtexmlPackage.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theQsarPackage.createPackageContents();

        // Initialize created meta-data
        theQsarPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theQsarPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(QsarPackage.eNS_URI, theQsarPackage);
        return theQsarPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorlistType() {
        return descriptorlistTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDescriptorlistType_Descriptors() {
        return (EReference)descriptorlistTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorproviderType() {
        return descriptorproviderTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorproviderType_Id() {
        return (EAttribute)descriptorproviderTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorproviderType_Name() {
        return (EAttribute)descriptorproviderTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorproviderType_URL() {
        return (EAttribute)descriptorproviderTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorproviderType_Vendor() {
        return (EAttribute)descriptorproviderTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorproviderType_Version() {
        return (EAttribute)descriptorproviderTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorresultlistsType() {
        return descriptorresultlistsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDescriptorresultlistsType_Descriptorresult() {
        return (EReference)descriptorresultlistsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorresultType() {
        return descriptorresultTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDescriptorresultType_Descriptorvalue() {
        return (EReference)descriptorresultTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorresultType_Descriptorid() {
        return (EAttribute)descriptorresultTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorresultType_ErrorString() {
        return (EAttribute)descriptorresultTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorresultType_Structureid() {
        return (EAttribute)descriptorresultTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorType() {
        return descriptorTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDescriptorType_Parameter() {
        return (EReference)descriptorTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorType_Id() {
        return (EAttribute)descriptorTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorType_Ontologyid() {
        return (EAttribute)descriptorTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorType_Provider() {
        return (EAttribute)descriptorTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescriptorvalueType() {
        return descriptorvalueTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorvalueType_Index() {
        return (EAttribute)descriptorvalueTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorvalueType_Label() {
        return (EAttribute)descriptorvalueTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescriptorvalueType_Value() {
        return (EAttribute)descriptorvalueTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Qsar() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMetadataType() {
        return metadataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMetadataType_Reference() {
        return (EReference)metadataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetadataType_Authors() {
        return (EAttribute)metadataTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetadataType_Datasetname() {
        return (EAttribute)metadataTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetadataType_Description() {
        return (EAttribute)metadataTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetadataType_License() {
        return (EAttribute)metadataTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetadataType_URL() {
        return (EAttribute)metadataTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterType() {
        return parameterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Key() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Value() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPreprocessingStepType() {
        return preprocessingStepTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreprocessingStepType_Id() {
        return (EAttribute)preprocessingStepTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreprocessingStepType_Name() {
        return (EAttribute)preprocessingStepTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreprocessingStepType_Namespace() {
        return (EAttribute)preprocessingStepTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreprocessingStepType_Order() {
        return (EAttribute)preprocessingStepTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreprocessingStepType_Vendor() {
        return (EAttribute)preprocessingStepTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPreprocessingType() {
        return preprocessingTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPreprocessingType_PreprocessingStep() {
        return (EReference)preprocessingTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQsarType() {
        return qsarTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Structurelist() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Descriptorlist() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Descriptorproviders() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Preprocessing() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Responselist() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Responseunit() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Metadata() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQsarType_Descriptorresultlist() {
        return (EReference)qsarTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResourceType() {
        return resourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResourceType_Structure() {
        return (EReference)resourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_Checksum() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_ContainsErrors() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_Excluded() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_File() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_Id() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_Name() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_No2d() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_No3d() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_NoMols() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_Type() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_URL() {
        return (EAttribute)resourceTypeEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResponsesListType() {
        return responsesListTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResponsesListType_Response() {
        return (EReference)responsesListTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResponseType() {
        return responseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseType_Value() {
        return (EAttribute)responseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseType_StructureID() {
        return (EAttribute)responseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseType_Unit() {
        return (EAttribute)responseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResponseunitType() {
        return responseunitTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseunitType_Description() {
        return (EAttribute)responseunitTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseunitType_Id() {
        return (EAttribute)responseunitTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseunitType_Name() {
        return (EAttribute)responseunitTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseunitType_Shortname() {
        return (EAttribute)responseunitTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponseunitType_URL() {
        return (EAttribute)responseunitTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStructurelistType() {
        return structurelistTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStructurelistType_Resources() {
        return (EReference)structurelistTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStructureType() {
        return structureTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Problem() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Has2d() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Has3d() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Id() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Inchi() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Resourceid() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructureType_Resourceindex() {
        return (EAttribute)structureTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTypeType() {
        return typeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTypeTypeObject() {
        return typeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public QsarFactory getQsarFactory() {
        return (QsarFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        descriptorlistTypeEClass = createEClass(DESCRIPTORLIST_TYPE);
        createEReference(descriptorlistTypeEClass, DESCRIPTORLIST_TYPE__DESCRIPTORS);

        descriptorproviderTypeEClass = createEClass(DESCRIPTORPROVIDER_TYPE);
        createEAttribute(descriptorproviderTypeEClass, DESCRIPTORPROVIDER_TYPE__ID);
        createEAttribute(descriptorproviderTypeEClass, DESCRIPTORPROVIDER_TYPE__NAME);
        createEAttribute(descriptorproviderTypeEClass, DESCRIPTORPROVIDER_TYPE__URL);
        createEAttribute(descriptorproviderTypeEClass, DESCRIPTORPROVIDER_TYPE__VENDOR);
        createEAttribute(descriptorproviderTypeEClass, DESCRIPTORPROVIDER_TYPE__VERSION);

        descriptorresultlistsTypeEClass = createEClass(DESCRIPTORRESULTLISTS_TYPE);
        createEReference(descriptorresultlistsTypeEClass, DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT);

        descriptorresultTypeEClass = createEClass(DESCRIPTORRESULT_TYPE);
        createEReference(descriptorresultTypeEClass, DESCRIPTORRESULT_TYPE__DESCRIPTORVALUE);
        createEAttribute(descriptorresultTypeEClass, DESCRIPTORRESULT_TYPE__DESCRIPTORID);
        createEAttribute(descriptorresultTypeEClass, DESCRIPTORRESULT_TYPE__ERROR_STRING);
        createEAttribute(descriptorresultTypeEClass, DESCRIPTORRESULT_TYPE__STRUCTUREID);

        descriptorTypeEClass = createEClass(DESCRIPTOR_TYPE);
        createEReference(descriptorTypeEClass, DESCRIPTOR_TYPE__PARAMETER);
        createEAttribute(descriptorTypeEClass, DESCRIPTOR_TYPE__ID);
        createEAttribute(descriptorTypeEClass, DESCRIPTOR_TYPE__ONTOLOGYID);
        createEAttribute(descriptorTypeEClass, DESCRIPTOR_TYPE__PROVIDER);

        descriptorvalueTypeEClass = createEClass(DESCRIPTORVALUE_TYPE);
        createEAttribute(descriptorvalueTypeEClass, DESCRIPTORVALUE_TYPE__INDEX);
        createEAttribute(descriptorvalueTypeEClass, DESCRIPTORVALUE_TYPE__LABEL);
        createEAttribute(descriptorvalueTypeEClass, DESCRIPTORVALUE_TYPE__VALUE);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__QSAR);

        metadataTypeEClass = createEClass(METADATA_TYPE);
        createEReference(metadataTypeEClass, METADATA_TYPE__REFERENCE);
        createEAttribute(metadataTypeEClass, METADATA_TYPE__AUTHORS);
        createEAttribute(metadataTypeEClass, METADATA_TYPE__DATASETNAME);
        createEAttribute(metadataTypeEClass, METADATA_TYPE__DESCRIPTION);
        createEAttribute(metadataTypeEClass, METADATA_TYPE__LICENSE);
        createEAttribute(metadataTypeEClass, METADATA_TYPE__URL);

        parameterTypeEClass = createEClass(PARAMETER_TYPE);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__KEY);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__VALUE);

        preprocessingStepTypeEClass = createEClass(PREPROCESSING_STEP_TYPE);
        createEAttribute(preprocessingStepTypeEClass, PREPROCESSING_STEP_TYPE__ID);
        createEAttribute(preprocessingStepTypeEClass, PREPROCESSING_STEP_TYPE__NAME);
        createEAttribute(preprocessingStepTypeEClass, PREPROCESSING_STEP_TYPE__NAMESPACE);
        createEAttribute(preprocessingStepTypeEClass, PREPROCESSING_STEP_TYPE__ORDER);
        createEAttribute(preprocessingStepTypeEClass, PREPROCESSING_STEP_TYPE__VENDOR);

        preprocessingTypeEClass = createEClass(PREPROCESSING_TYPE);
        createEReference(preprocessingTypeEClass, PREPROCESSING_TYPE__PREPROCESSING_STEP);

        qsarTypeEClass = createEClass(QSAR_TYPE);
        createEReference(qsarTypeEClass, QSAR_TYPE__STRUCTURELIST);
        createEReference(qsarTypeEClass, QSAR_TYPE__DESCRIPTORLIST);
        createEReference(qsarTypeEClass, QSAR_TYPE__DESCRIPTORPROVIDERS);
        createEReference(qsarTypeEClass, QSAR_TYPE__PREPROCESSING);
        createEReference(qsarTypeEClass, QSAR_TYPE__RESPONSELIST);
        createEReference(qsarTypeEClass, QSAR_TYPE__RESPONSEUNIT);
        createEReference(qsarTypeEClass, QSAR_TYPE__METADATA);
        createEReference(qsarTypeEClass, QSAR_TYPE__DESCRIPTORRESULTLIST);

        resourceTypeEClass = createEClass(RESOURCE_TYPE);
        createEReference(resourceTypeEClass, RESOURCE_TYPE__STRUCTURE);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__CHECKSUM);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__CONTAINS_ERRORS);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__EXCLUDED);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__FILE);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__ID);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__NAME);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__NO2D);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__NO3D);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__NO_MOLS);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__TYPE);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__URL);

        responsesListTypeEClass = createEClass(RESPONSES_LIST_TYPE);
        createEReference(responsesListTypeEClass, RESPONSES_LIST_TYPE__RESPONSE);

        responseTypeEClass = createEClass(RESPONSE_TYPE);
        createEAttribute(responseTypeEClass, RESPONSE_TYPE__VALUE);
        createEAttribute(responseTypeEClass, RESPONSE_TYPE__STRUCTURE_ID);
        createEAttribute(responseTypeEClass, RESPONSE_TYPE__UNIT);

        responseunitTypeEClass = createEClass(RESPONSEUNIT_TYPE);
        createEAttribute(responseunitTypeEClass, RESPONSEUNIT_TYPE__DESCRIPTION);
        createEAttribute(responseunitTypeEClass, RESPONSEUNIT_TYPE__ID);
        createEAttribute(responseunitTypeEClass, RESPONSEUNIT_TYPE__NAME);
        createEAttribute(responseunitTypeEClass, RESPONSEUNIT_TYPE__SHORTNAME);
        createEAttribute(responseunitTypeEClass, RESPONSEUNIT_TYPE__URL);

        structurelistTypeEClass = createEClass(STRUCTURELIST_TYPE);
        createEReference(structurelistTypeEClass, STRUCTURELIST_TYPE__RESOURCES);

        structureTypeEClass = createEClass(STRUCTURE_TYPE);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__PROBLEM);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__HAS2D);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__HAS3D);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__ID);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__INCHI);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__RESOURCEID);
        createEAttribute(structureTypeEClass, STRUCTURE_TYPE__RESOURCEINDEX);

        // Create enums
        typeTypeEEnum = createEEnum(TYPE_TYPE);

        // Create data types
        typeTypeObjectEDataType = createEDataType(TYPE_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
        BibtexmlPackage theBibtexmlPackage = (BibtexmlPackage)EPackage.Registry.INSTANCE.getEPackage(BibtexmlPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(descriptorlistTypeEClass, DescriptorlistType.class, "DescriptorlistType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDescriptorlistType_Descriptors(), this.getDescriptorType(), null, "descriptors", null, 0, -1, DescriptorlistType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(descriptorproviderTypeEClass, DescriptorproviderType.class, "DescriptorproviderType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDescriptorproviderType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, DescriptorproviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorproviderType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, DescriptorproviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorproviderType_URL(), theXMLTypePackage.getString(), "uRL", null, 0, 1, DescriptorproviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorproviderType_Vendor(), theXMLTypePackage.getString(), "vendor", null, 0, 1, DescriptorproviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorproviderType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, DescriptorproviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(descriptorresultlistsTypeEClass, DescriptorresultlistsType.class, "DescriptorresultlistsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDescriptorresultlistsType_Descriptorresult(), this.getDescriptorresultType(), null, "descriptorresult", null, 0, -1, DescriptorresultlistsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(descriptorresultTypeEClass, DescriptorresultType.class, "DescriptorresultType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDescriptorresultType_Descriptorvalue(), this.getDescriptorvalueType(), null, "descriptorvalue", null, 0, -1, DescriptorresultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorresultType_Descriptorid(), theXMLTypePackage.getString(), "descriptorid", null, 0, 1, DescriptorresultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorresultType_ErrorString(), theXMLTypePackage.getString(), "errorString", null, 0, 1, DescriptorresultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorresultType_Structureid(), theXMLTypePackage.getString(), "structureid", null, 0, 1, DescriptorresultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(descriptorTypeEClass, DescriptorType.class, "DescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDescriptorType_Parameter(), this.getParameterType(), null, "parameter", null, 0, -1, DescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, DescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorType_Ontologyid(), theXMLTypePackage.getString(), "ontologyid", null, 0, 1, DescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorType_Provider(), theXMLTypePackage.getString(), "provider", null, 0, 1, DescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(descriptorvalueTypeEClass, DescriptorvalueType.class, "DescriptorvalueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDescriptorvalueType_Index(), theXMLTypePackage.getInt(), "index", null, 0, 1, DescriptorvalueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorvalueType_Label(), theXMLTypePackage.getString(), "label", null, 0, 1, DescriptorvalueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDescriptorvalueType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, DescriptorvalueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_Qsar(), this.getQsarType(), null, "qsar", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(metadataTypeEClass, MetadataType.class, "MetadataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMetadataType_Reference(), theBibtexmlPackage.getBibTeXMLEntriesClass(), null, "reference", null, 0, -1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMetadataType_Authors(), theXMLTypePackage.getString(), "authors", null, 0, 1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMetadataType_Datasetname(), theXMLTypePackage.getString(), "datasetname", null, 0, 1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMetadataType_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMetadataType_License(), theXMLTypePackage.getString(), "license", null, 0, 1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMetadataType_URL(), theXMLTypePackage.getString(), "uRL", null, 0, 1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getParameterType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameterType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(preprocessingStepTypeEClass, PreprocessingStepType.class, "PreprocessingStepType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPreprocessingStepType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, PreprocessingStepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreprocessingStepType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PreprocessingStepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreprocessingStepType_Namespace(), theXMLTypePackage.getString(), "namespace", null, 0, 1, PreprocessingStepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreprocessingStepType_Order(), theXMLTypePackage.getString(), "order", null, 0, 1, PreprocessingStepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreprocessingStepType_Vendor(), theXMLTypePackage.getString(), "vendor", null, 0, 1, PreprocessingStepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(preprocessingTypeEClass, PreprocessingType.class, "PreprocessingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPreprocessingType_PreprocessingStep(), this.getPreprocessingStepType(), null, "preprocessingStep", null, 0, -1, PreprocessingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(qsarTypeEClass, QsarType.class, "QsarType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getQsarType_Structurelist(), this.getStructurelistType(), null, "structurelist", null, 0, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Descriptorlist(), this.getDescriptorlistType(), null, "descriptorlist", null, 0, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Descriptorproviders(), this.getDescriptorproviderType(), null, "descriptorproviders", null, 0, -1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Preprocessing(), this.getPreprocessingType(), null, "preprocessing", null, 0, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Responselist(), this.getResponsesListType(), null, "responselist", null, 0, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Responseunit(), this.getResponseunitType(), null, "responseunit", null, 0, -1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Metadata(), this.getMetadataType(), null, "metadata", null, 1, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getQsarType_Descriptorresultlist(), this.getDescriptorresultlistsType(), null, "descriptorresultlist", null, 0, 1, QsarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(resourceTypeEClass, ResourceType.class, "ResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getResourceType_Structure(), this.getStructureType(), null, "structure", null, 1, -1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_Checksum(), theXMLTypePackage.getString(), "checksum", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_ContainsErrors(), theXMLTypePackage.getBoolean(), "containsErrors", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_Excluded(), theXMLTypePackage.getBoolean(), "excluded", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_File(), theXMLTypePackage.getString(), "file", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_No2d(), theXMLTypePackage.getInt(), "no2d", "-1", 0, 1, ResourceType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_No3d(), theXMLTypePackage.getInt(), "no3d", "-1", 0, 1, ResourceType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_NoMols(), theXMLTypePackage.getInt(), "noMols", "0", 0, 1, ResourceType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_Type(), this.getTypeType(), "type", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResourceType_URL(), theXMLTypePackage.getString(), "uRL", null, 0, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(responsesListTypeEClass, ResponsesListType.class, "ResponsesListType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getResponsesListType_Response(), this.getResponseType(), null, "response", null, 0, -1, ResponsesListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(responseTypeEClass, ResponseType.class, "ResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getResponseType_Value(), theXMLTypePackage.getString(), "value", "NaN", 0, 1, ResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseType_StructureID(), theXMLTypePackage.getString(), "structureID", null, 1, 1, ResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseType_Unit(), theXMLTypePackage.getString(), "unit", null, 0, 1, ResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(responseunitTypeEClass, ResponseunitType.class, "ResponseunitType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getResponseunitType_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, ResponseunitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseunitType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, ResponseunitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseunitType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, ResponseunitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseunitType_Shortname(), theXMLTypePackage.getString(), "shortname", null, 0, 1, ResponseunitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResponseunitType_URL(), theXMLTypePackage.getString(), "uRL", null, 0, 1, ResponseunitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(structurelistTypeEClass, StructurelistType.class, "StructurelistType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getStructurelistType_Resources(), this.getResourceType(), null, "resources", null, 0, -1, StructurelistType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(structureTypeEClass, StructureType.class, "StructureType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStructureType_Problem(), theXMLTypePackage.getString(), "problem", null, 0, -1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Has2d(), theXMLTypePackage.getBoolean(), "has2d", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Has3d(), theXMLTypePackage.getBoolean(), "has3d", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Inchi(), theXMLTypePackage.getString(), "inchi", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Resourceid(), theXMLTypePackage.getString(), "resourceid", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStructureType_Resourceindex(), theXMLTypePackage.getInt(), "resourceindex", null, 0, 1, StructureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(typeTypeEEnum, TypeType.class, "TypeType");
        addEEnumLiteral(typeTypeEEnum, TypeType.XML);
        addEEnumLiteral(typeTypeEEnum, TypeType.TEXT);

        // Initialize data types
        initEDataType(typeTypeObjectEDataType, TypeType.class, "TypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
        addAnnotation
          (descriptorlistTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorlistType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDescriptorlistType_Descriptors(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptors",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (descriptorproviderTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorproviderType",
             "kind", "empty"
           });		
        addAnnotation
          (getDescriptorproviderType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getDescriptorproviderType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getDescriptorproviderType_URL(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "URL"
           });		
        addAnnotation
          (getDescriptorproviderType_Vendor(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "vendor"
           });		
        addAnnotation
          (getDescriptorproviderType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });		
        addAnnotation
          (descriptorresultlistsTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorresultlistsType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDescriptorresultlistsType_Descriptorresult(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptorresult",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (descriptorresultTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorresultType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDescriptorresultType_Descriptorvalue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptorvalue",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getDescriptorresultType_Descriptorid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "descriptorid"
           });		
        addAnnotation
          (getDescriptorresultType_ErrorString(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "errorString"
           });		
        addAnnotation
          (getDescriptorresultType_Structureid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "structureid"
           });		
        addAnnotation
          (descriptorTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDescriptorType_Parameter(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "parameter",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getDescriptorType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getDescriptorType_Ontologyid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ontologyid"
           });		
        addAnnotation
          (getDescriptorType_Provider(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "provider"
           });		
        addAnnotation
          (descriptorvalueTypeEClass, 
           source, 
           new String[] {
             "name", "descriptorvalueType",
             "kind", "empty"
           });		
        addAnnotation
          (getDescriptorvalueType_Index(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "index"
           });		
        addAnnotation
          (getDescriptorvalueType_Label(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "label"
           });		
        addAnnotation
          (getDescriptorvalueType_Value(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "value"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getDocumentRoot_Qsar(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "qsar",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (metadataTypeEClass, 
           source, 
           new String[] {
             "name", "metadataType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getMetadataType_Reference(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "reference",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getMetadataType_Authors(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "authors"
           });		
        addAnnotation
          (getMetadataType_Datasetname(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "datasetname"
           });		
        addAnnotation
          (getMetadataType_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });		
        addAnnotation
          (getMetadataType_License(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "license"
           });		
        addAnnotation
          (getMetadataType_URL(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "URL"
           });		
        addAnnotation
          (parameterTypeEClass, 
           source, 
           new String[] {
             "name", "parameterType",
             "kind", "empty"
           });		
        addAnnotation
          (getParameterType_Key(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "key"
           });		
        addAnnotation
          (getParameterType_Value(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "value"
           });		
        addAnnotation
          (preprocessingStepTypeEClass, 
           source, 
           new String[] {
             "name", "preprocessingStepType",
             "kind", "empty"
           });		
        addAnnotation
          (getPreprocessingStepType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getPreprocessingStepType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getPreprocessingStepType_Namespace(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "namespace"
           });		
        addAnnotation
          (getPreprocessingStepType_Order(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "order"
           });		
        addAnnotation
          (getPreprocessingStepType_Vendor(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "vendor"
           });		
        addAnnotation
          (preprocessingTypeEClass, 
           source, 
           new String[] {
             "name", "preprocessingType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPreprocessingType_PreprocessingStep(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "preprocessingStep",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (qsarTypeEClass, 
           source, 
           new String[] {
             "name", "qsarType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getQsarType_Structurelist(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "structurelist",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Descriptorlist(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptorlist",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Descriptorproviders(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptorproviders",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Preprocessing(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "preprocessing",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Responselist(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "responselist",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Responseunit(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "responseunit",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Metadata(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "metadata",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQsarType_Descriptorresultlist(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "descriptorresultlist",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (resourceTypeEClass, 
           source, 
           new String[] {
             "name", "resourceType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResourceType_Structure(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "structure",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getResourceType_Checksum(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "checksum"
           });		
        addAnnotation
          (getResourceType_ContainsErrors(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "containsErrors"
           });		
        addAnnotation
          (getResourceType_Excluded(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "excluded"
           });		
        addAnnotation
          (getResourceType_File(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "file"
           });		
        addAnnotation
          (getResourceType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getResourceType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getResourceType_No2d(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "no2d"
           });		
        addAnnotation
          (getResourceType_No3d(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "no3d"
           });		
        addAnnotation
          (getResourceType_NoMols(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "noMols"
           });		
        addAnnotation
          (getResourceType_Type(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "type"
           });		
        addAnnotation
          (getResourceType_URL(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "URL"
           });		
        addAnnotation
          (responsesListTypeEClass, 
           source, 
           new String[] {
             "name", "responsesListType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResponsesListType_Response(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "response",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (responseTypeEClass, 
           source, 
           new String[] {
             "name", "responseType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResponseType_Value(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "value",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getResponseType_StructureID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "structureID"
           });		
        addAnnotation
          (getResponseType_Unit(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "unit"
           });		
        addAnnotation
          (responseunitTypeEClass, 
           source, 
           new String[] {
             "name", "responseunitType",
             "kind", "empty"
           });		
        addAnnotation
          (getResponseunitType_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });		
        addAnnotation
          (getResponseunitType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getResponseunitType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getResponseunitType_Shortname(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "shortname"
           });		
        addAnnotation
          (getResponseunitType_URL(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "URL"
           });		
        addAnnotation
          (structurelistTypeEClass, 
           source, 
           new String[] {
             "name", "structurelistType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getStructurelistType_Resources(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resources",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (structureTypeEClass, 
           source, 
           new String[] {
             "name", "structureType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getStructureType_Problem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "problem",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getStructureType_Has2d(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "has2d"
           });		
        addAnnotation
          (getStructureType_Has3d(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "has3d"
           });		
        addAnnotation
          (getStructureType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getStructureType_Inchi(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "inchi"
           });		
        addAnnotation
          (getStructureType_Resourceid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resourceid"
           });		
        addAnnotation
          (getStructureType_Resourceindex(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resourceindex"
           });		
        addAnnotation
          (typeTypeEEnum, 
           source, 
           new String[] {
             "name", "type_._type"
           });		
        addAnnotation
          (typeTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "type_._type:Object",
             "baseType", "type_._type"
           });
    }

} //QsarPackageImpl
