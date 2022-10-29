package com.bsalon.daos;

import com.bsalon.exceptions.DAOException;
import com.bsalon.models.User;

import java.util.List;


/**
 * This interface represents a contract for a DAO for the {@link User} model.
 * Note that all methods which returns the {@link User} from the DB, will not
 * fill the model with the password, due to security reasons.
 *
 * @author @bkalika
 */
public interface IUserDAO extends IDao<User> {
    User findByEmail(String email) throws DAOException;
    List<User> listClients();
    List<User> listHairdressers();
}
