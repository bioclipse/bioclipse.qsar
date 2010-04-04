/*******************************************************************************
 *Copyright (c) 2010 The Bioclipse Team and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    Ola Spjuth - initial API and implementation
 *******************************************************************************/

package net.bioclipse.qsar.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.chembl.business.IChEMBLManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.util.LogUtils;

import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.ui.builder.QSARBuilder;
import net.bioclipse.qsar.ui.builder.QSARFileUtils;
import net.bioclipse.qsar.ui.builder.QSARNature;
import net.bioclipse.qsar.ui.util.QsarXMLUtils;
import net.bioclipse.qsar.util.QsarAdapterFactory;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 *A NewWizard to create a new QSARProject
 *
 * @author ola
 */
public class NewQSARProjectFromChEMBLWizard extends Wizard implements INewWizard {

    private static final Logger logger = Logger.getLogger(NewQSARProjectFromChEMBLWizard.class);

    private static final String SDF_RESPONSE_PROPERTY = "qsar.response";

    private WizardNewProjectCreationPage fFirstPage;

    private IWorkbench workbench;
    private IStructuredSelection selection;
    
    
    public String getResponseType() {
        return responseType;
    }
    public void setResponseType( String responseType ) {
        this.responseType = responseType;
    }

    @Override
    public boolean needsProgressMonitor() {
        return true;
    }
    
    public Integer getTargetID() {
    
        return targetID;
    }

    
    public void setTargetID( Integer targetID ) {
    
        this.targetID = targetID;
    }

    String responseType;
    Integer targetID;

    private WizardChEMBLPage fSecondPage;

    public NewQSARProjectFromChEMBLWizard() {
        super();
//        setDefaultPageImageDescriptor();
        setWindowTitle("New QSAR project from ChEMBL");
        setResponseType( "IC50" );
        setTargetID( null );

    }

    /**
     * Add WizardNewProjectCreationPage from IDE
     */
    public void addPages() {

        fFirstPage = new WizardNewProjectCreationPage("New QSAR project from ChEMBL");
        fFirstPage.setTitle("New QSAR project from ChEMBL");
        fFirstPage.setDescription("Create a new QSAR project from ChEMBL");
//        fFirstPage.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),
//        "/org/ananas/xm/eclipse/resources/newproject58.gif"));

        addPage(fFirstPage);

        fSecondPage = new WizardChEMBLPage("Select ChEMBL target");
        fSecondPage.setTitle("Select ChEMBL target");
        fSecondPage.setDescription("Select ChEMBL target and response");

        addPage(fSecondPage);

    }

    @Override
    public boolean canFinish() {

        if (targetID==null) return false;
        return super.canFinish();
    }

    /**
     * Create project and add QSARNature
     */
    @Override
    public boolean performFinish() {

        try
        {
            WorkspaceModifyOperation op =
                new WorkspaceModifyOperation()
            {

                @Override
                protected void execute(IProgressMonitor monitor)
                throws CoreException, InvocationTargetException,
                InterruptedException {
                    createProject(monitor != null ?
                            monitor : new NullProgressMonitor());

                }
            };
            getContainer().run(false,true,op);
        }
        catch(InvocationTargetException x)
        {
            LogUtils.debugTrace(logger, x);
            return false;
        }
        catch(InterruptedException x)
        {
            return false;
        }
        return true;     }

    /**
     * Init wizard
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle("New QSAR Project");
//        setDefaultPageImageDescriptor(TBC);
        setResponseType( "IC50" );
        setTargetID( null );
    }


    /**
     * Create project and add required natures, builders, folders, and files
     * @param monitor
     * @throws InvocationTargetException 
     */
    protected void createProject(IProgressMonitor monitor) throws InvocationTargetException
    {
        monitor.beginTask("Creating QSAR project from ChEMBL",10);
        monitor.worked(1);
        monitor.subTask( "Getting data from ChEMBL: Target="+getTargetID() 
                         +", Response type=" + getResponseType() );

        //TODO: Continue
        System.out.println("Selected target: " + getTargetID());
        System.out.println("Response type: " + getResponseType());

        //TODO: continue here with data like: 
        //http://pastebin.com/raw.php?i=08ghQYMj

        IChEMBLManager chembl=net.bioclipse.chembl.Activator.getDefault()
        .getJavaChEMBLManager();
        Map<String, Double> qsarDataMap=null;
            try {
                qsarDataMap = chembl.getQSARData( targetID, responseType );
            } catch ( BioclipseException e ) {
                throw new InvocationTargetException( e );
            }
            
            if (qsarDataMap.size()<=0)
                throw new InvocationTargetException( 
                       new BioclipseException( "No data returned from query" ));
            
        ICDKManager cdk = Activator.getDefault().getJavaCDKManager();

        monitor.worked(1);
        monitor.subTask( "Parsing ChEMBL compounds");
        
        //Serialize data to file
        List<IMolecule> mols=new ArrayList<IMolecule>();
        List<String> smilesErrors=new ArrayList<String>();
        for (String smiles : qsarDataMap.keySet()){
            Double response = qsarDataMap.get( smiles );
            ICDKMolecule mol;
            try {
                mol = cdk.fromSMILES( smiles );
                mol.getAtomContainer().setProperty( SDF_RESPONSE_PROPERTY, response );
                mols.add(mol);
            } catch ( BioclipseException e ) {
                smilesErrors.add( smiles );
            }
        }

        if (smilesErrors.size()>0)
            logger.error( "The following smiles could not be parsed " +
                          "with CDK:\n" + smilesErrors.toString() );
        //TODO: report smiles errors to UI too

        try
        {

            monitor.worked(1);
            monitor.subTask( "Creating new QSAR project: " 
                             + fFirstPage.getProjectName());

            //Get WS root
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            monitor.subTask("Creating directories");

            //Create the project
            IProject project = root.getProject(fFirstPage.getProjectName());

            //Add natures and builders
            IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
            if(!Platform.getLocation().equals(fFirstPage.getLocationPath()))
                description.setLocation(fFirstPage.getLocationPath());
            description.setNatureIds(new String[] { QSARNature.NATURE_ID });
            ICommand command = description.newCommand();
            command.setBuilderName(QSARBuilder.BUILDER_ID);
            description.setBuildSpec(new ICommand[] { command });
            project.create(description,monitor);

            //Open project
            project.open(monitor);

            monitor.worked(1);
            monitor.subTask( "Creating folders");

            //Create folders
            IPath projectPath = project.getFullPath(),
            molPath = projectPath.append("molecules");
            IFolder molFolder = root.getFolder(molPath);
            createFolderHelper(molFolder,monitor);

            //Create files (qsar.xml)
            monitor.subTask("Creating files");
            IPath qsarPath = projectPath.append("qsar.xml");
            IFile qsarFile = root.getFile(qsarPath);
            ByteArrayInputStream bos=new ByteArrayInputStream(
                                                QsarXMLUtils.getEmptyContent());
            qsarFile.create(bos,true,new SubProgressMonitor(monitor,10));
            bos.close();

            monitor.worked(1);
            monitor.subTask( "Serializing molecules to SDfile");

            //Save molecule file in molecules dir
            IPath molFilePath = molPath.append("chembl_"+targetID+"_"
                                               +responseType +".sdf");
            IFile molFile = root.getFile(molFilePath);
            cdk.saveSDFile( molFile, mols, new SubProgressMonitor( monitor, 2 ));
            
            project.refreshLocal( IResource.DEPTH_INFINITE, monitor );
            
            molFile.exists();
            
            monitor.worked(1);
            monitor.subTask( "Adding compounds and responses to QSAR project");
            IQsarManager qsar=net.bioclipse.qsar.init.Activator
                .getDefault().getJavaQsarManager();
            
            //Read qsar.xml into model
            QsarType qsarmodel = QSARFileUtils.readModelFromProjectFile( qsarFile );
  
            Map<IFile, Object> resourcePropertyMap=new HashMap<IFile, Object>();
            resourcePropertyMap.put( molFile, SDF_RESPONSE_PROPERTY);
            
            QsarAdapterFactory factory=new QsarAdapterFactory();
            EditingDomain editingDomain=new AdapterFactoryEditingDomain(factory, new BasicCommandStack());
            //Omit mols with errors
            qsar.addResourcesAndResponsesToQsarModel( qsarmodel, editingDomain, resourcePropertyMap, true, monitor );

            //Save qsar file.
            QSARFileUtils.saveModelToFile( qsarmodel, qsarFile );
            
        }
        catch(CoreException x)
        {
            LogUtils.debugTrace(logger, x);
        } catch (IOException e) {
            LogUtils.debugTrace(logger, e);
        } catch ( BioclipseException e ) {
            LogUtils.debugTrace(logger, e);
        }
        finally
        {
            monitor.done();
        }
    }

    /**
     * Create the folder in the closest parent which is a folder
     * @param folder
     * @param monitor
     */
    private void createFolderHelper (IFolder folder, IProgressMonitor monitor)
    {
        try {
            if(!folder.exists()) {
                IContainer parent = folder.getParent();

                if(parent instanceof IFolder
                        && (!((IFolder)parent).exists())) {

                    createFolderHelper((IFolder)parent, monitor);
                }

                folder.create(false,true,monitor);
            }
        } catch (Exception e) {
            LogUtils.debugTrace(logger, e);
        }
    }

}
