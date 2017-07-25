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

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.ca.gen.jmmi.schema.ObjTypeCode;
import com.ca.gen.jmmi.schema.ObjTypeHelper;
import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;

import eu.jgen.notes.automation.wrapper.JGenObject;

public class AnnotationsLaberProvider extends LabelProvider {
	
	private final Image IMG_FOLDER = getImage("folder.gif");
	private final Image IMG_MODEL = getImage("model.gif");
	private final Image IMG_SUBJECTAREA = getImage("subjectarea.gif");
	private final Image IMG_WORKSET = getImage("workset.gif");
	private final Image IMG_ENTITY = getImage("entity.gif");
	private final Image IMG_ATTRIBUTE = getImage("attribute.gif");
	private final Image IMG_RELATIONSHIP= getImage("relationship.gif");
	private final Image IMG_BUSSYS= getImage("bussys.png");
	private final Image IMG_PROCEDURE= getImage("procedure.gif");
	private final Image IMG_PROCEDURE_STEP= getImage("procedurestep.gif");
	private final Image IMG_ACTIONBLOCK= getImage("actionblock.gif");
	private final Image IMG_WINDOW= getImage("window.png");
	private final Image IMG_DIALOGBOX= getImage("dialogbox.png");
	private final Image IMG_BUTTON= getImage("button.png");
	private final Image IMG_ENTRYFIELD= getImage("entryfield.png");
	private final Image IMG_PICTURE = getImage("picture.png");
	private final Image IMG_LITERAL = getImage("literal.png");
	private final Image IMG_LISTBOX = getImage("listbox.png");
	private final Image IMG_CHECKBOX = getImage("checkbox.png");
	private final Image IMG_DROPDOWN = getImage("dropdown.gif");
	private final Image IMG_GROUP = getImage("group.png");
	private final Image IMG_MULTILINE = getImage("multiline.png");
	private final Image IMG_RADIOBUTTON= getImage("radiobutton.png");
	private final Image IMG_SCREEN= getImage("screen.jpg");
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof JGenObject) {
			JGenObject genObject = ( JGenObject) element;
			int code = genObject.getObjTypeCode();
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.PCROOT)) {
				return IMG_MODEL;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SUBJ)) {
				return IMG_SUBJECTAREA;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.HLENTDS)) {
				return IMG_WORKSET;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.HLENT)) {
				return IMG_ENTITY;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ATTRUSR)) {
				return IMG_ATTRIBUTE;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.RELMM)) {
				return IMG_RELATIONSHIP;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)) {
				return IMG_BUSSYS;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSPROC)) {
				return IMG_PROCEDURE;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSPRST)) {
				return IMG_PROCEDURE_STEP;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {
				return IMG_ACTIONBLOCK;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)) {
				return IMG_ACTIONBLOCK;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WINPRIME)) {
				return IMG_WINDOW;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.DLGBOX)) {
				return IMG_DIALOGBOX;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.PUSHBTN)) {
				return IMG_BUTTON;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SNGLNFLD)) {
				return IMG_ENTRYFIELD;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WINLIT)) {
				return IMG_LITERAL;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BITMAP)) {
				return IMG_PICTURE;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.STNDLST)) {
				return IMG_LISTBOX;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.CHKBOX)) {
				return IMG_CHECKBOX;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.COMBGRP)) {
				return IMG_DROPDOWN;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WNGROUP)) {
				return IMG_GROUP;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.MLTLNFLD)) {
				return IMG_MULTILINE;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.RDBTNOC)) {
				return IMG_RADIOBUTTON;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SCREEN)) {
				return IMG_SCREEN;
			}
		}
		if(element instanceof NodeFolder) {
			return IMG_FOLDER;
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof JGenObject) {
			JGenObject genObject = ( JGenObject) element;
			int code = genObject.getObjTypeCode();
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.PCROOT)) {
				return "Model: " + genObject.getModel().getName();
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SUBJ)) {
				return "Subject Area: " + genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.HLENTDS)) {
				return "Workset: " + genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.HLENT)) {
				return "Entity: " + genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ATTRUSR)) {
				return "Attribue: " + genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.RELMM)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Relationship: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSSYS)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Business System: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSPROC)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Procedure: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BUSPRST)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Procedure Step:" + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ACBLKBSD)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Action Block: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.ACBLKBAA)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Business Action Block: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WINPRIME)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Window: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.DLGBOX)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Dialog Box: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SNGLNFLD)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Field: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.PUSHBTN)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Push Button: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.BITMAP)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Picture: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.STNDLST)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Listbox: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WINLIT)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Literal: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.CHKBOX)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Check Box: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.COMBGRP)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Combo Box: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.WNGROUP)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.MLTLNFLD)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Multi Line Field: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.RDBTNOC)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Radio Button: " + name;
			}
			if(code ==  ObjTypeHelper.getCode(ObjTypeCode.SCREEN)) {				
				String name = genObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME));
				return "Screen: " + name;
			}
		}
		if(element instanceof NodeFolder) {
			return ( (NodeFolder) element).getDescription();
		}
		return null;
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(AnnotationsLaberProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();
	}
}
