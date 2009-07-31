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
package net.bioclipse.qsar.ui.editors;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.CTabFolder;

import org.eclipse.swt.graphics.Point;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.editor.FormEditor;

import org.eclipse.ui.ide.IGotoMarker;

import org.eclipse.ui.part.FileEditorInput;

import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.ViewerPane;

import org.eclipse.emf.common.ui.editor.ProblemEditorPart;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emf.edit.ui.util.EditUIUtil;

import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;

import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DocumentRoot;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.init.Activator;
import net.bioclipse.qsar.provider.QsarItemProviderAdapterFactory;
import net.bioclipse.qsar.ui.QsarHelper;
import net.bioclipse.ui.editors.XMLEditor;

import net.sf.bibtexml.provider.BibtexmlItemProviderAdapterFactory;

import org.eclipse.ui.actions.WorkspaceModifyOperation;


/**
 * This is an example of a Qsar model editor.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class QsarEditor extends FormEditor implements IEditingDomainProvider, 
                                                 ISelectionProvider, IAdaptable, IMenuListener{

    private static final Logger logger = Logger.getLogger(QsarEditor.class);

    private IProject activeProject;

    public int textEditorIndex;
    public int molPageIndex;
    public int descPageIndex;
    public int responsesPageIndex;
    public int overviewPageIndex;
    public int infoPageIndex;

    private MoleculesPage molPage;
    private DescriptorsPage descPage;
    private ResponsesPage responsesPage;
    private OverviewPage overviewPage;
    private InformationPage informationPage;

//    private QsarEditorSelectionProvider selectionProvider;
    private XMLEditor xmlEditor;

    private IQsarManager qsar;


    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = " Copyright (c) 2009 Ola Spjuth\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n";

    /**
     * This keeps track of the editing domain that is used to track all changes to the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AdapterFactoryEditingDomain editingDomain;

    /**
     * This is the one adapter factory used for providing views of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory adapterFactory;

    /**
     * This is the content outline page.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IContentOutlinePage contentOutlinePage;

    /**
     * This is a kludge...
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IStatusLineManager contentOutlineStatusLineManager;

    /**
     * This is the content outline page's viewer.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeViewer contentOutlineViewer;

    /**
     * This is the property sheet page.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PropertySheetPage propertySheetPage;

    /**
     * This is the viewer that shadows the selection in the content outline.
     * The parent relation must be correctly defined for this to work.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeViewer selectionViewer;

    /**
     * This inverts the roll of parent and child in the content provider and show parents as a tree.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeViewer parentViewer;

    /**
     * This shows how a tree view works.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeViewer treeViewer;

    /**
     * This shows how a list view works.
     * A list viewer doesn't support icons.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ListViewer listViewer;

    /**
     * This shows how a table view works.
     * A table can be used as a list with icons.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TableViewer tableViewer;

    /**
     * This shows how a tree view with columns works.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeViewer treeViewerWithColumns;

    /**
     * This keeps track of the active viewer pane, in the book.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ViewerPane currentViewerPane;

    /**
     * This keeps track of the active content viewer, which may be either one of the viewers in the pages or the content outline viewer.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Viewer currentViewer;

    /**
     * This listens to which ever viewer is active.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ISelectionChangedListener selectionChangedListener;

    /**
     * This keeps track of all the {@link org.eclipse.jface.viewers.ISelectionChangedListener}s that are listening to this editor.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

    /**
     * This keeps track of the selection of the editor as a whole.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ISelection editorSelection = StructuredSelection.EMPTY;

    /**
     * The MarkerHelper is responsible for creating workspace resource markers presented
     * in Eclipse's Problems View.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MarkerHelper markerHelper = new EditUIMarkerHelper();

    /**
     * This listens for when the outline becomes active
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IPartListener partListener =
        new IPartListener() {
        public void partActivated(IWorkbenchPart p) {
            if (p instanceof ContentOutline) {
                if (((ContentOutline)p).getCurrentPage() == contentOutlinePage) {
                    getActionBarContributor().setActiveEditor(QsarEditor.this);
                }
            }
            else if (p instanceof PropertySheet) {
                if (((PropertySheet)p).getCurrentPage() == propertySheetPage) {
                    getActionBarContributor().setActiveEditor(QsarEditor.this);
                    handleActivate();
                }
            }
            else if (p == QsarEditor.this) {
                handleActivate();
            }
        }
        public void partBroughtToTop(IWorkbenchPart p) {
            // Ignore.
        }
        public void partClosed(IWorkbenchPart p) {
            // Ignore.
        }
        public void partDeactivated(IWorkbenchPart p) {
            // Ignore.
        }
        public void partOpened(IWorkbenchPart p) {
            // Ignore.
        }
    };

    /**
     * Resources that have been removed since last activation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Resource> removedResources = new ArrayList<Resource>();

    /**
     * Resources that have been changed since last activation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Resource> changedResources = new ArrayList<Resource>();

    /**
     * Resources that have been saved.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Resource> savedResources = new ArrayList<Resource>();

    /**
     * Map to store the diagnostic associated with a resource.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();

    /**
     * Controls whether the problem indication should be updated.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected boolean updateProblemIndication = true;

    /**
     * Adapter used to update the problem indication when resources are demanded loaded.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EContentAdapter problemIndicationAdapter = 
        new EContentAdapter() {
        @Override
        public void notifyChanged(Notification notification) {
            if (notification.getNotifier() instanceof Resource) {
                switch (notification.getFeatureID(Resource.class)) {
                    case Resource.RESOURCE__IS_LOADED:
                    case Resource.RESOURCE__ERRORS:
                    case Resource.RESOURCE__WARNINGS: {
                        Resource resource = (Resource)notification.getNotifier();
                        Diagnostic diagnostic = analyzeResourceProblems(resource, null);
                        if (diagnostic.getSeverity() != Diagnostic.OK) {
                            resourceToDiagnosticMap.put(resource, diagnostic);
                        }
                        else {
                            resourceToDiagnosticMap.remove(resource);
                        }

                        if (updateProblemIndication) {
                            getSite().getShell().getDisplay().asyncExec
                            (new Runnable() {
                                public void run() {
                                    updateProblemIndication();
                                }
                            });
                        }
                        break;
                    }
                }
            }
            else {
                super.notifyChanged(notification);
            }
        }

        @Override
        protected void setTarget(Resource target) {
            basicSetTarget(target);
        }

        @Override
        protected void unsetTarget(Resource target) {
            basicUnsetTarget(target);
        }
    };

    /**
     * This listens for workspace changes.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IResourceChangeListener resourceChangeListener =
        new IResourceChangeListener() {
        public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta delta = event.getDelta();
            try {
                class ResourceDeltaVisitor implements IResourceDeltaVisitor {
                    protected ResourceSet resourceSet = editingDomain.getResourceSet();
                    protected Collection<Resource> changedResources = new ArrayList<Resource>();
                    protected Collection<Resource> removedResources = new ArrayList<Resource>();

                    public boolean visit(IResourceDelta delta) {
                        if (delta.getResource().getType() == IResource.FILE) {
                            if (delta.getKind() == IResourceDelta.REMOVED ||
                                    delta.getKind() == IResourceDelta.CHANGED && delta.getFlags() != IResourceDelta.MARKERS) {
                                Resource resource = resourceSet.getResource(URI.createURI(delta.getFullPath().toString()), false);
                                if (resource != null) {
                                    if (delta.getKind() == IResourceDelta.REMOVED) {
                                        removedResources.add(resource);
                                    }
                                    else if (!savedResources.remove(resource)) {
                                        changedResources.add(resource);
                                    }
                                }
                                
                                //See if this is a resource that is linked in QSAR model
                                for (ResourceType lres : getQsarModel().getStructurelist().getResources()){
                                    if (lres.getFile().equals( delta.getResource().getFullPath().toOSString() )){
                                        logger.debug("There was a change in a linked file: " + delta.getKind());
                                    }
                                }
                            }
                        }

                        return true;
                    }

                    public Collection<Resource> getChangedResources() {
                        return changedResources;
                    }

                    public Collection<Resource> getRemovedResources() {
                        return removedResources;
                    }
                }

                ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
                delta.accept(visitor);

                if (!visitor.getRemovedResources().isEmpty()) {
                    removedResources.addAll(visitor.getRemovedResources());
                    if (!isDirty()) {
                        getSite().getShell().getDisplay().asyncExec
                        (new Runnable() {
                            public void run() {
                                getSite().getPage().closeEditor(QsarEditor.this, false);
                            }
                        });
                    }
                }

                if (!visitor.getChangedResources().isEmpty()) {
                    changedResources.addAll(visitor.getChangedResources());
                    if (getSite().getPage().getActiveEditor() == QsarEditor.this) {
                        getSite().getShell().getDisplay().asyncExec
                        (new Runnable() {
                            public void run() {
                                handleActivate();
                            }
                        });
                    }
                }
            }
            catch (CoreException exception) {
                logger.error(exception);
            }
        }
    };

    private AdapterFactoryItemDelegator itemDelegator;

    private AdapterFactoryLabelProvider labelProvider;



    /**
     * Handles activation of the editor or it's associated views.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void handleActivate() {
        // Recompute the read only state.
        //
        if (editingDomain.getResourceToReadOnlyMap() != null) {
            editingDomain.getResourceToReadOnlyMap().clear();

            // Refresh any actions that may become enabled or disabled.
            //
            setSelection(getSelection());
        }

        if (!removedResources.isEmpty()) {
            if (handleDirtyConflict()) {
                getSite().getPage().closeEditor(QsarEditor.this, false);
            }
            else {
                removedResources.clear();
                changedResources.clear();
                savedResources.clear();
            }
        }
        else if (!changedResources.isEmpty()) {
            changedResources.removeAll(savedResources);
            handleChangedResources();
            changedResources.clear();
            savedResources.clear();

        }
    }

    /**
     * Handles what to do with changed resources on activation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void handleChangedResources() {
        if (!changedResources.isEmpty() && (!isDirty() || handleDirtyConflict())) {
            if (isDirty()) {
                changedResources.addAll(editingDomain.getResourceSet().getResources());
            }
            editingDomain.getCommandStack().flush();

            updateProblemIndication = false;
            
            try {
                Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
            } catch ( OperationCanceledException e1 ) {
                e1.printStackTrace();
            } catch ( InterruptedException e1 ) {
                e1.printStackTrace();
            }

            
            try {
                ResourcesPlugin.getWorkspace().run(
                                                   new IWorkspaceRunnable() {
                                                       public void run(IProgressMonitor monitor)
                                                       throws CoreException
                                                       {
                                                           for (Resource resource : changedResources) {
                                                               if (resource.isLoaded()) {
                                                                   resource.unload();
                                                                   try {
                                                                       resource.load(Collections.EMPTY_MAP);
//                                                                       getQsarModel( resource );
                                                                       
                                                                       QsarHelper.updateTransientProperties(getQsarModel(), getActiveProject());
                                                                       
                                                                       //Force a page change to pick up model reload on page
                                                                       setActivePage( getActivePage() );
                                                                   }
                                                                   catch (IOException exception) {
                                                                       if (!resourceToDiagnosticMap.containsKey(resource)) {
                                                                           resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                       }
                                                   },
//                                               getProject(),
//                                               IWorkspace.AVOID_UPDATE,
                                                   new NullProgressMonitor()
                );
            } catch ( CoreException e ) {
                e.printStackTrace();
            }
            

            if (AdapterFactoryEditingDomain.isStale(editorSelection)) {
                setSelection(StructuredSelection.EMPTY);
            }

            updateProblemIndication = true;
            updateProblemIndication();
        }
    }

    /**
     * Updates the problems indication with the information described in the specified diagnostic.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void updateProblemIndication() {
        if (updateProblemIndication) {
            BasicDiagnostic diagnostic =
                new BasicDiagnostic
                (Diagnostic.OK,
                 "net.bioclipse.qsar.model.editor",
                 0,
                 null,
                 new Object [] { editingDomain.getResourceSet() });
            for (Diagnostic childDiagnostic : resourceToDiagnosticMap.values()) {
                if (childDiagnostic.getSeverity() != Diagnostic.OK) {
                    diagnostic.add(childDiagnostic);
                }
            }

            int lastEditorPage = getPageCount() - 1;
            if (lastEditorPage >= 0 && getEditor(lastEditorPage) instanceof ProblemEditorPart) {
                ((ProblemEditorPart)getEditor(lastEditorPage)).setDiagnostic(diagnostic);
                if (diagnostic.getSeverity() != Diagnostic.OK) {
                    setActivePage(lastEditorPage);
                }
            }
            else if (diagnostic.getSeverity() != Diagnostic.OK) {
                ProblemEditorPart problemEditorPart = new ProblemEditorPart();
                problemEditorPart.setDiagnostic(diagnostic);
                problemEditorPart.setMarkerHelper(markerHelper);
                try {
                    addPage(++lastEditorPage, problemEditorPart, getEditorInput());
                    setPageText(lastEditorPage, problemEditorPart.getPartName());
                    setActivePage(lastEditorPage);
                    showTabs();
                }
                catch (PartInitException exception) {
                    logger.error(exception);
                }
            }

            if (markerHelper.hasMarkers(editingDomain.getResourceSet())) {
                markerHelper.deleteMarkers(editingDomain.getResourceSet());
                if (diagnostic.getSeverity() != Diagnostic.OK) {
                    try {
                        markerHelper.createMarkers(diagnostic);
                    }
                    catch (CoreException exception) {
                        logger.error(exception);
                    }
                }
            }
        }
    }

    /**
     * Shows a dialog that asks if conflicting changes should be discarded.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected boolean handleDirtyConflict() {
        return
        MessageDialog.openQuestion
        (getSite().getShell(),
         "File conflict",
        "The editor is unsaved but file was changed by background process. " +
        "Reload contents from file?");
    }

    /**
     * This creates a model editor.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public QsarEditor() {
        super();
        initializeEditingDomain();
    }

    /**
     * This sets up the editing domain for the model editor.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void initializeEditingDomain() {
        // Create an adapter factory that yields item providers.
        //
        adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

        adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
        adapterFactory.addAdapterFactory(new QsarItemProviderAdapterFactory());
        adapterFactory.addAdapterFactory(new BibtexmlItemProviderAdapterFactory());
        adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        // Create the command stack that will notify this editor as commands are executed.
        //
        BasicCommandStack commandStack = new BasicCommandStack();

        // Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
        //
        commandStack.addCommandStackListener
        (new CommandStackListener() {
            public void commandStackChanged(final EventObject event) {
                getContainer().getDisplay().asyncExec
                (new Runnable() {
                    public void run() {
                        firePropertyChange(IEditorPart.PROP_DIRTY);

                        // Try to select the affected objects.
                        //
                        Command mostRecentCommand = ((CommandStack)event.getSource()).getMostRecentCommand();
                        if (mostRecentCommand != null) {
                            setSelectionToViewer(mostRecentCommand.getAffectedObjects());
                        }
                        if (propertySheetPage != null && !propertySheetPage.getControl().isDisposed()) {
                            propertySheetPage.refresh();
                        }
                    }
                });
            }
        });

        // Create the editing domain with a special command stack.
        //
        editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
        
     // These provide access to the model items, their property source and label
        this.itemDelegator = new AdapterFactoryItemDelegator(adapterFactory);
        this.labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

    }

    /**
     * This is here for the listener to be able to call it.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void firePropertyChange(int action) {
        super.firePropertyChange(action);
    }

    /**
     * This sets the selection into whichever viewer is active.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSelectionToViewer(Collection<?> collection) {
        final Collection<?> theSelection = collection;
        // Make sure it's okay.
        //
        if (theSelection != null && !theSelection.isEmpty()) {
            // I don't know if this should be run this deferred
            // because we might have to give the editor a chance to process the viewer update events
            // and hence to update the views first.
            //
            //
            Runnable runnable =
                new Runnable() {
                public void run() {
                    // Try to select the items in the current content viewer of the editor.
                    //
                    if (currentViewer != null) {
                        currentViewer.setSelection(new StructuredSelection(theSelection.toArray()), true);
                    }
                }
            };
            runnable.run();
        }
    }

    /**
     * This returns the editing domain as required by the {@link IEditingDomainProvider} interface.
     * This is important for implementing the static methods of {@link AdapterFactoryEditingDomain}
     * and for supporting {@link org.eclipse.emf.edit.ui.action.CommandAction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public class ReverseAdapterFactoryContentProvider extends AdapterFactoryContentProvider {
        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public ReverseAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object [] getElements(Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object [] getChildren(Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public boolean hasChildren(Object object) {
            Object parent = super.getParent(object);
            return parent != null;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object getParent(Object object) {
            return null;
        }
    }




    /**
     * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @throws PartInitException 
     * @generated
     */
    public void createModel() throws PartInitException {
        URI resourceURI = EditUIUtil.getURI(getEditorInput());
        Exception exception = null;
        Resource resource = null;
        try {
            // Load the resource through the editing domain.
            //
            resource = editingDomain.getResourceSet().getResource(resourceURI, true);
        }
        catch (Exception e) {
            exception = e;
            resource = editingDomain.getResourceSet().getResource(resourceURI, false);
        }

        //Validate input
        Diagnostic diagnostic = analyzeResourceProblems(resource, exception);
        if (diagnostic.getSeverity() != Diagnostic.OK) {
            resourceToDiagnosticMap.put(resource,  diagnostic);

            if (diagnostic.getSeverity()== Diagnostic.WARNING){
                MessageDialog.openWarning( getSite().getShell(),
                                           "Warning",
                                           diagnostic.getException().getMessage() );
            }
            else if (diagnostic.getSeverity()== Diagnostic.ERROR){
                MessageDialog.openError( getSite().getShell(),
                                           "Error",
                                           diagnostic.getException().getMessage() );
                throw new PartInitException(diagnostic.getException().getMessage());
            }
        }
        editingDomain.getResourceSet().eAdapters().add(problemIndicationAdapter);
        
        if (resourceToDiagnosticMap.size()>0){
            //Warnings or errors
        }

        //Calculate non-stored properties like num of mols, no2D etc
        qsar=Activator.getDefault().getQsarManager();
        if (getQsarModel()!=null){
            qsar.addCalculatedPropertiesToQsarModel(getQsarModel());
            logger.debug(" ## ## QSAREditor read model file successfully");
        }else{
            logger.error(" ## ## QSAREditor read model file FAILED");
        }

    }

    public QsarType getQsarModel() {

        if (getEditingDomain().getResourceSet().getResources()==null || getEditingDomain().getResourceSet().getResources().size()<=0)
            return null;
        
        //There can be only one resource
        Resource resource=getEditingDomain().getResourceSet().getResources().get( 0 );

        if (resource != null) {
            DocumentRoot root = (DocumentRoot) resource.getContents().get(0);

            //Store the model in editor
            return root.getQsar();
        }else{
            logger.error("Could not get resource from EditingDomain.");
        }
        
        return null;
    }

    /**
     * Returns a diagnostic describing the errors and warnings listed in the resource
     * and the specified exception (if any).
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Diagnostic analyzeResourceProblems(Resource resource, Exception exception) {
        if (!resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty()) {
            BasicDiagnostic basicDiagnostic =
                new BasicDiagnostic
                (Diagnostic.ERROR,
                 "net.bioclipse.qsar.model.editor",
                 0,
                 "_UI_CreateModelError_message",
                 new Object [] { exception == null ? (Object)resource : exception });
            basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
            return basicDiagnostic;
        }
        else if (exception != null) {
            return
            new BasicDiagnostic
            (Diagnostic.ERROR,
             "net.bioclipse.qsar.model.editor",
             0,
             "_UI_CreateModelError_message",
             new Object[] { exception });
        }
        else {
            return Diagnostic.OK_INSTANCE;
        }
    }


    /**
     * If there is just one page in the multi-page editor part,
     * this hides the single tab at the bottom.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void hideTabs() {
        if (getPageCount() <= 1) {
            setPageText(0, "");
            if (getContainer() instanceof CTabFolder) {
                ((CTabFolder)getContainer()).setTabHeight(1);
                Point point = getContainer().getSize();
                getContainer().setSize(point.x, point.y + 6);
            }
        }
    }

    /**
     * If there is more than one page in the multi-page editor part,
     * this shows the tabs at the bottom.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void showTabs() {
        if (getPageCount() > 1) {
            setPageText(0, "_UI_SelectionPage_label");
            if (getContainer() instanceof CTabFolder) {
                ((CTabFolder)getContainer()).setTabHeight(SWT.DEFAULT);
                Point point = getContainer().getSize();
                getContainer().setSize(point.x, point.y - 6);
            }
        }
    }

    /**
     * This is used to track the active viewer.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void pageChange(int pageIndex) {
        super.pageChange(pageIndex);

        if (contentOutlinePage != null) {
            handleContentOutlineSelection(contentOutlinePage.getSelection());
        }
    }

    /**
     * This is how the framework determines which interfaces we implement.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class key) {
        if (key.equals(IContentOutlinePage.class)) {
            return showOutlineView() ? getContentOutlinePage() : null;
        }
        else if (key.equals(IPropertySheetPage.class)) {
            return getPropertySheetPage();
        }
        else if (key.equals(IGotoMarker.class)) {
            return this;
        }
        else {
            return super.getAdapter(key);
        }
    }

    /**
     * This accesses a cached version of the content outliner.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IContentOutlinePage getContentOutlinePage() {
        if (contentOutlinePage == null) {
            // The content outline is just a tree.
            //
            class MyContentOutlinePage extends ContentOutlinePage {
                @Override
                public void createControl(Composite parent) {
                    super.createControl(parent);
                    contentOutlineViewer = getTreeViewer();
                    contentOutlineViewer.addSelectionChangedListener(this);

                    // Set up the tree viewer.
                    //
                    contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
                    contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
                    contentOutlineViewer.setInput(editingDomain.getResourceSet());

                    // Make sure our popups work.
                    //
                    //                    createContextMenuFor(contentOutlineViewer);

                    if (!editingDomain.getResourceSet().getResources().isEmpty()) {
                        // Select the root object in the view.
                        //
                        contentOutlineViewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);
                    }
                }

                @Override
                public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
                    super.makeContributions(menuManager, toolBarManager, statusLineManager);
                    contentOutlineStatusLineManager = statusLineManager;
                }

                @Override
                public void setActionBars(IActionBars actionBars) {
                    super.setActionBars(actionBars);
                    getActionBarContributor().shareGlobalActions(this, actionBars);
                }
            }

            contentOutlinePage = new MyContentOutlinePage();

            // Listen to selection so that we can handle it is a special way.
            //
            contentOutlinePage.addSelectionChangedListener
            (new ISelectionChangedListener() {
                // This ensures that we handle selections correctly.
                //
                public void selectionChanged(SelectionChangedEvent event) {
                    handleContentOutlineSelection(event.getSelection());
                }
            });
        }

        return contentOutlinePage;
    }

    /**
     * This accesses a cached version of the property sheet.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IPropertySheetPage getPropertySheetPage() {
        if (propertySheetPage == null) {
            propertySheetPage =
                new ExtendedPropertySheetPage(editingDomain) {
                @Override
                public void setSelectionToViewer(List<?> selection) {
                    QsarEditor.this.setSelectionToViewer(selection);
                    QsarEditor.this.setFocus();
                }

                @Override
                public void setActionBars(IActionBars actionBars) {
                    super.setActionBars(actionBars);
                    getActionBarContributor().shareGlobalActions(this, actionBars);
                }
            };
            propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
        }

        return propertySheetPage;
    }

    /**
     * This deals with how we want selection in the outliner to affect the other views.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void handleContentOutlineSelection(ISelection selection) {
        if (currentViewerPane != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
            Iterator<?> selectedElements = ((IStructuredSelection)selection).iterator();
            if (selectedElements.hasNext()) {
                // Get the first selected element.
                //
                Object selectedElement = selectedElements.next();

                // If it's the selection viewer, then we want it to select the same selection as this selection.
                //
                if (currentViewerPane.getViewer() == selectionViewer) {
                    ArrayList<Object> selectionList = new ArrayList<Object>();
                    selectionList.add(selectedElement);
                    while (selectedElements.hasNext()) {
                        selectionList.add(selectedElements.next());
                    }

                    // Set the selection to the widget.
                    //
                    selectionViewer.setSelection(new StructuredSelection(selectionList));
                }
                else {
                    // Set the input to the widget.
                    //
                    if (currentViewerPane.getViewer().getInput() != selectedElement) {
                        currentViewerPane.getViewer().setInput(selectedElement);
                        currentViewerPane.setTitle(selectedElement);
                    }
                }
            }
        }
    }

    /**
     * This is for implementing {@link IEditorPart} and simply tests the command stack.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isDirty() {
        return ((BasicCommandStack)editingDomain.getCommandStack()).isSaveNeeded();
    }

    /**
     * This is for implementing {@link IEditorPart} and simply saves the model file.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void doSave(IProgressMonitor progressMonitor) {
        // Save only resources that have actually changed.
        //
        final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
        saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

        // Do the work within an operation because this is a long running activity that modifies the workbench.
        //
        WorkspaceModifyOperation operation =
            new WorkspaceModifyOperation() {
            // This is the method that gets invoked when the operation runs.
            //
            @Override
            public void execute(IProgressMonitor monitor) {
                // Save the resources to the file system.
                //
                boolean first = true;
                for (Resource resource : editingDomain.getResourceSet().getResources()) {
                    if ((first || !resource.getContents().isEmpty() || isPersisted(resource)) && !editingDomain.isReadOnly(resource)) {
                        try {
                            long timeStamp = resource.getTimeStamp();
                            resource.save(saveOptions);
                            if (resource.getTimeStamp() != timeStamp) {
                                savedResources.add(resource);
                            }
                        }
                        catch (Exception exception) {
                            resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
                        }
                        first = false;
                    }
                }
            }
        };

        updateProblemIndication = false;
        try {
            // This runs the options, and shows progress.
            //
            new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

            // Refresh the necessary state.
            //
            ((BasicCommandStack)editingDomain.getCommandStack()).saveIsDone();
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
        catch (Exception exception) {
            // Something went wrong that shouldn't.
            //
            logger.error(exception);
        }
        updateProblemIndication = true;
        updateProblemIndication();
    }

    /**
     * This returns whether something has been persisted to the URI of the specified resource.
     * The implementation uses the URI converter from the editor's resource set to try to open an input stream. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected boolean isPersisted(Resource resource) {
        boolean result = false;
        try {
            InputStream stream = editingDomain.getResourceSet().getURIConverter().createInputStream(resource.getURI());
            if (stream != null) {
                result = true;
                stream.close();
            }
        }
        catch (IOException e) {
            // Ignore
        }
        return result;
    }

    /**
     * This always returns true because it is not currently supported.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * This also changes the editor's input.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void doSaveAs() {
        SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
        saveAsDialog.open();
        IPath path = saveAsDialog.getResult();
        if (path != null) {
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            if (file != null) {
                doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), true), new FileEditorInput(file));
            }
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void doSaveAs(URI uri, IEditorInput editorInput) {
        (editingDomain.getResourceSet().getResources().get(0)).setURI(uri);
        setInputWithNotify(editorInput);
        setPartName(editorInput.getName());
        IProgressMonitor progressMonitor =
            getActionBars().getStatusLineManager() != null ?
                    getActionBars().getStatusLineManager().getProgressMonitor() :
                        new NullProgressMonitor();
                    doSave(progressMonitor);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void gotoMarker(IMarker marker) {
        try {
            if (marker.getType().equals(EValidator.MARKER)) {
                String uriAttribute = marker.getAttribute(EValidator.URI_ATTRIBUTE, null);
                if (uriAttribute != null) {
                    URI uri = URI.createURI(uriAttribute);
                    EObject eObject = editingDomain.getResourceSet().getEObject(uri, true);
                    if (eObject != null) {
                        setSelectionToViewer(Collections.singleton(editingDomain.getWrapper(eObject)));
                    }
                }
            }
        }
        catch (CoreException exception) {
            logger.error(exception);
        }
    }

    /**
     * This is called during startup.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
        setSite(site);
        setInputWithNotify(editorInput);
        setPartName(editorInput.getName());
        site.setSelectionProvider(this);
        site.getPage().addPartListener(partListener);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setFocus() {
        if (currentViewerPane != null) {
            currentViewerPane.setFocus();
        }
        else {
            getControl(getActivePage()).setFocus();
        }
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionChangedListeners.add(listener);
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeSelectionChangedListener(ISelectionChangedListener listener) {
        selectionChangedListeners.remove(listener);
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to return this editor's overall selection.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ISelection getSelection() {
        return editorSelection;
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to set this editor's overall selection.
     * Calling this result will notify the listeners.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSelection(ISelection selection) {
        editorSelection = selection;

        for (ISelectionChangedListener listener : selectionChangedListeners) {
            listener.selectionChanged(new SelectionChangedEvent(this, selection));
        }
        setStatusLineManager(selection);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStatusLineManager(ISelection selection) {
        IStatusLineManager statusLineManager = currentViewer != null && currentViewer == contentOutlineViewer ?
                contentOutlineStatusLineManager : getActionBars().getStatusLineManager();

        if (statusLineManager != null) {
            if (selection instanceof IStructuredSelection) {
                Collection<?> collection = ((IStructuredSelection)selection).toList();
                switch (collection.size()) {
                    case 0: {
                        statusLineManager.setMessage("");
                        break;
                    }
                    case 1: {
//                        String text = new AdapterFactoryItemDelegator(adapterFactory).getText(collection.iterator().next());
                        statusLineManager.setMessage("_UI_SingleObjectSelected");
                        break;
                    }
                    default: {
                        statusLineManager.setMessage("_UI_MultiObjectSelected");
                        break;
                    }
                }
            }
            else {
                statusLineManager.setMessage("");
            }
        }
    }

    /**
     * This implements {@link org.eclipse.jface.action.IMenuListener} to help fill the context menus with contributions from the Edit menu.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void menuAboutToShow(IMenuManager menuManager) {
        ((IMenuListener)getEditorSite().getActionBarContributor()).menuAboutToShow(menuManager);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditingDomainActionBarContributor getActionBarContributor() {
        return (EditingDomainActionBarContributor)getEditorSite().getActionBarContributor();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IActionBars getActionBars() {
        return getActionBarContributor().getActionBars();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void dispose() {
        updateProblemIndication = false;

        ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);

        getSite().getPage().removePartListener(partListener);

        adapterFactory.dispose();

        if (getActionBarContributor().getActiveEditor() == this) {
            getActionBarContributor().setActiveEditor(null);
        }

        if (propertySheetPage != null) {
            propertySheetPage.dispose();
        }

        if (contentOutlinePage != null) {
            contentOutlinePage.dispose();
        }

        super.dispose();
    }

    /**
     * Returns whether the outline view should be presented to the user.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected boolean showOutlineView() {
        return true;
    }

    @Override
    protected void addPages() {

        // Creates the model from the editor input
        //
        try {
            createModel();
        } catch ( PartInitException e1 ) {
            throw new RuntimeException(e1.getMessage());
        }
        
        if (getEditorInput() instanceof IFileEditorInput) {
            IFileEditorInput finput = (IFileEditorInput) getEditorInput();
            activeProject=finput.getFile().getProject();
        }

        //Create the MoleculesPage
        molPage=new MoleculesPage(this, editingDomain);
        descPage=new DescriptorsPage(this, editingDomain);
        responsesPage=new ResponsesPage(this, editingDomain);
        overviewPage=new OverviewPage(this, editingDomain);
        informationPage=new InformationPage(this, editingDomain);
        
        try {
            //Overview page comes first with summary
            overviewPageIndex=addPage(overviewPage);

            //Molecules page with interactions
            infoPageIndex=addPage(informationPage);

            //Molecules page with interactions
            molPageIndex=addPage(molPage);

            //Descriptors page
            descPageIndex=addPage(descPage);

            //Descriptors page
            responsesPageIndex=addPage(responsesPage);

            xmlEditor = new XMLEditor();
            textEditorIndex = addPage(xmlEditor, getEditorInput());
            setPageText(textEditorIndex, "Source");
            
        } catch (PartInitException e) {
            LogUtils.debugTrace(logger, e);
        }

    }

    public IProject getActiveProject() {

        return activeProject;
    }


    public void setActiveProject( IProject activeProject ) {

        this.activeProject = activeProject;
    }

//    public void resourceChanged( IResourceChangeEvent event ) {
//
//        IResource resource=event.getResource();
//        
//        //Only interested in resources in this QSAR project
//        if (!(resource.getProject()==activeProject)) return;
//
//        //Check if this resource is linked to in model
//        for (ResourceType rt :getQsarModel().getStructurelist().getResources()){
//            if (rt.getFile().equals( resource.getFullPath().toOSString() )){
//                System.out.println("The included resource: " + resource.getName() + " was changed!");
//            }
//        }
//        
//        // TODO Auto-generated method stub
//        
//    }
//
    
//    public QsarType getQsarModel() {
//    
//        return qsarModel;
//    }
//
//    
//    public void setQsarModel( QsarType qsarModel ) {
//    
//        this.qsarModel = qsarModel;
//    }
}
