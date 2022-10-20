package com.bsalon.controllers.commands;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class UnknownCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(UnknownCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing UnknownCommand#process");

        forward("unknown");
    }

}
