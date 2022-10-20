package com.bsalon.daos.jdbc;

import com.bsalon.daos.DAOException;
import com.bsalon.daos.IServiceHairdresserDAO;
import com.bsalon.datasource.ConnectionManager;
import com.bsalon.models.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bsalon.constants.SQLConstants.*;

/**
 * This class represents a concrete JDBC implementation of the {@link IServiceHairdresserDAO} interface.
 *
 * @author @bkalika
 */
public class ServiceHairdresserDAOJDBC implements IServiceHairdresserDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceHairdresserDAOJDBC.class);

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public ServiceHairdresser find(Long id) throws DAOException {
        LOGGER.trace("Start tracing ServiceHairdresserDAOJDBC#find");

        ServiceHairdresser serviceHairdresser = null;

        try(
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_SERVICEHAIRDRESSER_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                serviceHairdresser = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return serviceHairdresser;
    }

    @Override
    public List<ServiceHairdresser> list() throws DAOException {
        LOGGER.trace("Start tracing ServiceHairdresserDAOJDBC#list");

        List<ServiceHairdresser> serviceHairdressers = new ArrayList<>();

        try(
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_SERVICEHAIRDRESSER)
        ) {
            while (resultSet.next()) {
                serviceHairdressers.add(map(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return serviceHairdressers;
    }

    @Override
    public boolean create(ServiceHairdresser serviceHairdresser) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing ServiceHairdresserDAOJDBC#create");

        try(Connection connection = connectionManager.getConnection()) {
            if(connection != null) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_SERVICEHAIRDRESSER)) {
                    connection.setAutoCommit(false);
                    preparedStatement.setLong(1, serviceHairdresser.getService().getId());
                    preparedStatement.setLong(2, serviceHairdresser.getHairdresser().getId());
                    preparedStatement.executeUpdate();
                    connection.commit();
                    return true;
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                    connection.rollback();
                }
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void update(ServiceHairdresser serviceHairdresser) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing ServiceHairdresserDAOJDBC#update");

        try(Connection connection = connectionManager.getConnection()) {
            if(connection != null) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_SERVICEHAIRDRESSER)) {
                    connection.setAutoCommit(false);
                    preparedStatement.setLong(1, serviceHairdresser.getService().getId());
                    preparedStatement.setLong(2, serviceHairdresser.getHairdresser().getId());
                    preparedStatement.setLong(3, serviceHairdresser.getId());
                    preparedStatement.executeUpdate();
                    connection.commit();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                    connection.rollback();
                }
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(ServiceHairdresser serviceHairdresser) throws DAOException {
        LOGGER.trace("Starting tracing ServiceHairdresserDAOJDBC#delete");

        try(Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_SERVICE_HAIRDRESSER)
        ){
            preparedStatement.setLong(1, serviceHairdresser.getId());
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static ServiceHairdresser map(ResultSet resultSet) throws SQLException {
        ServiceHairdresser serviceHairdresser = new ServiceHairdresser();
        serviceHairdresser.setId(resultSet.getLong("sh_id"));

        Service service = new Service();
        service.setId(resultSet.getLong("s_id"));
        service.setName(resultSet.getString("s_name"));
        service.setPrice(resultSet.getBigDecimal("s_price"));
        serviceHairdresser.setService(service);

        User hairdresser = new User();
        hairdresser.setId(resultSet.getLong("h_id"));
        hairdresser.setName(resultSet.getString("h_name"));
        hairdresser.setEmail(resultSet.getString("h_email"));
        hairdresser.setRating(resultSet.getDouble("h_rating"));
        serviceHairdresser.setHairdresser(hairdresser);

        return serviceHairdresser;
    }
}
