package com.bsalon.exceptions;

import java.sql.SQLException;

/**
 * @author @bkalika
 */
public class ConnectionException extends SQLException {

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
