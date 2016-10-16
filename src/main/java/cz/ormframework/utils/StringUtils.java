package cz.ormframework.utils;

import java.security.SecureRandom;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
public class StringUtils {
    public static String firstUpper(String string) {
        return string.substring(0, 1).toUpperCase() + string.toLowerCase().substring(1, string.length());
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int size) {
        StringBuilder sb = new StringBuilder(size);
        for(int i = 0; i < size; i++ )
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
