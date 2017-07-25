/**
 * [The "BSD license"]
 * Copyright (c) 2016,JGen Notes
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
package eu.jgen.notes.annot.app.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.ca.gen.jmmi.schema.AscTypeCode;
import com.ca.gen.jmmi.schema.AscTypeHelper;
import com.ca.gen.jmmi.schema.ObjTypeCode;
import com.ca.gen.jmmi.schema.ObjTypeHelper;
import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;

import eu.jgen.notes.automation.wrapper.JGenModel;
import eu.jgen.notes.automation.wrapper.JGenObject;

public class AnnotationsTreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	private JGenModel genModel;

	public AnnotationsTreeContentProvider(JGenModel genModel) {
		this.genModel = genModel;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof JGenModel) {
			return new Object[] { genModel.findTypeObjects(ObjTypeHelper.getCode(ObjTypeCode.PCROOT))[0] };
		}
		if (inputElement instanceof StartingPoint) {
			StartingPoint startingPoint = (StartingPoint) inputElement;

			return startingPoint.getObjects();
		}
		return new Object[] {};
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof JGenObject) {
			JGenObject genObject = (JGenObject) parentElement;
			int code = genObject.getObjTypeCode();
			if (code == ObjTypeHelper.getCode(ObjTypeCode.PCROOT)) {
				JGenObject subj = genObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.HASSUBJ));
				return new Object[] { subj, new NodeFolder(genObject, "Business Systems", "folder.jpg",
						ObjTypeHelper.getCode(ObjTypeCode.PCROOT)) };
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.SUBJ)) {
				JGenObject[] array1 = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				JGenObject[] array2 = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.DETLBY));
				List<Object> list = new ArrayList<Object>(Arrays.asList(array1));
				for (JGenObject jGenObject : array2) {
					if (jGenObject.getObjTypeCode() == ObjTypeHelper.getCode(ObjTypeCode.HLENTDS)) {
						if (!ProjectUtility.ifOnListOfInternalWorkspaces(
								jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME)))) {
							list.add(jGenObject);
						}
					} else {
						list.add(jGenObject);
					}
				}
				return list.toArray();
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.HLENTDS)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.DSCBYA));
				return array;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.HLENT)) {
				JGenObject[] array1 = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.DSCBYA));
				JGenObject[] array2 = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.DSCBYR));
				List<Object> list = new ArrayList<Object>(Arrays.asList(array1));
				list.addAll(Arrays.asList(array2));
				return list.toArray();
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				List<Object> list = new ArrayList<Object>(Arrays.asList(array));
				list.add(new NodeFolder(genObject, "Owned Action Blocks", "folder.jpg",
						ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)));
				return list.toArray();
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.BUSPROC)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				return array;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.WNGROUP)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.GROUPS));
				return array;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.BUSPRST)) {
				JGenObject object = genObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.DEFNDBY));
				JGenObject[] array2 = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTNS));
				List<Object> list = new ArrayList<Object>(Arrays.asList(new Object[] { object }));
				list.addAll(Arrays.asList(array2));
				return list.toArray();
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.WINPRIME)) {
				return filterUiElements(genObject);
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.DLGBOX)) {
				return filterUiElements(genObject);
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				List<Object> list = new ArrayList<Object>();
				for (JGenObject jGenObject : array) {
					JGenObject object = jGenObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.REFS));
					int jcode = jGenObject.getObjTypeCode();
					if (jcode == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)
							|| jcode == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {
						list.add(object);
					}
				}
				return list.toArray();
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				List<Object> list = new ArrayList<Object>();
				for (JGenObject jGenObject : array) {
					JGenObject object = jGenObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.REFS));
					int jcode = jGenObject.getObjTypeCode();
					if (jcode == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)
							|| jcode == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {
						list.add(object);
					}
				}
				return list.toArray();
			}
		}
		if (parentElement instanceof NodeFolder) {
			NodeFolder nodeFolder = (NodeFolder) parentElement;
			if (nodeFolder.getCode() == ObjTypeHelper.getCode(ObjTypeCode.PCROOT)) {
				JGenObject[] array = nodeFolder.getGenObject()
						.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.HASBUSYS));
				return array;
			}
			if (nodeFolder.getCode() == ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)) {
				JGenObject[] array = nodeFolder.getGenObject()
						.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.OWNS));
				return array;
			}

		}
		return new Object[] {};
	}

	private Object[] filterUiElements(JGenObject genObject) {
		JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.BOUNDS));
		List<Object> list = new ArrayList<Object>();
		for (JGenObject jGenObject : array) {
			int jcode = jGenObject.getObjTypeCode();
			if (jcode == ObjTypeHelper.getCode(ObjTypeCode.SNGLNFLD)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.BITMAP)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.STNDLST)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.WINLIT)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.CHKBOX)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.COMBGRP)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.MLTLNFLD)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.WNGROUP)
					|| jcode == ObjTypeHelper.getCode(ObjTypeCode.PUSHBTN)) {
				list.add(jGenObject);
			}
		}
		return list.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof JGenObject) {
			JGenObject genObject = (JGenObject) element;
			int code = genObject.getObjTypeCode();
			if (code == ObjTypeHelper.getCode(ObjTypeCode.SNGLNFLD)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.PUSHBTN)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.BITMAP)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.STNDLST)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.WINLIT)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.CHKBOX)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.COMBGRP)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.WNGROUP)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.GROUPS));
				if (array.length == 0) {
					return false;
				} else {
					return true;
				}
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.MLTLNFLD)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.ATTRUSR)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.RELMM)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)) {
				return true;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.RDBTNOC)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.SCREEN)) {
				return false;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.WINPRIME)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				if (array.length == 0) {
					return false;
				}
				return true;
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				List<Object> list = new ArrayList<Object>();
				for (JGenObject jGenObject : array) {
					JGenObject object = jGenObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.REFS));
					list.add(object);
				}
				if (list.size() == 0) {
					return false;
				} else {
					return true;
				}
			}
			if (code == ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)) {
				JGenObject[] array = genObject.findAssociationMany(AscTypeHelper.getCode(AscTypeCode.CONTAINS));
				List<Object> list = new ArrayList<Object>();
				for (JGenObject jGenObject : array) {
					JGenObject object = jGenObject.findAssociationOne(AscTypeHelper.getCode(AscTypeCode.REFS));
					list.add(object);
				}
				if (list.size() == 0) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

}
