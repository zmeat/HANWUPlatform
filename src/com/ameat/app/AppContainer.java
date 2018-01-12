package com.ameat.app;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.ameat.utils.ConfigLoader;

public class AppContainer {
	private final static String appDir = "./configuration/app.properties";
	private final static String DBPath = "./configuration/database.properties";
	
	private HashMap<String, String> app;
	private HashMap<String, Object> services;
	
	public AppContainer() {
		services = new HashMap<String, Object>();
		app = new HashMap<String, String>();
		loadAppConfiguration();
		registerServices();
	}
	
	private void registerServices() {
		this.registerService(AppContainer.DBPath, app.get(AppContainer.DBPath));
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
	
	public String getConfig(String key) {
		return app.get(key);
	}
}
