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

import java.util.Collections;
import java.util.Set;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import com.google.common.collect.Sets;

/**
 * An abstract annotation processor designed to be a convenient
 * superclass for most concrete annotation processors.  
 * 
 * @author Marek Stankiewicz
 * @since 1.0
 */
public  abstract class  AbstractProcessor implements Processor { 
	
	public AbstractProcessor() {
		super();
	}

	protected ProcessingEnvironment processingEnv;
	private boolean initialized = false;

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		SupportedAnnotationTypes supportedTypes = this.getClass().getAnnotation(SupportedAnnotationTypes.class);
		if (supportedTypes == null) {
			if (initialized)
				processingEnv.getMessager().printMessage(DiagnosticKind.WARNING,
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
	public boolean process(Set<XAnnotation> annotations, ScanEnvironment scanEnvironment) {
		return false;
	}

}
