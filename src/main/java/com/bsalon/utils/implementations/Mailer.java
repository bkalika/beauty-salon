package com.bsalon.utils.implementations;

import com.bsalon.services.IEmailService;
import com.bsalon.services.implementations.EmailService;
import org.apache.log4j.Logger;

/**
 * @author @bkalika
 */
public class Mailer extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Mailer.class);

    private String email;
    private final IEmailService emailService = new EmailService();

    @Override
    public void run() {
        try {
            emailService.send(email);
            Thread.sleep(1000); // 1 day: 86400000
        } catch (Exception e) {
            LOGGER.error("Mailer error occurred!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
