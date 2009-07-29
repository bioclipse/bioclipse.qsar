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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.qsar.DescriptorType;
import net.bioclipse.qsar.DescriptorlistType;
import net.bioclipse.qsar.DescriptorproviderType;
import net.bioclipse.qsar.ParameterType;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.business.IQsarManager;
import net.bioclipse.qsar.descriptor.model.Descriptor;
import net.bioclipse.qsar.descriptor.model.DescriptorImpl;
import net.bioclipse.qsar.descriptor.model.DescriptorModel;
import net.bioclipse.qsar.descriptor.model.DescriptorProvider;
import net.bioclipse.qsar.ui.QsarHelper;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class DescriptorsPage extends FormPage implements IEditingDomainProvider, IViewerProvider, IPageChangedListener{

    private TreeViewer descViewer;
    private Tree descTree;

    private TableViewer rightViewer;
    private Table rightTable;

    private static final Logger logger = Logger.getLogger(DescriptorsPage.class);
    
    ICDKManager cdk;
    DecimalFormat formatter;
    
	private EditingDomain editingDomain;

	/**
	 * This list of Descriptors in the qsar model. Also used as input to 
	 * rightViewer, containing the selected descrriptors
	 */
//	private DescriptorlistType descriptorList;
	
	IQsarManager qsar;
	
	private OnlyWithImplFilter onlyWithImplFilter = new OnlyWithImplFilter();

//	private EList<DescriptorimplType> providerList;
//	private QsarType qsarModel;
private TableViewer paramsViewer;
private Table paramsTable;

//	private List<DescriptorInstance> selectedDescriptors;

    
	public DescriptorsPage(FormEditor editor, 
			EditingDomain editingDomain) {

		super(editor, "qsar.descriptors", "Descriptors");
		this.editingDomain=editingDomain;

		//Get Managers via OSGI
        qsar=net.bioclipse.qsar.init.Activator.getDefault().getQsarManager();
		cdk=Activator.getDefault().getJavaCDKManager();

    //We need to ensure that '.' is always decimal separator in all locales
    DecimalFormatSymbols sym=new DecimalFormatSymbols();
    sym.setDecimalSeparator( '.' );
    formatter = new DecimalFormat("0.00", sym);
        
		editor.addPageChangedListener(this);

	}


    /**
     * Add content to form
     */
    @Override
    protected void createFormContent(IManagedForm managedForm) {

        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Descriptors for QSAR analysis");
        toolkit.decorateFormHeading(form.getForm());
        
		IProject project=((QsarEditor)getEditor()).getActiveProject();
    ToolbarHelper.setupToolbar(form, project, (QsarEditor)getEditor());
        
//        setupToolbar(form);
        
//        form.setBackgroundImage(FormArticlePlugin.getDefault().getImage(FormArticlePlugin.IMG_FORM_BG));
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        form.getBody().setLayout(layout);
        createDescriptorSection(form, toolkit);
        createRightSection(form, toolkit);

		descViewer.setInput(new PendingObject());
		descViewer.refresh();

		//Populate descriptorViewer from qsar model
		descViewer.getTree().getDisplay().asyncExec(new Runnable() {

			public void run() {
				DescriptorModel descModel=qsar.getModel();

				//Set descriptor model as input object to viewer
				descViewer.setInput(descModel);
			}
		});

		//Populate selected descriptors from the read qsar model 
		populateRightViewerFromModel();

        //Post selections to Eclipse via our intermediate selectionprovider
        descViewer.getTree().setFocus();
        
    }

    private void populateRightViewerFromModel() {

        
        
        
        
        // The content provider is responsible to handle add and
        // remove notification for the Person#address EList
		ObservableListContentProvider provider = new ObservableListContentProvider();
		rightViewer.setContentProvider(provider);

		// The label provider in turn handles the addresses
		// The EStructuralFeature[] defines which fields get shown
		// in the TableViewer columns
		IObservableSet knownElements = provider.getKnownElements();
		IObservableMap[] observeMaps = EMFEditObservables.
			observeMaps(editingDomain, knownElements, new EStructuralFeature[]{
		          QsarPackage.Literals.DESCRIPTOR_TYPE__ONTOLOGYID,
		          QsarPackage.Literals.DESCRIPTOR_TYPE__PROVIDER});
//		ObservableMapLabelProvider labelProvider =
//			new ObservableQSARLabelProvider(observeMaps);
//		rightViewer.setLabelProvider(labelProvider);

		DescriptorlistType descriptorList = getDescriptorListFromModel();

		// Person#addresses is the Viewer's input
		rightViewer.setInput(EMFEditObservables.observeList(Realm.getDefault(), editingDomain, descriptorList,
			QsarPackage.Literals.DESCRIPTORLIST_TYPE__DESCRIPTORS));

	}


    private DescriptorlistType getDescriptorListFromModel() {

        //Get descriptorList from qsar model, init if empty (should not be)
		QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
		DescriptorlistType descriptorList = qsarModel.getDescriptorlist();
		if (descriptorList==null){
		    descriptorList=QsarFactory.eINSTANCE.createDescriptorlistType();
		    qsarModel.setDescriptorlist(descriptorList);
		}
        return descriptorList;
    }


	/**
     * Create the left part of the page for displaying molecules
     * @param form
     * @param toolkit
     */
    private void createDescriptorSection( final ScrolledForm form,
                                         FormToolkit toolkit) {

        Section descSection =
            toolkit.createSection(
              form.getBody(),
              Section.TWISTIE | Section.DESCRIPTION);
          descSection.setActiveToggleColor(
            toolkit.getHyperlinkGroup().getActiveForeground());
          descSection.setToggleColor(
            toolkit.getColors().getColor(IFormColors.SEPARATOR));
          toolkit.createCompositeSeparator(descSection);
          Composite client = toolkit.createComposite(descSection, SWT.WRAP);
          GridLayout layout = new GridLayout();
          layout.numColumns = 2;
          client.setLayout(layout);
          
          final Button btnOnlyImpl=toolkit.createButton(client, "Display only with implementation", SWT.CHECK);
          btnOnlyImpl.setSelection(true);
          btnOnlyImpl.addSelectionListener(new SelectionAdapter(){
        	  @Override
        	public void widgetSelected(SelectionEvent e) {
        		  if (btnOnlyImpl.getSelection()==true){
        			  descViewer.addFilter(onlyWithImplFilter);
        		  }
        		  else if (btnOnlyImpl.getSelection()==false){
        			  descViewer.removeFilter(onlyWithImplFilter);
        		  }
        	}
        	  
          });
          GridData gdBTN=new GridData(GridData.FILL_HORIZONTAL);
          gdBTN.horizontalSpan=2;
          btnOnlyImpl.setLayoutData( gdBTN );

          
          PatternFilter patternFilter = new PatternFilter();
          patternFilter.setIncludeLeadingWildcard(true);
          final FilteredTree filter = new FilteredTree(client, SWT.MULTI
                  | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER , patternFilter);
          descViewer = filter.getViewer();


//          descViewer = new TreeViewer(client, SWT.BORDER | SWT.MULTI );
          descTree=descViewer.getTree();
          toolkit.adapt(descTree, true, true);
          GridData gd=new GridData(GridData.FILL_BOTH);
          gd.widthHint=250;
          descTree.setLayoutData( gd );
          
          descTree.setHeaderVisible(true);
//          molTable.setLinesVisible(true);
          toolkit.adapt(descTree, true, true);
          
          //Add providers columns
          TableLayout tableLayout = new TableLayout();
          descTree.setLayout(tableLayout);
          
          TreeViewerColumn firstCol=new TreeViewerColumn(descViewer, SWT.NONE);
          firstCol.getColumn().setText("");
          tableLayout.addColumnData(new ColumnPixelData(200));
          
          TreeViewerColumn providersCol=new TreeViewerColumn(descViewer, SWT.NONE);
          providersCol.getColumn().setText("Provider(s)");
          tableLayout.addColumnData(new ColumnPixelData(100));
          
          descViewer.setContentProvider( new DescriptorContentProvider());
          descViewer.setLabelProvider( new DescriptorLabelProvider() );
          descViewer.setUseHashlookup(true);
          
          //Sort by name
          descViewer.setSorter(new ViewerSorter());

          //Default is to only show with impl (checkbox is selected!)
          descViewer.addFilter(onlyWithImplFilter);
          
      	//If focus gained, make this viewer provide selections
          descViewer.getTree().addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
		        rightViewer.setSelection(null);
			}

			public void focusLost(FocusEvent e) {
			}
          });
          
          descTree.addKeyListener( new KeyListener(){
              public void keyPressed( KeyEvent e ) {
                  //Delete key
                  if (e.keyCode==SWT.DEL){
                      deleteSelectedDescriptors();
                  }
                  
                  //Space key, toggle selection
                  if (e.keyCode==32){

//                	  IStructuredSelection msel=(IStructuredSelection) descViewer.getSelection();
                      //TODO: implement
                      
                  }
              }
              public void keyReleased( KeyEvent e ) {
              }
          });

          //Create composite centered vertically and add buttons to it
          Composite comp = toolkit.createComposite(client, SWT.WRAP);
          comp.setLayout(new GridLayout());
          GridData gd2=new GridData(GridData.VERTICAL_ALIGN_CENTER);
          comp.setLayoutData( gd2 );

          Button btnAdd=toolkit.createButton(comp, " >> ", SWT.PUSH);
          btnAdd.addListener(SWT.Selection, new Listener() {
              public void handleEvent(Event e) {
                  addSelectedDescriptors();
              }
            });
          GridData gda2=new GridData(GridData.VERTICAL_ALIGN_CENTER);
          gda2.widthHint=60;
          btnAdd.setLayoutData( gda2 );

          Button btnDel=toolkit.createButton(comp, " << ", SWT.PUSH);
          btnDel.addListener(SWT.Selection, new Listener() {
              public void handleEvent(Event e) {
                  deleteSelectedDescriptors();
              }
            });
          GridData gd21=new GridData(GridData.VERTICAL_ALIGN_CENTER);
          gd21.widthHint=60;
          btnDel.setLayoutData( gd21 );

          
          //Wrap up section
          toolkit.paintBordersFor(client);
          descSection.setText("Avaliable descriptors");
          descSection.setDescription("Descriptors available for QSAR analysis");
          descSection.setClient(client);
          descSection.setExpanded(true);
          descSection.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
              form.reflow(false);
            }
          });

          GridData gd122 = new GridData(GridData.FILL_BOTH);
          descSection.setLayoutData(gd122);        
          
    }
    


    /**
     * Handle the case when users press the ADD button next to moleculeviewer
     */
    protected void addSelectedDescriptors() {

    	List<String> errorList=new ArrayList<String>();
    	
    	IStructuredSelection ssel=(IStructuredSelection) descViewer.getSelection();
    	for (Object obj : ssel.toList()){
    		
    		if (obj instanceof Descriptor) {
				Descriptor desc = (Descriptor) obj;
				
				//Find out impl
//				DescriptorImpl impl2 = qsar.getDescriptorByID(desc.getId());
				DescriptorImpl impl = qsar.getPreferredImpl(desc.getId());
				if (impl!=null){

				    QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
				    qsar.addDescriptorToModel(qsarModel, editingDomain, desc, impl);
					
				}else{
					errorList.add("No implementation available for descriptor: " + desc);
				}
			}
    	}
    	
    	if (errorList.size()>0){
    		String errormsgs="The following errors occured:\n\n";
    		for (String str : errorList){
    			errormsgs=errormsgs+str+"\n";
    		}
    	}

//    	rightViewer.setInput(descriptorList.eContents().toArray());

    }




	/**
     * Handle the case when users press the Remove button next to moleculeviewer
     * or presses the delete button on something
     */
    protected void deleteSelectedDescriptors() {
    	
    	IStructuredSelection ssel=(IStructuredSelection) rightViewer.getSelection();
    	
      QsarType qsarModel = ((QsarEditor)getEditor()).getQsarModel();
      qsar.removeDescriptorsFromModel(qsarModel, editingDomain, ssel.toList());

    }

    private void showMessage(String message) {
        MessageDialog.openInformation( getSite().getShell(),
                                       "Information",
                                       message );
    }
    
    
    private void createRightSection( final ScrolledForm form,
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
    	layout.numColumns = 1;
    	client.setLayout(layout);
    	
    	//Right viewer
    	//=================
    	rightViewer = new TableViewer (client, SWT.BORDER | SWT.MULTI);

    	rightTable=rightViewer.getTable();
    	toolkit.adapt(rightTable, true, true);
    	GridData gd6=new GridData(GridData.FILL_BOTH);
    	gd6.widthHint=100;
    	rightTable.setLayoutData( gd6 );
    	
    	rightTable.setHeaderVisible(true);
    	rightTable.setLinesVisible(true);
    	
    	
        //Add providers columns
        TableLayout tableLayout = new TableLayout();
        rightTable.setLayout(tableLayout);
        
        TableViewerColumn descCol=new TableViewerColumn(rightViewer, SWT.H_SCROLL | SWT.V_SCROLL);
        descCol.getColumn().setText("Descriptor");
        tableLayout.addColumnData(new ColumnPixelData(150));
        descCol.setLabelProvider(new ColumnLabelProvider(){
          @Override
          public String getText(Object element) {
              DescriptorType desc=(DescriptorType)element;
              String label=desc.getOntologyid();

              if (element instanceof DescriptorType) {
                  if (label.indexOf('#')>0){
                    label=label.substring(label.lastIndexOf('#')+1, label.length());
                  }

                  String cpstr="";
                  if (desc.getParameter()!=null && desc.getParameter().size()>0){
                    for (ParameterType param : desc.getParameter()){
                      String pstr=param.getKey() + "=" + param.getValue()+", ";
                      cpstr=cpstr+pstr;
                    }
                    cpstr=cpstr.substring(0,cpstr.length()-2);
                  }
                  
                  if (cpstr.length()>1){
                    label=label + " [" + cpstr + "]";
                  }

                }

                return label;
          }
        });

        
        
        TableViewerColumn provCol=new TableViewerColumn(rightViewer, SWT.NONE);
        provCol.getColumn().setText("Provider");
        tableLayout.addColumnData(new ColumnPixelData(200));
        
        provCol.setLabelProvider(new ColumnLabelProvider(){
        @Override
        public String getText(Object element) {
            DescriptorType desc=(DescriptorType)element;
            DescriptorImpl impl;
            try {
                impl = qsar.getDescriptorImpl( desc.getOntologyid(), desc.getProvider() );
                return impl.getProvider().getShortName();
            } catch ( BioclipseException e ) {
                LogUtils.debugTrace( logger, e );
                return "ERROR";
            }
        }
      });
        
        provCol.setEditingSupport(new EditingSupport(rightViewer){
          
        @Override
        protected boolean canEdit(Object element) {
          return true;
        }

        /**
         * Build the combo with available providers shortname
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            
            DescriptorType desc=(DescriptorType)element;

            //Find available impls for this descriptor to populate combo
            //This is the model we look up index in also
            List<String> availImpls;
            try {
                availImpls = qsar.getDescriptorImpls( desc.getOntologyid() );

                String[] values=new String[availImpls.size()]; 
                for (int i = 0; i < availImpls.size();i++){
                    String shortname=qsar.getDescriptorImplByID( availImpls.get( i ) ).getProvider().getShortName();
                    values[i] = shortname;
                }

                ComboBoxCellEditor cbo=new ComboBoxCellEditor(rightTable,values);
                return cbo;
            } catch ( BioclipseException e ) {
                LogUtils.handleException( e, logger, 
                                  net.bioclipse.qsar.init.Activator.PLUGIN_ID );
                return null;
            }
        }

        /**
         * Get the value for the current element, here the shortname of the impl.provider
         * @param element
         * @return
         */
        @Override
        protected Object getValue(Object element) {

            DescriptorType desc=(DescriptorType)element;

            //This is the model we look up index in also
            try {
            List<String> availImpls = qsar.getDescriptorImpls( desc.getOntologyid() );

            DescriptorImpl impl = qsar.getDescriptorImpl( desc.getOntologyid(), 
                                                          desc.getProvider() );
                int ix = availImpls.lastIndexOf( impl.getId() );
                return new Integer(ix);
            } catch ( BioclipseException e ) {
                LogUtils.debugTrace( logger, e );
                return new Integer(0);
            }
            
        }

        /**
         * User has selected a new impl.shortname in the combo. Store it in model.
         * @param element
         * @param value
         */
        @Override
        protected void setValue(Object element, Object value) {

            //The descriptor
            DescriptorType desc=(DescriptorType)element;

            //User selected this shortname
            int ix=(Integer)value;
            
            //This is the model we look up index in also
            List<String> availImpls;
            try {
                availImpls = qsar.getDescriptorImpls( desc.getOntologyid() );

            String shortname=qsar.getDescriptorImplByID( availImpls.get( ix ) ).getProvider().getShortName();
            
            //Identify the provider from the shortname
            for (DescriptorProvider prov : qsar.getFullProviders()){
                if (prov.getShortName().equals( shortname )){
                    //match! prov is the one we are looking for
                    String newProvID=prov.getId();
                    
                    //Set the new provider id in the desctype
                    Command cmd = SetCommand.create(editingDomain, desc, QsarPackage.Literals.DESCRIPTOR_TYPE__PROVIDER, newProvID);
                    editingDomain.getCommandStack().execute(cmd);    

                    //Mark desc as changed so it recalculates
                    IProject project = ((QsarEditor)getEditor()).getActiveProject();
                    QsarHelper.setChangedInPreference( desc, project, true );
                    
                    rightViewer.refresh();
                    return;
                }
            }
            } catch ( BioclipseException e ) {
                LogUtils.handleException( e, logger, 
                                  net.bioclipse.qsar.init.Activator.PLUGIN_ID );
            }

            logger.error("We could not find the selected provider in combo. This " +
            "should not happen!");
        }
        });

        
        
        
        
        
        
        
        
        

    	//If focus gained, make this viewer provide selections
        rightTable.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
		        descViewer.setSelection(null);
			}

			public void focusLost(FocusEvent e) {
			}
          });
    	
    	rightTable.addKeyListener( new KeyListener(){
    		public void keyPressed( KeyEvent e ) {

    			//Delete key
    			if (e.keyCode==SWT.DEL){
    				deleteSelectedDescriptors();
    			}

    		}
    		public void keyReleased( KeyEvent e ) {
    		}
    	});

    	//Post changes to parameters viewer
    	rightViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				//For now, don't care about multiple selections 
				DescriptorType desc=(DescriptorType)((IStructuredSelection)event.getSelection()).getFirstElement();
				if (desc!=null){
					paramsViewer.setInput(desc.getParameter().toArray());
				}else{
					paramsViewer.setInput(new Object[0]);
				}
					
			}
    	});


      //=================
      //Parameters viewer
      //=================

    	Label lblParams=toolkit.createLabel(client, "Descriptor parameters");
    	lblParams.setEnabled(true);

    	paramsViewer = new TableViewer (client, SWT.BORDER | SWT.MULTI);
    	paramsTable=paramsViewer.getTable();
    	toolkit.adapt(paramsTable, true, true);
    	GridData gd7=new GridData(GridData.FILL_BOTH);
    	gd7.heightHint=40;
    	gd7.minimumHeight=100;
    	paramsTable.setLayoutData( gd7 );

    	paramsTable.setHeaderVisible(true);
    	paramsTable.setLinesVisible(true);
    	
    	
        //Add providers columns
        TableLayout paramTableLayout = new TableLayout();
        paramsTable.setLayout(paramTableLayout);
        
        TableViewerColumn keyCol=new TableViewerColumn(paramsViewer, SWT.H_SCROLL | SWT.V_SCROLL);
        keyCol.getColumn().setText("Key");
        paramTableLayout.addColumnData(new ColumnPixelData(150));
        keyCol.setLabelProvider(new ColumnLabelProvider(){
        	@Override
        	public String getText(Object element) {
        		ParameterType param = (ParameterType)element;
        		return param.getKey();
        	}
        });

        TableViewerColumn valueCol=new TableViewerColumn(paramsViewer, SWT.NONE);
        valueCol.getColumn().setText("Value");
        paramTableLayout.addColumnData(new ColumnPixelData(150));
        
    	valueCol.setLabelProvider(new ColumnLabelProvider(){
    		@Override
    		public String getText(Object element) {
        		ParameterType param = (ParameterType)element;
        		return param.getValue();
    		}
    	});
        
        valueCol.setEditingSupport(new EditingSupport(paramsViewer){
//    		private TextCellEditor cellEditor;
    		
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
        		ParameterType param = (ParameterType)element;
        		if ((param.getValue().equals("true")) || 
        				(param.getValue().equals("false"))){
        			String[] values=new String[]{"true","false"}; 
        			ComboBoxCellEditor cbo=new ComboBoxCellEditor(paramsTable,values);
        			return cbo;
        		}
        		return new TextCellEditor(paramsTable);
			}

			@Override
			protected Object getValue(Object element) {
        		ParameterType param = (ParameterType)element;
        		//For combo boolean
        		if (param.getValue().equals("true")){
        			return new Integer(0);
        		}
        		else if (param.getValue().equals("false")){
        			return new Integer(1);
        		}
        		else
        			return param.getValue();
			}

			@Override
			protected void setValue(Object element, Object value) {
        		ParameterType param = (ParameterType)element;
        		//Integers
        		if (value instanceof Integer) {
					Integer i = (Integer) value;
					if (i==0){
						if (param.getValue().equals("false")){
	                		SetCommand cmd=new SetCommand(editingDomain,param,QsarPackage.Literals.PARAMETER_TYPE__VALUE,"true");
	                		editingDomain.getCommandStack().execute(cmd);
	                		paramsViewer.refresh();
	                    rightViewer.refresh();
						}
					}
					if (i==1){
						if (param.getValue().equals("true")){
							SetCommand cmd=new SetCommand(editingDomain,param,QsarPackage.Literals.PARAMETER_TYPE__VALUE,"false");
							editingDomain.getCommandStack().execute(cmd);
							paramsViewer.refresh();
              rightViewer.refresh();
						}
					}
				}
        		
        		//String values
        		if (value instanceof String) {
            		String strval=(String)value;
            		if (!(strval.equals(param.getValue()))){
                		SetCommand cmd=new SetCommand(editingDomain,param,QsarPackage.Literals.PARAMETER_TYPE__VALUE,strval);
                		editingDomain.getCommandStack().execute(cmd);

                		paramsViewer.refresh();
                		rightViewer.refresh();
            		}
				}
			}
        	
        });
    	
    	
    	paramsViewer.setContentProvider(new ArrayContentProvider());



    	//Wrap up section
    	toolkit.paintBordersFor(client);
    	preSection.setText("Selected descriptors");
    	preSection.setDescription("Add descriptors here for calculation");
    	preSection.setClient(client);
    	preSection.setExpanded(true);
    	preSection.addExpansionListener(new ExpansionAdapter() {
    		public void expansionStateChanged(ExpansionEvent e) {
    			form.reflow(false);
    		}
    	});
    	GridData gd = new GridData(GridData.FILL_BOTH);
    	preSection.setLayoutData(gd);        

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
		return rightViewer;
	}


	public void pageChanged(PageChangedEvent event) {

	    if (event.getSelectedPage()!=this) return;

	    if (rightViewer!=null){
	        populateRightViewerFromModel();
	    }

	    activatePage();

	}


}
