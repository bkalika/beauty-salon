package com.bsalon.services.implementations;

import com.bsalon.daos.DaoFactory;
import com.bsalon.daos.IFeedbackDAO;
import com.bsalon.daos.IUserDAO;
import com.bsalon.models.Feedback;
import com.bsalon.models.User;
import com.bsalon.services.IFeedbackService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author @bkalika
 */
public class FeedbackService implements IFeedbackService {
    private static final Logger LOGGER = Logger.getLogger(FeedbackService.class);

    private final IFeedbackDAO feedbackDAO = (IFeedbackDAO) DaoFactory.create(DaoFactory.Entity.FEEDBACK);
    private final IUserDAO userDAO = (IUserDAO) DaoFactory.create(DaoFactory.Entity.USER);

    @Override
    public boolean create(Feedback feedback) {
        LOGGER.trace("Starting tracing FeedbackService#create");
        try {
            feedbackDAO.create(feedback);
            User hairdresser = feedback.getRequest().getServiceHairdresser().getHairdresser();
            List<Feedback> feedbacks = feedbackDAO.list(hairdresser);
            double hairdresserAvgRage = calculateAverageRate(feedbacks);
            hairdresser.setRating(hairdresserAvgRage);
            userDAO.update(hairdresser);

            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());

            return false;
        }
    }

    @Override
    public List<Feedback> list() {
        LOGGER.trace("Starting tracing FeedbackService#list");

        return feedbackDAO.list();
    }

    @Override
    public List<Feedback> list(User hairdresser) {
        LOGGER.trace("Starting tracing FeedbackService#list(User)");

        return feedbackDAO.list(hairdresser);
    }

    @Override
    public Feedback findByRequest(Long requestId) {
        LOGGER.trace("Starting tracing FeedbackService#getByRequest(Long requestId)");

        return feedbackDAO.findByRequest(requestId);
    }

    private double calculateAverageRate(List<Feedback> hairdresserFeedbacks) {
        return hairdresserFeedbacks.stream().mapToDouble(Feedback::getRate).average().orElse(0);
    }
}
