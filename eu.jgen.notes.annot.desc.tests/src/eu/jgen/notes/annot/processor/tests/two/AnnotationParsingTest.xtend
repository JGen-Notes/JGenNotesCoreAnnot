/*
 * generated by Xtext 2.12.0
 */
package eu.jgen.notes.annot.processor.tests.two

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import eu.jgen.notes.annot.desc.annotation.Metadata
import eu.jgen.notes.annot.desc.tests.AnnotationInjectorProvider

@RunWith(XtextRunner) 
@InjectWith(AnnotationInjectorProvider)
class AnnotationParsingTest { 
	@Inject
	ParseHelper<Metadata> parseHelper 
		@Inject extension ParseHelper<Metadata>
		@Inject extension ValidationTestHelper
	
	@Test   
 	def void loadModel() {
		''' 
			{
				import a.b.Hello;
				@Hello
			}
		'''.parse.assertNoErrors
	}
}
