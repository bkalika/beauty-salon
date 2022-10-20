package com.bsalon.controllers.commands;

import com.bsalon.services.implementations.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class ClientsCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(ClientsCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace(String.format("Start tracing %s#process", ClientsCommand.class));

        HttpSession session = request.getSession();
        session.setAttribute("lastCommand", "Client");
        String role = (String) session.getAttribute("role");

        if("ADMIN".equalsIgnoreCase(role)) {
            UserService userService = new UserService();
            session.setAttribute("clients", userService.listClients());
            forward("clients");
        } else {
            forward("403");
        }
    }
}
