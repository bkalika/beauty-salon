package com.bsalon.controllers;

import com.bsalon.controllers.commands.FrontCommand;
import com.bsalon.controllers.commands.UnknownCommand;
import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author @bkalika
 */
@WebServlet("/commands")
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().initialize();
        } catch (ConnectionException e) {
            LOGGER.error("Servlet wasn't init!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().destroy();
            super.destroy();
        } catch (ConnectionException e) {
            LOGGER.error("Servlet wasn't destroy!");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        handler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        handler(req, resp);
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            String requestURL = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            String command = queryString
                    .replace("%3D", "=")
                    .replace("%26", "&");

            String url = requestURL + "?" + command;

            Map<String, String> query = getQueryMap(command);

            Class<?> type = Class.forName(String.format(
                    "com.bsalon.controllers.commands.%sCommand",
//                    request.getParameter("command")
                    query.get("command")
            ));


            return type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new UnknownCommand();
        }
    }

    private void handler(HttpServletRequest req, HttpServletResponse resp) {
        try {
            FrontCommand command = getCommand(req);
            command.init(getServletContext(), req, resp);
            command.process();
        } catch (IOException | ServletException e) {
            LOGGER.error(String.format("IO or Servlet exception happened: %s", e.getMessage()), e);
        } catch (Exception e) {
            LOGGER.error(String.format("Something unexpected happened: %s", e.getMessage()), e);
        }
    }

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();

        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
