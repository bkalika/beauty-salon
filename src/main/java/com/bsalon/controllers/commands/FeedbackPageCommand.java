package com.bsalon.controllers.commands;

import com.bsalon.services.implementations.FeedbackService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class FeedbackPageCommand extends FrontCommand{
    private static final Logger LOGGER = Logger.getLogger(FeedbackPageCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if("ADMIN".equalsIgnoreCase(role)) {
            FeedbackService feedbackService = new FeedbackService();
            session.setAttribute("feedbacks", feedbackService.list());

            String page = request.getParameter("page");
            request.setAttribute("page", (page == null) ? 1 : page);
            session.setAttribute("lastCommand", "FeedbackPage");

            forward("feedbacks");
        } else {
            forward("403");
        }
    }
}
