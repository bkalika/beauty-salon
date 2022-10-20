package com.bsalon.exceptions;

/**
 * @author @bkalika
 */
public class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException() {

    }

    public InvalidUserDataException(String message) {
        super(message);
    }
}
