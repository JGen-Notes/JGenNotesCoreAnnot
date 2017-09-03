package eu.jgen.notes.annot.desc.processor.tests;

import eu.jgen.notes.annot.desc.processor.AbstractProcessor;
import eu.jgen.notes.annot.desc.processor.SupportedAnnotationTypes;

@SupportedAnnotationTypes(value = { "eu.jgen.notes.annot.desc.processor.tests.Ab01", "eu.jgen.notes.annot.desc.processor.tests.Ab02"})
public class TestProcessor extends AbstractProcessor  {

	public TestProcessor() {
		super();
	}

}
