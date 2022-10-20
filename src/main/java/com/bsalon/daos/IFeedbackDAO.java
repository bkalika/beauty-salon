package com.bsalon.daos;

import com.bsalon.models.Feedback;
import com.bsalon.models.User;

import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link Feedback} model.
 *
 * @author @bkalika
 */
public interface IFeedbackDAO extends IDao<Feedback> {
    List<Feedback> list(User hairdresser);
    Feedback findByRequest(Long requestId);
}
