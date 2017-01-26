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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.qsar.QSARConstants;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ResourceType;
import net.bioclipse.qsar.ResponseType;
import net.bioclipse.qsar.ResponsesListType;
import net.bioclipse.qsar.ResponseunitType;
import net.bioclipse.qsar.StructureType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.model.ResponseUnit;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


public class ResponsesPage extends FormPage implements IEditingDomainProvider, IViewerProvider, IPageChangedListener{

    private TableViewer responsesViewer;
    private Table responsesTable;

    private static final Logger logger = Logger.getLogger(MoleculesPage.class);

    ICDKManager cdk;
    DecimalFormat formatter;

    private EditingDomain editingDomain;
    private Action setAllResponsesAction;


    public ResponsesPage(FormEditor editor,
                         EditingDomain editingDomain) {

        super(editor, "qsar.responses", "Responses");
        this.editingDomain=editingDomain;

        //Get Managers via OSGI
        cdk=Activator.getDefault().getJavaCDKManager();


        //We need to ensure that '.' is always decimal separator in all locales
        DecimalFormatSymbols sym=new DecimalFormatSymbols();
        sym.setDecimalSeparator( '.' );
        formatter = new DecimalFormat("0.00", sym);

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        ResponsesListType responsesList = qsarModel.getResponselist();
        if (responsesList==null){
            responsesList=QsarFactory.eINSTANCE.createResponsesListType();
            qsarModel.setResponselist(responsesList);
        }

        editor.addPageChangedListener(this);

    }


    /**
     * Add content to form
     */
    @Override
    protected void createFormContent(IManagedForm managedForm) {

        final QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();

        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("QSAR responses");
        toolkit.decorateFormHeading(form.getForm());

        IProject project=((QsarEditor)getEditor()).getActiveProject();
        ToolbarHelper.setupToolbar(form, project, (QsarEditor)getEditor());

        GridLayout layout = new GridLayout();
        layout.numColumns=2;
        form.getBody().setLayout(layout);
        
        //Response Label
        Label lblResponseLabel = toolkit.createLabel( form.getBody(), "Response label:", SWT.NONE);
        GridData gd22 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblResponseLabel.setLayoutData(gd22);

        final Text txtResponseLabel = toolkit.createText(form.getBody(), "", SWT.MULTI|SWT.WRAP);
        GridData gd23 = new GridData();
        txtResponseLabel.setLayoutData(gd23);
        gd23.widthHint=200;
        gd23.heightHint=16;
//        txtResponseLabel.addModifyListener(new ModifyListener() {
//			
//			public void modifyText(ModifyEvent e) {
//                Command cmd=new SetCommand(editingDomain,qsarModel.getMetadata(),
//                		QsarPackage.Literals.METADATA_TYPE__RESPONSE_LABEL,
//                        txtResponseLabel.getText());
//                editingDomain.getCommandStack().execute(cmd);
//                responsesViewer.refresh();
//				
//			}
//		});
        
        
        DataBindingContext bindingContext = new DataBindingContext();
        bindingContext.bindValue(SWTObservables.observeText(txtResponseLabel, SWT.Modify), 
                EMFEditObservables.observeValue(editingDomain,
                qsarModel.getMetadata(),
                QsarPackage.Literals.METADATA_TYPE__RESPONSE_LABEL),
                null, null);
        
        //Response placement
        Label lblPlaceResponse = toolkit.createLabel( form.getBody(), "Response label placement in CSV:", SWT.NONE);
        GridData gd222 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lblPlaceResponse.setLayoutData(gd222);
        
        Combo cboPlaceResponse = new Combo( form.getBody(), SWT.NONE);
        GridData gd232 = new GridData();
        txtResponseLabel.setLayoutData(gd232);
        gd232.widthHint=200;
        gd232.heightHint=16;
        cboPlaceResponse.add("first");
        cboPlaceResponse.add("last");
        cboPlaceResponse.select(0);
        
        bindingContext.bindValue(SWTObservables.observeText(cboPlaceResponse), 
                EMFEditObservables.observeValue(editingDomain,
                qsarModel.getMetadata(),
                QsarPackage.Literals.METADATA_TYPE__RESPONSE_PLACEMENT),
                null, null);

        
//        cboPlaceResponse.addSelectionListener( new SelectionListener(){
//
//            public void widgetDefaultSelected( SelectionEvent e ) {
//            }
//
//            public void widgetSelected( SelectionEvent e ) {
//                Combo cbo=(Combo) e.getSource();
//                if (cbo.getSelectionIndex()==0){
//
//                	qsarModel.get
//                    
//                    Command cmd=new SetCommand(editingDomain,response,
//                                               QsarPackage.Literals.RESPONSE_TYPE__VALUE,
//                                               String.valueOf(value));
//                    editingDomain.getCommandStack().execute(cmd);
//                    responsesViewer.refresh();
//
//                    
//                }
//                else{
//                    //OFF
//                    QsarHelper.setAutoBuild( project, false );
//                }
//            }
//        });
        
        
        responsesViewer = new TableViewer(form.getBody(), SWT.BORDER
                                          | SWT.FULL_SELECTION );
        responsesTable=responsesViewer.getTable();
        GridData gd=new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan=2;
//        gd.widthHint=400;
        responsesTable.setLayoutData( gd );

        responsesTable.setHeaderVisible(true);
        toolkit.adapt(responsesTable, true, true);

        //Add providers columns
        TableLayout tableLayout = new TableLayout();
        responsesTable.setLayout(tableLayout);

        TableViewerColumn molCol=new TableViewerColumn(responsesViewer, SWT.NONE);
        molCol.getColumn().setText("Structure");
        tableLayout.addColumnData(new ColumnPixelData(250));
        molCol.setLabelProvider(new ColumnLabelProvider(){
            @Override
            public String getText(Object element) {
                ResponseType response = (ResponseType)element;
                return response.getStructureID();
            }
        });

        TableViewerColumn responseCol=new TableViewerColumn(responsesViewer, SWT.NONE);
        responseCol.getColumn().setText("Response");
        tableLayout.addColumnData(new ColumnPixelData(100));

        responseCol.setLabelProvider(new ColumnLabelProvider(){
            @Override
            public String getText(Object element) {
                ResponseType response = (ResponseType)element;
                    if (response.getValue().equals( QSARConstants.MISSING_VALUE_STRING  ))
                        return "";
                    else
                        return ""+response.getValue();
            }
        });

        responseCol.setEditingSupport(new EditingSupport(responsesViewer){
            private TextCellEditor cellEditor;

            @Override
            protected boolean canEdit(Object element) {
                return true;
            }

            @Override
            protected CellEditor getCellEditor(Object element) {
                if (cellEditor==null) cellEditor=new TextCellEditor(responsesTable);
                return cellEditor;
            }

            @Override
            protected Object getValue(Object element) {
                ResponseType response = (ResponseType)element;
                    if (response.getValue().equals( QSARConstants.MISSING_VALUE_STRING ))
                        return "";
                    else
                        return ""+response.getValue();
            }

            @Override
            protected void setValue(Object element, Object value) {
                ResponseType response = (ResponseType)element;
                
                Command cmd=new SetCommand(editingDomain,response,
                                           QsarPackage.Literals.RESPONSE_TYPE__VALUE,
                                           String.valueOf(value));
                editingDomain.getCommandStack().execute(cmd);
                responsesViewer.refresh();
            }

        });

        
        
        TableViewerColumn unitsCol=new TableViewerColumn(responsesViewer, SWT.NONE);
        unitsCol.getColumn().setText("Unit");
        tableLayout.addColumnData(new ColumnPixelData(100));

        unitsCol.setLabelProvider(new ColumnLabelProvider(){
            @Override
            public String getText(Object element) {
                ResponseType response = (ResponseType)element;
                    return response.getUnit();
            }
        });

        unitsCol.setEditingSupport(new EditingSupport(responsesViewer){
            private ComboBoxCellEditor cellEditor;

            @Override
            protected boolean canEdit(Object element) {
                return true;
            }

            @Override
            protected CellEditor getCellEditor(Object element) {
                
                //Get values from qsarmodel
                List<String> values=new ArrayList<String>();
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
                for (ResponseunitType resp : qsarModel.getResponseunit()){
                    values.add(resp.getShortname());
                }
                
                cellEditor=new ComboBoxCellEditor(
                                                 responsesTable, 
                                                 values.toArray(new String[0]), 
                                                 SWT.DROP_DOWN | SWT.READ_ONLY);
                
                return cellEditor;
            }

            @Override
            protected Object getValue(Object element) {
                ResponseType response = (ResponseType)element;

                //Build a list in same order as combocelleditor
                List<String> values=new ArrayList<String>();
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
                for (ResponseunitType resp : qsarModel.getResponseunit()){
                    values.add(resp.getId());
                }

                //Look up the integer for this in the combo
                int ix=values.indexOf( response.getUnit() );
                return new Integer(ix);
            }

            @Override
            protected void setValue(Object element, Object value) {
                ResponseType response = (ResponseType)element;

                //User selected this shortname
                int ix=(Integer)value;
                if (ix<0) return;
                
                //Build a list in same order as combocelleditor
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
                ResponseunitType resp = qsarModel.getResponseunit().get( ix );
                
                if (response.getUnit()!=null && response.getUnit().equals( resp.getId() )){
                    //Same selected, nothing to store
                    return;
                }

                Command cmd=new SetCommand(editingDomain,response,
                                           QsarPackage.Literals.RESPONSE_TYPE__UNIT,
                                           resp.getId());
                editingDomain.getCommandStack().execute(cmd);
                responsesViewer.refresh();
            }

        });
        
        

        responsesViewer.setContentProvider(new ArrayContentProvider());
        //        responsesViewer.setLabelProvider(new DescriptorLabelProvider());

        //Sort by name
        responsesViewer.setSorter(new ResponseSorter());

        responsesTable.addKeyListener( new KeyListener(){
            public void keyPressed( KeyEvent e ) {
                //Delete key
                if (e.keyCode==SWT.DEL){
                    deleteSelectedResponses();
                }

                //Space key, toggle selection
                if (e.keyCode==32){

//                    IStructuredSelection msel=(IStructuredSelection) responsesViewer.getSelection();
                    //TODO: implement

                }
            }
            public void keyReleased( KeyEvent e ) {
            }
        });

        /*
        Button btnAdd=toolkit.createButton(form.getBody(), "Import values... ", SWT.PUSH);
        btnAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                importResponses();
            }
        });
        GridData gda2=new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gda2.widthHint=100;
        btnAdd.setLayoutData( gda2 );
        
        */

        toolkit.paintBordersFor(form);

        synchronizeResponesWithModel();

        //Populate selected descriptors from the read qsar model 
        //		populateResponsesViewerFromModel();
        ResponsesListType responsesList = qsarModel.getResponselist();

        if (responsesList.eContents()!=null){
            responsesViewer.setInput(responsesList.eContents().toArray());
        }
        
        makeActions();
        hookContextMenu();

    }

    
    /**
     * The actions in the viewer
     */
    private void makeActions() {

        setAllResponsesAction=new Action("Set all responses...", 
                            net.bioclipse.qsar.ui.Activator.getImageDescriptor( "icons/error_co.gif" )) {
            @Override
            public void run() {
                
                //Open up units dialog with available units
                IQsarManager qsar = net.bioclipse.qsar.init.Activator
                                        .getDefault().getJavaQsarManager();
                List<ResponseUnit> list = qsar.getFullResponseUnits();
                
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
                if (objs==null || objs[0]==null) return;

                //It can only be one from ListDialog
                ResponseUnit newUnit =(ResponseUnit) objs[0];

                //Add selected units to model
                QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
                List<ResponseUnit> toAddList=new ArrayList<ResponseUnit>();
                toAddList.add( newUnit );
                
                qsar.addResponseUnitToModel( qsarModel, editingDomain, toAddList );
                
                //Ok, set this response for all responses in qsar model
                CompoundCommand ccmd=new CompoundCommand();
                for (ResponseType resp : qsarModel.getResponselist().getResponse()){

                    Command cmd=new SetCommand(editingDomain,resp,
                                               QsarPackage.Literals.RESPONSE_TYPE__UNIT,
                                               newUnit.getId());
                    ccmd.append( cmd );
                    
                }

                editingDomain.getCommandStack().execute(ccmd);

                responsesViewer.refresh();

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
                    manager.add(setAllResponsesAction);
            }

        });
        Menu menu = menuMgr.createContextMenu(responsesViewer.getControl());
        responsesViewer.getControl().setMenu(menu);
    }

    
    
    
    

    /**
     * Add a response for every structure.
     */
    private void synchronizeResponesWithModel() {

//        List<ICDKMolecule> allMolecules=new ArrayList<ICDKMolecule>();

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        ResponsesListType responsesList = qsarModel.getResponselist();

        //Loop over all resources
        for (ResourceType molres : qsarModel.getStructurelist().getResources()){

            //Loop over all structures in a resource
            for (StructureType structure : molres.getStructure()){

                boolean hasResponse=false;

                //Does this structure have a response already?
                for (ResponseType response : responsesList.getResponse()){
                    if (structure.getId().equals(response.getStructureID())){
                        logger.debug("Found an existing response for structure: " + structure.getId());
                        hasResponse=true;
                    }
                }

                if (!hasResponse){
                    //Create a new response and add to structure
                    ResponseType newResponse=QsarFactory.eINSTANCE.createResponseType();
                    newResponse.setStructureID( structure.getId());
                    newResponse.setValue(QSARConstants.MISSING_VALUE_STRING);
                    responsesList.getResponse().add(newResponse);
                    //Do not use command, this will fire dirty.
                    //Keep silent, if things do not change we will recreate
                    //these the next time.
                }
            }
        }

    }


    private void populateResponsesViewerFromModel() {

        //FIXME: is this unused for a reason?

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        ResponsesListType responsesList = qsarModel.getResponselist();
        responsesViewer.setInput(responsesList.eContents().toArray());

        /*		
		// The content provider is responsible to handle add and
		// remove notification for the Person#address EList
		ObservableListContentProvider provider = new ObservableListContentProvider();
		responsesViewer.setContentProvider(provider);

		// The label provider in turn handles the addresses
		// The EStructuralFeature[] defines which fields get shown
		// in the TableViewer columns
		IObservableSet knownElements = provider.getKnownElements();
		IObservableMap[] observeMaps = EMFEditObservables.
			observeMaps(editingDomain, knownElements, new EStructuralFeature[]{
					QsarPackage.Literals.DESCRIPTOR_TYPE__ID});
		ObservableMapLabelProvider labelProvider =
			new ObservableQSARLabelProvider(observeMaps);
		responsesViewer.setLabelProvider(labelProvider);

		// Person#addresses is the Viewer's input
		responsesViewer.setInput(EMFEditObservables.observeList(Realm.getDefault(), editingDomain, descriptorList,
			QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTOR));
         */
    }


    protected void importResponses() {

//        ImportResultsDialog dlg=new ImportResultsDialog(getSite().getShell());
        
        FileDialog dlg=new FileDialog(getSite().getShell(),SWT.OPEN);
        String path=((QsarEditor)getEditor()).getActiveProject().getRawLocation().toOSString();
        dlg.setFilterPath( path );
        dlg.setText( "Select a text file with column 1=StructureID and column2=response value" );
        dlg.open();

        String filepath=path+dlg.getFileName();
        
        logger.debug("Import file path is: " + filepath);

        Map<String, Float> values=new HashMap<String, Float>();
        
        //Read the file as csv or tsv
            BufferedReader r;
            try {
                r = new BufferedReader(new FileReader(filepath));
                String line=r.readLine();
                while(line!=null){
                    
                    StringTokenizer tk=new StringTokenizer(line);
                    if (tk.countTokens()==2) {
                        String structid=tk.nextToken();
                        String stringvalue=tk.nextToken();
                        try{
                            Float floatval=Float.parseFloat( stringvalue );
                            values.put(structid, floatval);
                        }catch (NumberFormatException e){
                            
                        }
                    }
                    line=r.readLine();
                }
            } catch ( FileNotFoundException e1 ) {
                e1.printStackTrace();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            
            logger.debug("=======================");
            logger.debug("Read values in file:");
            logger.debug("=======================");
            for (String sid : values.keySet()){
                logger.debug(sid +" - " + values.get( sid ));
            }
            logger.debug("=======================");

    }


    /**
     * Handle the case when users press the Remove button next to moleculeviewer
     * or presses the delete button on something
     */
    protected void deleteSelectedResponses() {

        logger.error("Delete responses not implemented");

        /*    	
    	IStructuredSelection ssel=(IStructuredSelection) rightViewer.getSelection();

    	CompoundCommand ccmd=new CompoundCommand();

    	//Collect commands from selection
    	for (Iterator<?> it=ssel.iterator(); it.hasNext();){

    		Object obj = it.next();

    		if (obj instanceof DescriptorType) {
				DescriptorType descType = (DescriptorType) obj;
				Command cmd=RemoveCommand.create(editingDomain, descriptorList, QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTOR, descType);
				ccmd.append(cmd);
			}

    	}

		//Execute the commands
		editingDomain.getCommandStack().execute(ccmd);
         */
    }

    private void showMessage(String message) {
        MessageDialog.openInformation( getSite().getShell(),
                                       "Information",
                                       message );
    }




    public void activatePage() {

        QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
        ResponsesListType responsesList = qsarModel.getResponselist();

        synchronizeResponesWithModel();
        
        if (responsesList.eContents()!=null){
            responsesViewer.setInput(responsesList.eContents().toArray());
        }

        responsesViewer.refresh();
        
    }

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }


    public Viewer getViewer() {
        return responsesViewer;
    }

    public void pageChanged(PageChangedEvent event) {

        if (event.getSelectedPage()!=this) return;

        activatePage();

    }


}
