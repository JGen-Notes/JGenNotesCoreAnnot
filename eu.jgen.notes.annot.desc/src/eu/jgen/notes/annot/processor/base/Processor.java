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
package eu.jgen.notes.annot.processor.base;

import java.util.Set;

import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

public interface Processor {

 /**
     * Returns the names of the annotation types supported by this
     * processor.  Names are fully qualified.
      */
   public  Set<String> getSupportedAnnotationTypes();	
   
   /**
     * Initialises the processor with the processing environment.
     *
     * @param processingEnv environment for facilities the tool framework
     * provides to the processor
     */
    public  void init(ProcessingEnvironment processingEnv);
    
     /**
     * Processes a set of annotation types on type elements
     * originating from the Annotation Worker scanning list of  objects in the  CA Gen Model. 
     * It indicates whether or not these annotation types are claimed by this processor.  
     *
     * @param annotations the annotation types requested to be processed
     * @param scanEnv  environment for information about the current model scan
     * @return whether or not the set of annotation types are claimed by this processor
     */
    public  boolean process(Set<XAnnotation> annotations, ScanEnvironment scanEnv);
    
}