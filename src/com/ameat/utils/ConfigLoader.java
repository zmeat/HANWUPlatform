package com.ameat.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
	private static String configurationDir = "configuration/";
	private static String tail = ".properties";
	
	private static Properties loadProperties(String pathName){
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
	
	
	/**
	 * get one config property from a file  eg: config("filename.property.property.property");
	 * @param str
	 * @return String
	 */
	public static String config(String str) {
		int index = str.indexOf('.');
		String fileName = str;
		String propertyName = "";
		
		if(index > 0) {
			fileName = str.substring(0, index);
			propertyName = str.substring(index+1);
		}
		
		Properties properties = loadProperties(configurationDir+fileName+tail);
		
		return properties.getProperty(propertyName);
	}
	
	
	/**
	 * get all of the configs under a namespace eg: configs("database.development"), 
	 * this will return all the database configs under development namespace without the "development" prefix;
	 * @param str
	 * @return Map<String, String>
	 */
	public static Map<String, String> configs(String str) {
		Map<String, String> confs = new HashMap<String, String>();
		
		int index = str.indexOf('.');
		String fileName = str;
		String propertyName = null;
		
		if(index > 0) {
			fileName = str.substring(0, index);
			propertyName = str.substring(index+1);
		}
		
		Properties properties = loadProperties(configurationDir+fileName+tail);
		Enumeration<Object> keys = properties.keys();

		if(propertyName != null) {
			while(keys.hasMoreElements()) {
				String key = keys.nextElement().toString();
				
				if(key.startsWith(propertyName)) {
					int sindex = str.indexOf('.');
					String sKey = "";
					
					if(sindex > 0) {
						sKey = key.substring(key.indexOf('.')+1);
						confs.put(sKey, properties.getProperty(key));
					}
					
				}
			}
		}else {
			while(keys.hasMoreElements()) {
				String key = keys.nextElement().toString();
				confs.put(key, properties.getProperty(key));
			}
		}
		
		return confs;
	}
	
	
}
