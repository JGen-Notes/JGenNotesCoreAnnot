/**
 * [The "BSD license"]
 * Copyright (c) 2016, JGen Notes
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
package eu.jgen.notes.annot.desc.processor;

import java.util.Map;

import org.eclipse.xtext.resource.XtextResourceSet;

import com.ca.gen.jmmi.Ency;
import com.ca.gen.jmmi.Model;

/**
 * This is default implementation of the <code>ProcessingEnvironment</code>.
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class ProcessingEnvironmentImpl implements ProcessingEnvironment {

	private Filer filer;
	
	private Messager messager;

	private XtextResourceSet resourceSet;

	private Map<String, String> options;

	private Ency ency;
	
	private Model model;

	public ProcessingEnvironmentImpl() {
	}

	public Ency getEncy() {
		return ency;
	}

	@Override
	public Filer getFiler() {
		return filer;
	}

	@Override
	public Messager getMessager() {
		return messager;
	}

	public Model getModel() {
		return model;
	}

	@Override
	public Map<String, String> getOptions() {
		return options;
	}

	public XtextResourceSet getResourceSet() {
		return resourceSet;
	}

	public void init(Model model, Map<String, String> options, Messager messager, Filer filer) {
		this.model = model;
		this.ency = model.getEncy();
		this.options = options;
		this.messager = messager;
		this.filer = filer;
	}

	@Override
	public void setOptions(Map<String, String> options) {
		this.options =options;		
	}

	@Override
	public void setResourceSet(XtextResourceSet resourceSet) {
		this.resourceSet = resourceSet;		
	}

}
