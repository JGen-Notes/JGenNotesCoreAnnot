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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;
import com.google.inject.Inject;

import eu.jgen.notes.annot.desc.annotation.Metadata;
import eu.jgen.notes.annot.processor.base.Processor;
import eu.jgen.notes.annot.processor.base.ScanEnvironment;
import eu.jgen.notes.automation.wrapper.JGenObject;

/**
 * This is tool allowing to retrieve the CA Gen Model (local) for 
 * a number of objects and scan them for meta-data. Worker  
 * is parsing meta-data looking for the supported annotation types.
 * Annotation worker executes <code>process</code> method for each
 * object which matches search criteria.
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class AnnotationWorker {

	private JGenObject[] sources;
	private Processor processor;

	@Inject
	private XtextResourceSet resourceSet;

	private DefaultProcessingEnvironment processingEnv;

	public AnnotationWorker() {
	}

	public AnnotationWorker initProcessor(Processor processor) {
		this.processor = processor;
		this.processingEnv = new DefaultProcessingEnvironment(resourceSet);
		return this;
	}

	public AnnotationWorker setSources(JGenObject[] sources) {
		this.sources = sources;
		processor.init(processingEnv);
		return this;
	}

	public void activate() {
		for (JGenObject jGenObject : sources) {
			processObject(jGenObject);
		}
	}

	private void processObject(JGenObject jGenObject) {
		ScanEnvironment roundEnv = new DefaultScanEnvironment();
		String description = jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.DESC));
		if (description.length() >= 5 && description.startsWith("#meta")) {
			parseAndValidateDescription(roundEnv, jGenObject, description,
					Integer.toString(jGenObject.getId()) + "."
							+ jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME)));		  
			Set<XAnnotation> annotations = new HashSet<XAnnotation>();
			for (AnnotationObject annotationObject : roundEnv.getScanResult()) {
				annotations.add(annotationObject.getxAnnotation());				
			}
			processor.process(annotations, roundEnv);
		} 
	}

	private void parseAndValidateDescription(ScanEnvironment roundEnv, JGenObject jGenObject, String desc, String name) {
		Set<AnnotationObject> foundObjects = new HashSet<AnnotationObject>();
		URI uri = URI.createURI(name + ".desc");
		Resource resource = resourceSet.createResource(uri);
		try {
			resource.load(new ByteArrayInputStream(desc.getBytes("UTF-8")), new HashMap<>());
			Metadata metadata = (Metadata) resource.getContents().get(0);
			if(metadata == null) {
				roundEnv.setScanResult(foundObjects);
			}
			List<Issue> list = validate(resource);
			for (Issue issue : list) {
				if (issue.getSeverity() == Severity.ERROR) {
					 processingEnv.getMessager().printMessage(DiagnosticKind.ERROR, issue.getMessage(), jGenObject);
					roundEnv.setScanResult(foundObjects);
				}
			}
			for (EObject eobject : metadata.eContents()) {
				if (eobject instanceof XAnnotation) {
					XAnnotation xAnnotation = (XAnnotation) eobject;
					if(matchTypes(xAnnotation.getAnnotationType().getIdentifier())) {
						foundObjects.add(new AnnotationObject(jGenObject, xAnnotation));
					}				
				}
			}
			roundEnv.setScanResult(foundObjects);
		} catch (IOException e) {
			 processingEnv.getMessager().printMessage(DiagnosticKind.ERROR, e.getMessage(), jGenObject);
			throw new RuntimeException(e);
		}
	}

	private boolean matchTypes(String name) {		 
		for (String text : processor.getSupportedAnnotationTypes()) {
			if(text.equals(name)) {
				return true;
			}
		}		
		return false;
	}

	private List<Issue> validate(Resource resource) {
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		return validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
	}

}
