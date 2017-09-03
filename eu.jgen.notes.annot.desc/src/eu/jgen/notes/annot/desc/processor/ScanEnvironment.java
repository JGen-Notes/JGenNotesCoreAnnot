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

 
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.resource.XtextResourceSet;

import com.ca.gen.jmmi.MMObj;

 

/** *
 * @author Marek Stankiewicz
 * @since 1.0
 */
public interface ScanEnvironment {
	
	void init( XtextResourceSet resourceSet, ProcessingEnvironment processingEnv, List<MMObj> list);  
	
	 void setScanResult( Set<AnnotationObject> foundObjects);

    /**
     * Returns {@code true} if an error was raised in the scan; 
     * returns {@code false} otherwise.
     *
     * @return {@code true} if an error was raised in the prior round
     * of processing; returns {@code false} otherwise
     */
    boolean errorRaised();

    /**
     * Returns the elements annotated with the given annotation type.
     *
     * @param a  annotation type being requested
     * @return the elements annotated with the given annotation type,
     * or an empty set if there are none
     * @throws IllegalArgumentException if the argument does not
     * represent an annotation type
     */
    Set<AnnotationObject> getElementsAnnotatedWith(Class<? extends Annotation> a);

	Set<AnnotationObject> getScanResult();
	
}
