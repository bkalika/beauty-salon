package com.bsalon.services;

import com.bsalon.models.Feedback;
import com.bsalon.models.User;

import java.util.List;

/**
 * This interface represents a contract for a DAO for the {@link Feedback} model.
 * @author @bkalika
 */
public interface IFeedbackService {
    boolean create(Feedback feedback);
    List<Feedback> list();
    List<Feedback> list(User hairdresser);
    Feedback findByRequest(Long requestId);
}
