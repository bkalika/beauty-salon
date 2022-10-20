package com.bsalon.listeners;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author @bkalika
 */

//@WebListener
public class Listener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(Listener.class);

    Mailer mailer = new Mailer();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Try to start mailer 1 time per day");

        mailer.setEmail("biotechnology.nubip@gmail.com");
        mailer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        mailer.interrupt();
    }
}
