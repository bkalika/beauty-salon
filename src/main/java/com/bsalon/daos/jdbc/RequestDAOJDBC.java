package com.bsalon.daos.jdbc;

import com.bsalon.daos.DAOException;
import com.bsalon.daos.IRequestDAO;
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
 * This class represents a concrete JDBC implementation of the {@link IRequestDAO} interface.
 *
 * @author @bkalika
 */
public class RequestDAOJDBC implements IRequestDAO {
    private static final Logger LOGGER = Logger.getLogger(RequestDAOJDBC.class);

    /**
     * Returns the Request from the database matching the given id value.
     * @param id The Request id values to be set.
     * @return The Request from the database matching given values.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public Request find(Long id) throws DAOException {
        LOGGER.trace("Start tracing RequestDAOJDBC#find");

        Request request = null;

        try(PreparedStatement preparedStatement = createStatement(SQL_SELECT_REQUEST_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                request = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return request;
    }

    /**
     * Returns a list of all Requests from the database. The list is never null and
     * is empty when the database does not contain any Request.
     * @return A list of all Requests from the database.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public List<Request> list() throws DAOException {
        LOGGER.trace("Start tracing RequestDAOJDBC#list");

        List<Request> requests = new ArrayList<>();

        try(
                ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_REQUESTS)
        ) {
            while (resultSet.next()) {
                requests.add(map(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return requests;
    }

    /**
     * Create the Request in the database. The Request ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Request.
     * @param request The request to be created in the database.
     * @throws IllegalArgumentException If the request ID is not null.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public boolean create(Request request) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing RequestDAOJDBC#create");

        try(PreparedStatement preparedStatement = createStatement(SQL_INSERT_REQUEST)) {
            preparedStatement.setLong(1, request.getServiceHairdresser().getId());
            preparedStatement.setLong(2, request.getClient().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(request.getDate()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return false;
    }

    /**
     * Update the request in the database. The request ID must not be null, otherwise it will throw
     * @param request The request to be updated in the database.
     * @throws IllegalArgumentException If the request ID is null.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public void update(Request request) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing RequestDAOJDBC#update");

        try(PreparedStatement preparedStatement = createStatement(SQL_UPDATE_REQUEST)) {
            preparedStatement.setLong(1, request.getServiceHairdresser().getId());
            preparedStatement.setLong(2, request.getClient().getId());
            preparedStatement.setString(3, request.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(request.getDate()));
            preparedStatement.setBoolean(5, request.isPaid());
            preparedStatement.setLong(6, request.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Update the request status in the database. The request ID must not be null, otherwise it will throw
     * @param request The request to be updated the status in the database.
     * @throws IllegalArgumentException If the request ID is null.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public void updateStatus(Request request, Status status) {
        LOGGER.trace("Starting tracing RequestDAOJDBC#updateStatus");

        try (PreparedStatement preparedStatement = createStatement(SQL_UPDATE_REQUEST_STATUS)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, request.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Delete the given request from the database. After deleting, the DAO will set the ID of the given
     * request to null.
     * @param request The request to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public void delete(Request request) throws DAOException {
        LOGGER.trace("Starting tracing RequestDAOJDBC#delete");

        try(PreparedStatement preparedStatement = createStatement(SQL_DELETE_REQUEST)){
            preparedStatement.setLong(1, request.getId());
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Request> listByHairdresser(Long hairdresserId) {
        LOGGER.trace("Start tracing RequestDAOJDBC#listByHairdresser");

        return getRequests(SQL_SELECT_REQUEST_BY_HAIRDRESSER, hairdresserId);
    }

    @Override
    public List<Request> listByClient(Long clientId) {
        LOGGER.trace("Start tracing RequestDAOJDBC#listByClient");

        return getRequests(SQL_SELECT_REQUEST_BY_CLIENT, clientId);
    }

    private List<Request> getRequests(String sqlQuery, Long id) {
        List<Request> requests = new ArrayList<>();

        try (PreparedStatement statement = createStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                requests.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return requests;
    }

    /**
     * Map the current row of the given ResultSet to a Request.
     * @param resultSet The ResultSet of which the current row is to be mapped to a Request.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Request map(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getLong("id"));

        ServiceHairdresser serviceHairdresser = new ServiceHairdresser();
        serviceHairdresser.setId(resultSet.getLong("servicehairdresser_id"));

        User hairdresser = new User();
        hairdresser.setId(resultSet.getLong("hairdresser_id"));
        hairdresser.setName(resultSet.getString("h_name"));
        hairdresser.setEmail(resultSet.getString("h_email"));
        hairdresser.setRating(resultSet.getDouble("h_rating"));
        serviceHairdresser.setHairdresser(hairdresser);

        Service service = new Service();
        service.setId(resultSet.getLong("service_id"));
        service.setName(resultSet.getString("s_name"));
        service.setPrice(resultSet.getBigDecimal("s_price"));
        serviceHairdresser.setService(service);

        request.setServiceHairdresser(serviceHairdresser);

        User client = new User();
        client.setId(resultSet.getLong("client_id"));
        client.setName(resultSet.getString("c_name"));
        client.setEmail(resultSet.getString("c_email"));
        request.setClient(client);

        request.setStatus(Status.valueOf(resultSet.getString("status")));
        request.setDate(resultSet.getTimestamp("date").toLocalDateTime());
        request.setPaid(resultSet.getBoolean("r_paid"));

        return request;
    }
}
