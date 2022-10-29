package com.bsalon.services.implementations;

import com.bsalon.daos.DaoFactory;
import com.bsalon.daos.IRequestDAO;
import com.bsalon.daos.IServiceHairdresserDAO;
import com.bsalon.daos.IUserDAO;
import com.bsalon.utils.implementations.Mailer;
import com.bsalon.models.Request;
import com.bsalon.models.ServiceHairdresser;
import com.bsalon.models.Status;
import com.bsalon.models.User;
import com.bsalon.services.IRequestService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author @bkalika
 */
public class RequestService implements IRequestService {
    private static final Logger LOGGER = Logger.getLogger(RequestService.class);

    private final IRequestDAO requestDAO = (IRequestDAO) DaoFactory.create(DaoFactory.Entity.REQUEST);
    private final IUserDAO userDAO = (IUserDAO) DaoFactory.create(DaoFactory.Entity.USER);
    private final IServiceHairdresserDAO serviceHairdresserDAO = (IServiceHairdresserDAO) DaoFactory.create(DaoFactory.Entity.SERVICEHAIRDRESSER);

    @Override
    public boolean create(Long serviceHairdresserId, Long clientId, LocalDateTime dateTime) {
        LOGGER.trace("Starting tracing RequestService#create");

        ServiceHairdresser serviceHairdresser = serviceHairdresserDAO.find(serviceHairdresserId);
        User client = userDAO.find(clientId);

        Request request = new Request();
        request.setServiceHairdresser(serviceHairdresser);
        request.setClient(client);
        request.setStatus(Status.ACTIVE);
        request.setDate(dateTime);

        return requestDAO.create(request);
    }

    @Override
    public List<Request> list() {
        LOGGER.trace("Starting tracing RequestService#list");

        return requestDAO.list();
    }

    @Override
    public List<Request> listByHairdresser(Long id) {
        LOGGER.trace("Starting tracing RequestService#listByHairdresser");

        return requestDAO.listByHairdresser(id);
    }

    @Override
    public List<Request> listByClient(Long id) {
        LOGGER.trace("Starting tracing RequestService#listByClient");

        return requestDAO.listByClient(id);
    }

    @Override
    public Request get(Long id) {
        LOGGER.trace("Starting tracing RequestService#get");

        return requestDAO.find(id);
    }

    @Override
    public void update(Request request) {
        LOGGER.trace("Starting tracing RequestService#update");

        if(request.getStatus().equals(Status.DONE)) {
            Mailer mailer = new Mailer();
            mailer.setEmail(request.getClient().getEmail());
            mailer.start();
        }
        requestDAO.update(request);
    }
}
