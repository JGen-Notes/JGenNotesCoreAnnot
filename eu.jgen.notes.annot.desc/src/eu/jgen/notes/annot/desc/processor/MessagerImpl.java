/**
 * [The "BSD license"]
 * Copyright (c) 2016, JGen Notes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *    and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package eu.jgen.notes.annot.desc.processor;

import java.util.List;
import java.util.Vector;

import com.ca.gen.jmmi.MMObj;
import com.ca.gen.jmmi.schema.PrpTypeCode;

/**
 * This is default implementation of the <code>Messager</code>.
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class MessagerImpl implements Messager {
	
	private List<String> messages = null;

	public MessagerImpl() {
	}

	public void printMessage(DiagnosticKind kind, CharSequence msg) {
		if(messages == null) {
			messages = new Vector<String>();
		}
		String text = kind + ": " + msg.toString();
		messages.add(text);
		System.out.println(text);
	}

	public void printMessage(DiagnosticKind kind, CharSequence msg, MMObj jGenObject) {
		if(messages == null) {
			messages = new Vector<String>();
		}
		String text = kind + ": " + msg.toString() + " (" + jGenObject.getId() + ","
				+ jGenObject.getTextProperty(PrpTypeCode.NAME) + ")";
		messages.add(text);
		System.out.println(text);
	}

	public List<String> getMessages() {
		return messages;
	}

}