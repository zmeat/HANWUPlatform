package com.ameat.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	public static Properties loadProperties(String pathName){
		Properties properties = null;
		pathName = UnifyFileName.convert(pathName);
		InputStream configStream;
		try {
			configStream = new FileInputStream(pathName);
			properties = new Properties();
			properties.load(configStream);
			configStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("load config file: " + pathName + " failed");
		}
		return properties;
	}
	
}
