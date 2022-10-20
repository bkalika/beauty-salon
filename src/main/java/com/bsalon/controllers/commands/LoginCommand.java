package com.bsalon.controllers.commands;

import com.bsalon.models.User;
import com.bsalon.services.IUserService;
import com.bsalon.services.implementations.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class LoginCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace(String.format("Start tracing %s#process", LoginCommand.class));

        HttpSession session = request.getSession();
        session.setAttribute("lastCommand", "Login");
        String lang = request.getParameter("lang");

        if("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                IUserService userService = new UserService();

                User user = userService.login(email, password);

                String role = user.getRole().getName();

                session.setAttribute("user", user);
                session.setAttribute("role", role);
                session.setAttribute("lastCommand", "ServicePage");

                if("Admin".equals(role)) {
                    forward("services");
                } else if("Hairdresser".equals(role)) {
                    response.sendRedirect("/commands?command=RequestPage" + "&lang=" + lang);
                } else {
                    response.sendRedirect("/commands?command=RequestPage" + "&lang=" + lang);
                }
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);

                request.setAttribute("message", e.getMessage());
                forward("login");
            }
        } else {
            forward("login");
        }
    }
}
