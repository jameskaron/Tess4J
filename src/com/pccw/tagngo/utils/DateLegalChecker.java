package com.pccw.tagngo.utils;

import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateLegalChecker {
	public static void main(String[] args){
//		String testString="22-04-1990";
//		isDateLegal(testString);
		
		
		
//		String testerror1="29-02-2000";
//		System.out.println(isDateLegal(testerror1));
		
//		String testString="123219781 00-00-0000 aŮ- Fsfas";
//		System.out.println(getDateString(testString));
		
	}
	public static String getDateString(String inputString){
		
		Pattern p = Pattern.compile("[0-9]{2}[-][0-9]{2}[-][0-9]{4}");  
        Matcher m = p.matcher(inputString);  
        String resultString="";
        while (m.find()) {
        	resultString=m.group();  
        }
        return resultString;
	}
	
	public static boolean isDateLegal(String dateString){
		
		if(dateString==null)
			return false;
		// dateString format should be DD-MM-YYYY
		Pattern pattern = Pattern.compile("[0-3]{1}[0-9]{1}[-][0-1]{1}[0-9]{1}[-][1,2]{1}[0-9]{3}");
		Matcher matcher = pattern.matcher(dateString);
		boolean isLegal= matcher.matches();
		
		if (!isLegal) {
			System.out.println("return false 1");
			return false;
		}
		
		String[] dateArray=dateString.split("-");
		int day=Integer.parseInt(dateArray[0]);
		int month=Integer.parseInt(dateArray[1]);
		int year=Integer.parseInt(dateArray[2]);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int currentYear=Integer.parseInt(df.format(System.currentTimeMillis()));
		
		System.out.println("day: "+day+"  month: "+month+"  year: "+year + " currentYear: "+currentYear);
		if (day==0 || day>31 || month==0 || month>12 || year > currentYear || year<(currentYear-150)){
			System.out.println("return false 2");
			return false;
		}
		
		switch (month) {
		case 1:
			if (day<=31) return true; else return false;
		case 3:
			if (day<=31) return true; else return false;
		case 5:
			if (day<=31) return true; else return false;
		case 7:
			if (day<=31) return true; else return false;
		case 8:
			if (day<=31) return true; else return false;
		case 10:
			if (day<=31) return true; else return false;
		case 12:
			if (day<=31) return true; else return false;
		case 4:
			if (day<=30) return true; else return false;
		case 6:
			if (day<=30) return true; else return false;
		case 9:
			if (day<=30) return true; else return false;
		case 11:
			if (day<=30) return true; else return false;
		default:
			if (year%100==0){
				if (year%400==0 && day<=29) return true; else return false;
			} else if (year%4==0){
				if (day<=29) return true; else return false;
			} else {
				if (day<=28) return true; else return false;
			}
		}
	}

}
