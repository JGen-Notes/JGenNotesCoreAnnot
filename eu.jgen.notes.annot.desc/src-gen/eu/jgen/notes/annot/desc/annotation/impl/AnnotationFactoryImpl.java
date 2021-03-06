/**
 * generated by Xtext 2.12.0
 */
package eu.jgen.notes.annot.desc.annotation.impl;

import eu.jgen.notes.annot.desc.annotation.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnnotationFactoryImpl extends EFactoryImpl implements AnnotationFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AnnotationFactory init()
  {
    try
    {
      AnnotationFactory theAnnotationFactory = (AnnotationFactory)EPackage.Registry.INSTANCE.getEFactory(AnnotationPackage.eNS_URI);
      if (theAnnotationFactory != null)
      {
        return theAnnotationFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new AnnotationFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case AnnotationPackage.METADATA: return createMetadata();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Metadata createMetadata()
  {
    MetadataImpl metadata = new MetadataImpl();
    return metadata;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationPackage getAnnotationPackage()
  {
    return (AnnotationPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static AnnotationPackage getPackage()
  {
    return AnnotationPackage.eINSTANCE;
  }

} //AnnotationFactoryImpl
