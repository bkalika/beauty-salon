package com.bsalon.controllers.commands;

import com.bsalon.daos.jdbc.UserDAOJDBC;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author @bkalika
 */
public abstract class FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(UserDAOJDBC.class);

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    public abstract void process() throws ServletException, IOException;

    protected void forward(String target) throws ServletException, IOException {
        LOGGER.trace(String.format("Start tracing FrontCommand#forward(%s)", target));

        target = String.format("/WEB-INF/pages/%s.jsp", target);
        context.getRequestDispatcher(target).forward(request,response);
    }
}
