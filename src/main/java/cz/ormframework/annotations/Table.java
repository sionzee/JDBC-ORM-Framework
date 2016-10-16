package cz.ormframework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * Custom table name or default simple class name
     *
     * @return the string
     */
    String value() default "";
}
