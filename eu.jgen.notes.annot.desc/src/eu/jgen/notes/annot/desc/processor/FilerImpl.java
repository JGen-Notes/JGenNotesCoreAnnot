package eu.jgen.notes.annot.desc.processor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class FilerImpl implements Filer {

	public FilerImpl() {
		super();
	}

	@Override
	public OutputStream createSourceFile(CharSequence name) throws IOException {
		return null;
	}

	@Override
	public Writer openWriter(String path) throws IOException {		 
		return new  FileWriter(path);
	}

}
