package com.bsalon.services;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author @bkalika
 */
public interface IEmailService {
    void send(String email) throws IOException, MessagingException, ParseException;
}
