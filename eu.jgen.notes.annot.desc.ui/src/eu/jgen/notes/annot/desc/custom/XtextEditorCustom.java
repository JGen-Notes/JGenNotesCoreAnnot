package eu.jgen.notes.annot.desc.custom;

import eu.jgen.notes.automation.wrapper.JGenModel;
import eu.jgen.notes.automation.wrapper.JGenObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.ca.gen.jmmi.schema.PrpTypeCode;
import com.ca.gen.jmmi.schema.PrpTypeHelper;

public class XtextEditorCustom extends XtextEditor {
	
	public static final String TXT_EU_JGEN_NOTES_ANNOT = "annotation";
	public static final String TXT_ID = "id";
	public static final String TXT_GENMODEL = "genModel";

	@Override
	public void doSave(IProgressMonitor progressMonitor) {		
		IFile file = (IFile) getResource();
		try {
			Integer id = (Integer) file.getSessionProperty(
					new QualifiedName(TXT_EU_JGEN_NOTES_ANNOT, TXT_ID));
			JGenModel genModel = (JGenModel) file.getSessionProperty(new QualifiedName(TXT_EU_JGEN_NOTES_ANNOT,
					TXT_GENMODEL));
			JGenObject genObject = genModel.getEncy().findObjectById(id.intValue());
			genObject.updateTextProperty(PrpTypeHelper.getCode(PrpTypeCode.DESC), getDocument().get());
			genModel.getEncy().commit();			
		} catch (CoreException e) {
			e.printStackTrace();
		}		 
		super.doSave(progressMonitor);
	}
}
