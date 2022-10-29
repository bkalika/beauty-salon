package com.bsalon.utils.implementations;

import com.bsalon.utils.IValidationService;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author @bkalika
 */
public class ValidationService implements IValidationService {
    private static final Logger LOGGER = Logger.getLogger(ValidationService.class);

    @Override
    public boolean isEmailValid(String email) {
        LOGGER.trace("Start tracing ValidationService#isEmailValid");

        if(email == null) return false;
        Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean isPasswordValid(String password) {
        LOGGER.trace("Start tracing ValidationService#isPasswordValid");

        if(password == null) return false;

        Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$"); // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    @Override
    public boolean isConfirmationPassMatched(String password, String passwordConfirm) {
        LOGGER.trace("Start tracing ValidationService#isConfirmationPassMatched");

        return password != null && password.equals(passwordConfirm);
    }

    @Override
    public boolean isRequestDataValid(LocalDateTime localDateTime) {
        LOGGER.trace("Start tracing ValidationService#isRequestDataValid");

        final int ACCEPTABLE_NUMBER_OF_DAYS = 14;
        final int START_HOUR = 9;
        final int END_HOUR = 17;

        LocalDateTime currentDate = LocalDateTime.now();
        return !(localDateTime == null ||
                localDateTime.isBefore(currentDate) ||
                localDateTime.isAfter(currentDate.plusDays(ACCEPTABLE_NUMBER_OF_DAYS)) ||
                localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY ||
                localDateTime.getHour() > END_HOUR ||
                localDateTime.getHour() < START_HOUR
        );
    }

}
