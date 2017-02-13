package org.studyStepNext.part10.core.di;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HourTest {
	@Test
	public void morning() {
		Hour hour = new Hour(11);
		assertEquals("오전", hour.getMessage());
	}

	@Test
	public void afternoon() {
		Hour hour = new Hour(13);
		assertEquals("오후", hour.getMessage());
	}
}
