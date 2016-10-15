package cz.ormframework.utils;

import java.sql.Date;

/**
 * Created by siOnzee on 10/15/2016.
 */
public class Dates {
    /**
     * Now date.
     *
     * @return the date
     */
    public static Date now() {
        return new Date(new java.util.Date().getTime());
    }

}
