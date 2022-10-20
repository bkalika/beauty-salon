package com.bsalon.utils.implementations;

import com.bsalon.utils.IValidationService;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * @author @bkalika
 */
public class ValidationService implements IValidationService {
    private static final Logger LOGGER = Logger.getLogger(ValidationService.class);

    private static final int CODE_LENGTH = 4;
    private static final int MAX_DIGIT = 9;

    @Override
    public boolean isEmailValid(String email) {
        LOGGER.trace("Start tracing ValidationService#isEmailValid");

        if(email == null) return false;
        return email.matches("^([\\w-\\\\.]+){1,64}@([\\w&&[^_]]+){1,255}.[a-z]{2,}$");
    }

    @Override
    public boolean isPasswordValid(String password) {
        LOGGER.trace("Start tracing ValidationService#isPasswordValid");

        if(password == null) return false;

        return password.matches("\\w{1,255}");
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
                localDateTime.getHour() < START_HOUR //||
//                localDateTime.getMinute() != 0
        )
                ;
    }

}
