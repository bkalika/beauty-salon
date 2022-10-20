package com.bsalon.exceptions;

/**
 * @author @bkalika
 */
public class RequestFailException extends RuntimeException {
    public RequestFailException() {

    }
    public RequestFailException(String message) {
        super(message);
    }
}
