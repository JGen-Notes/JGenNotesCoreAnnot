package eu.jgen.notes.annot.processor.base;

/**
 * A suggested {@linkplain Processor#getCompletions <em>completion</em>} for an
 * annotation.  A completion is text meant to be inserted into a
 * program as part of an annotation.
 *
 */

public interface Completion {
	
	
    /**
     * Returns the text of the suggested completion.
     * @return the text of the suggested completion.
     */
    public  String getValue();

    /**
     * Returns an informative message about the completion.
     * @return an informative message about the completion.
     */
    public  String getMessage();
}