package eu.jgen.notes.annot.app.views

import eu.jgen.notes.automation.wrapper.JGenModel
import java.io.ByteArrayInputStream
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.ResourcesPlugin
import java.io.File
import eu.jgen.notes.automation.wrapper.JGenObject
import com.ca.gen.jmmi.schema.PrpTypeHelper
import com.ca.gen.jmmi.schema.PrpTypeCode
import com.ca.gen.jmmi.schema.ObjTypeHelper

class ProjectUtility {

	var static String[] internalNames = #["CONCAT", "CONCATMIXED", "CONCATDBCS", "LENGTH", "LENGTHDBCS", "SUBSTR",
		"SUBSTRMIXED", "SUBSTRDBCS", "TRIM", "TRIMMIXED", "TRIMDBCS", "VERIFY", "VERIFYDBCS", "FIND", "FINDDBCS",
		"UPPER", "UPPERMIXED", "UPPERDBCS", "TEXTNUM", "NUMTEXT", "DATETEXT", "DATENUM", "NUMDATE", "JULDATE",
		"DATEJUL", "DATEDAYS", "DAYS", "YEAR", "MONTH", "DAY", "DAYOFWEEK", "TIMETEXT", "TIMENUM", "NUMTIME", "HOUR",
		"MINUTE", "SECOND", "CYYDATE", "CYYNUM", "NUMCYY", "TIMESTAMP", "DATETIMESTAMP", "YEARTIMESTAMP",
		"MONTHTIMESTAMP", "DAYTIMESTAMP", "TIMETIMESTAMP", "HOURTIMESTAMP", "MINUTETIMESTAMP", "SECONDTIMESTAMP",
		"MICROSECOND", "DAYSTIMESTAMP", "$IEF"]

	def static boolean ifOnListOfInternalWorkspaces(String name) {
		for (String wsName : internalNames) {
			if (wsName.equals(name.trim())) {
				return true;
			}
		}
		return false;
	}

	def static IProject createProject(JGenModel genModel) {
		val workspace = ResourcesPlugin.getWorkspace()
		val root = workspace.getRoot()
		val project = root.getProject(genModel.localName)
		if (project.exists()) {
			project.delete(true, true, null)
		}
		project.create(null);
		project.open(null)
		cretateProjectInfastructure(project, genModel.localName, findAllExternalJars(genModel.modelPath))
		workspace.save(true, null);
		return project
	}

	def private static String[] findAllExternalJars(String modelPath) {
		val fullpath = modelPath + "annot\\";
		val dir = new File(fullpath);
		var listOfJars = newArrayList()
		dir.list
		for (element : dir.list) {
			if (element.endsWith(".jar")) {
				listOfJars.add(fullpath + "\\" + element)
			}
		}
		return listOfJars
	}

	def private static cretateProjectInfastructure(IProject project, String localName, String[] array) {
		val src = project.getFolder("src");
		src.create(IResource.NONE, true, null);
		val bin = project.getFolder("bin");
		bin.create(IResource.NONE, true, null);

		// create project file
		val fileProject = project.getFile(".project");
		if (fileProject.exists) {
			fileProject.delete(true, null)
		}
		var contents = createProjectContents(localName)
		var bytes = contents.getBytes();
		var source = new ByteArrayInputStream(bytes);
		fileProject.create(source, IResource.NONE, null);

		// create project class file
		val fileClasspath = project.getFile(".classpath");
		var contentClasspath = createProjectClasspath(array)
		bytes = contentClasspath.getBytes();
		source = new ByteArrayInputStream(bytes);
		fileClasspath.create(source, IResource.NONE, null);

	}

	def private static createProjectContents(String name) {
		val contents = '''
			<?xml version="1.0" encoding="UTF-8"?>
			<projectDescription>
				<name>«name»</name>
				<comment></comment>
				<projects>
				</projects>
				<buildSpec>
					<buildCommand>
						<name>org.eclipse.xtext.ui.shared.xtextBuilder</name>
						<arguments>
						</arguments>
					</buildCommand>
					<buildCommand>
						<name>org.eclipse.jdt.core.javabuilder</name>
						<arguments>
						</arguments>
					</buildCommand>
				</buildSpec>
				<natures>
					<nature>org.eclipse.jdt.core.javanature</nature>
					<nature>org.eclipse.xtext.ui.shared.xtextNature</nature>
				</natures>
			</projectDescription>
		'''
		return contents;
	}

	def private static createProjectClasspath(String[] listOfJars) {
		val contents = '''
			<?xml version="1.0" encoding="UTF-8"?>
			<classpath>
				<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
				<classpathentry kind="src" path="src"/>
				«FOR jarfilename : listOfJars»
					<classpathentry kind="lib" path="«jarfilename»"/>
				«ENDFOR»
				<classpathentry kind="output" path="bin"/>
			</classpath>
		'''
		return contents;
	}

	def public static String createFileName(JGenObject jGenObject) {
		val mnemonic = ObjTypeHelper.getMnemonic(ObjTypeHelper.valueOf(jGenObject.findObjectTypeCode as short))
		val name = jGenObject.findTextProperty(PrpTypeHelper.getCode(PrpTypeCode.NAME))
		val filename = mnemonic + "." + name + "."  + "desc"
		return filename
	}

}
