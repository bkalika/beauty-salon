package com.bsalon.services.implementations;

import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import com.bsalon.exceptions.InvalidUserDataException;
import com.bsalon.models.User;
import com.bsalon.services.IUserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author @bkalika
 */
public class UserServiceTest {
    IUserService userService = new UserService();

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().getConnection();
    }

    @Test
    public void testLogin_ShouldReturnUser_WhenDataIsCorrect() {
        User actual = userService.login("admin@mail.com", "1");
        assertNotNull(actual);
    }

    @Test(expected = InvalidUserDataException.class)
    public void testLogin_ShouldReturnNull_WhenDataIsNotCorrect() {
        User user = userService.login("admin@mail.com", "");
        assertNull(user);
    }

    @Test
    public void testEmail_ShouldReturnUser_WhenEmailIsCorrect() {
        User actual = userService.findByEmail("admin@mail.com");
        assertNotNull(actual);
    }

    @Test
    public void testGetListClients_ShouldReturnClients_WhenDataIsNotNull() {
        List<User> actual = userService.listClients();
        assertNotNull(actual);
    }

    @Test
    public void testGetListHairdressers_ShouldReturnHairdressers_WhenDataIsNotNull() {
        List<User> actual = userService.listHairdressers();
        assertNotNull(actual);
    }
}
