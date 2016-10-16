package cz.ormframework.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class JarUtils {

    /**
     * Gets jar file.
     *
     * @param mainInstance the main instance
     * @return the jar file
     */
    public static File getJarFile(Object mainInstance) {
        return getJarFile(mainInstance.getClass());
    }

    /**
     * Gets jar file.
     *
     * @param clazzMainInstance the clazz main instance
     * @return the jar file
     */
    public static File getJarFile(Class<?> clazzMainInstance) {
        try {
            return new File(clazzMainInstance.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
