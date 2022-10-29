package com.bsalon.daos.jdbc;

import com.bsalon.daos.DAOException;
import com.bsalon.daos.IServiceDAO;
import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.daos.connection.ProxyConnection;
import com.bsalon.models.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bsalon.daos.jdbc.Util.createStatement;
import static com.bsalon.constants.SQLConstants.*;

/**
 * This class represents a concrete JDBC implementation of the {@link IServiceDAO} interface.
 *
 * @author @bkalika
 */
public class ServiceDAOJDBC implements IServiceDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceDAOJDBC.class);

    @Override
    public Service find(Long id) throws DAOException {
        LOGGER.trace("Start tracing ServiceDAOJDBC#find");

        Service service = null;

        try(PreparedStatement preparedStatement = createStatement(SQL_SELECT_SERVICE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                service = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return service;
    }

    @Override
    public List<Service> list() throws DAOException {
        LOGGER.trace("Start tracing ServiceDAOJDBC#list");

        List<Service> services = new ArrayList<>();

        try(
                ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_SERVICE)
        ) {
            while (resultSet.next()) {
                services.add(map(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return services;
    }

    @Override
    public boolean create(Service service) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing ServiceDAOJDBC#create");

        try(PreparedStatement preparedStatement = createStatement(SQL_INSERT_SERVICE)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setBigDecimal(2, service.getPrice());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public void update(Service service) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing ServiceDAOJDBC#update");

        try(PreparedStatement preparedStatement = createStatement(SQL_UPDATE_SERVICE)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setBigDecimal(2, service.getPrice());
            preparedStatement.setLong(3, service.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void delete(Service service) throws DAOException {
        LOGGER.trace("Starting tracing ServiceDAOJDBC#delete");

        try(PreparedStatement preparedStatement = createStatement(SQL_DELETE_SERVICE)){
            preparedStatement.setLong(1, service.getId());
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static Service map(ResultSet resultSet) throws SQLException {
        Service service = new Service();
        service.setId(resultSet.getLong("id"));
        service.setName(resultSet.getString("name"));
        service.setPrice(resultSet.getBigDecimal("price"));

        return service;
    }
}
