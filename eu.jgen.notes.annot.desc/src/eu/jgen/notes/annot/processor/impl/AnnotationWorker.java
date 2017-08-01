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
		String text = jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.DESC));
		if (text.length() >= 5 && text.startsWith("#meta")) {
			parseAndValidateDescription(roundEnv, jGenObject, text,
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
					System.out.println(issue);
					System.out.println(desc);
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
