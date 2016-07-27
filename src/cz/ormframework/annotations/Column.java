
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
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * Column name in a database. Default is field name
     *
     * @return the string
     */
    String value() default "";

    /**
     * Can be value null?
     *
     * @return Default true
     */
    boolean isNullable() default true;

    /**
     * Column type (int, varchar, timestamp...) or match default by Repository
     *
     * @return the string
     */
    String type() default "";

    /**
     * Lenght for a like Varchar(value)
     * Add number with squares after type
     * Example: type() + (length())
     *
     * @return the int
     */
    int length() default -1;

    /**
     * Will be column indexed?
     *
     * @return true for indexing
     */
    boolean indexed() default false;

    /**
     * Is Array or List?
     *
     * @return true for collection
     */
    boolean isCollection() default false;
}
