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
package eu.jgen.notes.annot.app;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.jgen.notes.annot.app.views.ProjectUtility;
import eu.jgen.notes.annot.app.views.StartingPoint;
import eu.jgen.notes.annot.app.views.StartingPointParser;
import eu.jgen.notes.automation.wrapper.JGMSG;
import eu.jgen.notes.automation.wrapper.JGenEncyclopedia;
import eu.jgen.notes.automation.wrapper.JGenFactory;
import eu.jgen.notes.automation.wrapper.JGenModel;
 

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	

	// The plug-in ID
	public static final String PLUGIN_ID = "eu.jgen.notes.annot.app"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public JGenModel genModel;
	
	public IProject project;
	
	public StartingPoint startingPoint;
	
	private JGenEncyclopedia ency;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		System.out.println(PLUGIN_ID + ": " + "Starting");
		launchSequence( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		disconnectFromToolset();
		project.delete(true, null);
		System.out.println(PLUGIN_ID + ": " + "Stopped");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	private void launchSequence( ) {
		connectToToolset( );
		if (genModel == null) {
			return;
		}
		startingPoint = StartingPointParser.parse(genModel, Platform.getApplicationArgs());
		try {
			openOpenCreateInternalProject();
		} catch (CoreException e) {
			System.out.println(PLUGIN_ID + ": " + "Core exception when creating internal project. Message:" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void connectToToolset( ) {
		JGenFactory factory = JGenFactory.eINSTANCE;
	    ency = factory.createEncyclopedia();
		ency.connect();
		if (ency.getReturnCode() == JGMSG.MODEL_NOT_FOUND_RC) {
			System.out.println(PLUGIN_ID + ": " + "Connect to the CA Gen model cannot be completed. rc=" + ency.getReturnCode());
			return;
		}
		genModel = ency.findModels()[0];
		System.out.println(PLUGIN_ID + ": " + "Connected to the CA Gen Model: " + genModel.getName());
	}
	
	private void disconnectFromToolset( ) {
		if(genModel != null) {
			ency.disconnect();
		}
		try {
			project.getWorkspace().save(false, null);
		} catch (CoreException e) {
			System.out.println(PLUGIN_ID + ": " + "Cannot complete when exiting from the internal project. Message:  " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void openOpenCreateInternalProject() throws CoreException {
		project = ProjectUtility.createProject(genModel);
	}
}
