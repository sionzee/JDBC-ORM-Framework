package cz.ormframework.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class Timestamps {

    /**
     * Now timestamp.
     *
     * @return the timestamp
     */
    public static Timestamp now() {
        return new Timestamp(new Date().getTime());
    }

}
