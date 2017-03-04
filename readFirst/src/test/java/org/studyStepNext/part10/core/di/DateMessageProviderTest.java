package org.studyStepNext.part10.core.di;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class DateMessageProviderTest {

	@Test
	public void morning() {
		DateMessageProvider provider = new DateMessageProvider();
		assertEquals("오전", provider.getDateMessage(createCurrentDate(11)));
	}
	@Test
	public void afternoon() {
		DateMessageProvider provider = new DateMessageProvider();
		assertEquals("오후", provider.getDateMessage(createCurrentDate(13)));
	}
	private Calendar createCurrentDate(int hourOfDay){
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, hourOfDay);
		return now;
	}
}
