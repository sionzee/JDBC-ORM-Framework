package cz.ormframework.database;

import cz.ormframework.log.Debug;
import cz.ormframework.queries.QueryBase;
import cz.ormframework.queries.SQLiteQueryBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class SQLite implements Database {

    private File file;
    private Connection connection;
    private QueryBase queryBase;

    public SQLite(File file) {
        this.file = file;
        this.queryBase = new SQLiteQueryBase();
    }

    @Override
    public boolean validateClasses() {
        try {
            Class.forName("org.sqlite.JDBC");
            return true;
        } catch (ClassNotFoundException e) {
            Debug.error("org.sqlite.JDBC not found! Can't create SQLite Connection!\nInstall driver or choose another type of connection.");
        }
        return false;
    }

    @Override
    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", file.getAbsolutePath()));
            connection.setAutoCommit(false);
            return true;
        } catch (SQLException e) {
            Debug.error(e.getMessage());
            return false;
        }
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
