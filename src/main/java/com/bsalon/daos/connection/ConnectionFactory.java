package com.bsalon.daos.connection;

import com.bsalon.exceptions.ConnectionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author @bkalika
 */
public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";

    static ProxyConnection createConnection(Properties dbProperties) throws ConnectionException {
        ProxyConnection proxyConnection;
        try {
            String dbUrl = dbProperties.getProperty(DB_URL);
            String dbUser = dbProperties.getProperty(DB_USER);
            String dbPassword = dbProperties.getProperty(DB_PASSWORD);
            Class.forName(dbProperties.getProperty(DB_DRIVER));
            proxyConnection = new ProxyConnection(DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        } catch (SQLException e) {
            LOGGER.error("Unable to connect to DB!", e);
            throw new ConnectionException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("PostgresSQL JDBC driver not found!", e);
            throw new ConnectionException(e.getMessage(), e);
        }
        return proxyConnection;
    }
}
