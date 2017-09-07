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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
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

import com.ca.gen.jmmi.MMObj;
import com.ca.gen.jmmi.schema.PrpTypeCode;

import eu.jgen.notes.annot.desc.annotation.Metadata;
import eu.jgen.notes.annot.desc.processor.ScanEnvironment;

/**
 * This is default implementation of the <code>ProcessingEnvironment</code>.
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class ScanEnvironmentImpl implements ScanEnvironment {

	private boolean errorRaised = false;

	private XtextResourceSet resourceSet;

	private ProcessingEnvironment processingEnv;

	public Set<AnnotationObject> foundObjects;

	public ScanEnvironmentImpl() {
		super();
	}

	@Override
	public boolean errorRaised() {
		return errorRaised;
	}

	@Override
	public Set<AnnotationObject> getElementsAnnotatedWith(Class<? extends Annotation> annotationClass) {
		Set<AnnotationObject> selectedObjects = new HashSet<AnnotationObject>();
		for (AnnotationObject annotationObject : foundObjects) {
			if (annotationObject.getAnnotation().getAnnotationType().getIdentifier()
					.equals(annotationClass.getName())) {
				selectedObjects.add(annotationObject);
			}
		}
		return selectedObjects;
	}

	@Override
	public void setScanResult(Set<AnnotationObject> foundObjects) {
		this.foundObjects = foundObjects;
	}

	@Override
	public Set<AnnotationObject> getScanResult() {
		return foundObjects;
	}

	@Override
	public void init(XtextResourceSet resourceSet, ProcessingEnvironment processingEnv, List<MMObj> list) {
		this.resourceSet = resourceSet;
		this.processingEnv = processingEnv;
		foundObjects = new HashSet<AnnotationObject>();
		for (MMObj mmObj : list) {
			String name = Long.toString(mmObj.getId().getValue()) + "." + mmObj.getTextProperty(PrpTypeCode.NAME);
			String description = mmObj.getTextProperty(PrpTypeCode.DESC);
			parseAndValidateDescription(mmObj, name, description);
		}
	}

	private void parseAndValidateDescription(MMObj mmObj, String name, String description) {
		URI uri = URI.createURI(name + ".desc");
		Resource resource = resourceSet.createResource(uri);
		try {
			resource.load(new ByteArrayInputStream(description.getBytes("UTF-8")), new HashMap<>());
			Metadata metadata = (Metadata) resource.getContents().get(0);
			if (metadata == null) {
				return;
			}
			List<Issue> list = validate(resource);
			for (Issue issue : list) {
				if (issue.getSeverity() == Severity.ERROR) {
					errorRaised = true;
					processingEnv.getMessager().printMessage(DiagnosticKind.ERROR, issue.getMessage(), mmObj);
				}
			}
			for (EObject eobject : metadata.eContents()) {
				if (eobject instanceof XAnnotation) {
					XAnnotation annotation = (XAnnotation) eobject;
					foundObjects.add(new AnnotationObject(mmObj, annotation));
				}
			}
		} catch (IOException e) {
			errorRaised = true;
			processingEnv.getMessager().printMessage(DiagnosticKind.ERROR,
			 e.getMessage(), mmObj);
			throw new RuntimeException(e);
		}

	}

	private List<Issue> validate(Resource resource) {
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		return validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
	}

}
