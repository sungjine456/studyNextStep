package org.studyStepNext.part2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int add(String str) {
		if(str == null || str.trim().isEmpty()){
			return 0;
		}
		String[] numbers;
		int sum = 0;
		Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str);
		if(m.find()){
			numbers = m.group(2).split(m.group(1));
		} else {
			numbers = str.split(",|:");
		}
		for(int i = 0; i < numbers.length; i++){
			sum += parseInt(numbers[i]);
		}
		return sum;
	}
	
	private int parseInt(String s){
		int n = Integer.parseInt(s);
		if(n < 0){
			throw new RuntimeException();
		}
		return n;
	}
}
