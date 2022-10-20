package com.bsalon.controllers.commands;

import com.bsalon.services.implementations.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class HairdressersCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(HairdressersCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace(String.format("Start tracing %s#process", HairdressersCommand.class));

        HttpSession session = request.getSession();
        session.setAttribute("lastCommand", "Hairdressers");
        String role = (String) session.getAttribute("role");

        if("ADMIN".equalsIgnoreCase(role)) {
            UserService userService = new UserService();
            session.setAttribute("hairdressers", userService.listHairdressers());
            forward("hairdressers");
        } else {
            forward("403");
        }
    }
}
