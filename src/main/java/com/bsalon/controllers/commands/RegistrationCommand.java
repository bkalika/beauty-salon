package com.bsalon.controllers.commands;

import com.bsalon.models.Role;
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
public class RegistrationCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace(String.format("Start tracing %s#process", RegistrationCommand.class));

        HttpSession session = request.getSession();
        session.setAttribute("lastCommand", "Registration");

        if("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String passwordConfirm = request.getParameter("passwordConfirm");
                Role role = Role.getById(Integer.parseInt(request.getParameter("role_id")));

                IUserService userService = new UserService();

                if(userService.register(name, email, password, passwordConfirm, role)) {
                    User user = userService.login(email, password);
                    session.setAttribute("user", user);
                    session.setAttribute("role", user.getRole().getName());
                    session.setAttribute("lastCommand", "Registration");

//                    forward("home");
                    response.sendRedirect("/commands?command=RequestPage");
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);

                request.setAttribute("message", e.getMessage());
                forward("registration");
            }
        } else {
            forward("registration");
        }
    }
}
