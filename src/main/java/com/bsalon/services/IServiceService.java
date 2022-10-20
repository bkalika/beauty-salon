package com.bsalon.services;

import com.bsalon.models.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link Service} model.
 * @author @bkalika
 */
public interface IServiceService {
    boolean create(String name, BigDecimal price);
    List<Service> list();
    Service get(Long id);
}
