package com.bsalon.services;

import com.bsalon.models.Request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link Request} model.
 *
 * @author @bkalika
 */
public interface IRequestService {
    boolean create(Long hairdresserId, Long clientId, LocalDateTime dateTime);
    List<Request> list();
    List<Request> listByHairdresser(Long id);
    List<Request> listByClient(Long id);
    Request get(Long id);
    void done(Request request);
    void cancel(Request request);
    void update(Request request);
}
