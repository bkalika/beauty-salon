package com.bsalon.services.implementations;

import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import com.bsalon.models.ServiceHairdresser;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author @bkalika
 */
public class ServiceHairdresserServiceTest {
    ServiceHairdresserService serviceHairdresserService = new ServiceHairdresserService();

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    public void testGetById_ShouldReturnService_WhenDataIsCorrect() {
        ServiceHairdresser actual = serviceHairdresserService.get(1L);
        assertNotNull(actual);
    }

    @Test
    public void testListServiceHairdressers_ShouldReturnServiceHairdressers_WhenDataIsNotNull() {
        List<ServiceHairdresser> actual = serviceHairdresserService.list();

        assertTrue(actual.size()>0);
    }
}
