package com.bsalon.controllers.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class LogoutCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("user");
        session.removeAttribute("role");
        session.removeAttribute("requests");
        session.removeAttribute("lastCommand");

        forward("login");
    }
}
