package cz.ormframework.utils;

import java.math.BigInteger;
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

    public static String randomString(int size) {
        SecureRandom sr = new SecureRandom();
        return new BigInteger(size, sr).toString(32);
    }
}
