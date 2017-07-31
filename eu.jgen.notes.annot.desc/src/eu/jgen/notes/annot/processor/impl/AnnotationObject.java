package eu.jgen.notes.annot.processor.impl;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import eu.jgen.notes.automation.wrapper.JGenObject;

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
