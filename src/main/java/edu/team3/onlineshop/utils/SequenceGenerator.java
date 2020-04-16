package edu.team3.onlineshop.utils;


import java.util.Calendar;
import java.util.Random;

public class SequenceGenerator {
	
	private static SequenceGenerator generator;
	
	private SequenceGenerator() {
		
	}
	
	public static SequenceGenerator getInstance() {
		if(generator == null) 
			generator = new SequenceGenerator();
		
		return generator;
	}
	
	public String getNext() {
		Random random = new Random();
		String b = String.format("%08d", random.nextInt(10000000));
		
		Calendar today = Calendar.getInstance();
		String next = today.get(Calendar.YEAR) + "-" 
				+ today.get(Calendar.MONTH) + "-"
				+ today.get(Calendar.DAY_OF_MONTH) + "/" ;
		
		System.out.println(next + b);
		
		return next + b;
	}

}
