package com.bsalon.utils;

import java.time.LocalDateTime;

/**
 * @author @bkalika
 */
public interface IValidationService {
    boolean isEmailValid(String email);
    boolean isPasswordValid(String password);
    boolean isConfirmationPassMatched(String password, String passwordConfirm);
    boolean isRequestDataValid(LocalDateTime localDateTime);
}
