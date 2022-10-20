package com.bsalon.services.implementations;

import com.bsalon.services.IEmailService;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * https://www.codejava.net/java-ee/jsp/sending-e-mail-with-jsp-servlet-and-javamail
 *
 * @author @bkalika
 */
public class EmailService implements IEmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class);

    List<String> mails = new ArrayList<>();

    @Override
    public void send(String email) throws MessagingException {
        LOGGER.trace("Starting tracing EmailService#send");
        mails.add(email);

        final Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtps.user", "bogdan.kalika@gmail.com");
        properties.setProperty("mail.smtps.password", "aaqvnbwfowfhwlcu");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.debug", "true");

        String fromEmail = "bogdan.kalika@gmail.com";
        String password = "aaqvnbwfowfhwlcu";

//        Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        });

        Session mailSession = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(fromEmail));
        for(String mail : mails) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        }
        message.setSubject("Beauty Salon");
        message.setText("Leave feedback please for your yesterday service");

        Transport transport = mailSession.getTransport();
        transport.connect(fromEmail, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        LOGGER.trace("Message was sent successfully!");
    }
}
