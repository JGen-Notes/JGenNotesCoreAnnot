package eu.jgen.notes.annot.processor.impl;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import com.google.common.collect.Sets;

import eu.jgen.notes.annot.processor.base.ProcessingEnvironment;
import eu.jgen.notes.annot.processor.base.Processor;
import eu.jgen.notes.annot.processor.base.ScanEnvironment;
import eu.jgen.notes.annot.processor.base.SupportedAnnotationTypes;

public abstract class AbstractProcessor implements Processor { 
	
	protected ProcessingEnvironment processingEnv;
	private boolean initialized = false;

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		SupportedAnnotationTypes supportedTypes = this.getClass().getAnnotation(SupportedAnnotationTypes.class);
		if (supportedTypes == null) {
			if (initialized)
				processingEnv.getMessager().printMessage(MessageKind.WARNING,
					"No SupportedAnnotationTypes annotation " + "found on " + this.getClass().getName() +
						", returning an empty set.");
					return Collections.emptySet();
				} else
					return Sets.newHashSet(supportedTypes.value());
	}

	@Override
	public void init(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
		
	}
  
	@Override
	public boolean process(Set<XAnnotation> annotations, ScanEnvironment roundEnv) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation1,
			ExecutableElement member, String userText) {
		// TODO Auto-generated method stub
		return null;
	}

}
