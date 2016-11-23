package com.pccw.tagngo.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class OpencvXTess4jConfig {
		
	public static String tessdataPath;
	public static String debugPath;
	public static String isDebug;
	
	static {
		Properties p = new Properties();
		try {
			p.load(OpencvXTess4jConfig.class.getClassLoader().getResourceAsStream("ocvxt4j.properties"));
			tessdataPath=p.getProperty("tessdataPath");
			if(tessdataPath.trim().equals("")){
				tessdataPath = null;
			}
			
			debugPath=p.getProperty("debugPath");
			isDebug=p.getProperty("isDebug");
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTessdataPath() {
		return tessdataPath;
	}

	public static void setTessdataPath(String tessdataPath) {
		OpencvXTess4jConfig.tessdataPath = tessdataPath;
	}

	public static String getDebugPath() {
		return debugPath;
	}

	public static void setDebugPath(String debugPath) {
		OpencvXTess4jConfig.debugPath = debugPath;
	}

	public static String getIsDebug() {
		return isDebug;
	}

	public static void setIsDebug(String isDebug) {
		OpencvXTess4jConfig.isDebug = isDebug;
	}
	

}
