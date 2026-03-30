package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuantityServiceTest {

    @Test
    void testServiceBasic() {

        // Step 1: create fake repository
        QuantityMeasurementRepository repo = mock(QuantityMeasurementRepository.class);

        // Step 2: create service with fake repo
        QuantityService service = new QuantityService(repo);

        // Step 3: call method
        // (change this method according to your service)
        Object result = service.convert(null);

        // Step 4: check result (basic check)
        assertNotNull(result);
    }
}