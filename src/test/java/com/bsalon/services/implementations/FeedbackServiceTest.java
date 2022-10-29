package com.bsalon.services.implementations;

import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import com.bsalon.models.Feedback;
import com.bsalon.models.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author @bkalika
 */
public class FeedbackServiceTest {
    FeedbackService feedbackService = new FeedbackService();

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    public void testListFeedbacks_ShouldReturnFeedbacks_WhenDataIsNotNull() {
        List<Feedback> actual = feedbackService.list();

        assertTrue(actual.size()>0);
    }

    @Test
    public void testListFeedbacksByHairdresser_ShouldReturnFeedbacks_WhenDataIsNotNull() {
        User hairdresser = new User();
        hairdresser.setId(2L);

        List<Feedback> actual = feedbackService.list(hairdresser);

        assertTrue(actual.size()>0);
    }

    @Test
    public void testFeedbackByRequestId_ShouldReturnFeedback_WhenDataIsNotNull() {
        Feedback actual = feedbackService.findByRequest(1L);

        assertNotNull(actual);
    }
}
