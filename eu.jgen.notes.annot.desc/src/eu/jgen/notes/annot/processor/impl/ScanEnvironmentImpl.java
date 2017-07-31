package eu.jgen.notes.annot.processor.impl;

import java.util.Set;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import eu.jgen.notes.annot.processor.base.ScanEnvironment;

public class ScanEnvironmentImpl implements ScanEnvironment {
	
	public Set<AnnotationObject> foundObjects;

	public ScanEnvironmentImpl() {
		super();
	}

	@Override
	public boolean processingOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean errorRaised() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<? extends AnnotationObject> getElementsAnnotatedWith(Class<? extends XAnnotation> a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScanResult(Set<AnnotationObject> foundObjects) {
		this.foundObjects = foundObjects;
		
	}
	
	@Override
	public Set<AnnotationObject>  getScanResult() {
		return  foundObjects;
		
	}



}
