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

import net.bioclipse.qsar.QsarType;
import net.bioclipse.qsar.ui.QsarHelper;
import net.bioclipse.qsar.ui.builder.QSARBuilder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ToolbarHelper {

	
	public static void setupToolbar(ScrolledForm form, final IProject project, final QsarEditor qsarEditor) {
	    
		IAction buildAction=new Action(){
			@Override
			public void run() {

				WorkspaceJob job = new WorkspaceJob("Building qsar project"){

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {

					    //Make all dirty
					    QsarHelper.setAllDirty(qsarEditor.getQsarModel(), project);
					    boolean storedStatus=QsarHelper.isAutoBuild( project );
					    QsarHelper.setAutoBuild( project, true );
              project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
              QsarHelper.setAutoBuild( project, storedStatus );
					    return Status.OK_STATUS;
					}
					
				};
				
				job.setUser(true);
				job.schedule();
				
			}
		};
		buildAction.setText("Build all descriptors");
		buildAction.setToolTipText("Build all descriptors");
		buildAction.setDescription( "Build all descriptors" );
//    buildAction.setHoverImageDescriptor( net.bioclipse.qsar.ui.Activator.getImageDescriptor("icons32/execute32_hover.gif"));
		buildAction.setImageDescriptor(net.bioclipse.qsar.ui.Activator.getImageDescriptor("icons32/execute32.gif"));
		form.getToolBarManager().add(buildAction);
		form.updateToolBar();
		
	}

}
