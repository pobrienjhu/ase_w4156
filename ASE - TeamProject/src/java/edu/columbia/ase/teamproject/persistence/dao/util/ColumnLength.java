package edu.columbia.ase.teamproject.persistence.dao.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface ColumnLength.
 */
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ColumnLength {

	/**
	 * Value.
	 *
	 * @return the int
	 */
	public int value() default -1;

}
