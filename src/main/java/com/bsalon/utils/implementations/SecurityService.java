package com.bsalon.utils.implementations;

import com.bsalon.utils.ISecurityService;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author @bkalika
 */
public class SecurityService implements ISecurityService {
    private static final Logger LOGGER = Logger.getLogger(SecurityService.class);

    private static final String SALT = "$2a$10$JY8cZc3h2m5yX0626rW1bO";

    @Override
    public String encryptPassword(String password) {
        LOGGER.trace("Start tracing SecurityService#encryptPassword");

        return BCrypt.hashpw(password, SALT);
    }

    @Override
    public boolean checkPassword(String password, String hashed) {
        LOGGER.trace("Start tracing SecurityService#checkPassword");

        return BCrypt.checkpw(password, hashed);
    }
}
