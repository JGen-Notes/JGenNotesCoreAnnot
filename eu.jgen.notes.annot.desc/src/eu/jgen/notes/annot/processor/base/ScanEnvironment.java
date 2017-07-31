package eu.jgen.notes.annot.processor.base;

 
import java.util.Set;
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;
import eu.jgen.notes.annot.processor.impl.AnnotationObject;

public interface ScanEnvironment {
	
	 void setScanResult( Set<AnnotationObject> foundObjects);

	 /**
     * Returns {@code true} if types generated by this round will not
     * be subject to a subsequent round of annotation processing;
     * returns {@code false} otherwise.
     *
     * @return {@code true} if types generated by this round will not
     * be subject to a subsequent round of annotation processing;
     * returns {@code false} otherwise
     */
    boolean processingOver();

    /**
     * Returns {@code true} if an error was raised in the prior round
     * of processing; returns {@code false} otherwise.
     *
     * @return {@code true} if an error was raised in the prior round
     * of processing; returns {@code false} otherwise
     */
    boolean errorRaised();

    /**
     * Returns the elements annotated with the given annotation type.
     * The annotation may appear directly or be inherited.  Only
     * package elements and type elements <i>included</i> in this
     * round of annotation processing, or declarations of members,
     * constructors, parameters, or type parameters declared within
     * those, are returned.  Included type elements are {@linkplain
     * #getRootElements root types} and any member types nested within
     * them.  Elements in a package are not considered included simply
     * because a {@code package-info} file for that package was
     * created.
     *
     * @param a  annotation type being requested
     * @return the elements annotated with the given annotation type,
     * or an empty set if there are none
     * @throws IllegalArgumentException if the argument does not
     * represent an annotation type
     */
    Set<? extends AnnotationObject> getElementsAnnotatedWith(Class<? extends XAnnotation> a);

	Set<AnnotationObject> getScanResult();
	
}