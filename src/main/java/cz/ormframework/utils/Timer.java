package cz.ormframework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class Timer {

    private static Map<Integer, Long> times = new HashMap<>();

    /**
     * Get long.
     *
     * @param id the id
     * @return the long
     */
    public static long get(int id) {
        long toReturn = times.get(id);
        times.remove(id);
        return toReturn;
    }

    /**
     * Start.
     *
     * @param id the id
     */
    public static void start(int id) {
        times.put(id, System.nanoTime());
    }

    /**
     * End.
     *
     * @param id the id
     */
    public static void end(int id) {
        times.put(id, System.nanoTime() - times.get(id));
    }

}
