package cz.ormframework.database;

import cz.ormframework.log.Debug;
import cz.ormframework.queries.MySQLQueryBase;
import cz.ormframework.queries.QueryBase;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by siOnzee on 10/16/2016.
 */
public class Dynamic implements Database {

    private Connection connection;
    private QueryBase queryBase;

    public Dynamic(Connection connection) {
        this.connection = connection;
        this.queryBase = new MySQLQueryBase();
    }

    @Override
    public boolean validateClasses() {
        return true;
    }

    @Override
    public boolean openConnection() {
        return true;
    }

    @Override
    public boolean isClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            Debug.exception(e);
        }
        return false;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            Debug.exception(e);
        }
    }

    @Override
    public QueryBase getQueryBase() {
        return queryBase;
    }
}
