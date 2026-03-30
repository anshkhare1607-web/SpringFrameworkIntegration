package com.app.quantitymeasurement.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityControllerTest {

	@Test
	void testControllerBasic() {

		QuantityController controller = new QuantityController(null);

		// call your method (change accordingly)
		Object result = controller.convert(null);

		assertNotNull(result);
	}
}