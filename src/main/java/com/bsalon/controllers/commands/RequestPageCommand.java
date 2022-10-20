package com.bsalon.controllers.commands;

import com.bsalon.models.Request;
import com.bsalon.models.Role;
import com.bsalon.models.User;
import com.bsalon.services.IRequestService;
import com.bsalon.services.implementations.RequestService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author @bkalika
 */
public class RequestPageCommand extends FrontCommand{
    private static final Logger LOGGER = Logger.getLogger(RequestPageCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Starting tracing RequestPageCommand#process");

        HttpSession session = request.getSession();

        String clientIdString = request.getParameter("clientId");
        Long clientId = null;
        if(clientIdString != null) {
            clientId = Long.parseLong(clientIdString);
        }
        String hairdresserIdString = request.getParameter("hairdresserId");
        Long hairdresserId = null;
        if(hairdresserIdString != null) {
            hairdresserId = Long.parseLong(hairdresserIdString);
        }

        Role role = Role.getByName((String) session.getAttribute("role"));
        User user = (User) session.getAttribute("user");
        IRequestService requestService = new RequestService();
        List<Request> requests;

        if(user != null) {
            if("Admin".equals(role.getName())) {
                if(clientId != null) {
                    requests = requestService.listByClient(clientId);
                } else if(hairdresserId != null) {
                    requests = requestService.listByHairdresser(hairdresserId);
                } else {
                    requests = requestService.list();
                }
            } else if("Hairdresser".equals(role.getName())) {
                requests = requestService.listByHairdresser(user.getId());
            } else if("Customer".equals(role.getName())) {
                requests = requestService.listByClient(user.getId());
            } else {
                requests = null;
            }

            if(requests != null) {
                session.setAttribute("requests", requests);
                session.setAttribute("lastCommand", "RequestPage");

                String page = request.getParameter("page");
                request.setAttribute("page", (page == null)? 1 : page);

                forward("requests");
                }
        } else {
            LOGGER.warn("You do not have rights to be here!");

            forward("login");
        }
    }
}
