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
package net.bioclipse.qsar.ui.editors;


import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ui.QsarHelper;
import net.bioclipse.qsar.ui.wizards.AddMoleculeFilesWizard;
import net.bioclipse.qsar.ui.wizards.ExportQsarWizard;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;


public class OverviewPage extends FormPage implements IEditingDomainProvider, IPageChangedListener{

    private static final Logger logger = Logger.getLogger(MoleculesPage.class);

    ICDKManager cdk;

    private EditingDomain editingDomain;

    private IProject project;

    private QsarEditor editor;

    private Label lblNumFiles;
    private Label lblNumStructures;
    private Label lblNumDescriptors;
    private Label lblNumResponses;
    private Label lblNumMissingResponses;

    private Label lblCalculationTime;

    private Label lblCalculationStatus;

    private Label lblDatasetName;

    private Label lblAuthors;

    private Combo cboAutobuild;

    public OverviewPage(FormEditor editor, 
                        EditingDomain editingDomain) {

        super(editor, "qsar.overview", "Overview");
        this.editingDomain=editingDomain;

        //Get Managers via OSGI
        cdk=Activator.getDefault().getJavaCDKManager();

        this.editor=(QsarEditor) editor;

        this.project=this.editor.getActiveProject();

        editor.addPageChangedListener(this);

    }


    /**
     * Add content to form
     */
    @Override
    protected void createFormContent(IManagedForm managedForm) {

        final ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("QSAR responses");
        toolkit.decorateFormHeading(form.getForm());

        project=((QsarEditor)getEditor()).getActiveProject();
        ToolbarHelper.setupToolbar(form, project, (QsarEditor)getEditor());

        TableWrapLayout layout=new TableWrapLayout();
        layout.numColumns=2;
        layout.makeColumnsEqualWidth=true;
        form.getBody().setLayout(layout);

        createInformationSection(form, toolkit);
        createMoleculesSection(form, toolkit);
        createDescriptorsSection(form, toolkit);
        createResponsesSection(form, toolkit);
        createLastBuildSection(form, toolkit);
        createExportSection(form, toolkit);

        toolkit.paintBordersFor(form);

        updateValues();

    }




    /**
     * Update the values based on the QSAR model
     */
    private void updateValues() {

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();

        //Resources and structures
        if (qsarModel.getStructurelist()!=null && 
                qsarModel.getStructurelist().getResources()!=null){
            int ndesc=qsarModel.getStructurelist().getResources().size();
            lblNumFiles.setText(""+ndesc);

            //Count total # structures
            int numStructures=0;
            for (ResourceType res : qsarModel.getStructurelist().getResources()){
                if (res.getNoMols()>0){
                    numStructures=numStructures+res.getNoMols();
                }
            }
            lblNumStructures.setText( ""+numStructures );
        }else{
            lblNumFiles.setText("N/A");
            lblNumStructures.setText("N/A");
        }

        //Descriptors
        if (qsarModel.getDescriptorlist()!=null && 
                qsarModel.getDescriptorlist().getDescriptors()!=null){
            int ndesc=qsarModel.getDescriptorlist().getDescriptors().size();
            lblNumDescriptors.setText(""+ndesc);
        }else{
            lblNumDescriptors.setText("N/A");
        }

        //Information
        if (qsarModel.getMetadata()!=null){
            if (qsarModel.getMetadata().getDatasetname()!=null){
                lblDatasetName.setText(qsarModel.getMetadata().getDatasetname());
            }else{
                lblDatasetName.setText("N/A");
            }
            if (qsarModel.getMetadata().getAuthors()!=null){
                lblAuthors.setText(qsarModel.getMetadata().getAuthors());
            }else{
                lblAuthors.setText("N/A");
            }
        }
        //Responses
        if (qsarModel.getResponselist()!=null && 
                qsarModel.getResponselist().getResponse()!=null){
            int ndesc=qsarModel.getResponselist().getResponse().size();
            lblNumResponses.setText(""+ndesc);

            //Count total # missing values
            int numMissing=0;
            for (ResponseType res : qsarModel.getResponselist().getResponse()){
                if (res.getValue().equals( QSARConstants.MISSING_VALUE_STRING )){
                    numMissing++;
                }
            }
            lblNumMissingResponses.setText( ""+numMissing );

        }else{
            lblNumResponses.setText("N/A");
            lblNumMissingResponses.setText("N/A");
        }

        //Should be either Calculating, finished, and finsished with <a>errors</a>
        lblCalculationStatus.setText(QsarHelper.getBuildStatus( project ));
        lblCalculationTime.setText(QsarHelper.getBuildTime( project ));
        
        
        if (QsarHelper.isAutoBuild( project ))
            cboAutobuild.select( 0 );
        else
            cboAutobuild.select( 1 );

    }


    private void createMoleculesSection(final ScrolledForm form,
                                        FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Molecules");
        molSection.setDescription("The molecules that are added to this QSAR analysis");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/chemstruct.png" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);
        
        Label lblMoltext=toolkit.createLabel(sectionClient, "Files in analysis:");
        GridData gdtxt=new GridData(GridData.FILL_VERTICAL);
        gdtxt.widthHint=100;
        lblMoltext.setLayoutData(gdtxt);

        lblNumFiles=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblNumFiles.setLayoutData(gdtxt2);

        Label lblMolRestext=toolkit.createLabel(sectionClient, "Structures in analysis:");
        GridData gdtxtres=new GridData(GridData.FILL_VERTICAL);
        lblMolRestext.setLayoutData(gdtxtres);

        lblNumStructures=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2res=new GridData(GridData.FILL_BOTH);
        lblNumStructures.setLayoutData(gdtxt2res);

        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Edit molecules...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                editor.setActivePage("qsar.molecules");
            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=2;
        link.setLayoutData(gd);
    }

    private void createDescriptorsSection(final ScrolledForm form,
                                          FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Descriptors");
        molSection.setDescription("The selected descriptors in this analysis");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/3dmol.jpg" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Descriptors:");
        GridData gdtxt=new GridData(GridData.FILL_VERTICAL);
        gdtxt.widthHint=100;
        lblMoltext.setLayoutData(gdtxt);

        lblNumDescriptors=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblNumDescriptors.setLayoutData(gdtxt2);

        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Edit descriptors...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                editor.setActivePage("qsar.descriptors");
            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=2;
        link.setLayoutData(gd);
    }


    private void createResponsesSection(final ScrolledForm form,
                                        FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Responses");
        molSection.setDescription("The biological responses in this analysis");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/chemistry.png" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Responses:");
        GridData gdtxt=new GridData(GridData.FILL_VERTICAL);
        gdtxt.widthHint=100;
        lblMoltext.setLayoutData(gdtxt);

        lblNumResponses=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblNumResponses.setLayoutData(gdtxt2);

        Label lblMolErrorText=toolkit.createLabel(sectionClient, "Missing responses: ");
        GridData gdtxt3=new GridData(GridData.FILL_VERTICAL);
        lblMolErrorText.setLayoutData(gdtxt3);

        lblNumMissingResponses=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt4=new GridData(GridData.FILL_BOTH);
        lblNumMissingResponses.setLayoutData(gdtxt4);

        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Edit responses...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                editor.setActivePage("qsar.responses");
            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=2;
        link.setLayoutData(gd);
    }


    private void createLastBuildSection(final ScrolledForm form, FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Last build");
        molSection.setDescription("Results from the last build");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/wheel.png" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);
        
        Label lblMoltext51=toolkit.createLabel(sectionClient, "Status");
        GridData gdtxt51=new GridData(GridData.FILL_VERTICAL);
        gdtxt51.widthHint=100;
        lblMoltext51.setLayoutData(gdtxt51);

        lblCalculationStatus=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt76=new GridData(GridData.FILL_BOTH);
        lblCalculationStatus.setLayoutData(gdtxt76);

        Label lblMoltext5=toolkit.createLabel(sectionClient, "Calculation time:");
        GridData gdtxt5=new GridData(GridData.FILL_VERTICAL);
        gdtxt5.verticalAlignment=GridData.CENTER;
        lblMoltext5.setLayoutData(gdtxt5);

        lblCalculationTime=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt7=new GridData(GridData.FILL_BOTH);
        gdtxt7.verticalAlignment=GridData.CENTER;
        lblCalculationTime.setLayoutData(gdtxt7);

        Label lblAutobuild=toolkit.createLabel(sectionClient, "Autobuild is: ");
        GridData gdtxt=new GridData(GridData.FILL_VERTICAL);
        gdtxt.verticalAlignment=GridData.CENTER;
        lblAutobuild.setLayoutData(gdtxt);
        
        cboAutobuild = new Combo(sectionClient, SWT.NONE);
        GridData gdAutoBuild=new GridData();
        gdAutoBuild.widthHint=70;
        cboAutobuild.setLayoutData(gdAutoBuild);
        cboAutobuild.add( "ON" );
        cboAutobuild.add( "OFF" );
        
        cboAutobuild.addSelectionListener( new SelectionListener(){

            public void widgetDefaultSelected( SelectionEvent e ) {
            }

            public void widgetSelected( SelectionEvent e ) {
                Combo cbo=(Combo) e.getSource();
                if (cbo.getSelectionIndex()==0){
                    //ON
                    QsarHelper.setAutoBuild( project, true );
                }
                else{
                    //OFF
                    QsarHelper.setAutoBuild( project, false );
                }
            }
        });


        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Trigger full build...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                
                //Save editor
                if (editor.isDirty()){

                    boolean res = MessageDialog.openConfirm( form.getShell(), 
                                               "Confirm save", "There are changes in the " +
                                                   "QSAR editor that must " +
                                               "be saved before a full build can " +
                                               "take place.\n\n" +
                                               "OK to save?" );
                    if (res==false)
                        return;
                    else{
                        editor.doSave( new NullProgressMonitor() );
                    }
                }
                
                //Make all dirty
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
                QsarHelper.setAllDirty(qsarModel, project);

                //Start build job
                WorkspaceJob job = new WorkspaceJob("Building qsar project"){

                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor)
                    throws CoreException {

                        project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
                        return Status.OK_STATUS;
                    }

                };

                job.setUser(true);
                job.schedule();

            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=3;
        link.setLayoutData(gd);

    }

    private void createExportSection(final ScrolledForm form, FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Export");
        		molSection.setDescription("Export the project to allow for " +
        				"exchanging of complete datasets.");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/archiver48.png" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);

//        //Hyperlink to export QSAR.ML
//        Hyperlink link = toolkit.createHyperlink(sectionClient,"Export QSAR-ML", SWT.WRAP);
//        link.addHyperlinkListener(new HyperlinkAdapter() {
//            public void linkActivated(HyperlinkEvent e) {
//                showMessage("Not implemented");
//            }
//        });
//        GridData gd=new GridData(GridData.FILL_BOTH);
//        link.setLayoutData(gd);

//        Label lblMolErrorText=toolkit.createLabel(sectionClient, "");
//        GridData gdtxt3=new GridData(GridData.FILL_BOTH);
//        lblMolErrorText.setLayoutData(gdtxt3);


        //Hyperlink to export QSAR project
        Hyperlink link2 = toolkit.createHyperlink(sectionClient,"Export QSAR project", SWT.WRAP);
        link2.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                
                ExportQsarWizard wizard = new ExportQsarWizard();
                StructuredSelection sel = new StructuredSelection(((QsarEditor)getEditor()).getActiveProject());
                wizard.init( getSite().getWorkbenchWindow().getWorkbench(), sel);
                
                // Instantiates the wizard container with the wizard and opens it
                WizardDialog dialog = new WizardDialog(getSite().getShell(), wizard);
                dialog.create();
                int r = dialog.open();
                if (r==Window.CANCEL){
                }
            }
        });
        GridData gd2=new GridData(GridData.FILL_BOTH);
        gd2.verticalAlignment=GridData.CENTER;
        link2.setLayoutData(gd2);

        Label lblMolErrorText2=toolkit.createLabel(sectionClient, "", SWT.WRAP);

        GridData gdtxt32=new GridData(GridData.FILL_BOTH);
        lblMolErrorText2.setLayoutData(gdtxt32);
        
    }

    private void createInformationSection(final ScrolledForm form, FormToolkit toolkit) {

        Section molSection =
            toolkit.createSection(
                                  form.getBody(), Section.TWISTIE | Section.DESCRIPTION | 
                                  Section.EXPANDED | Section.TITLE_BAR);
        molSection.setActiveToggleColor(
                                        toolkit.getHyperlinkGroup().getActiveForeground());
        molSection.setToggleColor(
                                  toolkit.getColors().getColor(IFormColors.SEPARATOR));
        toolkit.createCompositeSeparator(molSection);


        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
        molSection.setLayoutData(td);

        molSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        molSection.setText("Information");
        molSection.setDescription("Metadata about the datase");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(3,false));
        molSection.setClient(sectionClient);
        
        final Image consensusImage=net.bioclipse.qsar.ui.Activator
                        .getImageDescriptor( "icons48/info.jpg" ).createImage();
        Canvas consensusCanvas = new Canvas(sectionClient,SWT.NO_REDRAW_RESIZE);
        consensusCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
             e.gc.drawImage(consensusImage,0,1);
            }
        });
        GridData gdIm=new GridData(GridData.FILL_VERTICAL);
        gdIm.verticalSpan=3;
        gdIm.widthHint=60;
        consensusCanvas.setLayoutData(gdIm);


        Label lblMoltext=toolkit.createLabel(sectionClient, "Dataset name:");
        GridData gdtxt=new GridData(GridData.FILL_VERTICAL);
        lblMoltext.setLayoutData(gdtxt);

        lblDatasetName=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblDatasetName.setLayoutData(gdtxt2);

        Label lblMolRestext=toolkit.createLabel(sectionClient, "Authors:");
        GridData gdtxtres=new GridData(GridData.FILL_VERTICAL);
        gdtxtres.widthHint=100;
        lblMolRestext.setLayoutData(gdtxtres);

        lblAuthors=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2res=new GridData(GridData.FILL_BOTH);
        lblAuthors.setLayoutData(gdtxt2res);

        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Edit information...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                editor.setActivePage("qsar.model.information");
            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=2;
        link.setLayoutData(gd);

    }


    private void showMessage(String message) {
        MessageDialog.openInformation( getSite().getShell(),
                                       "Information",
                                       message );
    }

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    public void pageChanged( PageChangedEvent event ) {

        if (event.getSelectedPage()!=this) return;
        updateValues();

    }


}
