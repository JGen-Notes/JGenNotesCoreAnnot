package eu.jgen.notes.annot.processor.tests.two;

import com.google.inject.Guice;
import com.google.inject.Injector;
import eu.jgen.notes.annot.desc.AnnotationStandaloneSetup;
import eu.jgen.notes.annot.desc.annotation.Metadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;
import org.eclipse.xtext.xbase.annotations.xAnnotations.impl.XAnnotationImpl;

public class MyTest {

	public static void main(String[] args) {

		Injector injector = new AnnotationStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		validate(resourceSet, "{import a.b.Hello; @Hello}");

	}

//	private static XtextResourceSet resourceSet;

	public static Metadata validate(ResourceSet resourceSet, String desc) {
		URI uri = URI.createURI("abc" + ".desc");
		Resource resource = resourceSet.createResource(uri);
		System.out.println(resource);
		try {
			resource.load(new ByteArrayInputStream(desc.getBytes("UTF-8")), new HashMap<>());
			Metadata model = (Metadata) resource.getContents().get(0);
			List<Issue> list = validate(resource);
			for (Issue issue : list) {
				if (issue.getSeverity() == Severity.ERROR) {
					System.out.println(issue);
				}
			}
			
			;
			for (EObject eobject : model.eContents()) {
				if(eobject instanceof XAnnotationImpl) {
					XAnnotation xAnnotation = (XAnnotation) eobject;
					System.out.println(xAnnotation.getAnnotationType().getQualifiedName());
					 
				}
				
				
			}
			
			return model;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Issue> validate(Resource resource) {
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		return validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
	}

}
