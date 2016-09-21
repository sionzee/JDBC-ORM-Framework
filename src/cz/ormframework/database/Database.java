package cz.ormframework.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public interface Database {

    /**
     * Validate classes boolean.
     *
     * @return true if all clases are available
     */
    boolean validateClasses();

    /**
     * Open connection
     * local = DriverManager....
     *
     * @return the boolean
     */
    boolean openConnection();

    /**
     * Is connection closed?
     *
     * @return true if closed
     */
    boolean isClosed();

    /**
     * Return a connection instance
     *
     * @return connection instance
     */
    Connection getConnection();

    /**
     * Close connection
     */
    void close();

}
