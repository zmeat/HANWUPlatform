package com.ameat.application;

import java.util.HashMap;
import java.util.Map;

import static com.ameat.utils.ConfigLoader.configs;

public class AppContainer {

	private static Map<String, String> app = new HashMap<String, String>();
	private static Map<String, Object> services = new HashMap<String, Object>();

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
		app = configs("configuration.application");
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
