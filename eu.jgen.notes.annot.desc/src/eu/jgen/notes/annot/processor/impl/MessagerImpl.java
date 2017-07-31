package eu.jgen.notes.annot.processor.impl;

import eu.jgen.notes.automation.wrapper.JGenObject;
import eu.jgen.notes.annot.processor.base.Messager;

public class MessagerImpl implements Messager {
	
	public MessagerImpl() {
		
	}
	
	public void printMessage(MessageKind kind, CharSequence msg) {
		System.out.println(kind + ": " + msg.toString());
	}
	
	public void printMessage(MessageKind kind, CharSequence msg, JGenObject jGenObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	

	
}