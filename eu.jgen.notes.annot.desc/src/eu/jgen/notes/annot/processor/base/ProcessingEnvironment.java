package eu.jgen.notes.annot.processor.base;

 
public interface ProcessingEnvironment {
	
	 /**
     * Returns the messager used to report errors, warnings, and other
     * notices.
     *
     * @return the messager
     */
    public  Messager getMessager();

}