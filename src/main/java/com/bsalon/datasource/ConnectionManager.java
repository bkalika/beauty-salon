package com.bsalon.datasource;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author @bkalika
 */
public class ConnectionManager {
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class);

    private static final ConnectionManager instance = new ConnectionManager();
    private final DataSource dataSource;

    private ConnectionManager() {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/beauty_salon");
        } catch (NamingException e) {
            throw new IllegalStateException("jdbc/beauty_salon_db is mission in JNDI!", e);
        }
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        LOGGER.trace("Starting tracing ConnectionManager#getConnection");

        return dataSource.getConnection();
    }
}
