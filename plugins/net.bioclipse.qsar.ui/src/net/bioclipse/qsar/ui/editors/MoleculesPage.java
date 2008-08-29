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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.bioclipse.cdk.business.Activator;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.qsar.MoleculeResourceType;
import net.bioclipse.qsar.MoleculelistType;
import net.bioclipse.qsar.QsarFactory;
import net.bioclipse.qsar.QsarPackage;
import net.bioclipse.qsar.QsarType;
import net.bioclipse.ui.dialogs.WSFileDialog;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
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
public class MoleculesPage extends FormPage{

    private TableViewer molViewer;
    private Table molTable;

    private TableViewer preTableViewer;
    private Table preTable;

    
    private MoleculesContentProvider molContentProv;

    private static final Logger logger = Logger.getLogger(MoleculesPage.class);
    
    ICDKManager cdk;
    DecimalFormat formatter;
    
    private MoleculesEditorSelectionProvider selectionProvider;
	private EditingDomain editingDomain;
	private List<MoleculeResource> molecules;
	private MoleculelistType moleculeList;
	private boolean dirty;

    
	public MoleculesPage(FormEditor editor, QsarType qsarModel, 
			EditingDomain editingDomain, MoleculesEditorSelectionProvider selectionProvider) {

		super(editor, "qsar.molecules", "Molecules");
		this.editingDomain=editingDomain;
	    
		cdk=Activator.getDefault().getCDKManager();
        molContentProv=new MoleculesContentProvider();
        formatter = new DecimalFormat("0.00");
        this.selectionProvider=selectionProvider;
	    
		//Currently displayed in table, duplicates the model in moleculeList
		molecules=new ArrayList<MoleculeResource>();

		//Get mollist from qsar model, init if empty (should not be)
		moleculeList=qsarModel.getMoleculelist();
		if (moleculeList==null){
			moleculeList=QsarFactory.eINSTANCE.createMoleculelistType();
			qsarModel.setMoleculelist(moleculeList);
		}

		dirty=false;

	}


    /**
     * Add content to form
     */
    @Override
    protected void createFormContent(IManagedForm managedForm) {

        
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Molecules for QSAR analysis");
//        form.setBackgroundImage(FormArticlePlugin.getDefault().getImage(FormArticlePlugin.IMG_FORM_BG));
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        form.getBody().setLayout(layout);
        createMoleculesSection(form, toolkit);
        populateMolsViewerFromModel();
        createPreprocessingSection(form, toolkit);
        
        //Post selections to Eclipse via our intermediate selectionprovider
        selectionProvider.setSelectionProviderDelegate( molViewer );

        
    }
    
	private void populateMolsViewerFromModel() {

		for (MoleculeResourceType mol: moleculeList.getMoleculeResource()){
			if (mol.getFile()!=null){
				IPath pth=new Path(mol.getFile());
				if (pth!=null){
					IFile file=ResourcesPlugin.getWorkspace().getRoot().getFile(pth);
					MoleculeResource mr=new MoleculeResource(file);
					molecules.add(mr);
					System.out.println("Added mol: " + file.getName());
				}else{
					logger.error("could not parse path of mol: " + mol.getFile());
				}
			}else{
				logger.error("could not parse mol: " + mol.getName() + ". path=null");
			}
		}

		molViewer.setInput( molecules );

	}


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


    /**
     * Create the left part of the page for displaying molecules
     * @param form
     * @param toolkit
     */
    private void createMoleculesSection( final ScrolledForm form,
                                         FormToolkit toolkit) {

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

          molViewer = new TableViewer(client, SWT.CHECK | SWT.BORDER | SWT.MULTI);
          molTable=molViewer.getTable();
          toolkit.adapt(molTable, true, true);
          GridData gd=new GridData(GridData.FILL_VERTICAL);
          gd.widthHint=350;
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
          
          molViewer.setContentProvider( molContentProv );
          molViewer.setLabelProvider( new MoleculesLabelProvider() );
          molViewer.setUseHashlookup(true);
          
          molTable.addKeyListener( new KeyListener(){
              public void keyPressed( KeyEvent e ) {
                  //Delete key
                  if (e.keyCode==SWT.DEL){
                      deleteSelectedMolecules();
                  }
                  
                  //Space key, toggle selection
                  if (e.keyCode==32){

                	  IStructuredSelection msel=(IStructuredSelection) molViewer.getSelection();
                      //TODO: implement
                      
                  }
              }
              public void keyReleased( KeyEvent e ) {
              }
          });

          Button btnAdd=toolkit.createButton(client, "Add...", SWT.PUSH);
          btnAdd.addListener(SWT.Selection, new Listener() {
              public void handleEvent(Event e) {
                  addMoleculeFile();
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
    protected void addMoleculeFile() {

		IProject proj=((QSARFormEditor)getEditor()).getActiveProject();

		WSFileDialog dlg=new WSFileDialog(getEditorSite().getShell()
				,SWT.MULTI,"Select molecules"
				,proj,true,null,null);

		//Collect a list of resources currently in viewer 
		//to hide them in dialog


		if (molecules!=null && molecules.size()>0){
			List<IResource> res=new ArrayList<IResource>();
			for (MoleculeResource r : molecules){
				res.add(r.getResource());
			}
			dlg.setBlacklistFilter( res );
		}

		int r=dlg.open();
		if (r==Window.CANCEL){
			return;
		}
		
		if (dlg.getMultiResult()==null || dlg.getMultiResult().length<=0){
			showMessage("Please select at least one molecule to add");
			return;
		}

		System.out.println("Selected mols to add: ");
		for (IResource resource : dlg.getMultiResult()){
			if (!(molecules.contains( resource ))){

				MoleculeResource mr=new MoleculeResource(resource);
				
				//Add to List of resources
				molecules.add( mr );

				//Also add to QSAR model
				MoleculeResourceType mol1=QsarFactory.eINSTANCE.createMoleculeResourceType();
				mol1.setId(resource.getName());
				mol1.setName(resource.getName());
				mol1.setFile(resource.getFullPath().toString());
				Command cmd=AddCommand.create(editingDomain, moleculeList, 
   			   QsarPackage.Literals.MOLECULELIST_TYPE__MOLECULE_RESOURCE, mol1);

				//Execute the CompoundCommand
				editingDomain.getCommandStack().execute(cmd); 		
			}
		}

		molViewer.setInput( molecules );
		
		setDirty(true);



    }



    /**
     * Handle the case when users press the Remove button next to moleculeviewer
     * or presses the delete button on something
     */
    protected void deleteSelectedMolecules() {


		IStructuredSelection ssel=(IStructuredSelection) molViewer.getSelection();
		if (ssel == null) {
			showMessage("Please select a molecule to remove");
			return;
		}
		
		for (Object obj : ssel.toList()){
			if (!(obj instanceof MoleculeResource)) {
				//Should not happen
				logger.error("A non-MoleculeResource selected in MolViewer: " + obj);
				return;
			}

			MoleculeResource molres=(MoleculeResource)obj;

			if (!(molecules.contains(molres))){
				logger.error("Error: sel mol must be contained in list!");
				return;
			}

			//Remove the moltype, look up by id=resource name
			MoleculeResourceType molToRemove=null;
			for (MoleculeResourceType mol: moleculeList.getMoleculeResource()){
				if (mol.getId().equals(molres.getResource().getName())){
					molToRemove=mol;
				}
			}

			//Remove molType from Viewers List
			molecules.remove(molres);

			//Remove entry (should be found)
			if (molToRemove==null){
				System.out.println("found no matching mol in emf model. " +
						"Should not happen.");
				return;
			}
			Command cmd=RemoveCommand.create(editingDomain, 
					moleculeList, QsarPackage.Literals.
					MOLECULELIST_TYPE__MOLECULE_RESOURCE, molToRemove);
			editingDomain.getCommandStack().execute(cmd);

			molViewer.setInput(molecules);
			
			setDirty(true);
		}
    }

    private void showMessage(String message) {
        MessageDialog.openInformation( getSite().getShell(),
                                       "Information",
                                       message );
    }



    public void activatePage() {

        selectionProvider.setSelectionProviderDelegate( molViewer );
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

	public void setDirty(boolean dirty) {
		this.dirty=dirty;
		((QSARFormEditor)getEditor()).fireDirtyChanged();
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}