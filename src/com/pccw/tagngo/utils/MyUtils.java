package com.pccw.tagngo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pccw.tagngo.mode.Tess4JRegExBool;

public class  MyUtils {
	
	public static String removeSpace(String str){
		return str.replaceAll(" ", "");
	}

	public static String regularExpression(String data,Tess4JRegExBool regBool) {

		Pattern commercialPat = null;
		Matcher commercialMat = null;
		
		Pattern namePat = null;
		Matcher nameMat = null;
		
		Pattern idPat = null;
		Matcher idMat = null;
		
		Pattern birthdayPat = null;
		Matcher birthdayMat = null;
		
		if(!regBool.isFindCommercial()){
			commercialPat = Pattern.compile(SystemConfig.getHKID_Commercial_Code_RegEx());
			commercialMat = commercialPat.matcher(data);
		}
		
		if(!regBool.isFindName()){
			namePat = Pattern.compile(SystemConfig.getHKID_Name_RegEx());
			nameMat = namePat.matcher(data);
		}
		
		if(!regBool.isFindId()){
			idPat = Pattern.compile(SystemConfig.getHKID_RegEx());
			idMat = idPat.matcher(data);
		}
		
		if(!regBool.isFindBirth()){
			birthdayPat = Pattern.compile("[0-9]{2}[-][0-9]{2}[-][0-9]{4}");
			birthdayMat = birthdayPat.matcher(data);
		}
		

		String regMatchResult = null;
		if (commercialMat != null && commercialMat.find() && !regBool.isFindCommercial()) {
			regMatchResult = commercialMat.group();
			regBool.setFindCommercial(true); 
		} else if (nameMat != null && nameMat.find() && !regBool.isFindName()) {
			regMatchResult = nameMat.group();
			regBool.setFindName(true);
		} else if (idMat != null && idMat.find() && !regBool.isFindId()) {
			regMatchResult = idMat.group();
			regBool.setFindId(true);
		} else if (birthdayMat != null && birthdayMat.find() && !regBool.isFindBirth()) {
			regMatchResult = birthdayMat.group();
			regBool.setFindBirth(true);
		}

//		System.out.println("show reg result : " + regMatchResult);
		System.out.println("test reg bool : " + regBool.toString());
		return regMatchResult;
	}
}
