package com.bsalon.exceptions;

/**
 * @author @bkalika
 */
public class UserExistException extends Exception {

    public UserExistException(){

    }

    public UserExistException(String message) {
        super(message);
    }
}
