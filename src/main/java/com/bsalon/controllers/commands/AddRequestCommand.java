package com.bsalon.controllers.commands;

import com.bsalon.exceptions.RequestFailException;
import com.bsalon.models.Request;
import com.bsalon.models.ServiceHairdresser;
import com.bsalon.models.Status;
import com.bsalon.models.User;
import com.bsalon.services.IRequestService;
import com.bsalon.services.IServiceHairdresserService;
import com.bsalon.services.IUserService;
import com.bsalon.services.implementations.RequestService;
import com.bsalon.services.implementations.ServiceHairdresserService;
import com.bsalon.services.implementations.UserService;
import com.bsalon.utils.implementations.ValidationService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author @bkalika
 */
public class AddRequestCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(AddRequestCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing AddRequestCommand#process");

        HttpSession session = request.getSession();
        IUserService userService = new UserService();

        if("POST".equalsIgnoreCase(request.getMethod())) {
            String errorMessage = null;
            
            try {
                IRequestService requestService = new RequestService();
                User user = (User) session.getAttribute("user");
                String date = request.getParameter("date");

                IServiceHairdresserService serviceHairdresserService = new ServiceHairdresserService();
                ServiceHairdresser serviceHairdresser = serviceHairdresserService.get(Long.valueOf(request.getParameter("serviceHairdresserId")));

                if(user == null)
                    forward("login");

                if(date == null) {
                    throw new RequestFailException("Invalid date or service");
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                ValidationService validationService = new ValidationService();

                if(!validationService.isRequestDataValid(dateTime)) {
                    errorMessage = "Unacceptable period of time or invalid date";
                    throw new RequestFailException(errorMessage);
                }

                List<Request> hairdresserRequests = requestService.listByHairdresser(serviceHairdresser.getHairdresser().getId());

                List<Request> clientRequests = new ArrayList<>();
                if(user != null) {
                    clientRequests = requestService.listByClient(user.getId());
                }
                for(Request r : clientRequests) {
                    if(r.getStatus() != Status.CANCELED && dateTime.isBefore(r.getDate().plusHours(1))
                            && dateTime.isAfter(r.getDate().minusHours(1))
                    ) {
                        errorMessage = "You have another request during this time. Please cancel those request or choose another time.";

                        throw new RequestFailException(errorMessage);
                    }
                }

                for(Request r : hairdresserRequests) {
                    if(r.getStatus() != Status.CANCELED && dateTime.isBefore(r.getDate().plusHours(1))
                            && dateTime.isAfter(r.getDate().minusHours(1))
                    ) {
                        errorMessage = "This period of time is already booked. Please choose this service with another hairdresser or choose another time.";
                        throw new RequestFailException(errorMessage);
                    }
                }

                assert user != null;
                if(requestService.create(serviceHairdresser.getId(), user.getId(), dateTime)) {
                    session.setAttribute("requests", requestService.listByClient(user.getId()));
                    forward("requests");
                } else {
                    forward("unknown");
                }

            } catch (NumberFormatException | DateTimeParseException e) {
                LOGGER.error(e.getMessage(), e);

                request.setAttribute("message", "Invalid request data");
                session.setAttribute("lastCommand", "AddRequest");
                forward("services");
            } catch (RequestFailException e) {
                LOGGER.error(e.getMessage(), e);

                request.setAttribute("message", errorMessage);
                session.setAttribute("lastCommand", "AddRequest");
                forward("services");
            }
        } else {
            String serviceId = request.getParameter("id");

            if(serviceId != null) {
                request.setAttribute("chosen_service_id", serviceId);
            }

            session.setAttribute("hairdressers", userService.listHairdressers());
            session.setAttribute("lastCommand", "AddRequest");

            forward("services");
        }
    }
}
