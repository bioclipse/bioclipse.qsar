/*******************************************************************************
 *Copyright (c) 2008 The Bioclipse Team and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    Ola Spjuth - initial API and implementation
 *******************************************************************************/
package net.bioclipse.qsar.ui.builder;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.DescriptorresultType;
import net.bioclipse.qsar.DescriptorresultlistsType;
import net.bioclipse.qsar.DescriptorvalueType;
import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.IDescriptorResult;
import net.bioclipse.qsar.ui.QsarHelper;
import net.bioclipse.qsar.util.QsarAdapterFactory;
import net.bioclipse.qsar.util.QsarResourceFactoryImpl;
import net.sf.bibtexml.BibtexmlPackage;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A Builder for QSAR projects that synchronizes the project file 
 * qsar.xml with derived resources, e.g. matrix.csv.
 * 
 * @author ola
 *
 */
public class QSARBuilder extends IncrementalProjectBuilder
{

    private static final Logger logger = Logger.getLogger(QSARBuilder.class);

    public static final int MISSING_KEY_VIOLATION = 1;
    public static final int UNUSED_KEY_VIOLATION = 2;

    /**
     * The unique identifier for this builder
     */
    public static final String BUILDER_ID = "net.bioclipse.qsar.QSARBuilder";

    /**
     * The unique identifier for the audit marker
     */
    private static final String MARKER_ID = "net.bioclipse.qsar.qsarMarker";

    // auditMarker attributes
    public static final String KEY = "key";
    public static final String VIOLATION = "violation";

    //Extend the building with a specific full build identifier since
    //we do not want full build on startup for this project
    public static final int FULL_BUILD_QSAR = 852;

    /**
     * Simple data structure class containing a key and the location of
     * that key in a file
     */
    private class Location
    {
        IFile file;
        String key;
        int charStart;
        int charEnd;
    }

    private Stopwatch stopwatch;

    /**
     * When called by Eclipse, this builder should perform an audit as
     * necessary. If the build kind is <code>INCREMENTAL_BUILD</code>
     * or <code>AUTO_BUILD</code>, the <code>getDelta</code>
     * method can be used during the invocation of this method to
     * obtain information about what changes have occurred since the
     * last invocation of this method. After completing a build, this
     * builder may return a list of projects for which it requires a
     * resource delta the next time it is run.
     * 
     * @param kind the kind of build being requested. Valid values are
     *           <ul>
     *           <li><code>FULL_BUILD</code>- indicates a full
     *           build.</li>
     *           <li><code>INCREMENTAL_BUILD</code>- indicates an
     *           incremental build. </li>
     *           <li><code>AUTO_BUILD</code>- indicates an
     *           automatically triggered incremental build
     *           (autobuilding on).</li>
     *           </ul>
     * @param args a table of builder-specific arguments keyed by
     *           argument name (key type: <code>String</code>, value
     *           type: <code>String</code>); <code>null</code> is
     *           equivalent to an empty map
     * @param monitor a progress monitor, or <code>null</code> if
     *           progress reporting and cancellation are not desired
     * @return the list of projects for which this builder would like
     *         deltas the next time it is run or <code>null</code> if
     *         none
     * @exception CoreException if this build fails.
     * @see IProject#build(int, String, Map, IProgressMonitor)
     */
    protected IProject[] build(
                               int kind, 
                               Map args,
                               IProgressMonitor monitor
    ) throws CoreException
    {
        if (shouldBuild(kind)) {

            ResourcesPlugin.getWorkspace().run(
                                               new IWorkspaceRunnable() {
                                                   public void run(IProgressMonitor monitor)
                                                   throws CoreException
                                                   {
                                                       buildQSARfile(monitor);
                                                   }
                                               },
                                               //                                               getProject(),
                                               //                                               IWorkspace.AVOID_UPDATE,
                                               monitor
            );
        }
        return null;
    }

    /**
     * Clean is an opportunity for a builder to discard any additional state that has 
     * been computed as a result of previous builds. It is recommended that builders 
     * override this method to delete all derived resources created by previous builds, 
     * and to remove all markers of type <code>IMarker.PROBLEM</code> that 
     * were created by previous invocations of the builder. The platform will
     * take care of discarding the builder's last built state (there is no need
     * to call <code>forgetLastBuiltState</code>).
     * 
     * In our case, we already scrub the project as part of the FULL_BUILD
     * so no additional work needed.
     */
    protected void clean(IProgressMonitor monitor) throws CoreException {
        // no additional work needed here
    }

    /**
     * Determines whether files should be built by checking for
     * FULL_BUILD_QSAR, or if the qsar.xml has changed.
     * 
     * @param kind the kind of build
     * @return <code>true</code> if file should be audited, else
     *         <code>false</code>.
     */
    private boolean shouldBuild(int kind) {
        
        //IF not a FULL BUILD, require autobuild to be true
        if (!QsarHelper.isAutoBuild( getProject() ))
            return false;

        if (kind == FULL_BUILD){
            return true;
        }

        //Ok, check if delta is this projects qsar.xml and build if so
        IResourceDelta delta = getDelta(getProject());
        if (delta == null)
            return false;
        IResourceDelta[] children = delta.getAffectedChildren();
        for (int i = 0; i < children.length; i++) {
            IResourceDelta child = children[i];
            String fileName = child.getProjectRelativePath().lastSegment();
            if (fileName.equals(getQsarFile().getName()))
                return true;
        }
        return false;
    }

    private IFile getQsarFile(){
        return getProject().getFile( "qsar.xml" );
    }

    private IFile getDescriptorValuesFile(){
        return getProject().getFile( "descriptorValues.xml" );
    }

    /**
     * Scan the plugin.xml and plugin.properties files and correlate
     * the key/value pairs; any keys appearing in plugin.xml should
     * have a corresponding key/value pair in plugin.properties. Before
     * each lengthy operation, we check to see if the build has been
     * interrupted or canceled. After each lengthy operation, we report
     * progress to the user; while this is not strictly necessary, it
     * is certainly polite. If you do prematurely exit your build
     * process, you may need to call forgetLastBuildState() before
     * exiting so that a full rebuild will be performed the next time.
     * 
     * @param monitor the progress monitor
     */
    private void buildQSARfile(IProgressMonitor monitor) {

        if (!deleteAuditMarkers(getProject()))
            return; 

        if (checkCancel(monitor))
            return;

        //Scan plugin for molecules and descriptors to build
        scanQsarFile(monitor);

        if (checkCancel(monitor))
            return;

        monitor.done();
    }

    /**
     * Do the actual processing of file and descriptor calculation.
     * 
     * @param monitor
     * @throws OperationCanceledException
     */
    private void scanQsarFile(IProgressMonitor monitor) throws OperationCanceledException {

        //Read project file (qsar.xml) into an EMF model
        QsarType qsarModel=readModelFromProjectFile(getQsarFile());
        if (qsarModel==null){
            logger.debug( "Building qsar project '" + getProject() + "' skipped " +
            "since project file could not be parsed." );
            return;
        }

        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }


        logger.debug( "******************************************\n" +
                      "Building qsar project: " + getProject().getName() );
        logger.debug( "******************************************");

        //We have X phases in the qsar building, each with their respective
        //subtask
        monitor.beginTask("Building QSAR project", 5);
        monitor.subTask( "Preparing build...");
        monitor.worked( 1 );

        QsarHelper.setBuildStatus( getProject(), "RUNNING" );
        QsarHelper.setBuildTime( getProject(), "" );
        stopwatch=new Stopwatch();
        stopwatch.start();

        //Verify at least one structure and one descriptor
        //================================================
        if (isQsarTypeEmpty(qsarModel)){
            logger.debug("QSAR model does not contain structures and descriptors. Building canceled.");
            //TODO: remove csv and xml result file
            
            return;
        }


        //Extract structures from model for descriptor calculation
        //================================================
        Map<StructureType, IMolecule> structureMap=extractMoleculesFromQSARType(qsarModel);
        logger.debug("Structure count: " + structureMap.size() );
//        logger.debug(debugStructureMap(structureMap) );
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }

        //List descriptors for calculation
        //================================================
        List<DescriptorType> allDescriptors=qsarModel.getDescriptorlist().getDescriptors();
        logger.debug("Descriptor count: " + allDescriptors.size() );
//        logger.debug(debugDescList(allDescriptors) );
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }

        //Figure out which combos need calculations: dirty or no existing responses
        //================================================
        //Mol > List of descriptor IDs
        Map<IMolecule, List<DescriptorType>> molDescMap = 
                getComboForCalculation(structureMap, allDescriptors, qsarModel);
        logger.debug("In need of calculation (" + molDescMap.size() 
                     + " entries)");
//        logger.debug(debugMolDescMap(molDescMap));
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }

        //Calculate descriptors for all such combos
        //================================================
        int jobSize_old=allDescriptors.size() * structureMap.size()+1;
        int jobSize=molDescMap.size() + 1;
//        monitor.beginTask("Building QSAR project", jobSize);
//        monitor.subTask("Building QSAR project", jobSize);
        
        monitor.worked( 1 );
        monitor.subTask( "Calculating descriptors" );

        //Calculate all changed descriptors
        IQsarManager qsar = net.bioclipse.qsar.init.Activator.getDefault().getQsarManager();
        Map<IMolecule, List<IDescriptorResult>> resultMap=null;
        try {
            logger.debug("Time before calculation: " + stopwatch.toString());
            resultMap = qsar.doCalculation(molDescMap, new SubProgressMonitor(monitor, jobSize));
            logger.debug("Time after calculation: " + stopwatch.toString());
        }catch (BioclipseException e){
            //Check if exception was due to cancellation, no need for error box
            if (checkCancel(monitor)){
                handleInterruptedBuild();
                return;
            }
            
        } catch ( Exception e ) {
            //Check if exception was due to cancellation, no need for error box
            if (checkCancel(monitor)){
                handleInterruptedBuild();
                return;
            }

            LogUtils.handleException( e, logger, net.bioclipse.qsar.ui.Activator.PLUGIN_ID );
            handleInterruptedBuild();
            return;
            
        }

        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }
        monitor.worked( 1 );
        monitor.subTask( "Handling results" );


        logger.debug("Calculation results size: " + resultMap.size());
//        logger.debug(debugResultMap(resultMap));
        monitor.worked(1);
        monitor.subTask("Processing descriptor results");

        //Process results, concatenate with already calculated mols in QsarModel
//        storeDescrResultsInQsarModel(qsarModel, resultMap, structureMap);

        monitor.subTask("Storing descriptor values");
        QsarType valuesModel=readValuesModelFromFile();

        storeDescrResultsInSeparateFile(valuesModel, resultMap, structureMap);
        logger.debug("Stored results in model");
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }

        //Save qsarmodel
        saveModelToFile(qsarModel, getQsarFile());
        logger.debug("Saved QSAR model to file: " + getQsarFile().getName());
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }
        
        //Save valuesmodel
        saveModelToFile(valuesModel, getDescriptorValuesFile());
        try {
            getDescriptorValuesFile().setDerived( true );
        } catch ( CoreException e ) {
            LogUtils.handleException( e, logger, 
                                     net.bioclipse.qsar.ui.Activator.PLUGIN_ID);
        }
        logger.debug("Saved values model to file:" + getDescriptorValuesFile().getName());
        logger.debug("Time: " + stopwatch.toString());
        if (checkCancel(monitor)){
            handleInterruptedBuild();
            return;
        }

        //Make all structures and descriptors not dirty and save the prefs
        makeAllNonDirty(qsarModel);

        monitor.subTask("Writing CSV file");

        //Serialize qsarmodel to CSV file
        serializeToCSV(qsarModel, valuesModel);
        logger.debug("Serialized to CSV.");

        //Serialize mols with results to CML in the future maybe

        stopwatch.stop();
        QsarHelper.setBuildStatus(getProject(), "FINISHED");
        QsarHelper.setBuildTime(getProject(), stopwatch.toString());

        logger .debug ("** Building qsar project: " + getProject().getName() + " FINISHED. **" );

    }


    private QsarType readValuesModelFromFile() {

        //load old file into EMF model, we will extend it if exists 
        QsarType valuesModel=readModelFromProjectFile(getDescriptorValuesFile());
        if (valuesModel==null){
            //We might need to create a new model
            logger.debug( "Could not parse descriptorValues file. " +
                "Creating a new." );
            
            QsarAdapterFactory factory=new QsarAdapterFactory();
            EditingDomain editingDomain=new AdapterFactoryEditingDomain(factory, new BasicCommandStack());

            //Create the super types
            DocumentRoot root=QsarFactory.eINSTANCE.createDocumentRoot();
            
            //Collect commands here
            CompoundCommand cCmd = new CompoundCommand();
            Command cmd;

            //Create editor type to hold everything else
            valuesModel=QsarFactory.eINSTANCE.createQsarType();

//            //could not get SetCommand to work for root element, add directly 
            root.setQsar(valuesModel);
            
        }
        
       
        return valuesModel;
    }

    private void serializeToCSV( QsarType qsarModel, QsarType valuesModel ) {

        List<String> columnLabels=new ArrayList<String>();
        List<String> rowLabels=new ArrayList<String>();
        List<String> responseColumn=new ArrayList<String>();
        float[][] dataset;

        //===============================
        //Set up row and column labels + compute size of dataset
        //===============================
        
        //First, give any structures missing a response an NaN
        fillEmptyRespones(qsarModel);
        
        //Isolate first structure without problems and count its descriptors
        String firstOKStructureID="";
        int numberDescriptorsPerStructure=0;

        //Isolate first structure without error in descriptor calc
        for (DescriptorresultType descres : valuesModel.getDescriptorresultlist().getDescriptorresult()){
            if (descres.getErrorString()==null){
                //No error! Use this as first ok structure until a better way is found
                firstOKStructureID = descres.getStructureid();
            }
            //If this is the structure, get values from this descriptorresult
            if (descres.getStructureid().equals( firstOKStructureID )){
                numberDescriptorsPerStructure=numberDescriptorsPerStructure+descres.getDescriptorvalue().size();
            }
        }
        
        logger.debug("First OK structure: " + firstOKStructureID 
                     + "; # descriptor values for this: " + numberDescriptorsPerStructure);
        
        
        //Set up rowlabels and fill responseColumn
        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            for (StructureType structure : resource.getStructure()){
                rowLabels.add( structure.getId() );
                for (ResponseType resp : qsarModel.getResponselist().getResponse()){
                    if (structure.getId().equals( resp.getStructureID())){
                        responseColumn.add( ""+resp.getValue() );
                    }
                }
            }
        }
        
        if (responseColumn.size()!=rowLabels.size()){
            logger.error("Responses and rows have different length. Serialize to CSV failed.");
            return;
        }

        //Set up columnheaders
        for (DescriptorresultType descres : valuesModel.getDescriptorresultlist().getDescriptorresult()){

            //We only want for one structure so get first one (and hope it has not failed!)
            if (descres.getStructureid().equals( firstOKStructureID )){
                //Get values in order
                for (int i=0; i<descres.getDescriptorvalue().size();i++){
                    DescriptorvalueType val = descres.getDescriptorvalue().get( i );
                    columnLabels.add( val.getLabel());
                }
            }
        }
        logger.debug("============================================");
        logger.debug("Dataset dimensions: " + rowLabels.size() +" rows and " 
                     + columnLabels.size() + " columns");
        
        //If <2 rows and <2 cols, then do not save csv file
        if (rowLabels.size()<2 || columnLabels.size()<1){
            logger.debug( "To few columns or rows to save CSV so skipped." );

            //Remove csv file if exists
            IFile datasetFile=getProject().getFile("dataset.csv");
            if (datasetFile.exists()){
                try {
                    datasetFile.delete( true, new NullProgressMonitor() );
                } catch ( CoreException e ) {
                    LogUtils.debugTrace( logger,e );
                }
            }

            return;
        }

        //==============================
        //Compute dataset from qsarmodel
        //==============================
        dataset=new float[rowLabels.size()][columnLabels.size()];
        
        //Fill with NaN
        for (int i=0; i<dataset.length; i++){
            for (int j=0; j<dataset[i].length; j++){
                dataset[i][j]=Float.NaN;
            }
        }

        //For each row=structureid
        int currentrow=0;
        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            for (StructureType structure : resource.getStructure()){

                //For each column
                int currentcol=0;
                for (DescriptorresultType descres : valuesModel.getDescriptorresultlist().getDescriptorresult()){

                    //If this is the structure, get values from this descriptorresult
                    if (descres.getStructureid().equals( structure.getId() )){

                        if (descres.getDescriptorvalue()!=null){

                            for (int i=0; i<descres.getDescriptorvalue().size();i++){

                                DescriptorvalueType val = descres.getDescriptorvalue().get( i );
                                if (val!= null){
                                    try{
                                        float pval=Float.parseFloat( val.getValue() );
                                        dataset[currentrow][currentcol]=pval;
                                    }catch (NumberFormatException e){
                                        dataset[currentrow][currentcol]=Float.NaN;
                                    }
                                }
                                else{
                                    //IF we have no result here, add NaN
                                    dataset[currentrow][currentcol]=Float.NaN;
                                }

                                currentcol++;
                            }
                        }
                        else{
                            //IF we have no result here, how do we know how many NaN to add?
                            logger.debug("Descriptor value problems for " +
                                "structure: " + structure.getId() 
                                + " and descr: " + descres.getDescriptorid());
                        }
                    }
                }

                currentrow++;
            }
        }



        //Serialize dataset to file
        //=========================
        logger.debug("============================================");
        logger.debug("Serializing to CSV");
        logger.debug("============================================");
        String SEPARATOR=",";
        String NEWLINE="\n";
        
        //We need to ensure that '.' is always decimal separator in all locales
        DecimalFormatSymbols sym=new DecimalFormatSymbols();
        sym.setDecimalSeparator( '.' );
        DecimalFormat formatter = new DecimalFormat("0.00", sym);

        StringBuffer buffer=new StringBuffer();    

        buffer.append(SEPARATOR + "RESPONSE");

        //Serialize one row at a time
        //Start with colheaders
        for (String colheader : columnLabels){
            buffer.append(SEPARATOR + colheader);
        }
        buffer.append(NEWLINE);
        for (int i=0;i< dataset.length; i++){

            //Add label first in line
            buffer.append(rowLabels.get(i));
            buffer.append(SEPARATOR + responseColumn.get(i));

            //Add descriptor values
            for (int j=0;j< dataset[i].length; j++){
                String toAppend="";
                //Serialize NaN as String
                if (Double.isNaN(dataset[i][j])) toAppend="NaN";
                else toAppend=formatter.format(dataset[i][j]);
                buffer.append(SEPARATOR + toAppend);
            }
            buffer.append(NEWLINE);
        }

        logger.debug(buffer.toString());

        logger.debug("============================================");
        logger.debug("============================================");

        //Write this String to file
        InputStream is=new ByteArrayInputStream(buffer.toString().getBytes());
        BufferedInputStream source=new BufferedInputStream(is);

        try {
            IFile datasetFile=getProject().getFile("dataset.csv");
            if (datasetFile.exists()){
                datasetFile.setContents(source, true,true, new NullProgressMonitor());
            }else{
                datasetFile.create(source, true, new NullProgressMonitor());
            }
            datasetFile.setDerived(true);
            logger.debug("Wrote file dataset.csv");
        } catch (CoreException e) {
            logger.error("Writing of file dataset.csv FAILED");
            e.printStackTrace();
        }
        logger.debug("============================================");

        //        if (listOfErrorMessages.size()>0){
        //            logger.error("Finished with the following errors: ");
        //            for (String er: listOfErrorMessages){
        //                logger.error(er);
        //            }
        //
        //        }else{
        //            logger.debug("No errors to report");
        //        }
        logger.debug("============================================");

    }

    private void fillEmptyRespones( QsarType qsarModel ) {

        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            for (StructureType structure : resource.getStructure()){
                ResponseType response=null;
                for (ResponseType resp : qsarModel.getResponselist().getResponse()){
                    if (resp.getStructureID().equals( structure.getId() ))
                        response=resp;
                }
                if (response==null){
                    response=QsarFactory.eINSTANCE.createResponseType();
                    response.setStructureID( structure.getId() );
                    qsarModel.getResponselist().getResponse().add( response );
                }
            }
        }
        
        // TODO Auto-generated method stub
        
    }

    private void handleInterruptedBuild() {

        if (stopwatch!=null)
            stopwatch.stop();
        QsarHelper.setBuildStatus(getProject(), "INTERRUPTED");
        logger.debug( "QSAR build was canceled." );

    }

    private void makeAllNonDirty(QsarType qsarModel) {

        //Set all structures to changed=false
        for (ResourceType resource : qsarModel.getStructurelist().getResources()){
            for (StructureType structure : resource.getStructure()){
                QsarHelper.setChangedInPreference(structure, getProject(), false);
            }
        }

        //Set all descriptors to changed=false
        for (DescriptorType desc : qsarModel.getDescriptorlist().getDescriptors()){
            QsarHelper.setChangedInPreference(desc, getProject(), false);
        }

        //Store prefs
        net.bioclipse.qsar.ui.Activator.getDefault().savePluginPreferences();

    }

    
    private void storeDescrResultsInSeparateFile(QsarType valuesModel,
                                                 Map<IMolecule, List<IDescriptorResult>> resultMap,
                                                 Map<StructureType, IMolecule> structureMap ) {

        
        //If we have no list to hold descriptorresults, add it
        if (valuesModel.getDescriptorresultlist()==null){
            DescriptorresultlistsType descreslist=QsarFactory.eINSTANCE.createDescriptorresultlistsType();
            valuesModel.setDescriptorresultlist( descreslist );
        }

        //Loop over all calculated molecules
        for (IMolecule mol : resultMap.keySet()){

            StructureType structure=findStructure(mol, structureMap);
            if (structure!=null) {

                //Loop over all descriptors
                for (IDescriptorResult descres : resultMap.get( mol )){
                    if (descres==null){
                        logger.error("DescriptorResult is null! " +
                        		"Cannot store results for it.");
                        break;
                    }

//                    logger.debug( " ## Attempting to store struct: " 
//                                  + structure.getId() + " - AND - " 
//                                  + descres.getDescriptor().getOntologyid());

                    //If this descriptorresult already exists in qsarmodel, use it
                    DescriptorresultType dres=getDescriptorResultFromQsarModel(valuesModel, descres, structure);
                    if (dres==null){
                        //Else, create a new
                        dres=QsarFactory.eINSTANCE.createDescriptorresultType();
                        dres.setDescriptorid( descres.getDescriptor().getId() );
                        dres.setStructureid( structure.getId());
                        valuesModel.getDescriptorresultlist().getDescriptorresult().add( dres );
                    }

                    //Remove all values, we have new!
                    dres.getDescriptorvalue().clear();

                    if (descres.getErrorMessage()!=null && 
                            descres.getErrorMessage().length()>0){

                        logger.error("Descriptor calculation has failed for: " 
                                     + structure + "\n   Failing descriptor: " 
                                     + descres.getDescriptor() 
                                     + "\n  Error message: " 
                                     + descres.getErrorMessage());
                        dres.setErrorString( descres.getErrorMessage() );
                    }

                    //Else no error, add result
                    else{

                        //Add values to the found/created descriptorresult
                        for (int i=0; i< descres.getLabels().length;i++){
                            DescriptorvalueType dval=QsarFactory.eINSTANCE.createDescriptorvalueType();
                            dval.setIndex( i );
                            dval.setLabel( descres.getLabels()[i] );
                            dval.setValue( "" + descres.getValues()[i] );
                            dres.getDescriptorvalue().add( dval );
//                            logger.debug( "   #### added value: " + dval  );
                        }
                    }
                }

            }else{
                logger.error("Desired mol not found in allMap. Should not happen." );
            }

        }
        
        
        
   }

    
    /**
     * Store calculated results in the QsarModel.
     * @param qsarModel
     * @param resultMap
     * @param structureMap
     */
    private void storeDescrResultsInQsarModel( QsarType qsarModel,
                                               Map<IMolecule, List<IDescriptorResult>> resultMap,
                                               Map<StructureType, IMolecule> structureMap ) {

        //If we have no list to hold descriptorresults, add it
        if (qsarModel.getDescriptorresultlist()==null){
            DescriptorresultlistsType descreslist=QsarFactory.eINSTANCE.createDescriptorresultlistsType();
            qsarModel.setDescriptorresultlist( descreslist );
        }

        //Loop over all calculated molecules
        for (IMolecule mol : resultMap.keySet()){

            StructureType structure=findStructure(mol, structureMap);
            if (structure!=null) {

                //Loop over all descriptors
                for (IDescriptorResult descres : resultMap.get( mol )){

//                    logger.debug( " ## Attempting to store struct: " 
//                                  + structure.getId() + " - AND - " 
//                                  + descres.getDescriptor().getOntologyid());

                    //If this descriptorresult already exists in qsarmodel, use it
                    DescriptorresultType dres=getDescriptorResultFromQsarModel(qsarModel, descres, structure);
                    if (dres==null){
                        //Else, create a new
                        dres=QsarFactory.eINSTANCE.createDescriptorresultType();
                        dres.setDescriptorid( descres.getDescriptor().getId() );
                        dres.setStructureid( structure.getId());
                        qsarModel.getDescriptorresultlist().getDescriptorresult().add( dres );
                    }

                    //Remove all values, we have new!
                    dres.getDescriptorvalue().clear();

                    if (descres.getErrorMessage()!=null && 
                            descres.getErrorMessage().length()>0){

                        logger.error("Descriptor calculation has failed for: " 
                                     + structure + "\n   Failing descriptor: " 
                                     + descres.getDescriptor() 
                                     + "\n  Error message: " 
                                     + descres.getErrorMessage());
                        dres.setErrorString( descres.getErrorMessage() );
                    }

                    //Else no error, add result
                    else{

                        //Add values to the found/created descriptorresult
                        for (int i=0; i< descres.getLabels().length;i++){
                            DescriptorvalueType dval=QsarFactory.eINSTANCE.createDescriptorvalueType();
                            dval.setIndex( i );
                            dval.setLabel( descres.getLabels()[i] );
                            dval.setValue( "" + descres.getValues()[i] );
                            dres.getDescriptorvalue().add( dval );
//                            logger.debug( "   #### added value: " + dval  );
                        }
                    }
                }

            }else{
                logger.error("Desired mol not found in allMap. Should not happen." );
            }

        }

    }

    /**
     * Return an existing descriptorresult based on id of descriptor and structure, 
     * or null if not found
     * @param qsarModel
     * @param descres
     * @param structure
     * @return
     */
    private DescriptorresultType getDescriptorResultFromQsarModel(
                                                                  QsarType qsarModel, IDescriptorResult descres,
                                                                  StructureType structure ) {

        for (DescriptorresultType lres : qsarModel.getDescriptorresultlist().getDescriptorresult()){
            if (lres.getDescriptorid().equals( descres.getDescriptor().getId() ) && 
                    lres.getStructureid().equals( structure.getId() ))
                return lres;
        }

        //Not found
        return null;
    }

    /**
     * Find the structure for the Imolecule in the Map or null if not found
     * @param mol
     * @param structureMap
     * @return
     */
    private StructureType findStructure( IMolecule mol,
                                         Map<StructureType, IMolecule> structureMap ) {

        for (StructureType structure : structureMap.keySet()){
            if (mol.equals( structureMap.get( structure ) ))
                return structure;
        }
        return null;
    }

    /**
     * Return a string for debugging a resultmap
     * @param resultMap
     * @return
     */
    private String debugResultMap(
                                  Map<IMolecule, List<IDescriptorResult>> resultMap ) {

        String ret="";
        for (IMolecule mol : resultMap.keySet()){
            ret=ret+"Mol: " + mol+"\n";
            for (IDescriptorResult res : resultMap.get( mol )){
                ret=ret+ res + "\n";
            }
        }
        return ret;
    }

    /**
     * Return a String of contents in a moldescmap
     * @param molDescMap
     * @return
     */
    private String debugMolDescMap(
                                   Map<IMolecule, List<DescriptorType>> molDescMap ) {
        String ret="";
        for (IMolecule mol : molDescMap.keySet()){
            ret=ret+"Mol: " + mol+"\n";
            for (DescriptorType desc : molDescMap.get( mol )){
                ret=ret+"    Desc: id=" + desc.getId() + " ; prov="
                + desc.getProvider() + " ; params=" + desc.getParameter() + " \n";
            }
        }
        return ret;
    }

    /**
     * Colelct and return a string of all descriptors in a list
     * @param allDescriptors
     * @return
     */
    private String debugDescList( List<DescriptorType> allDescriptors ) {
        String ret="";
        for (DescriptorType desc : allDescriptors){
            ret=ret+"DescriptorID=" + desc.getId() + " ; provider=" + desc.getProvider()+ " ; params=" + desc.getParameter();

            if (QsarHelper.isDirtyInPreference( desc, getProject() ))
                ret=ret+" - CHANGED\n";
            else
                ret=ret+" - unchanged\n";
        }
        return ret;
    }

    /**
     * Collect a String for debug output from a list of mols
     * @param structureMap
     * @return
     */
    private String debugStructureMap(Map<StructureType, IMolecule> structureMap) {
        String ret="";
        for (StructureType structure : structureMap.keySet()){
            try {
                ret=ret+structure.getId() + " - " + structureMap.get( structure )
                .toSMILES();
                if (QsarHelper.isDirtyInPreference( structure, getProject() ))
                    ret=ret+" - CHANGED\n";
                else
                    ret=ret+" - unchanged\n";
            } catch ( BioclipseException e ) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private Map<IMolecule, List<DescriptorType>> getComboForCalculation(
                                                                        Map<StructureType, IMolecule> structureMap,
                                                                        List<DescriptorType> allDescriptors, QsarType qsarModel ) {

        Map<IMolecule, List<DescriptorType>> molDescMap=new HashMap<IMolecule, List<DescriptorType>>();

        //A Changed structure needs computing for all descriptors
        for (StructureType structure : structureMap.keySet()){
            IMolecule mol=structureMap.get( structure);
            if (QsarHelper.isDirtyInPreference(structure, getProject()))
                molDescMap.put( mol, allDescriptors );
        }

        //A descriptor need computing for all molecules
        for (DescriptorType desc : allDescriptors){
            for (StructureType structure : structureMap.keySet()){
                IMolecule mol = structureMap.get( structure );

                //                logger.debug("Combo: " + structure.getId() + "--" + desc.getId() + " is dirty: " + QsarHelper.isDirtyInPreference(desc, getProject()));
                //                logger.debug("Combo: " + structure.getId() + "--" + desc.getId() + " has response empty: " + isResponseEmpty(desc, structure, qsarModel));

                //If descriptor is dirty
                if (QsarHelper.isDirtyInPreference(desc, getProject())
                        /*|| isResponseEmpty(desc, structure, qsarModel)*/       
                ){
                    List<DescriptorType>  localDescList;
                    if (molDescMap.containsKey( mol )){
                        localDescList = molDescMap.get( mol );
                    }else{
                        localDescList = new ArrayList<DescriptorType>();
                        molDescMap.put( mol, localDescList );
                    }

                    if (!(localDescList.contains( desc ))){
                        localDescList.add( desc );
                    }
                }
            }

        }

        return molDescMap;
    }



    private boolean isResponseEmpty( DescriptorType desc, StructureType structure, QsarType qsarModel) {


        for (DescriptorresultType res: qsarModel.getDescriptorresultlist().getDescriptorresult()){

            if (res.getDescriptorid().equals( desc.getId() ) 
                    &&
                    res.getStructureid().equals( structure.getId() )){
                //Make sure we have values
                if (res.getDescriptorvalue()!=null && res.getDescriptorvalue().size()>0)
                    //No, it's not empty, we've found at least one result
                    return false;
            }
        }

        //Yes, it's empty
        return true;
    }

    /**
     * Extract all molecules and if they have changed to a Map
     * of Mol -> hasChanged (true if mol has changed)
     * @param qsarType
     * @return
     */
    private Map<StructureType, IMolecule> extractMoleculesFromQSARType(
                                                                       QsarType qsarType ) {

        Map<StructureType, IMolecule> retmap=new HashMap<StructureType, IMolecule>();

        ICDKManager cdk=Activator.getDefault().getJavaCDKManager();

        for (ResourceType resource : qsarType.getStructurelist().getResources()){

            //Hold the mols in this resource
            List<ICDKMolecule> mols =null;

            //            if (resource.getURL() !=null && resource.getURL()().length()>0){
            //                //We have an URL
            //                //TODO: URL is not implemented yet
            //            }
            //            else
            if (resource.getFile()!=null && resource.getFile().length()>0){

                //We have a file
                //Load file with CDK
                try {
                    mols = cdk.loadMolecules( resource.getFile() );
                } catch ( Exception e ) {
                    logger.error( "Could not load file: " + resource.getFile() );
                }
            }

            if (mols!=null && mols.size()>0){

                for (StructureType structure : resource.getStructure()){
                    //                    if (resource.getType().equals( TypeType.XML )){
                    //                        String id=structure.getResourceid();
                    //                        //TODO: XML-files is not implemented yet
                    //                    }
                    //                    if (resource.getType().equals( TypeType.TEXT )){
                    //                        int ix=structure.getResourceindex();
                    //                    }

                    //for now, only use index-based
                    int ix=structure.getResourceindex();
                    retmap.put( structure, mols.get( ix ) );

                }


            }

        }

        return retmap;
    }

    private boolean isQsarTypeEmpty(QsarType qsarType) {

        if (qsarType.getStructurelist()==null){
            logger.debug("No structureList in qsartype.");
            return true;
        }

        else if (qsarType.getStructurelist().getResources()==null || 
                qsarType.getStructurelist().getResources().size()<=0){
            logger.debug("No resources in structurelist.");
            return true;
        }

        else if (qsarType.getDescriptorlist()==null){
            logger.debug("No descriptorlist in qsar model.");
            return true;
        }

        else if (qsarType.getDescriptorlist().getDescriptors()==null || 
                qsarType.getDescriptorlist().getDescriptors().size()<=0){
            logger.debug("No descriptors in descriptorlist.");
            return true;
        }

        else if (qsarType.getDescriptorproviders()==null || 
                qsarType.getDescriptorproviders().size()<=0){
            logger.debug("No descriptorptoviders in qsar model.");
            return true;
        }

        //OK, we have enough for building project
        return false;
    }

    /**
     * Read in project file and parse it with EMF
     * @param file 
     * @return QsarType model object
     */
    private QsarType readModelFromProjectFile(IFile file) {

        // Register the package -- only needed for stand-alone!
        @SuppressWarnings("unused")
        QsarPackage qsarPackage=QsarPackage.eINSTANCE;

        // Register the package -- only needed for stand-alone!
        @SuppressWarnings("unused")
        BibtexmlPackage bibPackage=BibtexmlPackage.eINSTANCE;

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the appropriate resource factory to handle all file extensions.
        //
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
        (Resource.Factory.Registry.DEFAULT_EXTENSION, 
         new QsarResourceFactoryImpl());

        // Register the package to ensure it is available during loading.
        //
        resourceSet.getPackageRegistry().put
        (QsarPackage.eNS_URI, 
         QsarPackage.eINSTANCE);

        // Register the package to ensure it is available during loading.
        //
        resourceSet.getPackageRegistry().put
        (BibtexmlPackage.eNS_URI, 
         BibtexmlPackage.eINSTANCE);

        EcoreUtil.resolveAll( resourceSet );


        logger.debug("Model file to read: " 
                     + file.getRawLocation().toOSString());

        // Get the URI of the model file.
        //        URI fileURI = URI.createFileURI(qsarfile.getRawLocation().toOSString());

        URI uri=URI.createPlatformResourceURI(file.getFullPath().toString(), true);

        // Demand load the resource for this file.
        try{
            Resource resource = resourceSet.getResource(uri, true);
            DocumentRoot root=(DocumentRoot) resource.getContents().get(0);
            QsarType qsarType=root.getQsar();

            return qsarType;
        }catch (Exception e){
            logger.error("Could not read file:" + file.getName() 
                         + " in project: " + getProject() 
                         + " because error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Save the model to file
     * @param qsarModel 
     * @return QsarType model object
     */
    private void saveModelToFile(QsarType qsarModel, IFile file) {

        //We need a documentroot for serialization
        DocumentRoot root=QsarFactory.eINSTANCE.createDocumentRoot();
        root.setQsar( qsarModel );

        ResourceSet resourceSet=new ResourceSetImpl();
        //        URI fileURI;
        try {
            //For now, only one QSAR file per project
            //            IFile qsarfile = getProject().getFile("qsar.xml");
            //            fileURI = URI.createFileURI(qsarfile.getRawLocation().toOSString());
            URI uri=URI.createPlatformResourceURI(file.getFullPath().toString(), true);

            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml", new QsarResourceFactoryImpl());

            Resource resource=resourceSet.createResource(uri);
            resource.getContents().add(root);

            //Serialize with extra options
            Map opts=new HashMap();
            opts.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            opts.put(XMLResource.OPTION_ENCODING, "UTF-8");

            //Save to file
            resource.save(opts);

            file.refreshLocal( 0, new NullProgressMonitor());

            //Serialize to byte[] and print to sysout
            //            ByteArrayOutputStream os=new ByteArrayOutputStream();
            //            resource.save(os, opts);
            //            System.out.println(new String(os.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        } catch ( CoreException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /*
    private void scanQsarFile_old(IProgressMonitor monitor) throws OperationCanceledException {

        //We need to read in qsar.xml and parse it with EMF
        //=================================================

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                                                                                Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        // Register the package -- only needed for stand-alone!
        @SuppressWarnings("unused")
        QsarPackage qsarPackage=QsarPackage.eINSTANCE;

        IFile qsarfile = getProject().getFile("qsar.xml");
        logger.debug("QSAR file: " + qsarfile.getRawLocation().toOSString());

        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(qsarfile.getRawLocation().toOSString());

        // Demand load the resource for this file.
        Resource resource = resourceSet.getResource(fileURI, true);

        DocumentRoot root=(DocumentRoot) resource.getContents().get(0);

        QsarType qsarType=root.getQsar();

        //Extract molecules from qsar model
        //=================================
        //Verify and load mols
        if (qsarType.getStructurelist()==null){
            logger.debug("No structureList.");
            return;
        }

        final StructurelistType mollist = qsarType.getStructurelist();

        if (mollist.getResources()==null || mollist.getResources().size()<=0){
            logger.debug("No molecules in MoleculesList.");
            return;
        }

        ICDKManager cdk=Activator.getDefault().getCDKManager();

        //Get list of IMolecules from MoleculeResources
        Map<ICDKMolecule, String> molMap=new HashMap<ICDKMolecule, String>();

        //Get list of responses from qsar.xml matching resources
        //	   List<String> responseStrings=new ArrayList<String>();

        //For all Resources (e.g. files) , add to list with responses as properties in AC
        for (ResourceType structureResource : mollist.getResource()){
            logger.debug(" ++ Resource: " + structureResource.getId() + " [" + structureResource.getFile()+"]");
            try {

                //Refactored out. We now only read info stored in file.
                List<ICDKMolecule> newMols=cdk.loadMolecules(structureResource.getFile());
                logger.debug("    - contained " + newMols.size() + " imols after readin");

                logger.debug("    - We have " + structureResource.getStructure().size() 
                             + " structures referenced in XML");

                //We need to add responses to the molecules from the qsarType
                //Add response values as property
                for (ResponseType response : qsarType.getResponselist().getResponse()){

                    //Make sure the response structureresource is same as the current resource
                    if (response.getStructureResource().equals(structureResource.getId())){

                        for (int i=0;i<newMols.size();i++){
                            if (response.getResourceIndex()==i){

                                //If we have array value, us it
                                if (response.getArrayValues()!=null && response.getArrayValues().length()>0){
                                    newMols.get(i).getAtomContainer().setProperty(
                                                                                  QSARConstants.QSAR_RESPONSE_PROPERTY, 
                                                                                  response.getArrayValues());
                                    molMap.put(newMols.get(i), response.getArrayValues());
                                    //								responseStrings.add(response.getArrayValues());
                                    logger.debug("Added value: " + 
                                                 response.getArrayValues() + 
                                                 "to mol: " + structureResource + " ix " + i);
                                }else{

                                    //If we have no array value, us float value
                                    newMols.get(i).getAtomContainer().setProperty(
                                                                                  QSARConstants.QSAR_RESPONSE_PROPERTY, 
                                                                                  response.getValue());
                                    logger.debug("Added value: " + 
                                                 response.getValue() + 
                                                 " to mol: " + structureResource.getId() + " ix " + i);
                                    molMap.put(newMols.get(i), ""+response.getValue());
                                    //								responseStrings.add(""+response.getValue());
                                }
                            }
                            //						if (!(molMap.containsKey(newMols.get(i)))){
                            //							molMap.put(newMols.get(i), ""+Float.NaN);
                            //						}
                        }
                    }

                }

                //We've gone through all existing responses. Add missing responses now!
                for (ICDKMolecule nmol : newMols){
                    boolean hasResult=false;
                    for (Iterator<ICDKMolecule> it = molMap.keySet().iterator(); it.hasNext();){
                        ICDKMolecule mapMol=it.next();
                        //Cannot compare ICDKMolecules, some CGLIB issue
                        if (mapMol.getAtomContainer().equals( nmol.getAtomContainer() )){
                            hasResult=true;
                        }
                    }
                    if (!hasResult){
                        nmol.getAtomContainer().setProperty(
                                                            QSARConstants.QSAR_RESPONSE_PROPERTY, 
                                                            Float.NaN);
                        molMap.put(nmol, ""+Float.NaN);
                        logger.debug("Molecule: " + nmol.getName() +" had no response, " +
                        "so added NaN");
                    }


                    //				if (!(molMap.containsKey(nmol))){
                    //				}
                }

                //			mols.addAll(newMols);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BioclipseException e) {
                e.printStackTrace();
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        logger.debug("==== Total loaded mols: " + molMap.size());
        for (ICDKMolecule mol : molMap.keySet()){
            System.out.println("  ++++ " + mol.getName());
        }

        //Get lists from molsMap
        List<ICDKMolecule> sortedMols=new ArrayList<ICDKMolecule>();
        sortedMols.addAll((Collection<? extends ICDKMolecule>) molMap.keySet());

        //Sort the mol based on name
        //	   Collections.sort(sortedMols,new Comparator<ICDKMolecule>(){
        //		   public int compare(ICDKMolecule o1, ICDKMolecule o2) {
        //			   return o1.getName().compareTo(o2.getName());
        //		   }
        //	   });

        //Sort the mols as they appear in original list, as this is intuitive
        //(we have used Set that destroys the sorting)
        Collections.sort(sortedMols,new Comparator<ICDKMolecule>(){
            public int compare(ICDKMolecule o1, ICDKMolecule o2) {
                //Sort depending on what comes first in mollist which is input list
                for (MoleculeResourceType mr : mollist.getMoleculeResource()){
                    IPath path=new Path(mr.getFile());
                    IFile f=ResourcesPlugin.getWorkspace().getRoot().getFile( path );
                    if (o1.getResource().getName().equalsIgnoreCase( f.getName() )){
                        if (o2.getResource().getName().equalsIgnoreCase( mr.getFile() )){
                            //Same file, let filename decide
                            return o1.getName().compareTo(o2.getName());
                        }
                        //o1 comes before o2 in list of files 
                        return -1;
                    }
                    else if (o2.getResource().getName().equalsIgnoreCase( f.getName() )){
                        return 1;
                    }
                }
                return o1.getName().compareTo(o2.getName());//Should not happen, fall back on name comparison
            }
        });


FIXME HERE 
        //Set up structure for dataset
        //===============================
        List<String> columnLabels=new ArrayList<String>();
        List<String> rowLabels=new ArrayList<String>();
        double[][] dataset;

        //Get Descriptors from qsar model
        //===============================
        if (qsarType.getDescriptorlist()==null){
            logger.debug("No descriptorList.");
            return;
        }

        DescriptorlistType desclist = qsarType.getDescriptorlist();

        if (desclist.getDescriptor()==null || desclist.getDescriptor().size()<=0){
            logger.debug("No descriptors in descriptorList.");
            return;
        }

        List<String> descriptorIDs=new ArrayList<String>();
        for (DescriptorType desc : desclist.getDescriptor()){
            descriptorIDs.add(desc.getId());
            DescriptorimplType impl = desc.getDescriptorimpl();

            logger.debug(" ** desc: " + desc.getId() + " [" + impl.getId() + "]");
        }



        //Calculate descriptor for mols
        //=============================
        //We have desclist.getDescriptor().size descriptors
        //We have sortedMols.size molecules

        int jobSize=desclist.getDescriptor().size() * sortedMols.size()+1;
        monitor.beginTask("Building QSAR project", jobSize);

        if (checkCancel(monitor))
            return;

        IQsarManager qsar = net.bioclipse.qsar.init.Activator.getDefault().getQsarManager();
        //	   Map<? extends IMolecule, List<IDescriptorResult>> resultMap = qsar.calculateNoParams(sortedMols, descriptorIDs);
        Map<? extends IMolecule, List<IDescriptorResult>> resultMap = qsar.calculate(sortedMols, desclist.getDescriptors(), monitor);


        if (checkCancel(monitor))
            return;
        monitor.worked(1);
        monitor.subTask("Processing descriptor results");


        //Calculate dimension for dataset
        //================================
        int numMolecules=resultMap.keySet().size();

        //Hope that first is representative TODO: create better approach here, first could contain errors!
        List<IDescriptorResult> descrResWIthNoErrors=null;
        IMolecule firstMolWithoutDescriptorErrors=null;
        for (IMolecule mol : resultMap.keySet()){
            boolean hasErrors=false;
            List<IDescriptorResult> rlist=resultMap.get(mol);
            for (IDescriptorResult dr : rlist){
                if (dr.getErrorMessage()!=null){
                    hasErrors=true;
                }
            }
            if (hasErrors==false){
                //We have found one without errors, use this for getting labels!
                descrResWIthNoErrors=rlist;
                firstMolWithoutDescriptorErrors=mol;
            }
        }
        //	   List<IDescriptorResult> firstDescrResWIthNoErrors=(List<IDescriptorResult>) resultMap.values().toArray()[0];

        //Total amount of values =value columns in matrix
        int numTotalDescrValues=0;

        //Total number of descriptors
        int numDescriptors=descrResWIthNoErrors.size();

        //One int per descriptor to show how many values are there (for array results of a descriptor)
        int[] descColumns=new int[numDescriptors];

        //Calculate total number of descriptor values and number of values per descriptor
        int descIndex=0;
        for (IDescriptorResult res : descrResWIthNoErrors){
            numTotalDescrValues=numTotalDescrValues+res.getLabels().length;
            descColumns[descIndex]=res.getLabels().length;
            descIndex++;
        }

        logger.debug("************* We have " + numMolecules + " mols, " + numDescriptors 
                     + " descriptors,  and " + numTotalDescrValues +" descriptor values");

        //Make room in dataset
        dataset=new double[numMolecules][numTotalDescrValues];

        //Allocate list of error messages to output at the end
        List<String> listOfErrorMessages=new ArrayList<String>();


        //Store descriptor results
        //================================
        int cnt=0;
        for (IMolecule mol : sortedMols){
            double[] datasetRow=dataset[cnt];
            cnt++;
            org.openscience.cdk.interfaces.IMolecule cdkcasted=null;
            ICDKMolecule cdkmol=null;
            try {
                cdkmol=cdk.create(mol);
                if (cdkmol.getName()==null){
                    cdkmol.setName("molecule"+cnt);
                }
                logger.debug("Molecule: " + cdkmol.getName());
                rowLabels.add(cdkmol.getName());
                cdkcasted=(org.openscience.cdk.interfaces.IMolecule) cdkmol.getAtomContainer();
            } catch (BioclipseException e) {
                e.printStackTrace();
            }

            int datasetrowindex=0;
            int descriptorIndex=0;
            List<IDescriptorResult> molres=resultMap.get(mol);
            for (IDescriptorResult dres : molres){
                logger.debug("  Descriptor: " + dres.getDescriptorId());

                //Hold cdk results here for persistence
                DoubleArrayResult result=new DoubleArrayResult(descColumns[descriptorIndex]);
                for (int i=0; i<descColumns[descriptorIndex];i++){

                    if (dres.getErrorMessage()!=null){
                        logger.error("    Result " + i + ": ERROR. Message: " + 
                                     dres.getErrorMessage() );

                        listOfErrorMessages.add("Molecule: " + cdkmol.getName() + " : " + dres.getErrorMessage());

                        //NaN has to symbolize error for now as well
                        result.add(Double.NaN);
                        datasetRow[datasetrowindex]=Double.NaN;

                    }
                    else{
                        logger.debug("    Result " + i + ": " + dres.getLabels()[i] + 
                                     " - " + dres.getValues()[i]);

                        result.add(dres.getValues()[i]);
                        datasetRow[datasetrowindex]=dres.getValues()[i];

                        //Assume same for all molecules as we calculate the same descriptors!
                        //TODO: what if first molecule has errors? UPDATE!
                        if (mol==firstMolWithoutDescriptorErrors){
                            columnLabels.add(dres.getLabels()[i]);
                        }

                    }


                    //Increase index in datasetrow
                    datasetrowindex++;

                }

                DescriptorSpecification spec=new DescriptorSpecification(dres.getDescriptorId(),"impl", "impl id","cdk");

                //Create the DescriptorValue that's used for serialization
                DescriptorValue value=new DescriptorValue(spec,null,null,result,dres.getLabels());

                cdkcasted.setProperty(spec, value);

                descriptorIndex++;

            }


            monitor.worked(1);
            monitor.subTask("Saving molecules with values");


            //Serialize to CML files
            //======================
            try {

                //First serialize to string
                ByteArrayOutputStream bos=new ByteArrayOutputStream();

                //				StringWriter writer = new StringWriter();
                CMLWriter cmlWriter = new CMLWriter(bos);
                cmlWriter.registerCustomizer(new QSARCustomizer());
                cmlWriter.write(cdkcasted);

                ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());

                //Path to results
                IFolder processedFolder= getProject().getFolder("processedMols");
                if (!(processedFolder.exists())){
                    processedFolder.create(true, true, new NullProgressMonitor());
                    processedFolder.setDerived(true);
                }
//                IFile molfile=processedFolder.getFile(cdkmol.getName()+"_"+cnt+".cml");
                IFile molfile=processedFolder.getFile(cdkmol.getName()+".cml");
                logger.debug("Attempting to write file with descr res: " + molfile);

                if (!(molfile.exists())){
                    //Create file and add contents
                    molfile.create(bis, true, new NullProgressMonitor());
                    molfile.setDerived(true);
                }
                else{
                    //Overwrite contents
                    molfile.setContents(bis, true, true, new NullProgressMonitor());
                    molfile.setDerived(true);
                }

            } catch (CDKException e) {
                e.printStackTrace();
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


        monitor.worked(1);
        monitor.subTask("Creating dataset");

        //Serialize dataset to file
        //=========================
        logger.debug("============================================");
        logger.debug("============================================");
        String SEPARATOR=",";
        String NEWLINE="\n";
        DecimalFormat formatter = new DecimalFormat("0.00");

        StringBuffer buffer=new StringBuffer();	   

        //We start with response values, hence RESPONSE label is first
        buffer.append(SEPARATOR + "RESPONSE");

        //Serialize one row at a time
        //Start with colheaders
        for (String colheader : columnLabels){
            buffer.append(SEPARATOR + colheader);
        }
        buffer.append(NEWLINE);
        for (int i=0;i< dataset.length; i++){

            //Add label first in line
            buffer.append(rowLabels.get(i));

            //Then add response string from map containing it
            if (molMap.get(sortedMols.get(i))!=null){
                buffer.append(SEPARATOR + molMap.get(sortedMols.get(i)));
            }else{
                //Backup: NaN
                buffer.append(SEPARATOR + Float.NaN);
            }
            //		   if (responseStrings.size()>i){
            //			   buffer.append(SEPARATOR + responseStrings.get(i));
            //		   }else{
            //			   buffer.append(SEPARATOR + Float.NaN);
            //		   }


            //Add descriptor values
            for (int j=0;j< dataset[i].length; j++){
                String toAppend="";
                //Serialize NaN as String
                if (Double.isNaN(dataset[i][j])) toAppend="NaN";
                else toAppend=formatter.format(dataset[i][j]);
                buffer.append(SEPARATOR + toAppend);
            }
            buffer.append(NEWLINE);
        }

        logger.debug(buffer.toString());

        logger.debug("============================================");
        logger.debug("============================================");

        //Write this String to file
        InputStream is=new ByteArrayInputStream(buffer.toString().getBytes());
        BufferedInputStream source=new BufferedInputStream(is);

        try {
            IFile datasetFile=getProject().getFile("dataset.csv");
            if (datasetFile.exists()){
                datasetFile.setContents(source, true,true, new NullProgressMonitor());
            }else{
                datasetFile.create(source, true, new NullProgressMonitor());
            }
            datasetFile.setDerived(true);
            logger.debug("Wrote file dataset.csv");
        } catch (CoreException e) {
            logger.error("Writing of file dataset.csv FAILED");
            e.printStackTrace();
        }
        logger.debug("============================================");
        if (listOfErrorMessages.size()>0){
            logger.error("Finished with the following errors: ");
            for (String er: listOfErrorMessages){
                logger.error(er);
            }

        }else{
            logger.debug("No errors to report");
        }
        logger.debug("============================================");

        monitor.done();

    }
     */
    /**
     * Check to see if the build operation in progress was canceled by
     * the user or should be canceled because another builder needs
     * access to the workspace.
     * 
     * @param monitor the progress monitor
     * @return <code>true</code> if the build operation should stop
     */
    private boolean checkCancel(IProgressMonitor monitor) {
        if (monitor.isCanceled()) {
            // Discard build state if necessary.
            throw new OperationCanceledException();
        }

        if (isInterrupted()) {
            // Discard build state if necessary.
            return true;
        }
        return false;
    }






    ////////////////////////////////////////////////////////////////////////////
    //
    // Marker methods
    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Delete all audit markers in the specified project
     * 
     * @param project the project to be modified
     * @return <code>true</code> if successful, else
     *         <code>false</code>
     */
    public static boolean deleteAuditMarkers(IProject project) {
        try {
            project.deleteMarkers(
                                  MARKER_ID, false, IResource.DEPTH_INFINITE);
            return true;
        }
        catch (CoreException e) {
            logger.error(e);
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Utility methods
    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Add this builder to the specified project if possible.
     * Do nothing if the builder has already been added.
     *  
     * @param project the project (not <code>null</code>)
     */
    @SuppressWarnings("unchecked")
    public static void addBuilderToProject(IProject project) {

        // Cannot modify closed projects.
        if (!project.isOpen())
            return;

        // Get the description.
        IProjectDescription description;
        try {
            description = project.getDescription();
        }
        catch (CoreException e) {
            logger.error(e);
            return;
        }

        // Look for builder already associated.
        ICommand[] cmds = description.getBuildSpec();
        for (int j = 0; j < cmds.length; j++)
            if (cmds[j].getBuilderName().equals(BUILDER_ID))
                return;

        // Associate builder with project.
        ICommand newCmd = description.newCommand();
        newCmd.setBuilderName(BUILDER_ID);
        List newCmds = new ArrayList();
        newCmds.addAll(Arrays.asList(cmds));
        newCmds.add(newCmd);
        description.setBuildSpec(
                                 (ICommand[]) newCmds.toArray(
                                                              new ICommand[newCmds.size()]));
        try {
            project.setDescription(description, null);
        }
        catch (CoreException e) {
            logger.error(e);
        }
    }

    /**
     * Determine if the specified project has the receiver associated
     * with it.
     * 
     * @param project the project to test
     * @return <code>true</code> if the specified project is open and
     *         accessible and has the builder associated with it, else
     *         <code>false</code>
     */
    public static boolean hasBuilder(IProject project) {

        // Cannot modify closed projects.
        if (!project.isOpen())
            return false;

        // Get the description.
        IProjectDescription description;
        try {
            description = project.getDescription();
        }
        catch (CoreException e) {
            logger.error(e);
            return false;
        }

        // Look for builder already associated.
        ICommand[] cmds = description.getBuildSpec();
        for (int j = 0; j < cmds.length; j++)
            if (cmds[j].getBuilderName().equals(BUILDER_ID))
                return true;
        return false;
    }

    /**
     * Remove this builder from the specified project if possible.
     * Do nothing if the builder has already been removed.
     *  
     * @param project the project (not <code>null</code>)
     */
    @SuppressWarnings("unchecked")
    public static void removeBuilderFromProject(IProject project) {

        // Cannot modify closed projects.
        if (!project.isOpen())
            return;

        // Get the description.
        IProjectDescription description;
        try {
            description = project.getDescription();
        }
        catch (CoreException e) {
            logger.error(e);
            return;
        }

        // Look for builder.
        int index = -1;
        ICommand[] cmds = description.getBuildSpec();
        for (int j = 0; j < cmds.length; j++) {
            if (cmds[j].getBuilderName().equals(BUILDER_ID)) {
                index = j;
                break;
            }
        }
        if (index == -1)
            return;

        // Remove builder from project.
        List newCmds = new ArrayList();
        newCmds.addAll(Arrays.asList(cmds));
        newCmds.remove(index);
        description.setBuildSpec(
                                 (ICommand[]) newCmds.toArray(
                                                              new ICommand[newCmds.size()]));
        try {
            project.setDescription(description, null);
        }
        catch (CoreException e) {
            logger.error(e);
        }
    }

    public class Stopwatch {
        private long start;
        private long stop;

        public void start() {
            start = System.currentTimeMillis(); // start timing
        }

        public void stop() {
            stop = System.currentTimeMillis(); // stop timing
        }

        public long elapsedTimeMillis() {
            return stop - start;
        }

        //return nice string
        public String toString() {

            int seconds = (int) ((elapsedTimeMillis() / 1000) % 60);
            int minutes = (int) ((elapsedTimeMillis() / 1000) / 60);
            if (minutes>0)
                return "" + minutes + " mins and " + seconds + " s";
            else
                return "" + seconds + " s";
        }
    }



}
