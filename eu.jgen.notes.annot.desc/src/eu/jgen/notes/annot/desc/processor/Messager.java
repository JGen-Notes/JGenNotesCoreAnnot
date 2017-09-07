package eu.jgen.notes.annot.desc.processor;

import java.util.List;

import com.ca.gen.jmmi.MMObj;

/**
 * *
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public interface Messager {

	/**
	 * Prints a message of the specified kind.
	 *
	 * @param kind
	 *            the kind of message
	 * @param msg
	 *            the message, or an empty string if none
	 */
	public void printMessage(DiagnosticKind kind, CharSequence msg);

	/**
	 * Prints a message of the specified kind at the location of the element.
	 *
	 * @param kind
	 *            the kind of message
	 * @param msg
	 *            the message, or an empty string if none
	 * @param mmObj
	 *            the element to use as a position hint
	 */
	public void printMessage(DiagnosticKind kind, CharSequence msg, MMObj mmObj);

	public List<String> getMessages();
}