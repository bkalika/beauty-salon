package com.bsalon.controllers.commands;

import com.bsalon.models.Request;
import com.bsalon.services.IRequestService;
import com.bsalon.services.implementations.RequestService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author @bkalika
 */
//TODO: Does not used
public class ChangeRequestCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(ChangeRequestCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing ChangeRequest#process");

        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");

        IRequestService requestService = new RequestService();
        String date = request.getParameter("date");
        Request selectedRequest = requestService.get(Long.valueOf(request.getParameter("requestId"))) ;
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        selectedRequest.setDate(LocalDateTime.parse(date));
        requestService.update(selectedRequest);
        List<Request> requests = requestService.list();
        session.setAttribute("requests", requests);
        session.setAttribute("lastCommand", "DateChangeRequests");
        response.sendRedirect(String.format("/commands?command=RequestPage&clientId=%s&lang=%s", clientId, lang));
    }
}
