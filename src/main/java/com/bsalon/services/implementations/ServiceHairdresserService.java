package com.bsalon.services.implementations;

import com.bsalon.daos.DaoFactory;
import com.bsalon.daos.IServiceDAO;
import com.bsalon.daos.IServiceHairdresserDAO;
import com.bsalon.daos.IUserDAO;
import com.bsalon.models.Service;
import com.bsalon.models.ServiceHairdresser;
import com.bsalon.models.User;
import com.bsalon.services.IServiceHairdresserService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author @bkalika
 */
public class ServiceHairdresserService implements IServiceHairdresserService {
    private static final Logger LOGGER = Logger.getLogger(ServiceHairdresserService.class);

    private final IServiceHairdresserDAO serviceHairdresserDAO = (IServiceHairdresserDAO) DaoFactory.create(DaoFactory.Entity.SERVICEHAIRDRESSER);
    private final IServiceDAO serviceDAO = (IServiceDAO) DaoFactory.create(DaoFactory.Entity.SERVICE);
    private final IUserDAO hairdresserDAO = (IUserDAO) DaoFactory.create(DaoFactory.Entity.USER);


    @Override
    public boolean create(Long serviceId, Long hairdresserId) {
        LOGGER.trace("Starting tracing ServiceHairdresserService#create");

        ServiceHairdresser serviceHairdresser = new ServiceHairdresser();
        Service service = serviceDAO.find(serviceId);
        User hairdresser = hairdresserDAO.find(hairdresserId);
        serviceHairdresser.setService(service);
        serviceHairdresser.setHairdresser(hairdresser);

        return serviceHairdresserDAO.create(serviceHairdresser);
    }

    @Override
    public List<ServiceHairdresser> list() {
        LOGGER.trace("Starting tracing ServiceHairdresserService#list");

        return serviceHairdresserDAO.list();
    }

    @Override
    public ServiceHairdresser get(Long id) {
        LOGGER.trace("Starting tracing ServiceHairdresserService#get");

        return serviceHairdresserDAO.find(id);
    }
}
