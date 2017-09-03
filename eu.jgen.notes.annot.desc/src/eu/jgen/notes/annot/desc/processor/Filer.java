package eu.jgen.notes.annot.desc.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;




public interface Filer {
	
	OutputStream createSourceFile(CharSequence name) throws IOException;
	
	Writer openWriter(String name) throws IOException;

}
