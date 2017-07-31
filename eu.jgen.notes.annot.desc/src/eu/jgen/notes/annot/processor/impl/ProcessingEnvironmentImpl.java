package eu.jgen.notes.annot.processor.impl;

import org.eclipse.xtext.resource.XtextResourceSet;

import eu.jgen.notes.annot.processor.base.Messager;
import eu.jgen.notes.annot.processor.base.ProcessingEnvironment;

public class ProcessingEnvironmentImpl implements ProcessingEnvironment  {
	
 private XtextResourceSet resourceSet;

	@Override
	public Messager getMessager() {		 
		return new MessagerImpl();
	}

	public ProcessingEnvironmentImpl() {
	}

	public ProcessingEnvironmentImpl(XtextResourceSet resourceSet ) {
		this.resourceSet = resourceSet;
	}

}
