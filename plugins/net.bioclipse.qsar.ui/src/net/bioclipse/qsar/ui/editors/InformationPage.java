/*******************************************************************************
 * Copyright (c) 2009 Ola Spjuth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth - initial API and implementation
 ******************************************************************************/
package net.bioclipse.qsar.ui.editors;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;
import net.bioclipse.qsar.init.Activator;
import net.sf.bibtexml.BibtexmlPackage;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.editor.*;
import org.eclipse.ui.forms.widgets.*;


/**
 * 
 * @author ola
 *
 */
public class InformationPage extends FormPage implements IEditingDomainProvider, IPageChangedListener{

    private static final Logger logger = Logger.getLogger(InformationPage.class);

    private CTabFolder tabFolder;
    private AdapterFactoryEditingDomain editingDomain;
    private ScrolledForm form;
    private TableViewer unitViewer;
//    private TableViewer refViewer;
//    private Table refTable;

    class TextSection {
        String text;
        public TextSection(String text) {this.text = text;}
    }

    public InformationPage(QsarEditor editor,
                           AdapterFactoryEditingDomain editingDomain) {

        super(editor, "qsar.model.information", "Information");
        this.editingDomain=editingDomain;

        editor.addPageChangedListener(this);

    }

    protected void createFormContent(IManagedForm managedForm) {

        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Qsar Model Information");

        toolkit.decorateFormHeading(form.getForm());

        IProject project=((QsarEditor)getEditor()).getActiveProject();
        ToolbarHelper.setupToolbar(form, project, (QsarEditor)getEditor());

        //		form.setBackgroundImage(FormArticlePlugin.getDefault().getImage(
        //				FormArticlePlugin.IMG_FORM_BG));

        GridLayout layout = new GridLayout();
        layout.marginWidth = 10;
        form.getBody().setLayout(layout);
        tabFolder = new CTabFolder(form.getBody(), SWT.FLAT|SWT.TOP);
        toolkit.adapt(tabFolder, true, true);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 0;
        tabFolder.setLayoutData(gd);
        Color selectedColor = toolkit.getColors().getColor(IFormColors.SEPARATOR);
        tabFolder.setSelectionBackground(new Color[] {selectedColor, toolkit.getColors().getBackground()}, new int[] {50});
        //tabFolder.setCursor(FormsResources.getHandCursor());

        toolkit.paintBordersFor(tabFolder);

        //Add the tabs
        createGeneralTab(toolkit);

        //TODO: Update when implemented, as of now it does not work
        //        createReferenceTab( toolkit);
        
        createUnitsTab(toolkit);

        tabFolder.setSelection(0);
        
        //        PlatformUI.getWorkbench().getHelpSystem().setHelp(form.getBody(), IHelpContextIds.MANIFEST_FEATURE_INFO);
    }

    private void createGeneralTab(FormToolkit toolkit) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText( "General information" );

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();

        Composite tabContent = toolkit.createComposite(tabFolder);
        item.setControl( tabContent );
        tabContent.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        tabContent.setLayout(layout);
        layout.numColumns = 2;
        layout.marginWidth = 0;

        //Dataset Name
        //=============
        Label lblDatasetName = toolkit.createLabel( tabContent, "Dataset Name:", SWT.NONE);
        GridData gd2 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblDatasetName.setLayoutData(gd2);

        Text txtDatasetName = toolkit.createText(tabContent, "", SWT.MULTI|SWT.WRAP);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        txtDatasetName.setLayoutData(gd);
        gd.heightHint=16;
        
        // Bind to EMF
        DataBindingContext bindingContext = new DataBindingContext();
        bindingContext.bindValue(SWTObservables.observeText(txtDatasetName, SWT.Modify), 
                                 EMFEditObservables.observeValue(editingDomain,
                                 qsarModel.getMetadata(),
                                 QsarPackage.Literals.METADATA_TYPE__DATASETNAME),
                                 null, null);

        //Authors
        //=============
        Label lblAuthors = toolkit.createLabel( tabContent, "Author(s)", SWT.NONE);
        GridData gd3 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblAuthors.setLayoutData(gd3);

        Text txtAuthors = toolkit.createText(tabContent, "", SWT.MULTI|SWT.WRAP);
        GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
        txtAuthors.setLayoutData(gd4);
        gd4.heightHint=40;
        
        // Bind to EMF
        bindingContext.bindValue(SWTObservables.observeText(txtAuthors, SWT.Modify), 
                                 EMFEditObservables.observeValue(editingDomain,
                                 qsarModel.getMetadata(),
                                 QsarPackage.Literals.METADATA_TYPE__AUTHORS),
                                 null, null);

        //Description
        //=============
        Label lblDescription = toolkit.createLabel( tabContent, "Description:", SWT.NONE);
        GridData gd9 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblDescription.setLayoutData(gd9);

        Text txtDescription = toolkit.createText(tabContent, "", SWT.MULTI|SWT.WRAP);
        GridData gd10 = new GridData(GridData.FILL_HORIZONTAL);
        txtDescription.setLayoutData(gd10);
        gd10.heightHint=70;

        // Bind to EMF
        bindingContext.bindValue(SWTObservables.observeText(txtDescription, SWT.Modify), 
                                 EMFEditObservables.observeValue(editingDomain,
                                 qsarModel.getMetadata(),
                                 QsarPackage.Literals.METADATA_TYPE__DESCRIPTION),
                                 null, null);


        //URL
        //=============
        Label lblUrl = toolkit.createLabel( tabContent, "URL:", SWT.NONE);
        GridData gd5 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblUrl.setLayoutData(gd5);

        Text txtUrl = toolkit.createText(tabContent, "", SWT.MULTI|SWT.WRAP);
        GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
        txtUrl.setLayoutData(gd6);
        gd6.heightHint=16;

        // Bind to EMF
        bindingContext.bindValue(SWTObservables.observeText(txtUrl, SWT.Modify), 
                                 EMFEditObservables.observeValue(editingDomain,
                                 qsarModel.getMetadata(),
                                 QsarPackage.Literals.METADATA_TYPE__URL),
                                 null, null);

        //License
        //=============
        Label lblLicense = toolkit.createLabel( tabContent, "License:", SWT.NONE);
        GridData gd7 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblLicense.setLayoutData(gd7);

        Text txtLicense = toolkit.createText(tabContent, "", SWT.MULTI|SWT.WRAP);
        GridData gd8 = new GridData(GridData.FILL_BOTH);
        txtLicense.setLayoutData(gd8);
        
        // Bind to EMF
        bindingContext.bindValue(SWTObservables.observeText(txtLicense, SWT.Modify), 
                                 EMFEditObservables.observeValue(editingDomain,
                                 qsarModel.getMetadata(),
                                 QsarPackage.Literals.METADATA_TYPE__LICENSE),
                                 null, null);


    }

    private void createReferenceTab( FormToolkit toolkit ) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText( "References" );

        Composite tabContent = toolkit.createComposite(tabFolder);
        item.setControl( tabContent );
        tabContent.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        tabContent.setLayout(layout);
        layout.numColumns = 2;
        layout.marginWidth = 0;

        TableViewer refViewer = new TableViewer(tabContent, SWT.BORDER | SWT.MULTI);
        Table refTable = refViewer.getTable();
        toolkit.adapt(refTable, true, true);
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.verticalSpan=2;
        refTable.setLayoutData( gd );

        refTable.setHeaderVisible(true);
        //          molTable.setLinesVisible(true);
        toolkit.adapt(refTable, true, true);

        //Add name columns
        TableLayout tableLayout = new TableLayout();
        refTable.setLayout(tableLayout);
        TableViewerColumn ixcol=new TableViewerColumn(refViewer,SWT.BORDER);
        ixcol.getColumn().setText("Title");
        tableLayout.addColumnData(new ColumnPixelData(150));

        TableViewerColumn col=new TableViewerColumn(refViewer,SWT.BORDER);
        col.getColumn().setText("Authors");
        tableLayout.addColumnData(new ColumnPixelData(100));

        col=new TableViewerColumn(refViewer,SWT.BORDER);
        col.getColumn().setText("journal");
        tableLayout.addColumnData(new ColumnPixelData(100));
        
        col=new TableViewerColumn(refViewer,SWT.BORDER);
        col.getColumn().setText("Year");
        tableLayout.addColumnData(new ColumnPixelData(50));

        col=new TableViewerColumn(refViewer,SWT.BORDER);
        col.getColumn().setText("URL");
        tableLayout.addColumnData(new ColumnPixelData(100));

        Button btnAdd=toolkit.createButton(tabContent, "Add...", SWT.PUSH);
        btnAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
//                addRef();
            }
        });
        GridData gd2=new GridData();
        gd2.verticalAlignment=SWT.BEGINNING;
        gd2.widthHint=60;
        btnAdd.setLayoutData( gd2 );

        Button btnDel=toolkit.createButton(tabContent, "Remove", SWT.PUSH);
        btnDel.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
//                deleteRef();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnDel.setLayoutData( gd2 );

   
        
        // The content provider is responsible to handle add and
        // remove notification for the Person#address EList
        ObservableListContentProvider provider = new ObservableListContentProvider();
        refViewer.setContentProvider(provider);

        // The label provider in turn handles the addresses
        // The EStructuralFeature[] defines which fields get shown
        // in the TableViewer columns
        IObservableSet knownElements = provider.getKnownElements();
        IObservableMap[] observeMaps = EMFEditObservables.
        observeMaps(editingDomain, knownElements, new EStructuralFeature[]{
                BibtexmlPackage.Literals.ARTICLE_TYPE__AUTHOR,
                BibtexmlPackage.Literals.BIB_TE_XML_ENTRIES_CLASS__ARTICLE,
                BibtexmlPackage.Literals.BIB_TE_XML_ENTRY_TYPE__ID,
                BibtexmlPackage.Literals.ARTICLE_TYPE__YEAR,
                BibtexmlPackage.Literals.ARTICLE_TYPE__URL
                });
        ObservableMapLabelProvider labelProvider =
            new ObservableQSARLabelProvider(observeMaps);
        refViewer.setLabelProvider(labelProvider);

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        EObject entryList = qsarModel.getMetadata();

        // Set the Viewer's input
        refViewer.setInput(EMFEditObservables.observeList(Realm.getDefault(), editingDomain, entryList,
                                                          QsarPackage.Literals.METADATA_TYPE__REFERENCE));


    }
    private void createUnitsTab( FormToolkit toolkit ) {
        CTabItem item = new CTabItem(tabFolder, SWT.NULL);
        item.setText( "Response units" );

        Composite tabContent = toolkit.createComposite(tabFolder);
        item.setControl( tabContent );
        tabContent.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        tabContent.setLayout(layout);
        layout.numColumns = 2;
        layout.marginWidth = 0;

        unitViewer = new TableViewer(tabContent, SWT.BORDER | SWT.MULTI);
        Table unitTable = unitViewer.getTable();
        toolkit.adapt(unitTable, true, true);
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.verticalSpan=2;
        unitTable.setLayoutData( gd );

        unitTable.setHeaderVisible(true);
        //          molTable.setLinesVisible(true);
        toolkit.adapt(unitTable, true, true);

        //Add name columns
        TableLayout tableLayout = new TableLayout();
        unitTable.setLayout(tableLayout);
        TableViewerColumn ixcol=new TableViewerColumn(unitViewer,SWT.BORDER);
        ixcol.getColumn().setText("Name");
        tableLayout.addColumnData(new ColumnPixelData(150));

        TableViewerColumn col=new TableViewerColumn(unitViewer,SWT.BORDER);
        col.getColumn().setText("Shortname");
        tableLayout.addColumnData(new ColumnPixelData(100));

        col=new TableViewerColumn(unitViewer,SWT.BORDER);
        col.getColumn().setText("Description");
        tableLayout.addColumnData(new ColumnPixelData(100));
        
        col=new TableViewerColumn(unitViewer,SWT.BORDER);
        col.getColumn().setText("URL");
        tableLayout.addColumnData(new ColumnPixelData(150));

        Button btnAdd=toolkit.createButton(tabContent, "Add...", SWT.PUSH);
        btnAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                addUnit();
                unitViewer.refresh();
            }
        });
        GridData gd2=new GridData();
        gd2.verticalAlignment=SWT.BEGINNING;
        gd2.widthHint=60;
        btnAdd.setLayoutData( gd2 );

        Button btnDel=toolkit.createButton(tabContent, "Remove", SWT.PUSH);
        btnDel.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                deleteUnit();
            }
        });
        gd2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd2.widthHint=60;
        btnDel.setLayoutData( gd2 );

        
        unitTable.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
                //Delete key
                if (e.keyCode==SWT.DEL){
                    deleteUnit();
                }
            }
            public void keyReleased( KeyEvent e ) {
            }
        });

        
        
        // The content provider is responsible to handle add and
        // remove notification for the Person#address EList
        ObservableListContentProvider provider = new ObservableListContentProvider();
        unitViewer.setContentProvider(provider);

        // The label provider in turn handles the addresses
        // The EStructuralFeature[] defines which fields get shown
        // in the TableViewer columns
        IObservableSet knownElements = provider.getKnownElements();
        IObservableMap[] observeMaps = EMFEditObservables.
        observeMaps(editingDomain, knownElements, new EStructuralFeature[]{
                QsarPackage.Literals.RESPONSEUNIT_TYPE__NAME,
                QsarPackage.Literals.RESPONSEUNIT_TYPE__SHORTNAME,
                QsarPackage.Literals.RESPONSEUNIT_TYPE__DESCRIPTION,
                QsarPackage.Literals.RESPONSEUNIT_TYPE__URL
                });
        ObservableMapLabelProvider labelProvider =
            new ObservableQSARLabelProvider(observeMaps);
        unitViewer.setLabelProvider(labelProvider);

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();

        // Set the Viewer's input
        unitViewer.setInput(EMFEditObservables.observeList(Realm.getDefault(), editingDomain, qsarModel,
                                                          QsarPackage.Literals.QSAR_TYPE__RESPONSEUNIT));


    }


    protected void addUnit() {

        
        List<ResponseUnit> list = Activator.getDefault().getQsarManager().getFullResponseUnits();
        
        ListDialog dlg=new ListDialog(getSite().getShell());
        dlg.setContentProvider( new ArrayContentProvider() );
        dlg.setLabelProvider( new LabelProvider(){
            @Override
            public String getText( Object element ) {
                if ( element instanceof ResponseUnit ) {
                    ResponseUnit unit = (ResponseUnit) element;
                    return "" + unit.getShortname() + " - " + unit.getName();
                }
                return super.getText( element );
            }
            
        } );
        
        dlg.setInput( list );
        
        int res=dlg.open();
        if (res==Window.CANCEL) return;
        
        Object[] objs = dlg.getResult();
        if (objs==null) return;

//        System.out.println("Got back: " + objs.length + " selections");

        //Add selected units to model
        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        IQsarManager qsar = Activator.getDefault().getQsarManager();
        List<ResponseUnit> toAddList=new ArrayList<ResponseUnit>();
        for (Object u : objs){
            ResponseUnit newUnit =(ResponseUnit) u;
            toAddList.add( newUnit );
            logger.debug("Added new unit: " + newUnit + " to qsar model");
        }
        
        qsar.addResponseUnitToModel( qsarModel, editingDomain, toAddList );
        
    }
    
    
    protected void deleteUnit() {
        //Get selection
        IStructuredSelection sel=(IStructuredSelection) 
                                                      unitViewer.getSelection();
        
        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        IQsarManager qsar = Activator.getDefault().getQsarManager();
        List<ResponseunitType> toRemoveList=new ArrayList<ResponseunitType>();
        for (Object obj : sel.toList()){
            if ( obj instanceof ResponseunitType ) {
                ResponseunitType ru = (ResponseunitType) obj;
                toRemoveList.add( ru );
            }
        }
        
        if (toRemoveList.isEmpty()) return;
        
        //Delete it
        qsar.removeResponseUnitsFromModel( qsarModel, editingDomain, toRemoveList );
        
    }



    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    public void pageChanged( PageChangedEvent event ) {

    }
}
