package org.studyStepNext.part2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int add(String str) {
		if(isBlank(str)){
			return 0;
		}
		return sum(toInts(split(str)));
	}
	
	private boolean isBlank(String str){
		return str == null || str.trim().isEmpty();
	}
	
	private int sum(int[] numbers){
		int sum = 0;
		for(int number : numbers){
			sum += number;
		}
		return sum;
	}
	
	private int[] toInts(String[] numbers){
		int[] iarr = new int[numbers.length];
		for(int i = 0; i < numbers.length; i++){
			iarr[i] = parseInt(numbers[i]);
		}
		return iarr;
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
