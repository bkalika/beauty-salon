package com.bsalon.daos.jdbc;

import com.bsalon.exceptions.DAOException;
import com.bsalon.daos.IFeedbackDAO;
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
 * This class represents a concrete JDBC implementation of the {@link IFeedbackDAO} interface.
 *
 * @author @bkalika
 */
public class FeedbackDAOJDBC implements IFeedbackDAO {
    private static final Logger LOGGER = Logger.getLogger(FeedbackDAOJDBC.class);

    @Override
    public Feedback find(Long id) throws DAOException {
        LOGGER.trace("Start tracing FeedbackDAOJDBC#find");

        return getFeedback(id, SQL_SELECT_FEEDBACK_BY_ID);
    }

    @Override
    public Feedback findByRequest(Long requestId) {
        LOGGER.trace("Start tracing FeedbackDAOJDBC#getByRequest");

        return getFeedback(requestId, SQL_SELECT_FEEDBACK_BY_REQUEST);
    }

    @Override
    public List<Feedback> list() throws DAOException {
        LOGGER.trace("Start tracing FeedbackDAOJDBC#list");

        List<Feedback> feedbacks = new ArrayList<>();

        try(
                ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_FEEDBACK)
                ) {
            while(resultSet.next()) {
                feedbacks.add(map(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return feedbacks;
    }

    @Override
    public boolean create(Feedback feedback) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing FeedbackDAOJDBC#create");

        try(PreparedStatement preparedStatement = createStatement(SQL_INSERT_FEEDBACK)) {
            preparedStatement.setLong(1, feedback.getRequest().getId());
            preparedStatement.setString(2, feedback.getName());
            preparedStatement.setInt(3, feedback.getRate());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return false;
    }

    @Override
    public void update(Feedback feedback) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing FeedbackDAOJDBC#update");

    }

    @Override
    public void delete(Feedback feedback) throws DAOException {
        LOGGER.trace("Starting tracing FeedbackDAOJDBC#delete");
    }

    @Override
    public List<Feedback> list(User hairdresser) {
        LOGGER.trace("Starting tracing FeedbackDAOJDBC#list(User)");

        List<Feedback> feedbacks = new ArrayList<>();

        try(PreparedStatement statement = createStatement(SQL_SELECT_FEEDBACKS_BY_HAIRDRESSER)) {
            statement.setLong(1, hairdresser.getId());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                feedbacks.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return feedbacks;
    }

    private Feedback getFeedback(Long id, String sqlSelectFeedbackBy) {
        Feedback feedback = null;

        try(PreparedStatement preparedStatement = createStatement(sqlSelectFeedbackBy)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                feedback = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return feedback;
    }

    private static Feedback map(ResultSet resultSet) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(resultSet.getLong("f_id"));
        feedback.setName(resultSet.getString("f_name"));
        feedback.setRate(resultSet.getInt("f_rate"));
        feedback.setCreated(resultSet.getTimestamp("f_created").toLocalDateTime());

        Request request = new Request();
        request.setId(resultSet.getLong("r_id"));

        ServiceHairdresser serviceHairdresser = new ServiceHairdresser();
        serviceHairdresser.setId(resultSet.getLong("r_servicehairdresser_id"));

        User hairdresser = new User();
        hairdresser.setId(resultSet.getLong("sh_hairdresser_id"));
        hairdresser.setName(resultSet.getString("h_name"));
        hairdresser.setEmail(resultSet.getString("h_email"));
        hairdresser.setRating(resultSet.getDouble("h_rating"));
        serviceHairdresser.setHairdresser(hairdresser);

        Service service = new Service();
        service.setId(resultSet.getLong("sh_service_id"));
        service.setName(resultSet.getString("s_name"));
        service.setPrice(resultSet.getBigDecimal("s_price"));
        serviceHairdresser.setService(service);

        request.setServiceHairdresser(serviceHairdresser);

        User client = new User();
        client.setId(resultSet.getLong("r_client_id"));
        client.setName(resultSet.getString("c_name"));
        client.setEmail(resultSet.getString("c_email"));
        request.setClient(client);

        request.setStatus(Status.valueOf(resultSet.getString("r_status")));
        request.setDate(resultSet.getTimestamp("r_date").toLocalDateTime());
        request.setPaid(resultSet.getBoolean("r_paid"));

        feedback.setRequest(request);

        return feedback;
    }
}
