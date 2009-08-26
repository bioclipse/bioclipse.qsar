/*******************************************************************************
 * Copyright (c) 2008 The Bioclipse Project and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth
 *     
 ******************************************************************************/
package net.bioclipse.qsar.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DescriptorproviderType;
import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ResponsesListType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.StructurelistType;
import net.bioclipse.qsar.descriptor.IDescriptorCalculator;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorCalculationResult;
import net.bioclipse.qsar.descriptor.model.DescriptorCategory;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.qsar.descriptor.model.DescriptorParameter;
import net.bioclipse.qsar.descriptor.model.DescriptorProvider;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;
import net.bioclipse.qsar.prefs.QSARPreferenceInitializer;
import net.bioclipse.qsar.prefs.QsarPreferenceHelper;
import net.bioclipse.qsar.util.QsarAdapterFactory;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;

public class QsarManager implements IQsarManager{

    private static final Logger logger = Logger.getLogger(QsarManager.class);


    //The descriptor model
    private volatile DescriptorModel model;

    //For console namespace
    public String getManagerName() {
        return "qsar";
    }



    /*====================================================
     * model initialization from EP below
     * ====================================================
     */

    public DescriptorModel getModel() {
        if (model==null) initializeDescriptorModel();
        return model;
    }


    public void addDescriptors(String urlString) throws BioclipseException {
        try {
            URL url = new URL(urlString);
            if (model == null) initializeDescriptorModel();
            model = OntologyHelper.addDescriptorHierarchy(model, url);
        } catch (MalformedURLException exc) {
            throw new BioclipseException("The given URL is not valid.", exc);
        } catch (IOException exc) {
            throw new BioclipseException("Error while adding descriptor.", exc);
        } catch (CoreException exc) {
            throw new BioclipseException("Error while adding descriptor.", exc);
        }
    }

    /**
     * Populate model from OWL Ontology and Extension Point.
     */
    public void initializeDescriptorModel() {

        //Firstly, build hierarchy from descriptor OWL with Jena
        try {
//            model=JenaReader.populateHierarchy();
            model=OntologyHelper.buildDescriptorHierarchy();
            logger.debug("** descriptor model initialized from ontology **");
        } catch (IOException e) {
            logger.error("Could not initialize Descriptor model from ontology:"
                         + e.getMessage());
            //			e.printStackTrace();
            return;
//        } catch (URISyntaxException e) {
//            logger.error("Could not initialize Jena model: " + e.getMessage());
//            //			e.printStackTrace();
//            return;
        } catch ( BioclipseException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( CoreException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (model==null){
            logger.error("Could not initialize Descriptor model from ontology.");
            return;
        }
        
        //Add separate descriptor definition files to model
        QsarHelper.addDescriptorDefinitionsFromFiles(model);

        //Create new list of providers
        List<DescriptorProvider> providers=QsarHelper.readProvidersAndDescriptorImplsfromEP();

        //Here we can see what providers impl does not have an entry in BODO
        checkProvidersAgainstOntology(providers);
        
        model.setProviders(providers);
        
        //Create new list of providers
        model.setUnits( QsarHelper.readUnitsFromEPAndPreferences());

    }
    
    private void checkProvidersAgainstOntology(
                                           List<DescriptorProvider> providers) {

        List<String> descIDs = getDescriptorIDs();
        for (DescriptorProvider prov : providers){
            for (DescriptorImpl impl : prov.getDescriptorImpls()){
                if (!(descIDs.contains( impl.getDefinition()))){
                    logger.error("Descriptor impl: " + impl + " does not " +
                    		"exist in available descriptors.");
                }
            }
        }

    }



    public void updateUnits(){
        if (model==null)
            initializeDescriptorModel();
        else
            model.setUnits( QsarHelper.readUnitsFromEPAndPreferences());
    }



    /*====================================================
     * Getter/setter for model below
     * ====================================================
     */


    /**
     * Get all descriptor categories. Read from EP if not initialized.
     * @return List<String> of category ID's.
     */
    public List<String> getCategories() {

        if (model==null) initializeDescriptorModel();
        List<String> ret=new ArrayList<String>();
        for (DescriptorCategory cat : model.getCategories()){
            ret.add(cat.getId());
        }

        return ret;
    }

    /**
     * 
     * @return
     */
    public List<String> getResponseUnits() {
        if (model==null) initializeDescriptorModel();
        List<String> ret=new ArrayList<String>();
        for (ResponseUnit unit : model.getUnits()){
            ret.add(unit.getId());
        }
        return ret;
    }
    

    /**
     * Get all descriptor providers. Read from EP if not initialized.
     * @return List<String> of provider ID's.
     */
    public List<String> getProviders() {

        if (model==null) initializeDescriptorModel();
        List<String> ret=new ArrayList<String>();
        for (DescriptorProvider prov : model.getProviders()){
            ret.add(prov.getId());
        }

        return ret;
    }

    /**
     * Get all descriptor categories. Read from EP if not initialized.
     * @return List of categories.
     */
    public List<DescriptorCategory> getFullCategories() {
        if (model==null) initializeDescriptorModel();
        return model.getCategories();
    }
    
    /**
     * Get all response unites. Read from EP if not initialized.
     * @return List of categories.
     */
    public List<ResponseUnit> getFullResponseUnits() {
        if (model==null) initializeDescriptorModel();
        return model.getUnits();
    }


    /**
     * Get all descriptor providers. Read from EP if not initialized.
     * @return List of providers.
     */
    public List<DescriptorProvider> getFullProviders() {
        if (model==null) initializeDescriptorModel();
        return model.getProviders();
    }

    /**
     * @param providerID the ID of the provider
     * @return provider or null if not found
     */
    public DescriptorProvider getProviderByID(String providerID) {

        for (DescriptorProvider prov : getFullProviders() ){
            if (prov.getId().equals(providerID)){
                return prov;
            }
        }
        return null;
    }

    /**
     * @param categoryID the ID of the category
     * @return category or null if not found
     */
    public DescriptorCategory getCategoryByID(String categoryID) {
        for (DescriptorCategory cat: getFullCategories() ){
            if (cat.getId().equals(categoryID)){
                return cat;
            }
        }
        return null;
    }

    /**
     * Get all available descriptors.
     * @return List of descriptor IDs or empty List.
     */
    public List<String> getDescriptorIDs() {
        //Collect all descriptors
        List<String> ret=new ArrayList<String>();
        for (Descriptor desc: getFullDescriptors()){
            ret.add(desc.getId());
        }

        return ret;
    }

    public Descriptor getDescriptorByID(String descriptorID) {

        for (Descriptor desc : getFullDescriptors()){
            if (desc.getId().equals(descriptorID))
                return desc;
        }

        return null;
    }


    /**
     * Get all available descriptors.
     * @return List of descriptor IDs or empty List.
     */
    public List<Descriptor> getFullDescriptors() {
        //Collect all descriptors
        List<Descriptor> ret=new ArrayList<Descriptor>();
        for (DescriptorCategory cat : getFullCategories()){
            if (cat.getDescriptors()!=null){
                for (Descriptor desc : cat.getDescriptors()){
                    ret.add(desc);
                }
            }
        }

        //Remove duplicates
        Set<Descriptor> noDups=new LinkedHashSet<Descriptor>(ret);
        List<Descriptor> noDupsList=new ArrayList<Descriptor>(noDups);

        return noDupsList;
    }


    /**
     * Get all descriptors in a category
     * @return List of descriptors or empty List.
     */
    public List<Descriptor> getDescriptorsInCategory(DescriptorCategory category) {
        return category.getDescriptors();
    }

    /**
     * Get all descriptor IDs in a category
     * @return List of descriptor IDs or empty List.
     */
    public List<String> getDescriptorsInCategory(String categoryID) {
        DescriptorCategory category=getCategoryByID(categoryID);

        //Collect all descriptors
        List<String> ret=new ArrayList<String>();
        for (Descriptor desc: category.getDescriptors()){
            ret.add(desc.getId());
        }

        return ret;
    }


    /**
     * Get all descriptor implementation IDs for a provider by ID or shortname.
     * @return List of descriptor implementation IDs or empty List.
     * @throws BioclipseException 
     */
    public List<String> getDescriptorImplsByProvider(String providerIDorShortName) throws BioclipseException {

        DescriptorProvider provider = getProviderByID(providerIDorShortName);
        if (provider==null)
            provider=getProviderByShortName( providerIDorShortName );
        if (provider==null)
            throw new BioclipseException("No provider could be found with " +
            		"id/name=" + providerIDorShortName);
        List<String> ret=new ArrayList<String>();
        for (DescriptorImpl desc : provider.getDescriptorImpls()){
            ret.add(desc.getId());
        }

        return ret;
    }
    
    /**
     * Get all descriptor implementation IDs for a provider.
     * @return List of descriptor implementation IDs or empty List.
     */
    public DescriptorProvider getProviderByShortName(String providerShortName) {

        for (DescriptorProvider prov : getFullProviders()){
            if (prov.getShortName().equals( providerShortName ))
                return prov;
        }
        return null;
    }


    /**
     * Get all descriptor implementations for a provider.
     * @return List of descriptors
     */
    public List<DescriptorImpl> getFullDescriptorImpls(DescriptorProvider provider) {
        return provider.getDescriptorImpls();
    }

    /**
     * Get all descriptor implementations for a provider.
     * @return List of descriptors
     */
    public List<DescriptorImpl> getFullDescriptorImpls() {
        List<DescriptorImpl> ret=new ArrayList<DescriptorImpl>();
        for (DescriptorProvider prov : getFullProviders()){
            if (prov.getDescriptorImpls()!=null)
                ret.addAll(prov.getDescriptorImpls());
        }
        return ret;
    }


    /**
     * Return list of implIDs for a given a descriptorID
     * @throws BioclipseException 
     */
    public List<String> getDescriptorImpls(String ontologyID) throws BioclipseException {
        
        String realOntologyID = getRealOntologyID(ontologyID);
        List<String> ret= new ArrayList<String>();
        for (DescriptorImpl impl : getFullDescriptorImpls()){
            if (impl.getDefinition().equals(realOntologyID))
                ret.add(impl.getId());
        }
        return ret;
    }




    /**
     * Return list of descriptorImpls for a given a descriptorID
     */
    public List<DescriptorImpl> getDescriptorImplsForDescriptor(String descriptorID) {
        List<DescriptorImpl> ret= new ArrayList<DescriptorImpl>();
        for (DescriptorImpl impl : getFullDescriptorImpls()){
            if (impl.getDefinition().equals(descriptorID))
                ret.add(impl);
        }
        return ret;
    }


    public DescriptorImpl getDescriptorImplByID(String descriptorImplID) {

        for (DescriptorProvider provider : getFullProviders()){
            for (DescriptorImpl desc : provider.getDescriptorImpls()){
                if (desc.getId().equals(descriptorImplID)){
                    return desc;
                }
            }
        }

        throw new NoSuchElementException("Could not find a descriptor with id: " 
                                         + descriptorImplID);

    }


    public boolean existsDescriptor(String descriptorID) {

        for (Descriptor desc : getFullDescriptors()){
            if (desc.getId().equals(descriptorID)){
                return true;
            }
        }

        return false;

    }

    public boolean existsDescriptorImpl(String descriptorImplID) {

        for (DescriptorProvider provider : getFullProviders()){
            for (DescriptorImpl desc : provider.getDescriptorImpls()){
                if (desc.getId().equals(descriptorImplID)){
                    return true;
                }
            }
        }

        return false;

    }


    /**
     * Returns preferred impl for a descriptorID or null if none existing
     */
    public DescriptorImpl getPreferredImpl(String descriptorID){

        //Read preference and get order of providers
        
        IPreferenceStore store =
            Activator.getDefault().getPreferenceStore();

        String ret = store.getString( 
                                QSARConstants.QSAR_PROVIDERS_ORDER_PREFERENCE );

        if (ret==null || ret.equalsIgnoreCase( "error" )){
            //If empty, initialize prefs from scratch
            new QSARPreferenceInitializer().initializeDefaultPreferences();
            
            ret=store.getString( QSARConstants.QSAR_PROVIDERS_ORDER_PREFERENCE);

            if (ret==null || ret.equalsIgnoreCase( "error" )){
                //If still empty, give up
                logger.equals( "Could not get default DescrProvider." );
                return null;
            }
        }

        //String of names
        String[] provArray=QsarPreferenceHelper.parseQsarPreferenceString(ret);

        for (String providerName : provArray){
            String providerID=QsarPreferenceHelper.getProviderID(providerName);
            if (providerID!=null){
                DescriptorProvider prov = getProviderByID(providerID);
                if (prov!=null){
                    for (DescriptorImpl impl : prov.getDescriptorImpls()){
                        if (impl.getDefinition().equals(descriptorID))
                            return impl;
                    }
                }
            } else{
                logger.error("Could not locate provider by name: " + providerName);
            }
        }
        //No impl found for this descrID
        return null;
    }


    /**
     * Get descriptor implementation be descriptorID and providerID or null if
     * none matching.
     * @throws BioclipseException 
     */
    public DescriptorImpl getDescriptorImpl(String ontologyID, String providerID) throws BioclipseException {

        for (String descriptorImplID : getDescriptorImplsByProvider(providerID)){
            DescriptorImpl impl = getDescriptorImplByID(descriptorImplID);
            if (impl.getDefinition().equals(getRealOntologyID( ontologyID)))
                return impl;
        }

        throw new BioclipseException("No implementation for ontololgyID " 
                                 + ontologyID + " for provider: " + providerID);
    }




    /*
     * Below are necessary?
     * 
     * 
     */





    /*====================================================
     * Calculations below
     * ====================================================
     */

    /**
     * Calculate descriptors for N molecules with D descriptors with P params.
     * @throws BioclipseException 
     * 
     */
    public Map<? extends IMolecule, List<IDescriptorResult>> calculate(
                                                                       List<? extends IMolecule> molecules, 
                                                                       List<DescriptorType> descriptorTypes, IProgressMonitor monitor) throws BioclipseException{

        Map<IMolecule, List<DescriptorType>> molDescMap=new HashMap<IMolecule, List<DescriptorType>>();

        for (IMolecule mol : molecules){
            molDescMap.put( mol, descriptorTypes );
        }

        return doCalculation( molDescMap, monitor );


    }


    /*
     * BELOW IS NEW IMPLE
     */
    
    /**
     * Return implementations and their accepted parameters
     * @throws BioclipseException 
     */
    public String show(String ontologyID) throws BioclipseException{

        String ret="";
        List<String> implIDs = getDescriptorImpls( ontologyID );
        for (String implid : implIDs){
            DescriptorImpl impl = getDescriptorImplByID( implid );
            String descstr="Name='" + impl.getName()
            +"',  Provider='" + impl.getProvider().getShortName()+"'\n";
            if (impl.getDescription()!=null)
                descstr=descstr + "Description: " + impl.getDescription()+"\n";
            else
                descstr=descstr + "Description: N/A\n";
            if (impl.getParameters()!=null && impl.getParameters().size()>0){
                for (DescriptorParameter p : impl.getParameters()){
                    descstr=descstr+"  * Parameter: Name='" + p.getKey() 
                    + "', default value='" + p.getDefaultvalue() 
                    + "', Description: " + p.getDescription();
                }
                descstr=descstr+"\n";
            }
            ret= ret + descstr +"----\n";
        }
        if (ret.length()<=0)
            ret="No descriptor implementations found.";
        
        if (ret.endsWith( "----\n" )){
            ret=ret.substring( 0, ret.length()-6 );
        }

        
        return ret;
    }

    /**
     * Output a list of all entries in the BODO.
     * @return
     */
    public String listDescriptors(){
        return listDescriptors(false);
    }

    /**
     * If input starts with 
     * http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#
     * then remove it and return the rightmost part
     * @param ontologyID
     * @return the rightmost part or entire string if not starting with http://
     */
    public String toShortOntologyForm(String ontologyID){
        if (ontologyID.startsWith( "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#" )){
            return ontologyID.substring( ontologyID.indexOf( "#" )+1 );
        }
        //Could not remove it, return as is
        return ontologyID;
    }
    
    /**
     * Output a list of all entries in the BODO that has an implementation
     * @return
     */
    public String listDescriptors(boolean hasImpl){

        StringBuffer buffer=new StringBuffer();
        for (Descriptor desc : getFullDescriptors()){
            
            String descstr=toShortOntologyForm( desc.getId() + " - " + desc.getName());
            
            List<String> implIDs;
            try {
                implIDs = getDescriptorImpls( desc.getId() );

                descstr=descstr + " [";
                for (String implID : implIDs){
                    DescriptorImpl impl = getDescriptorImplByID( implID ); 
                    descstr=descstr + impl.getProvider().getShortName() + ", ";
                }
                if (descstr.endsWith( ", " )){
                    descstr=descstr.substring( 0, descstr.length()-2 );
                }
                if (hasImpl){
                    if (implIDs!=null && implIDs.size()>0)
                        buffer.append( descstr + "]\n" );
                }
                else{
                    buffer.append( descstr + "]\n" );
                }
            } catch ( BioclipseException e ) {
                logger.error(e.getMessage());
            }
        }
        
        return buffer.toString();
    }

    
    /**
     * Accept full ontology id or just the last part and return a full ontologyID
     * Also removes parameters section after and including ?
     * @param ontologyID could be short or long form
     * @return
     * @throws BioclipseException 
     */
    public String getRealOntologyID( String ontologyID ) throws BioclipseException {

        String fullOntologyID="";
        
        //Remove parameters section after and including ?
        if (ontologyID.indexOf( "?" )>0){
            ontologyID=ontologyID.substring( 0, ontologyID.indexOf( "?" ) );
        }

        //If already full form, return it as is
        if (ontologyID.startsWith( 
        "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#" ))
            fullOntologyID=ontologyID;
        
        else{
            //Try to add it and see if it is valid
            fullOntologyID=
                "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#"
                + ontologyID;
        }

        //Confirm validity or throw exception
        for (Descriptor desc : getFullDescriptors()){
            if (desc.getId().equals( fullOntologyID ))
                    return fullOntologyID;
        }

        throw new BioclipseException("No descriptor exists with ID: " + ontologyID);
    }

    
    /*
     * BELOW IS NEW CALC2
     */

    
    /**
     * calculate("CCC", "xlogp")  > choose preferred impl
     * calculate("CCC", "bondcount?order=s")  > use param and preferred impl
     * Input can be short/full ontology id and optionally a parameter
     * @throws BioclipseException 
     */
    public IDescriptorResult calculate(IMolecule mol, String descriptor) throws BioclipseException{
        
        //Create a new instance of a descriptorimpl
        DescriptorImpl dimpl = getPreferredImpl( 
                                 getRealOntologyID( descriptor )).newInstance();
        parseDescriptorParameters(dimpl,descriptor);
        logger.debug("Chosen provider: " + dimpl.getProvider().getShortName() 
                     + " for descriptor: " + toShortOntologyForm( descriptor ));
        return calculate( mol, dimpl);
    }
    
    /**
     * calculate({"CCC","CCCN"}, {"bondcount/order=s","bpol"})
     * No provider specified
     * 
     * Input can be short/full ontology ids and optionally with parameters
     * @throws BioclipseException 
     * @throws BioclipseException 
     */
    public DescriptorCalculationResult calculate(List<IMolecule> mols, List<String> descriptors) throws BioclipseException{

        //Just propagate with provider as null
        return calculate( mols, descriptors, null );
    }
    
    /**
     * calculate({"CCC","CCCN"}, {"bondcount/order=s","bpol"}, "cdk.rest")
     * 
     * Input can be short/full ontology ids and optionally with parameters
     * @throws BioclipseException 
     * @throws BioclipseException 
     */
    public DescriptorCalculationResult calculate(List<IMolecule> mols, List<String> descriptors, String provider) throws BioclipseException{

        Map<IMolecule, List<DescriptorImpl>> calculationMap=new HashMap<IMolecule, List<DescriptorImpl>>();
        
        //All mols..
        for (IMolecule mol : mols){
            
            List<DescriptorImpl> localDList=new ArrayList<DescriptorImpl>();
            
            //All descriptors one by one
            for (String descriptor : descriptors){
                
                DescriptorImpl dimpl;
                if (provider==null){
                    dimpl = getPreferredImpl(getRealOntologyID( descriptor )).newInstance();
                }else{
                    dimpl=getDescriptorImpl(getRealOntologyID( descriptor ), provider).newInstance();
                }
                parseDescriptorParameters(dimpl,descriptor);

                localDList.add( dimpl );
            }

            calculationMap.put( mol, localDList);

        }
        
        return calculate( calculationMap);
    }
    
    

    /**
     * Convert MAP to an EMF MAP of DescriptorType to operate on
     * @param calculationMap
     * @return
     * @throws BioclipseException 
     */
    private DescriptorCalculationResult calculate(
                                                    Map<IMolecule, List<DescriptorImpl>> calculationMap ) throws BioclipseException {

        Map<IMolecule, List<DescriptorType>> molDescMap=new HashMap<IMolecule, List<DescriptorType>>();

        logger.debug("=== TO CALCULATE ==");
        for (IMolecule mol : calculationMap.keySet()){
            
            List<DescriptorType> descTypes=new ArrayList<DescriptorType>();

            for (DescriptorImpl impl : calculationMap.get( mol )){
                logger.debug("Calculate impl: " + impl.getId() + " for mol: " + mol);
                if (impl.getParameters()!=null){
                    for (DescriptorParameter param : impl.getParameters()){
                        logger.debug("   Parameter: key='" + param.getKey() + "', value='" + param.getValue() + "'");
                    }
                }

                //Convert to EMF DescriptorType
                DescriptorType descType=createDescriptorType2(impl);
                descTypes.add(descType);

            }

            molDescMap.put( mol, descTypes );
            
        }

        //Do QSAR calculation
        Map<IMolecule, List<IDescriptorResult>> res = doCalculation( molDescMap, new NullProgressMonitor() );

        DescriptorCalculationResult dcalres=new DescriptorCalculationResult(res);
        
        
        //Debug out
        logger.debug("==== RESULTS ====");
        for (IMolecule mol : res.keySet()){
            logger.debug("Molecule: " + mol);
            List<IDescriptorResult> lst = res.get( mol );
            for (IDescriptorResult dres : lst){
                logger.debug( "  - " + dres );
            }
            
        }

        return dcalres;
    }



    /**
     * Parse input string and if it contains parameters, set it on the dimpl.
     * @param dimpl
     * @param descriptor
     * @throws BioclipseException
     */
    private void parseDescriptorParameters( DescriptorImpl dimpl,
                                            String descriptor ) throws BioclipseException{

        if (dimpl.getParameters()==null || dimpl.getParameters().size()<=0)
            return;
        
        int paramstart=descriptor.indexOf( "?" );
        //If no param available, just return
        if (paramstart<0) return;
        
        String paramstring=descriptor.substring( paramstart+1 );
        StringTokenizer tok=new StringTokenizer(paramstring, ",");
        if (tok.hasMoreTokens()){
            String paramstr=tok.nextToken();
            boolean foundParam = false;
            if (paramstr.indexOf( "=" )>0){
                String paramkey=paramstr.substring( 0, 
                                                    paramstr.indexOf( "=" ) );
                String paramval=paramstr.substring( 
                                                   paramstr.indexOf( "=" )+1, paramstr.length() );
                for (DescriptorParameter p : dimpl.getParameters()){
                    if (p.getKey().equals( paramkey )){
                        if (p.getListedvalues()!=null && p.getListedvalues().size()>0){
                            //We have enum, must be one of defined values
                            if (!(p.getListedvalues().contains( paramval ))){

                                //Display a nic error
                                String estr="Parameter value for " + paramkey 
                                            + " must be one of: ";
                                for (String aval : p.getListedvalues()){
                                    estr=estr + aval + ", ";
                                }
                                estr=estr.substring( 0, estr.length()-2 );
                                throw new BioclipseException(estr);
                            }
                        }

                        //Accept the value as is
                        p.setValue( paramval );
                        foundParam=true;
                    }
                }
            }
            
            if (!foundParam){
                throw new BioclipseException("Could not parse parameter: " 
                                             + paramstr);
            }
            
        }
        
    }



    /**
     * calculate("CCC", "xlogp", "cdk.rest")
     * @throws BioclipseException  
     */
    public IDescriptorResult calculate(IMolecule mol, 
                                                  String ontologyID, 
                                                  String providerID)
                                                  throws BioclipseException {
        //Look up impl be ontologyID and provider
        DescriptorImpl dimpl = getDescriptorImpl( ontologyID, providerID );
        parseDescriptorParameters(dimpl,ontologyID);
        return calculate( mol, dimpl);
    }

    /**
     * Convenience call to set up calculationMap for one mol and one descimpl
     * @throws BioclipseException 
     */
    private IDescriptorResult calculate( IMolecule mol,
                                                    DescriptorImpl impl ) throws BioclipseException {
        
        Map<IMolecule, List<DescriptorImpl>> calculationMap=
                                 new HashMap<IMolecule, List<DescriptorImpl>>();
        List<DescriptorImpl> impls = new ArrayList<DescriptorImpl>();
        impls.add( impl );
        calculationMap.put( mol, impls );
        DescriptorCalculationResult res =  calculate( calculationMap );
        
        //We know we want only one result for one mol
        List<IDescriptorResult> results = res.getResultMap().get( mol );
        return results.get( 0 );
    }
    
    
    
    /*
     * BELOW IS OLD IMPLE
     */
    public List<IDescriptorResult> calculate(IMolecule molecule,
                                             List<DescriptorType> descriptorTypes) throws BioclipseException {

        List<IMolecule> mollist=new ArrayList<IMolecule>();
        mollist.add(molecule);

        Map<? extends IMolecule, List<IDescriptorResult>> ret = calculate(mollist, descriptorTypes, new NullProgressMonitor());

        return ret.get(molecule);
    }

    public DescriptorType createDescriptorType2(DescriptorImpl impl) {


        DescriptorType modelDescriptor=QsarFactory.eINSTANCE.createDescriptorType();
        modelDescriptor.setId(generateUniqueDescriptorID(null));
        modelDescriptor.setOntologyid( impl.getDefinition());
        modelDescriptor.setProvider( impl.getProvider().getId() );
        
        if (impl.getParameters()!=null){
            for (DescriptorParameter param : impl.getParameters()){

                ParameterType modelParam=QsarFactory.eINSTANCE.createParameterType();
                modelParam.setKey(param.getKey());

                //Set value from imple (=default)
                modelParam.setValue(param.getValue());
                
                modelDescriptor.getParameter().add( modelParam );
            }
        }

        return modelDescriptor;
    }

    /**
     * Add a descriptor with impl and optionally parameters to a QsarModel via 
     * an editingDomain
     * @param qsarModel
     * @param editingDomain
     * @param desc
     * @param impl
     * @param params
     * @return
     */
    public DescriptorType createDescriptorType(QsarType qsarModel, EditingDomain editingDomain, Descriptor desc,
                                               DescriptorImpl impl, List<DescriptorParameter> params) {

        //If qsarModel is null, create a new one
        //Used in tests
        qsarModel=QsarFactory.eINSTANCE.createQsarType();


        //Get and optionally create list of descriptors if null
        DescriptorlistType descriptorList = qsarModel.getDescriptorlist();
        if (descriptorList==null){
            descriptorList=QsarFactory.eINSTANCE.createDescriptorlistType();
            qsarModel.setDescriptorlist(descriptorList);
        }

        //If we have no editingDomain, create a basic one
        //Used in tests
        if (editingDomain==null){
            QsarAdapterFactory factory=new QsarAdapterFactory();
            editingDomain=new AdapterFactoryEditingDomain(factory, new BasicCommandStack());
        }


        //Collect all in a compound command, for ability 
        //to undo everything at the same time
        CompoundCommand cCmd = new CompoundCommand();
        Command cmd;

        DescriptorType modelDescriptor=QsarFactory.eINSTANCE.createDescriptorType();
        modelDescriptor.setId(generateUniqueDescriptorID(qsarModel));
        modelDescriptor.setOntologyid( desc.getId());
        cmd=AddCommand.create(editingDomain, descriptorList, QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS, modelDescriptor);
        cCmd.append(cmd);

        //Check if provider already added to qsarModel
        DescriptorproviderType dimpl=null;
        for (DescriptorproviderType pdimpl : qsarModel.getDescriptorproviders()){
            if (pdimpl.getId().equals(impl.getProvider().getId())){
                dimpl=QsarFactory.eINSTANCE.createDescriptorproviderType();
                dimpl.setId(pdimpl.getId());
            }
        }

        //If this is a new provider, add it to Qsar model
        if (dimpl==null){
            DescriptorProvider prov = impl.getProvider();

            String pid=prov.getId();
            String pname=prov.getName();
            String pvend=prov.getVendor();
            String pvers=prov.getVersion();
            String pns=prov.getNamespace();

            //Create a provider (=descrProviderType) in qsar model root
            DescriptorproviderType newdimpl=QsarFactory.eINSTANCE.createDescriptorproviderType();
            newdimpl.setId(pid);
            newdimpl.setURL(pns);
            newdimpl.setVendor(pvend);
            newdimpl.setName(pname);
            newdimpl.setVersion(pvers);
            cmd=AddCommand.create(editingDomain, qsarModel, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORPROVIDERS, newdimpl);
            cCmd.append(cmd);

            //Reference the created impl by ID
            dimpl=QsarFactory.eINSTANCE.createDescriptorproviderType();
            dimpl.setId(newdimpl.getId());

        }

        modelDescriptor.setProvider( dimpl.getId() );

        //        //Add found provider to descriptor element
        //        cmd=SetCommand.create(editingDomain, modelDescriptor, QsarPackage.Literals.DESCRIPTOR_TYPE__PROVIDER, dimpl);
        //        cCmd.append(cmd);

        //Parameters
        if (impl.getParameters()!=null){
            for (DescriptorParameter param : impl.getParameters()){

                ParameterType modelParam=QsarFactory.eINSTANCE.createParameterType();
                modelParam.setKey(param.getKey());

                //Set value from imple (=default)
                modelParam.setValue(param.getValue());

                //Check if provided parameters have values
                //If so, use it
                if (params!=null){
                    for (DescriptorParameter inparam : params){
                        if (inparam.getKey().equals(param.getKey())){
                            //We have input params, use value
                            modelParam.setValue(inparam.getValue());
                        }					}
                }
                cmd=AddCommand.create(editingDomain, modelDescriptor, QsarPackage.Literals.DESCRIPTOR_TYPE__PARAMETER, modelParam);
                cCmd.append(cmd);

            }
        }
        //Execute the compound command
        editingDomain.getCommandStack().execute(cCmd);

        return modelDescriptor;
    }


    /**
     * Get a new unique descriptorID by combining String descriptor with the 
     * lowest available int
     * @param qsarModel
     * @return
     */
    private String generateUniqueDescriptorID( QsarType qsarModel ) {

        //Build arraylist of existing IDs
        List<String> existingIDs=new ArrayList<String>();
        if (qsarModel!=null){
            for (DescriptorType desc : qsarModel.getDescriptorlist().getDescriptors()){
                existingIDs.add( desc.getId() );
            }
        }
        int cnt=1;
        String prefix="descriptor";
        while(existingIDs.contains( prefix+cnt )){
            cnt++;
        }

        return prefix+cnt;
    }


    /**
     * Collect by provider and invoke calculator ono the molecules.
     * @throws BioclipseException 
     */
    public Map<IMolecule, List<IDescriptorResult>> doCalculation(
                                Map<IMolecule, List<DescriptorType>> molDescMap,
                                IProgressMonitor monitor ) throws BioclipseException {
        
        //The complete workload is all mols x their descs
        int totalWorkload=0;
        for (IMolecule mol : molDescMap.keySet()){
            totalWorkload=totalWorkload+molDescMap.get( mol ).size();
        }
        logger.debug("All providers have a total workload: " + totalWorkload 
                     + " descr calculations");

        //We are to calculate the following combinations
        monitor.beginTask( "Calculating descriptors", totalWorkload );
        monitor.subTask( "Sorting descriptors by provider" );

        Map<IMolecule, List<IDescriptorResult>> allResults=
            new HashMap<IMolecule, List<IDescriptorResult>>();

        //We need to perform one QSAR calculation per provider
        //So, collect them by provider from input Map
        Map<DescriptorProvider,Map<IMolecule, List<DescriptorType>>> moldescByProvider 
        = new HashMap<DescriptorProvider, Map<IMolecule,List<DescriptorType>>>();

        //For all mols
        for (IMolecule mol : molDescMap.keySet()){
            List<DescriptorType> moldescriptors = molDescMap.get( mol );

            //For all descr
            for (DescriptorType desc : moldescriptors){
                String providerID=desc.getProvider();
                DescriptorProvider provider = getProviderByID( providerID );
                if (!(moldescByProvider.containsKey( provider ))){
                    //If not exists, create it
                    moldescByProvider.put( provider, new HashMap<IMolecule, 
                                           List<DescriptorType>>() );
                }
                Map<IMolecule, List<DescriptorType>> localMolDesc 
                = (Map<IMolecule, List<DescriptorType>>) moldescByProvider
                                                         .get( provider );

                if (!(localMolDesc.containsKey( mol ))){
                    localMolDesc.put( mol, new ArrayList<DescriptorType>() );
                }

                List<DescriptorType> localTypes=localMolDesc.get( mol );
                if (!(localTypes.contains( desc ))){
                    localTypes.add( desc );
                }
            }
        }


        //Process one provider at a time
        for (DescriptorProvider provider : moldescByProvider.keySet()){

            monitor.subTask( "Calculating descriptors for provider: " 
                             + provider.getName() );

            IDescriptorCalculator calculator=provider.getCalculator();

            Map<IMolecule, List<DescriptorType>> moldesc 
                                            = moldescByProvider.get( provider );
            
            //The workload for this provider is mols x their descs
            int workload=0;
            for (IMolecule mol : moldesc.keySet()){
                workload=workload+moldesc.get( mol ).size();
            }
            logger.debug("Provider: " + provider.getShortName() 
                        + " has workload: " + workload + " descr calculations");

            //Invoke calculation from providers calculator
            Map<? extends IMolecule, List<IDescriptorResult>> results = 
                calculator.calculateDescriptor(moldesc, 
                                               new SubProgressMonitor(monitor, workload));

            //Add these results to the molecule
            for (IMolecule mol : results.keySet()){
                if (allResults.get(mol)==null) allResults.put(mol, 
                                                              new ArrayList<IDescriptorResult>());
                List<IDescriptorResult> reslist=allResults.get(mol);

                if (results.get( mol )!=null){
                    //Add the computed result to the reslist
                    reslist.addAll(results.get(mol));
                }
            }

        }

        return allResults;

    }

    //===============================
    //QSAR model operatios below
    //===============================

//    /**
//     * Add resources to QSAR model.
//     */
//    public void addResourcesToQsarModel(QsarType qsarmodel, EditingDomain editingDomain, 
//                                        List<IResource> resourcesToAdd, final IProgressMonitor monitor) throws IOException, BioclipseException, CoreException {
//
//        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getJavaCDKManager();
//
//        StructurelistType structList = qsarmodel.getStructurelist();
//        CompoundCommand ccmd=new CompoundCommand();
//
//        //Intermediate storage to keep track of what we have added, 
//        //in order to get unique structureIds
//        List<String> storedStructureIDs=new ArrayList<String>();
//
//        for (IResource resource : resourcesToAdd){
//
//            if (resource instanceof IFile) {
//                IFile file = (IFile) resource;
//
//                //Check if this file is already in model
//                for (ResourceType existingRes : structList.getResources()){
//                    if (existingRes.getName().equals(file.getName())){
//                        throw new UnsupportedOperationException("File: " + 
//                                                                file.getName() + 
//                        " already exists in QSAR analysis.");
//                    }
//                }
//
//                //Load molecules into file
//                List<ICDKMolecule> mollist = cdk.loadMolecules(file);
//                if (mollist==null || mollist.size()<=0){
//                    throw new BioclipseException("No molecules in file");
//                }
//
//                //Count no of 2D and 3D
//                int no2d=0;
//                int no3d=0;
//                for (ICDKMolecule mol : mollist){
//                    if (cdk.has2d( mol ))
//                        no2d++;
//                    if (cdk.has3d( mol ))
//                        no3d++;
//                }
//
//                //Add resource to QSAR model
//                ResourceType res=QsarFactory.eINSTANCE.createResourceType();
//                res.setId(file.getName());
//                res.setName(file.getName());
//                res.setFile(file.getFullPath().toString());
//                res.setNo2d( no2d );
//                res.setNo3d( no3d );
//                res.setNoMols( mollist.size() );
//                Command cmd=AddCommand.create(editingDomain, structList, 
//                                              QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES, res);
//                ccmd.append(cmd);
//
//                //Add all structures in resource as well as children to resource
//                int molindex=0;
//                for (ICDKMolecule mol : mollist){
//
//                    StructureType structure=QsarFactory.eINSTANCE.createStructureType();
//
//                    if (mol.getName()!=null && mol.getName().length()>0){
//                        if (existsStructureIDInModel(qsarmodel, mol.getName())){
//                            //Use a generated structureID
//                            structure.setId( getStructureName(resource,molindex) );
//                        }else{
//                            if (storedStructureIDs.contains( mol.getName() )){
//                                //Use a generated structureID
//                                structure.setId( getStructureName(resource,molindex) );
//                            }else{
//                                //IDs should not start with _
//                                if (mol.getName().startsWith( "_" )){
//                                    //Use a generated structureID
//                                    structure.setId( getStructureName(resource,molindex) );
//                                }else{
//                                    //This id is free and can be used
//                                    structure.setId( mol.getName() );
//                                }
//                            }
//                        }
//                    }else{
//                        //Use a generated structureID
//                        structure.setId( getStructureName(resource,molindex) );
//                    }
//
//                    storedStructureIDs.add( structure.getId() );
//
//                    //If text-based (currently the only supported method in Bioclipse)
//                    structure.setResourceindex( molindex );
//
//                    //Calculate and add inchi to structure
//                    try {
//                        String inchistr = mol.getInChI(
//                            net.bioclipse.core.domain.IMolecule
//                                .Property.USE_CACHED_OR_CALCULATED
//                        );
//                        structure.setInchi( inchistr );
//                    } catch ( Exception e ) {
//                        logger.error("Could not generate inchi for mol " + 
//                                     molindex + " in file " + file.getName());
//                    }
//
//                    cmd=AddCommand.create(editingDomain, res, 
//                                          QsarPackage.Literals.RESOURCE_TYPE__STRUCTURE, structure);
//                    ccmd.append(cmd);
//
//                    molindex++;
//                }
//
//            }
//        }
//
//        //Execute the CompoundCommand
//        editingDomain.getCommandStack().execute(ccmd);    
//
//    }
    
    /**
     * Add resources to QSAR model, and also add selected property as response
     */
    public void addResourcesAndResponsesToQsarModel(
                                         QsarType qsarmodel, 
                                         EditingDomain editingDomain, 
                                         Map<IFile, Object> resourcePropertyMap, 
                                         final IProgressMonitor monitor) 
                                         throws IOException, 
                                         BioclipseException, 
                                         CoreException {

        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault()
                                                           .getJavaCDKManager();

        if (monitor.isCanceled()){
            logger.debug("Adding files was cencelled.");
            return;
        }
        
        monitor.subTask( "Parsing file..." );
        
        StructurelistType structList = qsarmodel.getStructurelist();
        ResponsesListType responseList = qsarmodel.getResponselist();
        CompoundCommand ccmd=new CompoundCommand();

        //Intermediate storage to keep track of what we have added, 
        //in order to get unique structureIds
        List<String> storedStructureIDs=new ArrayList<String>();

        for (IFile file  : resourcePropertyMap.keySet()){
            
            //========================================================
            // Add resources and extract structures for the QSAR model
            //========================================================


            //Check if this file is already in model
            for (ResourceType existingRes : structList.getResources()){
                if (existingRes.getName().equals(file.getName())){
                    throw new UnsupportedOperationException("File: " + 
                                                            file.getName() + 
                    " already exists in QSAR analysis.");
                }
            }

            //Load molecules into list from file
            List<ICDKMolecule> mollist = cdk.loadMolecules(file);
            if (mollist==null || mollist.size()<=0){
                throw new BioclipseException("No molecules in file");
            }
            
            if (monitor.isCanceled()){
                logger.debug("Adding files was cencelled.");
                return;
            }

            //Count no of 2D and 3D
            int no2d=0;
            int no3d=0;
            for (ICDKMolecule mol : mollist){
                if (cdk.has2d( mol ))
                    no2d++;
                if (cdk.has3d( mol ))
                    no3d++;
            }

            //Add resource to QSAR model
            ResourceType res=QsarFactory.eINSTANCE.createResourceType();
            res.setId(file.getName());
            res.setName(file.getName());
            res.setFile(file.getFullPath().toString());
            res.setNo2d( no2d );
            res.setNo3d( no3d );
            res.setNoMols( mollist.size() );
            Command cmd=AddCommand.create(editingDomain, structList, 
                                          QsarPackage.Literals
                                          .STRUCTURELIST_TYPE__RESOURCES, res);
            ccmd.append(cmd);

            //Add all structures in resource as well as children to resource
            int molindex=0;
            for (ICDKMolecule mol : mollist){

                StructureType structure=QsarFactory.eINSTANCE
                                                   .createStructureType();

                if (mol.getName()!=null && mol.getName().length()>0){
                    if (existsStructureIDInModel(qsarmodel, mol.getName())){
                        //Use a generated structureID
                        structure.setId( getStructureName(file,molindex) );
                    }else{
                        if (storedStructureIDs.contains( mol.getName() )){
                            //Use a generated structureID
                            structure.setId( getStructureName(file,molindex) );
                        }else{
                            //IDs should not start with _
                            if (mol.getName().startsWith( "_" )){
                               //Use a generated structureID
                               structure.setId(getStructureName(file,molindex));
                            }else{
                               //This id is free and can be used
                               structure.setId( mol.getName() );
                            }
                        }
                    }
                }else{
                    //Use a generated structureID
                    structure.setId( getStructureName(file,molindex) );
                }

                storedStructureIDs.add( structure.getId() );

                //If text-based (currently the only sup. method in Bioclipse)
                structure.setResourceindex( molindex );

                //Do some sanity checks with CDK
                //==============================
                
                //Check atom typing works
                IAtomContainer container = mol.getAtomContainer();
                CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(
                                                 container.getBuilder() );
                Iterator<IAtom> atoms = container.atoms().iterator();

                int totalCharge=0;
                try {
                    while (atoms.hasNext()) {
                        IAtom atom = atoms.next();

                        //Add atom's charge to total charge
                        totalCharge += atom.getFormalCharge() == null ? 0 
                                : atom.getFormalCharge();
                        
                        IAtomType type = matcher.findMatchingAtomType(container, atom);
                        if (type==null){
                            logger.error( "Atom typing error: Could not find atom " +
                            		"type for atom: " + container.getAtomNumber( atom ));
                            structure.getProblem().add( "Atom typing error: " +
                            		"Could not find atom type for atom: " 
                                             + container.getAtomNumber( atom ));
                            res.setContainsErrors( true );

                        }
//                        AtomTypeManipulator.configure(atom, type);
                    }
                }
                catch (CDKException e) {
                    structure.getProblem().add( "Atom typing error: " + e.getMessage());
                    res.setContainsErrors( true );
                    logger.error("Structure: " + structure.getId() 
                         + " in resource: " + file 
                         + " experienced Atom typing error: " + e.getMessage());
                    LogUtils.debugTrace( logger, e );
                }
                
                /*
                //Check so total charge is not zero
                if (totalCharge<=0){
                    String msg="Structure has zero total charge.";
                    logger.warn( msg );
                    structure.getProblem().add( msg);
                    res.setContainsErrors( true );
                }
                */
                
                //Check so not salt, this is not good
                if (cdk.partition( mol )!=null && 
                        cdk.partition( mol ).size()!=1){
                    String msg="Structure can be partitioned into more than " +
                    		"one structure.";
                    logger.warn( msg );
                    structure.getProblem().add( msg);
                    res.setContainsErrors( true );
                }

                //Calculate and add inchi to structure
                try {
                    String inchistr = mol.getInChI(
                                             net.bioclipse.core.domain.IMolecule
                                             .Property.USE_CALCULATED
                    );
                    structure.setInchi( inchistr );
                } catch ( Exception e ) {
                    structure.getProblem().add( "Could not generate inchi: " + e.getMessage());
                    res.setContainsErrors( true );
                    logger.error("Could not generate inchi for mol " + 
                                 molindex + " in file " + file.getName());
                }

                cmd=AddCommand.create(editingDomain, res, 
                                      QsarPackage.Literals
                                      .RESOURCE_TYPE__STRUCTURE, structure);
                ccmd.append(cmd);


                //====================================================
                // Add responses as well for the molecule, if exists
                //====================================================
                if (resourcePropertyMap.get( file )!=null){
                    
                    Object property=resourcePropertyMap.get( file );
                    Object acprop=mol.getAtomContainer().getProperty( property);
                    
//                    System.out.println("WOULD LIKE to add response value: " 
//                                       + acprop + " to structure: " + structure);
                    
                    //Add to responselist
                    ResponseType response1=QsarFactory.eINSTANCE
                                                      .createResponseType();
                    response1.setStructureID( structure.getId());
                    response1.setValue((String)acprop);

                    //response1.setUnit( unit1.getId() );
                    //TODO: implement default unit for project
                    
                    //Use AddCommand since a new structure is sure to not have 
                    //a response already
                    cmd=AddCommand.create(editingDomain, responseList, 
                                          QsarPackage.Literals
                                                 .RESPONSES_LIST_TYPE__RESPONSE, 
                                          response1);
                    ccmd.append(cmd);
                    
                }


                molindex++;
            }

        }

        //Execute the CompoundCommand
        editingDomain.getCommandStack().execute(ccmd);    

    }


    /**
     * Check if this newid already exists in model
     * @param qsarmodel
     * @param newid
     * @return
     */
    private boolean existsStructureIDInModel( QsarType qsarmodel, String newid ) {

        for (ResourceType res : qsarmodel.getStructurelist().getResources()){
            for (StructureType structure : res.getStructure()){
                if (structure.getId().equals( newid ))
                    return true;
            }
        }
        return false;
    }



    /**
     * This method generates a name from a resource with an index
     * @param resource
     * @param molindex
     * @return
     */
    private String getStructureName( IResource resource, int molindex ) {

        String inputname=resource.getName();
        if (molindex<=0)
            return inputname;

        String name=inputname.substring( 0, inputname.length()-4 );
        String ext=inputname.substring( inputname.length()-4, inputname.length() );
        //        return name +"_"+ molindex + ext;
        return name + ext +"-"+ molindex;

    }




    /**
     * Go through and add transient properties to EMF model, which are not 
     * stored in file.
     */
    public void addCalculatedPropertiesToQsarModel( QsarType qsarModel ) {

        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getJavaCDKManager();

        //Do resources first
        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            try {
                List<ICDKMolecule> mols = cdk.loadMolecules( resource.getFile());

                //Count no of 2D and 3D
                int no2d=0;
                int no3d=0;
                for (ICDKMolecule mol : mols){
                    if (cdk.has2d( mol ))
                        no2d++;
                    if (cdk.has3d( mol ))
                        no3d++;
                }

                resource.setNo2d( no2d );
                resource.setNo3d( no3d );
                resource.setNoMols( mols.size() );
            } catch ( Exception e ) {
                logger.error("Error parsing file: " + resource.getFile());
            }
        }
    }



    public void removeResourcesFromModel( QsarType qsarModel,
                                          EditingDomain editingDomain, List<ResourceType> list ) {

        CompoundCommand ccmd=new CompoundCommand();


        for (ResourceType resource : list){

            //Remove this resource, will remove responses too
            Command cmd=RemoveCommand.create(editingDomain, 
                                             qsarModel.getStructurelist(), 
                                             QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES, 
                                             resource);
            ccmd.append(cmd);

            //Also remove all responses, if any
            if (qsarModel.getResponselist()!=null && qsarModel.getResponselist().getResponse().size()>0){

                for (StructureType structure : resource.getStructure()){
                    for (ResponseType response : qsarModel.getResponselist().getResponse()){
                        if (response.getStructureID().equals( structure.getId() )){
                            //Remove this response
                            cmd=RemoveCommand.create(editingDomain, 
                                                     qsarModel.getResponselist(), QsarPackage.Literals.
                                                     RESPONSES_LIST_TYPE__RESPONSE, response);
                            ccmd.append(cmd);
                        }
                    }
                }
            }
            
            //Also remove all descriptorresults, if any
            if (qsarModel.getDescriptorresultlist()!=null && qsarModel.getDescriptorresultlist().getDescriptorresult().size()>0){
                for (DescriptorresultType descres : qsarModel.getDescriptorresultlist().getDescriptorresult()){

                    for (StructureType structure : resource.getStructure()){
                        if (descres.getStructureid().equals( structure.getId() )){
                            //Remove this descriptorresult
                            cmd=RemoveCommand.create(editingDomain, 
                                                     qsarModel.getDescriptorresultlist(), 
                                                     QsarPackage.Literals.
                                                     DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT, descres);
                            ccmd.append(cmd);
                        }
                    }
                }
            }


        }

        //Execute all commands in a batch
        editingDomain.getCommandStack().execute(ccmd);

    }


    /**
     * Add a descriptor to the QSAR model
     */
    public void addDescriptorToModel( QsarType qsarModel, 
                                      EditingDomain editingDomain,
                                      Descriptor desc, 
                                      DescriptorImpl impl ) {

        //Collect all in a compound command, for ability 
        //to undo everything at the same time
        CompoundCommand cCmd = new CompoundCommand();
        Command cmd;

        DescriptorType modelDescriptor=QsarFactory.eINSTANCE.createDescriptorType();
        modelDescriptor.setId(generateUniqueDescriptorID( qsarModel ));
        modelDescriptor.setOntologyid( desc.getId());

        //Check if provider already added to qsarModel
        DescriptorproviderType dprov=null;
        for (DescriptorproviderType pdimpl : qsarModel.getDescriptorproviders()){
            if (pdimpl.getId().equals(impl.getProvider().getId())){
                dprov=QsarFactory.eINSTANCE.createDescriptorproviderType();
                dprov.setId(pdimpl.getId());
            }
        }

        //If this is a new provider, add it to Qsar model
        if (dprov==null){
            DescriptorProvider prov = impl.getProvider();

            String pid=prov.getId();
            String pname=prov.getName();
            String pvend=prov.getVendor();
            String pvers=prov.getVersion();
            String pns=prov.getNamespace();

            //Create a provider (=descrImplType) in qsar model root
            DescriptorproviderType newdprov=QsarFactory.eINSTANCE.createDescriptorproviderType();
            newdprov.setId(pid);
            newdprov.setURL( pns);
            newdprov.setVendor(pvend);
            newdprov.setName(pname);
            newdprov.setVersion(pvers);
            cmd=AddCommand.create(editingDomain, qsarModel, QsarPackage.Literals.QSAR_TYPE__DESCRIPTORPROVIDERS, newdprov);
            cCmd.append(cmd);

            //Reference the created impl by ID
            dprov=QsarFactory.eINSTANCE.createDescriptorproviderType();
            dprov.setId(newdprov.getId());

        }

        modelDescriptor.setProvider( dprov.getId() );

        //Add found impl to descriptor element
        //      cmd=SetCommand.create(editingDomain, modelDescriptor, QsarPackage.Literals.DESCRIPTOR_TYPE__PROVIDER, dprov.getId());
        //      cCmd.append(cmd);

        //Parameters
        if (impl.getParameters()!=null){
            for (DescriptorParameter param : impl.getParameters()){

                ParameterType modelParam=QsarFactory.eINSTANCE.createParameterType();
                modelParam.setKey(param.getKey());
                modelParam.setValue(param.getValue());
                cmd=AddCommand.create(editingDomain, modelDescriptor, QsarPackage.Literals.DESCRIPTOR_TYPE__PARAMETER, modelParam);
                cCmd.append(cmd);

            }
        }

        //Add the descriptor to descriptorList last, for notification issues
        cmd=AddCommand.create(editingDomain, qsarModel.getDescriptorlist(), QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS, modelDescriptor);
        cCmd.append(cmd);

        //Execute the compound command
        editingDomain.getCommandStack().execute(cCmd);        
    }



    public void removeDescriptorsFromModel( QsarType qsarModel,
                                            EditingDomain editingDomain,
                                            List<DescriptorType> list ) {

        CompoundCommand ccmd=new CompoundCommand();

        //Collect commands from selection
        for (DescriptorType descType : list){

            Command cmd=RemoveCommand.create(editingDomain, qsarModel.getDescriptorlist(), QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS, descType);
            ccmd.append(cmd);
            logger.debug("Removing descriptor: " + descType.getId());

            if (qsarModel.getDescriptorresultlist()!=null){

                //Also delete any descriptorresults for this descriptor
                for (DescriptorresultType dres : qsarModel.getDescriptorresultlist().getDescriptorresult()){
                    if (dres.getDescriptorid().equals( descType.getId() )){
                        cmd=RemoveCommand.create(editingDomain, qsarModel.getDescriptorresultlist(), QsarPackage.Literals.DESCRIPTORRESULTLISTS_TYPE__DESCRIPTORRESULT, dres);
                        ccmd.append(cmd);
                        logger.debug("   Removing corresponding descriptorresult: " + dres);
                    }
                }
            }
            


            //Check for unused descriptorproviders and remove them too
            /*
            for (DescriptorproviderType prov : qsarModel.getDescriptorproviders()){
                boolean remove=true;
                for (DescriptorType desc : qsarModel.getDescriptorlist().getDescriptors()){
                    if (desc.getProvider().equals( prov.getId() )){
                        //Nope, still used
                        remove=false;
                    }
                }
                if (remove){
                    cmd=RemoveCommand.create(editingDomain, qsarModel.getDescriptorproviders(), QsarPackage.Literals.QSAR_TYPE__DESCRIPTORPROVIDERS, prov);
                    ccmd.append(cmd);
                    logger.debug("  No uses of qsar provider " + prov.getId() +" so removed.");
                }
            }
            */

        }

        //Execute the commands as one 
        editingDomain.getCommandStack().execute(ccmd);

    }

    /**
     * Add a list of response values to the QSAR model
     */
    public void addResponseUnitToModel( QsarType qsarModel, 
                                      EditingDomain editingDomain,
                                      List<ResponseUnit> list) {

        //Collect all in a compound command, for ability 
        //to undo everything at the same time
        CompoundCommand cCmd = new CompoundCommand();
        Command cmd;
        for (ResponseUnit storedunit : list){
            
            //Make sure already not in QSAR model
            boolean present=false;
            for (ResponseunitType rt : qsarModel.getResponseunit()){
                if (rt.getId().equals( storedunit.getId() ))
                    present=true;
            }

            if (!present){
                ResponseunitType unit1=QsarFactory.eINSTANCE.createResponseunitType();
                unit1.setId( storedunit.getId() );
                unit1.setName( storedunit.getName() );
                unit1.setShortname( storedunit.getShortname() );
                unit1.setDescription( storedunit.getDescription());
                unit1.setURL( storedunit.getUrl() );

                cmd=AddCommand.create(editingDomain, qsarModel, 
                                      QsarPackage.Literals.QSAR_TYPE__RESPONSEUNIT, unit1);
                logger.debug("Adding response value: " + unit1.getId());
                cCmd.append(cmd);
            }
        }

        //Execute the compound command
        if (!cCmd.isEmpty())
            editingDomain.getCommandStack().execute(cCmd);        
    }

    
    public void removeResponseUnitsFromModel( QsarType qsarModel,
                                              EditingDomain editingDomain,
                                              List<ResponseunitType> listToRemove ) {

          CompoundCommand ccmd=new CompoundCommand();



          //Remove the actual response unit too
          for (ResponseunitType unitToRemove : listToRemove){
              
              //Ok, also remove all references to this in responses, if not empty
              if (qsarModel.getResponselist()!=null){
                  for (ResponseType resp : qsarModel.getResponselist().getResponse()){

                      //IF this response has the unit to remove as unit, remove it
                      if (resp.getUnit()!=null && unitToRemove.getId().equals( resp.getUnit())){

                          Command cmd=new SetCommand(editingDomain,resp,
                                                     QsarPackage.Literals.RESPONSE_TYPE__UNIT,
                          "");
                          ccmd.append( cmd );
                      }
                  }
              }

              //Also remove the actuial responseunit
              Command cmd=RemoveCommand.create(editingDomain, qsarModel, 
                                               QsarPackage.Literals.QSAR_TYPE__RESPONSEUNIT, unitToRemove);
              ccmd.append(cmd);
              logger.debug("Removing response value: " + unitToRemove.getId());
          }


          //Execute the commands as one 
          editingDomain.getCommandStack().execute(ccmd);

      }

    
}
