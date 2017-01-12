package org.studyStepNext.part2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int add(String str) {
		if(str == null || str.trim().isEmpty()){
			return 0;
		}
		return add(split(str));
	}
	
	private int add(String[] numbers){
		int sum = 0;
		for(int i = 0; i < numbers.length; i++){
			sum += parseInt(numbers[i]);
		}
		return sum;
	}
	
	private String[] split(String str){
		Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str);
		if(m.find()){
			return m.group(2).split(m.group(1));
		} 
		return str.split(",|:");
	}
	
	private int parseInt(String s){
		int n = Integer.parseInt(s);
		if(n < 0){
			throw new RuntimeException();
		}
		return n;
	}
}
