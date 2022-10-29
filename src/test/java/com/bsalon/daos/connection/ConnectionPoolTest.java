package com.bsalon.daos.connection;

import com.bsalon.exceptions.ConnectionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author @bkalika
 */
public class ConnectionPoolTest {

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    public void testGetInstance_ShouldReturnTheSameClass_Always() {
        ConnectionPool poolFirst = ConnectionPool.getInstance();
        ConnectionPool poolSecond = ConnectionPool.getInstance();

        assertEquals(poolFirst, poolSecond);
    }

    @Test
    public void testGetConnection_ShouldReturnTrue_WhenConnectionValidTenSeconds() throws SQLException {
        assertTrue(ConnectionPool.getInstance().getConnection().isValid(10));
    }
}
