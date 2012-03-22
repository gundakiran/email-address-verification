/**
 * 
 */
package com.llt.email.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that represents the database entity property
 * 
 * @Column annotation mapping is used to populate the
 * object from the database, and also used to identify
 * which field is search able. This object could be used
 * to perform the search and stored the search results.
 * 
 * @see com.bcbsa.ca.annotation.Column
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * name of the data base entity column name
	 * 
	 * @return string-db column name
	 */
	String name();

}
