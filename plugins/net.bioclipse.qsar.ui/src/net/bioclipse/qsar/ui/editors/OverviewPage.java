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

import java.net.MalformedURLException;
import java.net.URL;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ui.QsarHelper;
import net.bioclipse.qsar.ui.birt.BirtHelper;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
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
    private Label lblDatasetRows;
    private Label lblDatasetColumns;

    private Label lblCalculationTime;

    private Label lblCalculationStatus;

    private Label lblDatasetName;

    private Label lblAuthors;

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
        ToolbarHelper.setupToolbar(form, project);

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

        lblDatasetRows.setText("N/A");
        lblDatasetColumns.setText("N/A");

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
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Files in analysis:");
        GridData gdtxt=new GridData(GridData.FILL_BOTH);
        lblMoltext.setLayoutData(gdtxt);

        lblNumFiles=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblNumFiles.setLayoutData(gdtxt2);

        Label lblMolRestext=toolkit.createLabel(sectionClient, "Structures in analysis:");
        GridData gdtxtres=new GridData(GridData.FILL_BOTH);
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
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Descriptors:");
        GridData gdtxt=new GridData(GridData.FILL_BOTH);
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
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Responses:");
        GridData gdtxt=new GridData(GridData.FILL_BOTH);
        lblMoltext.setLayoutData(gdtxt);

        lblNumResponses=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblNumResponses.setLayoutData(gdtxt2);

        Label lblMolErrorText=toolkit.createLabel(sectionClient, "Missing responses: ");
        GridData gdtxt3=new GridData(GridData.FILL_BOTH);
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
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        Label lblMoltext51=toolkit.createLabel(sectionClient, "Status");
        GridData gdtxt51=new GridData(GridData.FILL_BOTH);
        lblMoltext51.setLayoutData(gdtxt51);

        lblCalculationStatus=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt76=new GridData(GridData.FILL_BOTH);
        lblCalculationStatus.setLayoutData(gdtxt76);

        Label lblMoltext5=toolkit.createLabel(sectionClient, "Calculation time:");
        GridData gdtxt5=new GridData(GridData.FILL_BOTH);
        lblMoltext5.setLayoutData(gdtxt5);

        lblCalculationTime=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt7=new GridData(GridData.FILL_BOTH);
        lblCalculationTime.setLayoutData(gdtxt7);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Dataset # rows:");
        GridData gdtxt=new GridData(GridData.FILL_BOTH);
        lblMoltext.setLayoutData(gdtxt);

        lblDatasetRows=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblDatasetRows.setLayoutData(gdtxt2);

        Label lblMolErrorText=toolkit.createLabel(sectionClient, "Dataset # columns: ");
        GridData gdtxt3=new GridData(GridData.FILL_BOTH);
        lblMolErrorText.setLayoutData(gdtxt3);

        lblDatasetColumns=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt4=new GridData(GridData.FILL_BOTH);
        lblDatasetColumns.setLayoutData(gdtxt4);

        //Hyperlink to build
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Trigger full build...", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {

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
        gd.horizontalSpan=2;
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
        //		molSection.setDescription("Export the project az an archive to allow for " +
        //				"exchanging of complete dataset formation.");

        Composite sectionClient = toolkit.createComposite(molSection);
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        //Hyperlink to export QSAR.ML
        Hyperlink link = toolkit.createHyperlink(sectionClient,"Export QSAR-ML", SWT.WRAP);
        link.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                showMessage("Not implemented");
            }
        });
        GridData gd=new GridData(GridData.FILL_BOTH);
        link.setLayoutData(gd);

        Label lblMolErrorText=toolkit.createLabel(sectionClient, "");
        GridData gdtxt3=new GridData(GridData.FILL_BOTH);
        lblMolErrorText.setLayoutData(gdtxt3);


        //Hyperlink to export QSAR project
        Hyperlink link2 = toolkit.createHyperlink(sectionClient,"Export QSAR project", SWT.WRAP);
        link2.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                showMessage("Not implemented");
            }
        });
        GridData gd2=new GridData(GridData.FILL_BOTH);
        link2.setLayoutData(gd2);

        Label lblMolErrorText2=toolkit.createLabel(sectionClient, "", SWT.WRAP);

        GridData gdtxt32=new GridData(GridData.FILL_BOTH);
        lblMolErrorText2.setLayoutData(gdtxt32);
        
        //Hyperlink to open report
        Hyperlink linkReport = toolkit.createHyperlink(sectionClient,"Create Report", SWT.WRAP);
        linkReport.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                
                //Generate and open BIRT report
                
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
//                BirtHelper.openReportInBrowser(qsarModel);
                BirtHelper.openScriptedReportInBrowser2( qsarModel );
                
            }
        });
        GridData gdReport=new GridData(GridData.FILL_BOTH);
        linkReport.setLayoutData(gdReport);

        Label lblReport=toolkit.createLabel(sectionClient, "");

        GridData gdReport2=new GridData(GridData.FILL_BOTH);
        lblReport.setLayoutData(gdReport2);


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
        sectionClient.setLayout(new GridLayout(2,false));
        molSection.setClient(sectionClient);

        Label lblMoltext=toolkit.createLabel(sectionClient, "Dataset name:");
        GridData gdtxt=new GridData(GridData.FILL_BOTH);
        lblMoltext.setLayoutData(gdtxt);

        lblDatasetName=toolkit.createLabel(sectionClient, "N/A");
        GridData gdtxt2=new GridData(GridData.FILL_BOTH);
        lblDatasetName.setLayoutData(gdtxt2);

        Label lblMolRestext=toolkit.createLabel(sectionClient, "Authors:");
        GridData gdtxtres=new GridData(GridData.FILL_BOTH);
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
