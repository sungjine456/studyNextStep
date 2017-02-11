package org.studyStepNext.part10.core.nmvc;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerTest {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);

	private ControllerScanner cf = new ControllerScanner("org.studyStepNext.part10.next.controller");

	@Test
	public void getControllers() throws Exception {
		Map<Class<?>, Object> controllers = cf.getControllers();
		for (Class<?> controller : controllers.keySet()) {
			logger.debug("controller : {}", controller);
		}
	}
}
