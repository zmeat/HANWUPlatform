package com.application;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.utils.ConfigLoader;

public class Container {
	private final static String appDir = "./configuration/app.conf";
	private final static String DBPath = "appdatabasepath";
	
	private HashMap<String, String> app;
	private HashMap<String, Object> services;
	
	public Container() {
		services = new HashMap<String, Object>();
		app = new HashMap<String, String>();
		loadAppConfiguration();
		registerServices();
	}
	
	private void registerServices() {
		this.registerService(this.DBPath, app.get(this.DBPath));
	}
	/**
	 * load app configuration
	 */
	private void loadAppConfiguration() {
		Properties prop = ConfigLoader.loadProperties(appDir);
		Set<Object> keySet = prop.keySet();
		for (Object key : keySet) {
			app.put(key.toString(), prop.get(key).toString());
		}
	}

	public void registerService(String key, Object value) {
		services.put(key, value);
	}
	
	public Object getService(String key) {
		return services.get(key);
	}
	
	public static String getDBPath() {
		return DBPath;
	}
}
