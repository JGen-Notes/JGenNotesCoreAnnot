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
package eu.jgen.notes.annot.processor.impl;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import com.google.common.collect.Sets;

import eu.jgen.notes.annot.processor.base.ProcessingEnvironment;
import eu.jgen.notes.annot.processor.base.Processor;
import eu.jgen.notes.annot.processor.base.ScanEnvironment;
import eu.jgen.notes.annot.processor.base.SupportedAnnotationTypes;

public abstract class AbstractProcessor implements Processor { 
	
	protected ProcessingEnvironment processingEnv;
	private boolean initialized = false;

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		SupportedAnnotationTypes supportedTypes = this.getClass().getAnnotation(SupportedAnnotationTypes.class);
		if (supportedTypes == null) {
			if (initialized)
				processingEnv.getMessager().printMessage(MessageKind.WARNING,
					"No SupportedAnnotationTypes annotation " + "found on " + this.getClass().getName() +
						", returning an empty set.");
					return Collections.emptySet();
				} else
					return Sets.newHashSet(supportedTypes.value());
	}

	@Override
	public void init(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
		
	}
  
	@Override
	public boolean process(Set<XAnnotation> annotations, ScanEnvironment roundEnv) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation1,
			ExecutableElement member, String userText) {
		// TODO Auto-generated method stub
		return null;
	}

}
