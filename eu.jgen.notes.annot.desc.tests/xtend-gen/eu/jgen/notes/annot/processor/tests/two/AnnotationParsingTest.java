/**
 * generated by Xtext 2.12.0
 */
package eu.jgen.notes.annot.processor.tests.two;

import com.google.inject.Inject;
import eu.jgen.notes.annot.desc.annotation.Metadata;
import eu.jgen.notes.annot.desc.tests.AnnotationInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(AnnotationInjectorProvider.class)
@SuppressWarnings("all")
public class AnnotationParsingTest {
  @Inject
  private ParseHelper<Metadata> parseHelper;
  
  @Inject
  @Extension
  private ParseHelper<Metadata> _parseHelper;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Test
  public void loadModel() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#meta{");
      _builder.newLine();
      _builder.append("import eu.jgen.notes.annot.processor.tests.sample.Author;");
      _builder.newLine();
      _builder.append("import eu.jgen.notes.annot.processor.tests.sample.Function;");
      _builder.newLine();
      _builder.append("@Author (name=\"John\",date=\"2017-01-01\",version=\"1.0\")");
      _builder.newLine();
      _builder.append("@Function (modname=\"COSTAM\")");
      _builder.newLine();
      _builder.append("}\t\t");
      this._validationTestHelper.assertNoErrors(this._parseHelper.parse(_builder));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
