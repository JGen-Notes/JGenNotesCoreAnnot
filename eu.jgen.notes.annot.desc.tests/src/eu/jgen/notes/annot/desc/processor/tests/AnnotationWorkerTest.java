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
package eu.jgen.notes.annot.desc.processor.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ca.gen.jmmi.Ency;
import com.ca.gen.jmmi.EncyManager;
import com.ca.gen.jmmi.MMObj;
import com.ca.gen.jmmi.Model;
import com.ca.gen.jmmi.ModelManager;
import com.ca.gen.jmmi.exceptions.EncyUnsupportedOperationException;
import com.ca.gen.jmmi.ids.ObjId;
import com.ca.gen.jmmi.schema.ObjTypeCode;
import com.google.inject.Inject;

import eu.jgen.notes.annot.desc.processor.AnnotationWorker;
import eu.jgen.notes.annot.desc.processor.FilerImpl;
import eu.jgen.notes.annot.desc.processor.MessagerImpl;
import eu.jgen.notes.annot.desc.processor.ProcessingEnvironmentImpl;
import eu.jgen.notes.annot.desc.processor.ScanEnvironmentImpl;
 

/**
 * This test class checks basic functionality of the <code>AnnotationWorker</code>.
 * 
 * @author Marek Stankiewicz
 *
 */

@RunWith(XtextRunner.class)
@InjectWith(AnnotationInjectorProvider.class)

public class AnnotationWorkerTest {
	
	@Inject   
	  AnnotationWorker annotationWorker;
	private static Model model;
	private static Ency ency;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ency = EncyManager.connectLocalForReadOnly("C:\\jgen.notes.models.test\\tstan01.ief");
		model = ModelManager.open(ency, ency.getModelIds().get(0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		model.close();
		ency.disconnect();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loadAnnotationWorkerTest() {
		assertTrue(annotationWorker instanceof AnnotationWorker);
	}
	
	@Test
	public void loadProcessingEnvironmentTest() {
		assertTrue(annotationWorker.getProcessingEnv() instanceof ProcessingEnvironmentImpl);
	}
	
	@Test
	public void initAnnotationWorkerTest() {
		Map<String, String> options = new HashMap<String, String>();
		options.put("optionname", "optionvalue");
		annotationWorker.init(model, new TestProcessor(), options);
		assertTrue(annotationWorker. getProcessingEnv().getEncy() == ency);
		assertTrue(annotationWorker. getProcessingEnv().getModel() == model);
		assertTrue(annotationWorker. getProcessingEnv().getResourceSet() instanceof XtextResourceSet);
		assertTrue(annotationWorker. getProcessingEnv().getOptions().get("optionname").equals("optionvalue"));
		assertTrue(annotationWorker. getProcessingEnv().getMessager() instanceof MessagerImpl);
		assertTrue(annotationWorker. getProcessingEnv().getFiler() instanceof FilerImpl);
		assertTrue(annotationWorker.getMessager() instanceof MessagerImpl);
		assertTrue(annotationWorker.getFiler() instanceof FilerImpl);
		assertTrue(annotationWorker.getMessager() == annotationWorker. getProcessingEnv().getMessager());
		assertTrue(annotationWorker.getFiler() == annotationWorker. getProcessingEnv().getFiler());
		assertTrue(annotationWorker.getScanEnvironment() instanceof ScanEnvironmentImpl);
	}
	
	@Test
	public void setSourcesObjIdTest() {
		Map<String, String> options = new HashMap<String, String>();
		options.put("optionname", "optionvalue");
		annotationWorker.init(model, new TestProcessor(), options);
		try {
			List<ObjId> list = model.getObjIds(ObjTypeCode.ACBLKBSD);
			annotationWorker.setSources(list);
			assertTrue(annotationWorker. getSources().length == 3);
			assertTrue(annotationWorker. getSourcesAnnotated().size() == 2);
			annotationWorker.activate();
			assertTrue(annotationWorker.getProcessor() instanceof TestProcessor);
			assertTrue(annotationWorker. getScanEnvironment().getScanResult().size() == 2);
		} catch (EncyUnsupportedOperationException e) {
			fail(e.getMessage());
		}		
	}
	
	@Test
	public void setSourcesMMObjTest() {
		Map<String, String> options = new HashMap<String, String>();
		options.put("optionname", "optionvalue");
		annotationWorker.init(model, new TestProcessor(), options);
		try {
			List<ObjId> list = model.getObjIds(ObjTypeCode.ACBLKBSD);
			MMObj[] array = new MMObj[list.size()];
			int i = 0;
			for (ObjId objId : list) {
				array[i] = MMObj.getInstance(model, objId);
				i++;
			}
			annotationWorker.setSources(array);
			assertTrue(annotationWorker. getSources().length == 3);
			assertTrue(annotationWorker. getSourcesAnnotated().size() == 2);
			annotationWorker.activate();
			assertTrue(annotationWorker.getProcessor() instanceof TestProcessor);
			assertTrue(annotationWorker. getScanEnvironment().getScanResult().size() == 2);
			Set<String> set = annotationWorker.getProcessor().getSupportedAnnotationTypes();
			for (@SuppressWarnings("unused") String string : set) {
				assertTrue(set.contains("eu.jgen.notes.annot.desc.processor.tests.Ab01"));
				assertTrue(set.contains("eu.jgen.notes.annot.desc.processor.tests.Ab02"));
			}
			assertTrue(annotationWorker. getScanEnvironment().getElementsAnnotatedWith(eu.jgen.notes.annot.desc.processor.tests.Ab01.class).size() == 1);
			assertTrue(annotationWorker. getScanEnvironment().getElementsAnnotatedWith(eu.jgen.notes.annot.desc.processor.tests.Ab02.class).size() == 1);			
		} catch (EncyUnsupportedOperationException e) {
			fail(e.getMessage());
		}		
	}

}
