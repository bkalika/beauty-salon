package com.bsalon.controllers.commands;

import com.bsalon.models.Role;
import com.bsalon.models.Service;
import com.bsalon.models.ServiceHairdresser;
import com.bsalon.models.User;
import com.bsalon.services.IServiceHairdresserService;
import com.bsalon.services.implementations.ServiceHairdresserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.bsalon.controllers.FrontController.getQueryMap;
import static com.bsalon.utils.implementations.Utils.distinctByKey;

/**
 * @author @bkalika
 */
public class ServicePageCommand extends FrontCommand {
    private static final Logger LOGGER = Logger.getLogger(ServicePageCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("Start tracing ServicePageCommand#process");

        HttpSession session = request.getSession();
        Role role = Role.getByName((String) session.getAttribute("role"));
        User user = (User) session.getAttribute("user");
        IServiceHairdresserService serviceHairdresserService = new ServiceHairdresserService();
        List<ServiceHairdresser> serviceHairdressers = serviceHairdresserService.list();

        List<Service> services = serviceHairdressers.stream().map(ServiceHairdresser::getService)
                .filter(distinctByKey(Service::getName))
                .collect(Collectors.toList());

        session.setAttribute("services", services);

        List<User> hairdressers = serviceHairdressers.stream().map(ServiceHairdresser::getHairdresser)
                .filter(distinctByKey(User::getName))
                .collect(Collectors.toList());

        session.setAttribute("hairdressers", hairdressers);
//        List<ServiceHairdresser> sortByRating = serviceHairdressers.stream().sorted(
//                Comparator.comparingDouble(x -> x.getHairdresser().getRating())
//        ).collect(Collectors.toList());

        if(request.getParameter("sort") != null) {
            if(request.getParameter("sort").equals("hairdresserRating")) {
                serviceHairdressers.sort(Comparator.comparing((ServiceHairdresser sh) -> sh.getHairdresser().getRating()).reversed());
            } else if(request.getParameter("sort").equals("hairdresserName")) {
                serviceHairdressers.sort(Comparator.comparing((ServiceHairdresser sh) -> sh.getHairdresser().getName()));
            } else if(request.getParameter("sort").equals("serviceName")) {
                serviceHairdressers.sort(Comparator.comparing((ServiceHairdresser sh) -> sh.getService().getName()));
            } else if(request.getParameter("sort").equals("servicePrice")) {
                serviceHairdressers.sort(Comparator.comparing((ServiceHairdresser sh) -> sh.getService().getPrice()));
            }
        }

//        List<ServiceHairdresser> filterList = serviceHairdressers;

        String queryString = request.getQueryString();
        String command = queryString
                .replace("%3D", "=")
                .replace("%26", "&")
                .replace("+", " ")
                .replace("%3A", ":")
                ;

        Map<String, String> query = getQueryMap(command);
        if(query.get("hairdresserName") != null) {
            serviceHairdressers = serviceHairdressers.stream().filter(
                    sh -> sh.getHairdresser().getName().equals(query.get("hairdresserName"))
            ).collect(Collectors.toList());
            request.setAttribute("hairdresserName", query.get("hairdresserName"));
        }
        if(query.get("serviceName") != null) {
            serviceHairdressers = serviceHairdressers.stream().filter(
                    sh -> sh.getService().getName().equals(query.get("serviceName"))
            ).collect(Collectors.toList());
            request.setAttribute("serviceName", query.get("serviceName"));
        }

        session.setAttribute("serviceHairdressers", serviceHairdressers);

        request.getSession().setAttribute("lastCommand", "ServicePage");
        forward("services");
    }
}
