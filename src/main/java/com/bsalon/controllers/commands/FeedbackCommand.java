package com.bsalon.controllers.commands;

import com.bsalon.models.Feedback;
import com.bsalon.models.Request;
import com.bsalon.services.implementations.FeedbackService;
import com.bsalon.services.implementations.RequestService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class FeedbackCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(FeedbackCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing FeedbackCommand#process");

        HttpSession session = request.getSession();
        FeedbackService feedbackService = new FeedbackService();

        String rId =  request.getParameter("requestId");
        Long requestId = Long.parseLong(rId);
        RequestService requestService = new RequestService();
        Request forRequest = requestService.get(requestId);

        if("POST".equalsIgnoreCase(request.getMethod())) {
            session.setAttribute("lastCommand", "Feedback");

            String name = request.getParameter("name");
            int rate = Integer.parseInt(request.getParameter("rate"));

            Feedback feedback = new Feedback();
            feedback.setRequest(forRequest);
            feedback.setName(name);
            feedback.setRate(rate);

            if(feedbackService.create(feedback)) {
                session.setAttribute("feedback", feedbackService.findByRequest(forRequest.getId()));
                session.setAttribute("lastCommand", "Feedback");

                forward("feedback");
            }
        } else {
            session.setAttribute("request", forRequest);
            session.setAttribute("feedback", feedbackService.findByRequest(requestId));
            session.setAttribute("lastCommand", "Feedback");
            forward("feedback");
        }
    }
}
