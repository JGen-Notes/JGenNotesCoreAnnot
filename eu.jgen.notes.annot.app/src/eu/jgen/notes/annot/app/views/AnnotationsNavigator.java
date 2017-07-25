/**
 * [The "BSD license"]
 * Copyright (c) 2016,JGen Notes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *    and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package eu.jgen.notes.annot.app.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;

import eu.jgen.notes.annot.app.Activator;
import eu.jgen.notes.automation.wrapper.JGenModel;
import eu.jgen.notes.automation.wrapper.JGenObject;

public class AnnotationsNavigator extends ViewPart {
	public AnnotationsNavigator() {
	}

	@Inject
	IWorkbench workbench;

	public static final String ID = "eu.jgen.notes.annot.app.annotationnavigator";

	public static final String TXT_EU_JGEN_NOTES_ANNOT = "annotation";
	public static final String TXT_ID = "id";
	public static final String TXT_GENMODEL = "genModel";

	Logger logger;
	private JGenModel genModel;
	private IProject project;
	private TreeViewer treeViewer;
	private Tree tree;

	@Override
	public void createPartControl(Composite parent) {
		genModel = Activator.getDefault().genModel;
		if (genModel == null) {
			MessageDialog.openError(getSite().getShell(), "Cannot continue", "Failed to connect to the local model.");
			return;
		}
		project = Activator.getDefault().project;
		if (project == null) {
			MessageDialog.openError(getSite().getShell(), "Cannot continue", "Failed to create internal project.");
			return;
		}
		parent.getShell().setText(parent.getShell().getText() + ": " + genModel.getName());
		parent.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 88, 23);

		ToolItem tltmShawAll = new ToolItem(toolBar, SWT.NONE);
		tltmShawAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeViewer.setInput(Activator.getDefault().genModel);
			}
		});
		tltmShawAll.setToolTipText("Show All Objects");
		tltmShawAll.setImage(ResourceManager.getPluginImage("eu.jgen.notes.annot.app", "icons/allobjects.png"));

		ToolItem tltmShowOne = new ToolItem(toolBar, SWT.NONE);
		tltmShowOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeViewer.setInput(Activator.getDefault().startingPoint);
			}
		});
		tltmShowOne.setToolTipText("Show Starting Point");
		tltmShowOne.setImage(ResourceManager.getPluginImage("eu.jgen.notes.annot.app", "icons/oneobject.png"));

		treeViewer = new TreeViewer(composite, SWT.BORDER);
		tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		treeViewer.setContentProvider(new AnnotationsTreeContentProvider(genModel));
		treeViewer.setLabelProvider(new AnnotationsLaberProvider());
		treeViewer.setInput(Activator.getDefault().startingPoint);
		final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
		workbench.addWorkbenchListener(new IWorkbenchListener() {
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				activePage.closeEditors(activePage.getEditorReferences(), true);
				return true;
			}

			public void postShutdown(IWorkbench workbench) {
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection iStructuredSelection = (IStructuredSelection) event.getSelection();
				Object selectedObject = iStructuredSelection.getFirstElement();
				if (selectedObject instanceof JGenObject) {
					JGenObject jGenObject = (JGenObject) selectedObject;
					String name = ProjectUtility.createFileName(jGenObject);
					String desc = jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.DESC));
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IFolder folder = project.getFolder("src");
						IFile file = folder.getFile(name);
						if (file.exists()) {
							file.delete(true, null);
						}
						byte[] bytes = desc.getBytes();
						InputStream source = new ByteArrayInputStream(bytes);
						file.create(source, IResource.NONE, null);
						file.setSessionProperty(new QualifiedName(TXT_EU_JGEN_NOTES_ANNOT, TXT_ID),
								new Integer(jGenObject.getId()));
						file.setSessionProperty(new QualifiedName(TXT_EU_JGEN_NOTES_ANNOT, TXT_GENMODEL), genModel);
						IDE.openEditor(page, file);
					} catch (CoreException e) {
						// logger.error("Unable to successfully open editor",e);
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {
		if (treeViewer != null) {
			treeViewer.getControl().setFocus();
		}
	}

}