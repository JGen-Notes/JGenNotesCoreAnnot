package eu.jgen.notes.annot.processor.tests.sample;

import java.util.Set;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import eu.jgen.notes.annot.processor.base.ScanEnvironment;
import eu.jgen.notes.annot.processor.base.SupportedAnnotationTypes;
import eu.jgen.notes.annot.processor.impl.AbstractProcessor;
import eu.jgen.notes.annot.processor.impl.AnnotationObject;
import eu.jgen.notes.annot.processor.impl.DefaultScanEnvironment;

@javax.annotation.processing.SupportedAnnotationTypes(value = { "" })
@SupportedAnnotationTypes(value = { "eu.jgen.notes.annot.processor.tests.sample.Author",
		"eu.jgen.notes.annot.processor.tests.sample.Function" })
public class SampleProcessor extends AbstractProcessor {

	public SampleProcessor() {
		super();
	}

	@Override
	public boolean process(Set<XAnnotation> annotations, ScanEnvironment scanEnv) {
		System.out.println("\nSupported Annotations:");
		for (XAnnotation annotation : annotations) {
			System.out.println(annotation.getAnnotationType().getQualifiedName());
		}
		System.out.println("\n\nAnnotation Objects Found:");
		for (AnnotationObject annotationObject : scanEnv.getScanResult()) {
			System.out.println("\n" + annotationObject);
		}
		return true;
	}

}
