package cz.ormframework.utils;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
public class StringUtils {
    public static String firstUpper(String string) {
        return string.substring(0, 1).toUpperCase() + string.toLowerCase().substring(1, string.length());
    }

}
