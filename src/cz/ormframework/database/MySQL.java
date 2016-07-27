package cz.ormframework.database;

import cz.ormframework.log.Debug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class MySQL implements Database {

    private String host = "localhost", db = "minecraft", user, pass;
    private int port = 3306;
    private Connection connection;

    /**
     * Instantiates a new My sql.
     *
     * @param host the host
     * @param db   the db
     * @param user the user
     * @param pass the pass
     */
    public MySQL(String host, String db, String user, String pass) {
        this.host = host;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Instantiates a new My sql.
     *
     * @param db   the db
     * @param user the user
     * @param pass the pass
     */
    public MySQL(String db, String user, String pass) {
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Instantiates a new My sql.
     *
     * @param user the user
     * @param pass the pass
     */
    public MySQL(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /**
     * Instantiates a new My sql.
     *
     * @param host the host
     * @param db   the db
     * @param user the user
     * @param pass the pass
     * @param port the port
     */
    public MySQL(String host, String db, String user, String pass, int port) {
        this.host = host;
        this.db = db;
        this.user = user;
        this.pass = pass;
        this.port = port;
    }

    @Override
    public boolean validateClasses() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            Debug.error("com.mysql.jdbc.Driver not found! Can't create MySQL Connection!\nInstall driver or choose another type of connection.");
        }
        return false;
    }

    @Override
    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", host, port, db), user, pass);
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
}
