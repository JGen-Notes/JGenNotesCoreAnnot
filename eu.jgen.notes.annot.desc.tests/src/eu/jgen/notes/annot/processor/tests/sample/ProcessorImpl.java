package eu.jgen.notes.annot.processor.tests.sample;

import java.util.Set;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import eu.jgen.notes.annot.processor.base.ScanEnvironment;
import eu.jgen.notes.annot.processor.base.SupportedAnnotationTypes;
import eu.jgen.notes.annot.processor.impl.AbstractProcessor;
import eu.jgen.notes.annot.processor.impl.AnnotationObject;
import eu.jgen.notes.annot.processor.impl.DefaultScanEnvironment;

@javax.annotation.processing.SupportedAnnotationTypes(value = { "" })
@SupportedAnnotationTypes(value = { "eu.jgen.notes.annot.processor.tests.impl.Author",
		"eu.jgen.notes.annot.processor.tests.impl.Function" })
public class ProcessorImpl extends AbstractProcessor {

	public ProcessorImpl() {  
		super();
	}

	@Override
	public boolean process(Set<XAnnotation> annotations, ScanEnvironment roundEnv) {
		for (XAnnotation annotation : annotations) {
			System.out.println(annotation.getAnnotationType().getQualifiedName());
		}
		
		DefaultScanEnvironment x = (DefaultScanEnvironment) roundEnv;
		
		
		for(AnnotationObject object : x.foundObjects) {
			System.out.println(object.getjGenObject());
		}
		

		return true;
	}

}
