package com.bsalon.services.implementations;

import com.bsalon.daos.connection.ConnectionPool;
import com.bsalon.exceptions.ConnectionException;
import com.bsalon.models.Service;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author @bkalika
 */
public class ServiceServiceTest {
    ServiceService serviceService = new ServiceService();

    @BeforeAll
    static void initializeConnectionPool() throws ConnectionException {
        ConnectionPool.getInstance().initialize();
    }

    @Test
    public void testGetById_ShouldReturnService_WhenDataIsCorrect() {
        Service actual = serviceService.get(1L);
        assertNotNull(actual);
    }

    @Test
    public void testGetById_ShouldReturnNull_WhenDataIsNotCorrect() {
        Service actual = serviceService.get(10L);
        assertNull(actual);
    }

    @Test
    public void testListServices_ShouldReturnServices_WhenDataIsNotNull() {
        List<Service> actual = serviceService.list();

        Service hairCut = new Service();
        hairCut.setId(1L);
        hairCut.setName("Hair cut");
        hairCut.setPrice(BigDecimal.valueOf(200L));

        Service hairStyle = new Service();
        hairStyle.setId(2L);
        hairStyle.setName("Hair style");
        hairStyle.setPrice(BigDecimal.valueOf(350L));

        Service hairColoring = new Service();
        hairColoring.setId(3L);
        hairColoring.setName("Hair coloring");
        hairColoring.setPrice(BigDecimal.valueOf(150L));

        Service simpleHairCut = new Service();
        simpleHairCut.setId(4L);
        simpleHairCut.setName("Simple hair cut");
        simpleHairCut.setPrice(BigDecimal.valueOf(100L));

        List<Service> expected = new ArrayList<>();
        expected.add(hairCut);
        expected.add(hairStyle);
        expected.add(hairColoring);
        expected.add(simpleHairCut);

        assertEquals(expected, actual);
    }
}
