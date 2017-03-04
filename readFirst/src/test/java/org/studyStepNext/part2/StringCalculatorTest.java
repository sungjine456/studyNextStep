package org.studyStepNext.part2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {
	
	private StringCalculator sc;
	
	@Before
	public void setup(){
		sc = new StringCalculator();
	}

	@Test
	public void addNullTest() {
		assertEquals(0, sc.add(""));
		assertEquals(0, sc.add(" "));
		assertEquals(0, sc.add("   "));
		assertEquals(0, sc.add(null));
	}
	
	@Test
	public void addOneNumberTest() {
		assertEquals(2, sc.add("2"));
	}
	
	@Test
	public void addDivisionRestTest() {
		assertEquals(3, sc.add("1,2"));
		assertEquals(12, sc.add("5,7"));
	}
	
	@Test
	public void addDivisionRestAndColonTest() {
		assertEquals(9, sc.add("2,3:4"));
		assertEquals(19, sc.add("2:5:12"));
		assertEquals(6, sc.add("1,2,3"));
	}
	
	@Test(expected=RuntimeException.class)
	public void addNegativeNumberTest() {
		assertEquals(2, sc.add("-2"));
		assertEquals(2, sc.add("-2,2"));
		assertEquals(2, sc.add("-2;2"));
		assertEquals(2, sc.add("//:\n-2:2"));
	}
	
	@Test
	public void addDivisionCustomTest() {
		assertEquals(2, sc.add("//:\n1:1"));
		assertEquals(10, sc.add("//:\n2:3:5"));
	}
}
