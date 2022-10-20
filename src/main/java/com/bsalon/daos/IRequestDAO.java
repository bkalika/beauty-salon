package com.bsalon.daos;

import com.bsalon.models.Request;
import com.bsalon.models.Status;

import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link Request} model.
 *
 * @author @bkalika
 */
public interface IRequestDAO extends IDao<Request>  {
    List<Request> listByHairdresser(Long id);
    List<Request> listByClient(Long id);
    void updateStatus(Request request, Status status);
}
