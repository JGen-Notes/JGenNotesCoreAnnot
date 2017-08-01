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
package eu.jgen.notes.annot.processor.impl;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import eu.jgen.notes.automation.wrapper.JGenObject;

/**
 * Object returned by the annotation worker after model scan is complete. Object allows to associate annotation 
 * with the object concrete object in the model.
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class AnnotationObject {
	
	private JGenObject jGenObject;
	private XAnnotation xAnnotation;
	
	public AnnotationObject(JGenObject jGenObject, XAnnotation xAnnotation) {
		super();
		this.jGenObject = jGenObject;
		this.xAnnotation = xAnnotation;
	}

	public JGenObject getjGenObject() {
		return jGenObject;
	}

	public XAnnotation getxAnnotation() {
		return xAnnotation;
	}


}
