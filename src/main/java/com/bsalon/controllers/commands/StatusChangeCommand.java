package com.bsalon.controllers.commands;

import com.bsalon.models.Request;
import com.bsalon.models.Status;
import com.bsalon.models.User;
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
public class StatusChangeCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(StatusChangeCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing StatusChangeCommand#process");

        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");

        IRequestService requestService = new RequestService();
        User user = (User) session.getAttribute("user");
        String status = request.getParameter("requestStatus");
        String date = request.getParameter("date");
        Request selectedRequest = requestService.get(Long.valueOf(request.getParameter("requestId"))) ;

        if(status != null && status.equalsIgnoreCase("done")) {
            selectedRequest.setStatus(Status.DONE);
        } else if(status != null && status.equalsIgnoreCase("cancel")) {
            selectedRequest.setStatus(Status.CANCELED);
        } else if(status != null && status.equalsIgnoreCase("paid")) {
            selectedRequest.setPaid(true);
        } else if(date != null) {
            selectedRequest.setDate(LocalDateTime.parse(date));
        }
        requestService.update(selectedRequest);

        List<Request> requests = requestService.listByHairdresser(user.getId());
        session.setAttribute("requests", requests);
        session.setAttribute("lastCommand", "StatusChange");
        response.sendRedirect(String.format("/commands?command=RequestPage&lang=%s", lang));
    }
}
