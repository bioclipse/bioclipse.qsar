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

package net.bioclipse.qsar.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import net.bioclipse.core.util.LogUtils;

import net.bioclipse.qsar.ui.builder.QSARBuilder;
import net.bioclipse.qsar.ui.builder.QSARNature;
import net.bioclipse.qsar.ui.util.QsarXMLUtils;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 *A NewWizard to create a new QSARProject
 *
 * @author ola
 */
public class NewQSARProjectWizard extends Wizard implements INewWizard {

    private static final Logger logger = Logger.getLogger(NewQSARProjectWizard.class);

    private WizardNewProjectCreationPage fFirstPage;

    private IWorkbench workbench;
    private IStructuredSelection selection;

    public NewQSARProjectWizard() {
        super();
//        setDefaultPageImageDescriptor();
        setWindowTitle("New QSAR project");
    }

    /**
     * Add WizardNewProjectCreationPage from IDE
     */
    public void addPages() {

        super.addPages();
        fFirstPage = new WizardNewProjectCreationPage("New QSAR project");
        fFirstPage.setTitle("New QSAR project");
        fFirstPage.setDescription("Create a new QSAR project");
//        fFirstPage.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),
//        "/org/ananas/xm/eclipse/resources/newproject58.gif"));

        addPage(fFirstPage);

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
    }


    /**
     * Create project and add required natures, builders, folders, and files
     * @param monitor
     */
    protected void createProject(IProgressMonitor monitor)
    {
        monitor.beginTask("Creating QSAR project",50);
        try
        {
        	
        	logger.debug("Creating project: " + fFirstPage.getProjectName() + " at " + fFirstPage.getLocationPath());
        	
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

            monitor.worked(10);

            //Open project
            project.open(monitor);

            //Set persistent properties (not used here/yet)
//            project.setPersistentProperty(PluginConstants.SOURCE_PROPERTY_NAME,"src");
//            project.setPersistentProperty(PluginConstants.RULES_PROPERTY_NAME,"rules");
//            project.setPersistentProperty(PluginConstants.PUBLISH_PROPERTY_NAME,"publish");
//            project.setPersistentProperty(PluginConstants.BUILD_PROPERTY_NAME,"false");

            monitor.worked(10);

            //Create folders
            IPath projectPath = project.getFullPath(),
            molPath = projectPath.append("molecules");
            IFolder molFolder = root.getFolder(molPath);
            createFolderHelper(molFolder,monitor);

            monitor.worked(10);

            //Create files (qsar.xml)
            monitor.subTask("Creating files");
            IPath qsarPath = projectPath.append("qsar.xml");
            IFile qsarFile = root.getFile(qsarPath);
            ByteArrayInputStream bos=new ByteArrayInputStream(QsarXMLUtils.getEmptyContent());
            qsarFile.create(bos,true,new SubProgressMonitor(monitor,10));
            bos.close();
//            
//            InputStream qsarIS = getClass().getResourceAsStream("/net/bioclipse/qsar/resources/qsar.xml");
//            qsarFile.create(qsarIS,true,new SubProgressMonitor(monitor,10));
//            qsarIS.close();
        }
        catch(CoreException x)
        {
            LogUtils.debugTrace(logger, x);
        } catch (IOException e) {
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
