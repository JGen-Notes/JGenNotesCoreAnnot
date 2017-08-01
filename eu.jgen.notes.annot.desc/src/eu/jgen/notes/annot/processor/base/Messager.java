package eu.jgen.notes.annot.processor.base;


import eu.jgen.notes.annot.processor.impl.DiagnosticKind;
import eu.jgen.notes.automation.wrapper.JGenObject;

public interface Messager {
	
	  /**
     * Prints a message of the specified kind.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     */
    public void printMessage(DiagnosticKind kind, CharSequence msg);

    /**
     * Prints a message of the specified kind at the location of the
     * element.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the element to use as a position hint
     */
   public   void printMessage(DiagnosticKind kind, CharSequence msg, JGenObject jGenObject);


  }