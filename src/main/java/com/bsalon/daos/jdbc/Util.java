package com.bsalon.daos.jdbc;

import com.bsalon.exceptions.DAOException;
import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.daos.connection.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author @bkalika
 */
public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class);

    public static PreparedStatement createStatement(String query, Object... params) throws DAOException {
        try {
            ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = proxyConnection.prepareStatement(query);
            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params);
            }
            ConnectionPool.getInstance().releaseConnection(proxyConnection);
            return preparedStatement;
        } catch (SQLException e) {
            LOGGER.error("Unable to create statement!", e);
            throw new DAOException(e.getMessage(), e);
        }
    }
}
