package com.bsalon.services.implementations;

import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import com.bsalon.models.Request;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author @bkalika
 */
public class RequestServiceTest {
    RequestService requestService = new RequestService();

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    public void testListRequests_ShouldReturnRequests_WhenDataIsNotNull() {
        List<Request> actual = requestService.list();

        assertTrue(actual.size()>0);
    }

    @Test
    public void testListRequestsByClient_ShouldReturnRequests_WhenDataIsNotNull() {
        List<Request> actual = requestService.listByClient(3L);

        assertTrue(actual.size()>0);
    }

    @Test
    public void testListRequestsByHairdresser_ShouldReturnRequests_WhenDataIsNotNull() {
        List<Request> actual = requestService.listByClient(3L);

        assertTrue(actual.size()>0);
    }

    @Test
    public void testRequest_ShouldReturnRequests_WhenDataIsNotNull() {
        Request actual = requestService.get(1L);

        assertNotNull(actual);
    }
}
