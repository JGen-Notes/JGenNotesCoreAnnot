package eu.jgen.notes.annot.processor.tests.sample;

public @interface Author {
	  public String name();
	  public String date() default "";
	  public String version() default "1.0";
	} 