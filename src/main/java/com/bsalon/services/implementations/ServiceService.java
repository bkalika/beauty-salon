package com.bsalon.services.implementations;

import com.bsalon.daos.DaoFactory;
import com.bsalon.daos.IServiceDAO;
import com.bsalon.models.Service;
import com.bsalon.services.IServiceService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author @bkalika
 */
public class ServiceService implements IServiceService {
    private static final Logger LOGGER = Logger.getLogger(ServiceService.class);

    private final IServiceDAO serviceDAO = (IServiceDAO) DaoFactory.create(DaoFactory.Entity.SERVICE);

    @Override
    public boolean create(String name, BigDecimal price) {
        LOGGER.trace("Starting tracing ServiceService#create");

        Service service = new Service();
        service.setName(name);
        service.setPrice(price);

        return serviceDAO.create(service);
    }

    @Override
    public List<Service> list() {
        LOGGER.trace("Starting tracing ServiceService#list");

        return serviceDAO.list();
    }

    @Override
    public Service get(Long id) {
        LOGGER.trace("Starting tracing ServiceService#get");

        return serviceDAO.find(id);
    }
}
