package com.bsalon.daos.jdbc;

import com.bsalon.daos.DAOException;
import com.bsalon.daos.IUserDAO;
import com.bsalon.datasource.ConnectionManager;

import com.bsalon.models.Role;
import com.bsalon.models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bsalon.constants.SQLConstants.*;

/**
 * This class represents a concrete JDBC implementation of the {@link IUserDAO} interface.
 *
 * @author @bkalika
 */
public class UserDAOJDBC implements IUserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOJDBC.class);
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    /**
     * Returns the user from the database matching the given id value.
     * @param id The User id values to be set.
     * @return The user from the database matching given values.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public User find(Long id) throws DAOException {
        LOGGER.trace("Start tracing UserDAOJDBC#find");

        User user = null;

        try(
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)
                ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return user;
    }

    /**
     * Returns a list of all users from the database. The list is never null and
     * is empty when the database does not contain any user.
     * @return A list of all users from the database.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public List<User> list() throws DAOException {
        LOGGER.trace("Start tracing UserDAOJDBC#list");

        return getUsers(SQL_SELECT_USERS);
    }

    /**
     * Create the User in the database. The User ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given User.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the User ID is not null.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public boolean create(User user) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing UserDAOJDBC#create");

        try(Connection connection = connectionManager.getConnection()) {
            if(connection != null) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
                    connection.setAutoCommit(false);
                    preparedStatement.setString(1, user.getEmail());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getName());
                    preparedStatement.setInt(4, user.getRole().getId());
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

    /**
     * Update the user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public void update(User user) throws IllegalArgumentException, DAOException {
        LOGGER.trace("Starting tracing UserDAOJDBC#update");

        try(Connection connection = connectionManager.getConnection()) {
            if(connection != null) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
                    connection.setAutoCommit(false);
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setDouble(2, user.getRating());
                    preparedStatement.setLong(3, user.getId());
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

    /**
     * Delete the given user from the database. After deleting, the DAO will set the ID of the given
     * user to null.
     * @param user The user to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public void delete(User user) throws DAOException {
        LOGGER.trace("Starting tracing UserDAOJDBC#delete");

        try(Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)
        ){
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Returns the user from the database matching the given email value.
     * @param email The User email values to be set.
     * @return The user from the database matching given values.
     * @throws DAOException If something fails at database level.
     */
    @Override
    public User findByEmail(String email) throws DAOException {
        LOGGER.trace("Start tracing UserDAOJDBC#findByEmail");

        User user = null;

        try(
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL)
        ) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = map(resultSet);
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public List<User> listClients() {
        LOGGER.trace("Start tracing UserDAOJDBC#listHairdressers");

        return getUsers(SQL_SELECT_CLIENTS);
    }

    @Override
    public List<User> listHairdressers() {
        LOGGER.trace("Start tracing UserDAOJDBC#listHairdressers");

        return getUsers(SQL_SELECT_HAIRDRESSERS);
    }


    // not used
    @Override
    public List<String> listEmailsByTomorrowDoneRequest() {
        LOGGER.trace("Start tracing UserDAOJDBC#listEmails");

        List<String> emails = new ArrayList<>();

        try (
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_EMAILS_BY_YESTERDAYS_DONE_REQUEST);
                ) {
            while(resultSet.next()) {
                emails.add(resultSet.getString("u_email"));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return emails;
    }

    private List<User> getUsers(String sqlSelectHairdressers) {
        List<User> hairdressers = new ArrayList<>();

        try (
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlSelectHairdressers)
                ) {
            while(resultSet.next()) {
                hairdressers.add(map(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return hairdressers;
    }

    /**
     * Map the current row of the given ResultSet to a User.
     * @param resultSet The ResultSet of which the current row is to be mapped to a User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setRating(resultSet.getDouble("rating"));
        user.setRole(Role.getById(resultSet.getInt("role_id")));

        return user;
    }
}