package com.bsalon.services.implementations;

import com.bsalon.daos.DaoFactory;
import com.bsalon.daos.IUserDAO;
import com.bsalon.exceptions.InvalidUserDataException;
import com.bsalon.exceptions.UserExistException;
import com.bsalon.models.Role;
import com.bsalon.models.User;
import com.bsalon.services.IUserService;
import com.bsalon.utils.implementations.SecurityService;
import com.bsalon.utils.implementations.ValidationService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author @bkalika
 */
public class UserService implements IUserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private final ValidationService validationService = new ValidationService();

    private final SecurityService securityService = new SecurityService();

    private final IUserDAO userDAO = (IUserDAO) DaoFactory.create(DaoFactory.Entity.USER);

    @Override
    public User login(String email, String password) {
        LOGGER.trace("Starting tracing UserService#login");

        if(!validationService.isEmailValid(email) || !validationService.isPasswordValid(password))
            throw new InvalidUserDataException("Invalid email and password while sign in");

        User user = findByEmail(email);

        if(user == null || !securityService.checkPassword(password, user.getPassword()))
            throw new InvalidUserDataException("Invalid password");

        return user;
    }

    @Override
    public boolean register(String name, String email, String password, String passwordConfirm, Role role) throws UserExistException {
        LOGGER.trace("Starting tracing UserService#register");

        validateRegistration(email, password, passwordConfirm);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(securityService.encryptPassword(password));
        user.setRole(role);
        user.setRating(0);
        return userDAO.create(user);
    }

    @Override
    public User findByEmail(String email) {
        LOGGER.trace("Starting tracing UserService#findByEmail");

        return userDAO.findByEmail(email);
    }

    @Override
    public User find(Long id) {
        LOGGER.trace("Starting tracing UserService#find");

        return userDAO.find(id);
    }

    private boolean isAlreadyExist(String email) {
        LOGGER.trace("Starting tracing UserService#isAlreadyExist");

        return findByEmail(email) != null;
    }

    private void validateRegistration(String email, String password, String passwordConfirm) throws UserExistException {
        LOGGER.trace("Starting tracing UserService#validateRegistration");

        if(!validationService.isEmailValid(email))
            throw new InvalidUserDataException("Invalid email");

        if(!validationService.isPasswordValid(password))
            throw new InvalidUserDataException("Invalid password. It has to be more than 1 digits.");

        if(!validationService.isConfirmationPassMatched(password, passwordConfirm))
            throw new InvalidUserDataException("Password did not match");

        if(isAlreadyExist(email))
            throw new UserExistException("Email has already exist!");
    }

    @Override
    public List<User> listClients() {
        LOGGER.trace("Starting tracing UserService#listClients");

        return userDAO.listClients();
    }

    @Override
    public List<User> listHairdressers() {
        LOGGER.trace("Starting tracing UserService#listHairdressers");

        return userDAO.listHairdressers();
    }

    @Override
    public void delete(User user) {
        LOGGER.trace("Starting tracing UserService#delete");

        userDAO.delete(user);
    }
}
