package com.bsalon.services;

import com.bsalon.exceptions.UserExistException;
import com.bsalon.models.Role;
import com.bsalon.models.User;

import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link User} model.
 * @author @bkalika
 */
public interface IUserService {
    User login(String email, String password);
    boolean register(String name, String email, String password, String passwordConfirm, Role role) throws UserExistException;
    User findByEmail(String email);
    User find(Long id);
    List<User> listClients();
    List<User> listHairdressers();
    void delete(User user);
}
