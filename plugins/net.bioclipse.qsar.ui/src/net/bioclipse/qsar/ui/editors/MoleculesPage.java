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
package net.bioclipse.qsar.ui.editors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.chemoinformatics.util.ChemoinformaticUtils;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.StructurelistType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.ui.wizards.AddMoleculeFilesWizard;
import net.bioclipse.qsar.ui.wizards.PropertyEntry;
import net.bioclipse.ui.dialogs.WSFileDialog;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


/**
 * Page for adding molecular content to model
 * @author ola
 *
 */
public class MoleculesPage extends FormPage implements IEditingDomainProvider, IViewerProvider, IPageChangedListener{

    private TableViewer molViewer;
    private Table molTable;

    private TableViewer preTableViewer;
    private Table preTable;

    private static final Logger logger = Logger.getLogger(MoleculesPage.class);

    ICDKManager cdk;
    IQsarManager qsar;
    DecimalFormat formatter;

    private EditingDomain editingDomain;

    private IProject activeProject;
    private Action viewErrorsAction;


    public MoleculesPage(FormEditor editor, 
                         EditingDomain editingDomain) {

        super(editor, "qsar.molecules", "Molecules");
        this.editingDomain=editingDomain;

        //Get managers
        cdk=Activator.getDefault().getJavaCDKManager();
        qsar=net.bioclipse.qsar.init.Activator.getDefault().getQsarManager();

        //Set up formatter
        //We need to ensure that '.' is always decimal separator in all locales
        DecimalFormatSymbols sym=new DecimalFormatSymbols();
        sym.setDecimalSeparator( '.' );
        formatter = new DecimalFormat("0.00", sym);

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();

        //Get mollist from qsar model, init if empty (should not be)
        StructurelistType structList = qsarModel.getStructurelist();
        if (structList==null){
            structList=QsarFactory.eINSTANCE.createStructurelistType();
            qsarModel.setStructurelist( structList);
        }

        editor.addPageChangedListener(this);
        if (editor.getEditorInput() instanceof IFileEditorInput) {
            IFileEditorInput fin = (IFileEditorInput) editor.getEditorInput();
            activeProject=fin.getFile().getProject();
        }

    }


    /**
     * Add content to form
     */
    @Override
    protected void createFormContent(IManagedForm managedForm) {


        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Molecules for QSAR analysis");
        toolkit.decorateFormHeading(form.getForm());

        IProject project=((QsarEditor)getEditor()).getActiveProject();
        ToolbarHelper.setupToolbar(form, project);

        //        form.setBackgroundImage(FormArticlePlugin.getDefault().getImage(FormArticlePlugin.IMG_FORM_BG));
        GridLayout layout = new GridLayout();
//        layout.numColumns = 2;
        form.getBody().setLayout(layout);

        createMoleculesSection(form, toolkit);
        populateMolsViewerFromModel();

//        createPreprocessingSection(form, toolkit);
        //        populatePreViewerFromModel();  //TODO!
//        preTableViewer.getTable().setEnabled(false); //TODO: change!

        addDragAndDrop();

    }

    private void addDragAndDrop() {
        int ops = DND.DROP_COPY | DND.DROP_MOVE;
        Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance()};
        molViewer.addDropSupport(ops, transfers, new ViewerDropAdapter(molViewer){

            @Override
            public boolean performDrop(Object data) {

                if (!((data instanceof String[]) || (data instanceof IStructuredSelection))) {
                    return false;
                }

                final Object indata=data;

                WorkspaceJob job=new WorkspaceJob("Adding resources to QSAR project"){

                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor)
                    throws CoreException {

                        List<IResource> resources=new ArrayList<IResource>();

                        //Handle external file paths
                        if (indata instanceof String[]){
                            List<IResource> newRes=handleDropOfFiles((String[])indata, monitor);
                            if (newRes!=null && newRes.size()>0)
                                resources.addAll(newRes);
                        }

                        //Handle selections within Bioclipse
                        else if (indata instanceof IStructuredSelection){

                            IStructuredSelection ssel = (IStructuredSelection) indata;
                            for (Object obj : ssel.toList()){
                                if (obj instanceof IResource) {
                                    IResource res = (IResource) obj;
                                    resources.add(res);
                                }
                            }
                        }

                        //If none, return error
                        if (resources.size()<=0) return Status.CANCEL_STATUS;
                        
                        //Do not import properties on drop, but use map anyway
                        Map<IFile, PropertyEntry> filemap = 
                                            new HashMap<IFile, PropertyEntry>();
                        for (IResource res : resources){
                            filemap.put( (IFile)res, null );
                        }

                        //Add resources to model and molecules folder is necessary
                        try{
                            addResources(filemap, monitor);
//                            addResources(resources.toArray(new IResource[0]), monitor);
                        }catch (final UnsupportedOperationException e){
                            Display.getDefault().syncExec(new Runnable(){
                                public void run() {
                                    showError("Error adding files: " + e.getMessage());
                                }
                            });
                        }

                        Display.getDefault().syncExec(new Runnable(){

                            public void run() {
                                molViewer.getTable().setFocus();
                            }

                        });
                        return Status.OK_STATUS;

                    }

                };

                job.setUser(true);
                job.schedule();

                return true;

            }

            @Override
            public boolean validateDrop(Object target, int operation,
                                        TransferData transferType) {
                return true;
            }});
    }


    /**
     * Handle the dropping of files on molviewer.
     * Copy to molecules folder in that case.
     * @param data
     * @param monitor
     * @return
     */
    protected List<IResource> handleDropOfFiles(final String[] data, IProgressMonitor monitor) {

        final List<IResource> retlist=new ArrayList<IResource>();

        for (String path : (String[])data){
            
            try {
                
                //Without loading, try to check if this is a valid molecule
                String format = cdk.determineFormat( path );

                if (format!=null){
                    //Copy to molecules folder
                    IResource res=copyFileToMoleculesFolder(path, monitor);
                    if (res!=null)
                        retlist.add(res);
                }
                
            } catch ( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch ( CoreException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return retlist;
    }


    /**
     * Copy the file into the molecules folder
     * @param path absolut path to the file
     * @param monitor
     * @return IFile in molecules folder
     */
    protected IResource copyFileToMoleculesFolder(String path, IProgressMonitor monitor) {

        java.io.File file=new java.io.File(path);
        final String filename=file.getName();

        FileInputStream instream;
        IFile newfile=null;
        try {
            instream = new FileInputStream(file);

            IFolder molfolder=activeProject.getFolder("molecules");
            IPath newpath=molfolder.getProjectRelativePath().append(filename);

            newfile=activeProject.getFile(newpath);

            if (newfile.exists()){

                final boolean[] valueIsSet = {false};
                final boolean[] answer     = {false};

                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        synchronized ( valueIsSet ) {
                            answer[0] = MessageDialog.openQuestion(
                                                                   getSite().getShell(), 
                                                                   "Overwrite file?", 
                                                                   "File " + filename + "exists in " 
                                                                   + "project folder 'molecules'. " 
                                                                   + "Would you like to replace "
                                                                   + "it?" );
                            valueIsSet[0] = true;
                            valueIsSet.notifyAll();
                        }
                    }
                } );

                synchronized ( valueIsSet ) {
                    while ( !valueIsSet[0] ) {
                        try {
                            valueIsSet.wait();
                        } 
                        catch ( InterruptedException e ) {
                            continue;
                        }
                    }
                }

                if (!answer[0]) return null;

                newfile.setContents(instream, true,false, monitor);

            }else{
                newfile.create(instream, true, monitor);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (CoreException e) {
            e.printStackTrace();
            return null;
        }

        return newfile;

    }


    /**
     * Add resources to QSAR model. First check if contains molecules. 
     * Next copy the resource into the project/molecules folder if not already
     * there.
     * @param filemap
     * @param monitor
     * @return
     */
//    protected void addResources(final IResource[] resources, final IProgressMonitor monitor) {
    protected void addResources(final Map<IFile, PropertyEntry> filemap, final IProgressMonitor monitor) {

        
        //Set up input with propertyobject, not wrapper class since that is 
        //local to this plugin. This is the one we will send to QSARManager 
        //for adding in the end
        Map<IFile, Object> molprops=new HashMap<IFile, Object>();

        //Existing model and structures
        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        StructurelistType structList = qsarModel.getStructurelist();
        
        //Copy files to project if needed, hence we need a new map
        //Take one file at a time
        for (IFile file: filemap.keySet()){

            //Get property entry and extract AC property object
            PropertyEntry propEntry = filemap.get( file );
            Object propobj=null;
            if (propEntry!=null)
                propobj=propEntry.getPropObject();

            boolean skipFile=false;

            //Check if this file is already in model
            for (ResourceType existingRes : structList.getResources()){
                if (existingRes.getName().equals(file.getName())){
                    throw new UnsupportedOperationException(
                                           "File: " + file.getName() 
                                         + " already exists in QSAR analysis.");
                }
            }

            try {
                //Verify this is a file with at least one molecule
                int x=cdk.getNoMolecules( file.getFullPath().toOSString());
                if (x>0){

                    //If resource is in another project,
                    //copy it to molecules folder as use that copy
                    if (file.getProject()!=activeProject){
                        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                        IPath projectPath = activeProject.getFullPath(),
                        molFolderPath = projectPath.append("molecules"),
                        destinationPath=molFolderPath.append(file.getName());

                        
                        final IFile newfile=root.getFile(destinationPath);
                        
                        if (newfile.exists()){
                            final String filename=file.getName();

                            ConfirmRunnable cr=new ConfirmRunnable(
                               getSite().getShell(),
                               "Overwrite file?", 
                               "File " + filename + "exists in QSAR project, " +
                               "folder 'molecules'. " +
                               "Would you like to replace it?");

                            Display.getDefault().syncExec(cr);

                            if (!cr.getAnswer()) skipFile=true;

                            else{
                                try {
                                    newfile.setContents(file.getContents(), 
                                                        true, true, monitor);
                                } catch (CoreException e) {
                                    e.printStackTrace();
                                }
                            }



                        }else{
                            //Copy it
                            file.copy(destinationPath, true, monitor);
                        }

                        file=root.getFile(destinationPath);
                    }


                    if (!skipFile){
                        molprops.put( file,  propobj);
                    }
                }
            } catch (final Exception e) {
                Display.getDefault().syncExec(new Runnable(){
                    public void run() {
                        showError("Could not add molecule file. \n\nReason: " + e.getMessage());
                    }
                });
            }
        }

        //Ok, add these resources to QsarModel using manager
        try {
//            qsar.addResourcesToQsarModel( qsarModel, editingDomain, resourcesToAdd, monitor );
            qsar.addResourcesAndResponsesToQsarModel( qsarModel, editingDomain, 
                                                      molprops, monitor );
            
        } catch ( IOException e ) {
            logger.error(e.getStackTrace());
            showError( e.getMessage() );
        } catch ( BioclipseException e ) {
            logger.error(e.getStackTrace());
            showError( e.getMessage() );
        } catch ( CoreException e ) {
            logger.error(e.getStackTrace());
            showError( e.getMessage() );
        }

    }


    private void populateMolsViewerFromModel() {

        // The content provider is responsible to handle add and
        // remove notification for the Person#address EList
        ObservableListContentProvider provider = new ObservableListContentProvider();
        molViewer.setContentProvider(provider);

        // The label provider in turn handles the addresses
        // The EStructuralFeature[] defines which fields get shown
        // in the TableViewer columns
        IObservableSet knownElements = provider.getKnownElements();
        IObservableMap[] observeMaps = EMFEditObservables.
        observeMaps(editingDomain, knownElements, new EStructuralFeature[]{
                QsarPackage.Literals.RESOURCE_TYPE__NAME,
                QsarPackage.Literals.RESOURCE_TYPE__NO_MOLS,
                QsarPackage.Literals.RESOURCE_TYPE__NO2D,
                QsarPackage.Literals.RESOURCE_TYPE__NO3D,
                QsarPackage.Literals.RESOURCE_TYPE__CONTAINS_ERRORS});
        ObservableMapLabelProvider labelProvider =
            new ObservableQSARLabelProvider(observeMaps);
        molViewer.setLabelProvider(labelProvider);

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        StructurelistType structList = qsarModel.getStructurelist();

        // Person#addresses is the Viewer's input
        molViewer.setInput(EMFEditObservables.observeList(Realm.getDefault(), editingDomain, structList,
                                                          QsarPackage.Literals.STRUCTURELIST_TYPE__RESOURCES));

    }


    private void createMoleculesSection(final ScrolledForm form, FormToolkit toolkit) {


        Section molSection =
            toolkit.createSection(
                                  form.getBody(),
                                  Section.TWISTIE | Section.DESCRIPTION);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);
        Composite client = toolkit.createComposite(molSection, SWT.WRAP);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        client.setLayout(layout);

        molViewer = new TableViewer(client, SWT.BORDER | SWT.MULTI);
        molTable=molViewer.getTable();
        toolkit.adapt(molTable, true, true);
        GridData gd=new GridData(GridData.FILL_VERTICAL);
        gd.widthHint=450;
//        gd.widthHint=350;
        gd.verticalSpan=2;
        molTable.setLayoutData( gd );

        molTable.setHeaderVisible(true);
        //          molTable.setLinesVisible(true);
        toolkit.adapt(molTable, true, true);

        //Add name columns
        TableLayout tableLayout = new TableLayout();
        molTable.setLayout(tableLayout);
        TableViewerColumn ixcol=new TableViewerColumn(molViewer,SWT.BORDER);
        ixcol.getColumn().setText("Name");
        tableLayout.addColumnData(new ColumnPixelData(175));

        //Add # column
        TableViewerColumn col=new TableViewerColumn(molViewer,SWT.BORDER);
        col.getColumn().setText("# Molecules");
        tableLayout.addColumnData(new ColumnPixelData(75));

        //Add 2D column
        TableViewerColumn col2d=new TableViewerColumn(molViewer,SWT.BORDER);
        col2d.getColumn().setText("2D");
        tableLayout.addColumnData(new ColumnPixelData(30));

        //Add 2D column
        TableViewerColumn col3d=new TableViewerColumn(molViewer,SWT.BORDER);
        col3d.getColumn().setText("3D");
        tableLayout.addColumnData(new ColumnPixelData(30));

        //Add 2D column
        TableViewerColumn status=new TableViewerColumn(molViewer,SWT.BORDER);
        status.getColumn().setText("Status");
        tableLayout.addColumnData(new ColumnPixelData(100));


        molTable.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
                //Delete key
                if (e.keyCode==SWT.DEL){
                    deleteSelectedMolecules();
                }

                //Space key, toggle selection
                if (e.keyCode==32){

//                    IStructuredSelection msel=(IStructuredSelection) molViewer.getSelection();
                    //TODO: implement

                }
            }
            public void keyReleased( KeyEvent e ) {
            }
        });

        //If focus gained, make this viewer provide selections
        molViewer.getTable().addFocusListener(new FocusListener(){

            public void focusGained(FocusEvent e) {
                molViewer.setSelection(null);
            }

            public void focusLost(FocusEvent e) {
            }
        });


        Button btnAdd=toolkit.createButton(client, "Add...", SWT.PUSH);
        btnAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                addMoleculeFilesFromDialog();
            }
        });
        GridData gd2=new GridData();
        gd2.verticalAlignment=SWT.BEGINNING;
        gd2.widthHint=60;
        btnAdd.setLayoutData( gd2 );

        Button btnDel=toolkit.createButton(client, "Remove", SWT.PUSH);
        btnDel.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                deleteSelectedMolecules();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnDel.setLayoutData( gd2 );


        //Wrap up section
        toolkit.paintBordersFor(client);
        molSection.setText("Molecules");
        molSection.setDescription("Molecules for descriptor calculations");
        molSection.setClient(client);
        molSection.setExpanded(true);
        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(false);
            }
        });

        gd = new GridData(GridData.FILL_BOTH);
        molSection.setLayoutData(gd);        		

        makeActions();
        hookContextMenu();

        getSite().setSelectionProvider( molViewer );

    }

    /**
     * The actions in the viewer
     */
    private void makeActions() {

        viewErrorsAction=new Action("View errors", 
                            net.bioclipse.qsar.ui.Activator.getImageDescriptor( "icons/error_co.gif" )) {
            @Override
            public void run() {
                IStructuredSelection sel=
                                   (IStructuredSelection) molViewer.getSelection();
                
                if ( sel.getFirstElement() instanceof ResourceType ) {
                    
                    ResourceType res = (ResourceType)sel.getFirstElement();

                    String str="";
                    for (StructureType structure : res.getStructure()){
                        if (structure.getProblem()!=null && structure.getProblem().size()>0){
                            for (String problem : structure.getProblem()){
                                str=str+"Resource='" + res.getName() + "', id='" + structure.getId() + "', error: " + structure.getProblem() +"\n";
                            }
                        }
                    }

                    if (str.length()>1)
                        showError( str );
                }

            }
        };
        
    }

    /**
     * A context menu
     */
    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                manager.removeAll();
                
                //Only show actions if propertyentry selected
                IStructuredSelection sel=
                    (IStructuredSelection) molViewer.getSelection();

                if ( sel.getFirstElement() instanceof ResourceType ) {
                    manager.add(viewErrorsAction);
                    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
                }
            }

        });
        Menu menu = menuMgr.createContextMenu(molViewer.getControl());
        molViewer.getControl().setMenu(menu);
    }

    /**
     * Not yet implemented, hence not used
     * @param form
     * @param toolkit
     */
    @SuppressWarnings("unused")
    private void createPreprocessingSection( final ScrolledForm form,
                                             FormToolkit toolkit) {

        Section preSection =
            toolkit.createSection(
                                  form.getBody(),
                                  Section.TWISTIE | Section.DESCRIPTION);
        preSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        preSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(preSection);
        Composite client = toolkit.createComposite(preSection, SWT.WRAP);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        client.setLayout(layout);


        //Query TreeViewer
        preTableViewer = new TableViewer (client, SWT.BORDER | SWT.SINGLE);
        preTableViewer.setContentProvider( new PreprocessingContentProvider() );
        preTableViewer.setLabelProvider( new PreprocessingLabelProvider() );
        preTable=preTableViewer.getTable();
        toolkit.adapt(preTable, true, true);
        GridData gd6=new GridData(GridData.FILL_VERTICAL);
        gd6.widthHint=200;
        gd6.verticalSpan=4;
        preTable.setLayoutData( gd6 );

        preTable.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {

                //Delete key
                if (e.keyCode==SWT.DEL){
                    deletePreprocessingStep();
                }

            }
            public void keyReleased( KeyEvent e ) {
            }
        });


        Button btnAdd=toolkit.createButton(client, "Add...", SWT.PUSH);
        btnAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                addPreprocessingStep();
            }
        });
        GridData gd2=new GridData();
        gd2.verticalAlignment=SWT.BEGINNING;
        gd2.widthHint=60;
        btnAdd.setLayoutData( gd2 );

        Button btnDel=toolkit.createButton(client, "Remove", SWT.PUSH);
        btnDel.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                deletePreprocessingStep();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnDel.setLayoutData( gd2 );

        Button btnUp=toolkit.createButton(client, "Up", SWT.PUSH);
        btnUp.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                moveSelectedUp();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnUp.setLayoutData( gd2 );

        Button btnDown=toolkit.createButton(client, "Down", SWT.PUSH);
        btnDown.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                moveSelectedDown();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnDown.setLayoutData( gd2 );


        //Wrap up section
        toolkit.paintBordersFor(client);
        preSection.setText("Molecule preprocessing");
        preSection.setDescription("Add/remove and order preprocessing steps");
        preSection.setClient(client);
        preSection.setExpanded(true);
        preSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(false);
            }
        });
        GridData gd = new GridData(GridData.FILL_BOTH);
        preSection.setLayoutData(gd);        

        //Post selections to Eclipse
        //          getSite().setSelectionProvider(queryViewer);

    }

    protected void moveSelectedDown() {

        showMessage("Not implemented");
        //TODO: implement

    }


    protected void moveSelectedUp() {

        showMessage("Not implemented");
        //TODO: implement

    }


    /**
     * After click of "ADD" button, add preprocess step
     */
    protected void addPreprocessingStep() {

        showMessage("Not implemented");
        //TODO: implement

    }

    protected void deletePreprocessingStep() {

        showMessage("Not implemented");
        //TODO: implement

        /*
        IStructuredSelection sel=(IStructuredSelection)preTableViewer.getSelection();
        for (Object obj : sel.toList()){

            }
        }

        preViewe 
        preTableViewer.getTable().setFocus();
         */
    }



    protected void changeMolViewerState( Object obj, boolean newState ) {

        showMessage("Not implemented");
        //TODO: implement


        /*
        molViewer.setChecked( obj, newState );

        if ( obj instanceof MoleculeResource ) {
            molViewer.expandToLevel(obj, 1);
            MoleculeFile cont = (MoleculeFile) obj;
            for (PcoreMolecule mol : cont.getChildren()){
                molViewer.setChecked( mol, newState );
                for (PcoreConformer conf : mol.getConformers()){
                    molViewer.setChecked( conf, newState );
                }
            }
        }
        if ( obj instanceof PcoreMolecule ) {
//            molViewer.expandToLevel(obj, AbstractTreeViewer.ALL_LEVELS);
            PcoreMolecule pmol=(PcoreMolecule)obj;
            for (PcoreConformer conf : pmol.getConformers()){
                molViewer.setChecked( conf, newState );
            }
        }
         */
    }

    /**
     * Handle the case when users press the ADD button next to moleculeviewer
     */
    protected void addMoleculeFilesFromDialog() {

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        //Collect a list of resources currently in viewer 
        //to hide them in dialog
        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        StructurelistType structList = qsarModel.getStructurelist();
        List<IResource> blacklistFilter=new ArrayList<IResource>();
        for (ResourceType existingRes : structList.getResources()){
            String filepath=existingRes.getFile();
            //Locate resource
            IFile file = root.getFile( new Path(filepath) );
            if (file.exists())
                blacklistFilter.add( file );
                
        }

        AddMoleculeFilesWizard wizard=new AddMoleculeFilesWizard(blacklistFilter);
        
        // Instantiates the wizard container with the wizard and opens it
        WizardDialog dialog = new WizardDialog(getSite().getShell(), wizard);
        dialog.create();
        int r = dialog.open();
        if (r==Window.CANCEL){
            return;
        }

        //Here we have map of IFile > Property to add
        final Map<IFile, PropertyEntry> selprops = wizard.getSelectedProperties();

//        System.out.println("We should add: ");
//        for (IFile file : selprops.keySet()){
//            String str="  + " + file.getName();
//            if (selprops.get( file )!=null)
//                str = str + " -- " + selprops.get( file ).getPropObject();
//            System.out.println(str);
//        }

        //Add the resources in a job since long running operation
        WorkspaceJob job = new WorkspaceJob("Adding resources"){

            @Override
            public IStatus runInWorkspace( IProgressMonitor monitor )
                                                          throws CoreException {
                addResources(selprops, monitor);

                Runnable r=new Runnable(){public void run() {
                    molViewer.refresh();}
                };
                Display.getDefault().asyncExec( r );
                
                return Status.OK_STATUS;
            }
            
        };
        job.setUser( true );
        job.schedule();
        
    }



    /**
     * Handle the case when users press the Remove button next to moleculeviewer
     * or presses the delete button on something
     */
    @SuppressWarnings("unchecked")
    protected void deleteSelectedMolecules() {

        IStructuredSelection ssel=(IStructuredSelection) molViewer.getSelection();
        if (ssel == null) {
            showMessage("Please select a molecule to remove");
            return;
        }

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        qsar.removeResourcesFromModel(qsarModel, editingDomain, ssel.toList());

        molViewer.refresh();
    }

    
    private void showMessage(String message) {
        MessageDialog.openInformation( getSite().getShell(),
                                       "Information",
                                       message );
    }

    private void showError(String message) {
        MessageDialog.openError( getSite().getShell(),
                                 "Information",
                                 message );
    }


    public void activatePage() {

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

        //return number of seconds
        public String toString() {
            return "" + Long.toString(elapsedTimeMillis()/1000); // print execution time
        }
    }

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }


    public Viewer getViewer() {
        return molViewer;
    }

    public void pageChanged(PageChangedEvent event) {

        if (event.getSelectedPage()!=this) return;

        if (molViewer!=null){
            populateMolsViewerFromModel();
        }

        activatePage();

    }

}
