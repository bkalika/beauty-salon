package com.bsalon.services;

import com.bsalon.models.ServiceHairdresser;

import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link ServiceHairdresser} model.
 * @author @bkalika
 */
public interface IServiceHairdresserService {
    boolean create(Long serviceId, Long hairdresserId);
    List<ServiceHairdresser> list();
    ServiceHairdresser get(Long id);
}
