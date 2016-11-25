package com.pccw.tagngo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemConfig {

	private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	
	private static Properties props = null;
	
	private static String file_store_root_path;
	
	private static String hKID_Commercial_Code_RegEx;
	private static String hKID_Name_RegEx;
	private static String hKID_RegEx;
	
	private static String tessdata_path;
	
	static{
		props = new Properties();
		
		try {
			props.load(SystemConfig.class.getClassLoader().getResourceAsStream("system.properties"));
			
			file_store_root_path = props.getProperty("file_store_root_path");
			
			hKID_Commercial_Code_RegEx = props.getProperty("HKID_Commercial_Code_RegEx");
			hKID_Name_RegEx = props.getProperty("HKID_Name_RegEx");
			hKID_RegEx = props.getProperty("HKID_RegEx");
			
			tessdata_path = props.getProperty("tessdata_path");
			
		} catch (Exception e) {
			logger.error("get propertis file error : " + e.getMessage());
		}
	}

	public static String getFile_store_path() {
		String file_store_path = file_store_root_path + File.separator + "tmpFiles";
		return file_store_path;
	}

	
	public static String getHKID_Commercial_Code_RegEx() {
		return hKID_Commercial_Code_RegEx;
	}


	public static String getHKID_Name_RegEx() {
		return hKID_Name_RegEx;
	}


	public static String getHKID_RegEx() {
		return hKID_RegEx;
	}

	
	
	public static String getTessdata_path() {
		return tessdata_path;
	}



	public static void main(String[] args) {
		/*System.out.println(getFile_store_path());
		System.out.println(getHKID_Commercial_Code_RegEx());
		System.out.println(getHKID_Name_RegEx());
		System.out.println(getHKID_RegEx());*/
		
		/*List<String> regExGroup = getRegExGroup();
		for (String each : regExGroup) {
			System.out.println(each);
		}*/
		
		System.out.println(getTessdata_path());
	}
	
	
}
