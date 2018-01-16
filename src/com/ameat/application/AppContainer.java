package com.ameat.application;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.ameat.utils.ConfigLoader;

public class AppContainer {
	private final static String appDir = "configuration/application.properties";

	private static HashMap<String, String> app = new HashMap<String, String>();
	private static HashMap<String, Object> services = new HashMap<String, Object>();

	private static void init() {
		loadAppConfiguration();
		registerServices();
	}

	private static void registerServices() {
		
	}
	/**
	 * load app configuration
	 */
	private static void loadAppConfiguration() {
		Properties prop = ConfigLoader.loadProperties(appDir);
		Set<Object> keySet = prop.keySet();
		for (Object key : keySet) {
			app.put(key.toString(), prop.get(key).toString());
		}
	}


	public static void registerService(String key, Object value) {
		services.put(key, value);
	}

	public static Object getService(String key) {
		init();
		return services.get(key);
	}

	public static String getConfig(String key) {
		init();
		return app.get(key);
	}
}
