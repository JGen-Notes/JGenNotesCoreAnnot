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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;

import com.ca.gen.jmmi.MMObj;
import com.ca.gen.jmmi.Model;
import com.ca.gen.jmmi.ids.ObjId;
import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;
import com.google.inject.Inject;

/**
 * This class allows to retrieve annotations by scanning provided list of objects  
 * found in the CA Gen Model located on the workstation.
 * <p>
 *  AnnotationWorker looks for <code>DESC</code> text properties marked with 
 * <code>#meta</code> in the first five characters. Once marker found,
 * the AnnotationWorker parses entire text looking for the valid annotations.
 * Annotation worker invokes provided <code>Processor</code> class and executes
 *  <code>process()</code> method once  initial discovery process ends with
 *  none-empty list of qualifying and valid annotations.
 * <p>
 * @author Marek Stankiewicz
 * @since 1.0
 */
public class AnnotationWorker {

	private MMObj[] sources;
	
	private List<MMObj> sourcesAnnotated;
	
	private Processor processor;

	private Set<XAnnotation> annotations = new HashSet<XAnnotation>();

	@Inject
	private XtextResourceSet resourceSet;

	@Inject
	private ProcessingEnvironment processingEnv;
 
	@Inject
	private Filer filer;

	@Inject
	private Messager messager; 

	@Inject
	private ScanEnvironment scanEnvironment;
	
	Map<String,String> options;
	
	public AnnotationWorker() {
	} 
	
	public void activate() {
		processor.init(processingEnv);
		scanEnvironment.init(resourceSet, processingEnv, sourcesAnnotated);
		processor.process(annotations, scanEnvironment);
	} 
	
	private List<MMObj> filterAnnotatedOnly() {		
		List<MMObj> list = new Vector<MMObj>();
		 for (MMObj mmObj : sources) {
			if( PrpTypeHelper.isValid(mmObj.getObjTypeCode(), PrpTypeCode.DESC)) {
				String description = mmObj.getTextProperty(PrpTypeCode.DESC);
				if (description.length() >= 5 && description.startsWith("#meta")) {
					list.add(mmObj);
				}
			} 
		}
		return list;
	}

	public Filer getFiler() {
		return filer;
	}

	public Messager getMessager() {
		return messager;
	}

	public ProcessingEnvironment getProcessingEnv() {
		return processingEnv;
	}

	public Processor getProcessor() {
		return processor;
	}

	public ScanEnvironment getScanEnvironment() {
		return scanEnvironment;
	}
   
	public MMObj[] getSources() {
		return sources;
	}

	public List<MMObj> getSourcesAnnotated() {
		return sourcesAnnotated;
	}

	public AnnotationWorker init(Model model, Processor processor, Map<String,String> options) {
		this.processor = processor;
		this.options = options;
		this.processingEnv.setOptions(options); 
		this.processingEnv.setResourceSet(resourceSet); 
		this.processingEnv.init(model, options, messager, filer);
		return this;
	}

	public AnnotationWorker setSources(List<ObjId> list) {
		this.sources = new MMObj[list.size()];
		int i = 0;
		for (ObjId objId : list) {
			this.sources[i]= MMObj.getInstance(processingEnv.getModel(), objId);
			i++;
		}
		sourcesAnnotated = filterAnnotatedOnly();
		return this;
	}

	public AnnotationWorker setSources(MMObj[] sources) {
		this.sources = sources;	
		sourcesAnnotated = filterAnnotatedOnly();
		return this;
	}

}
