package com.database.models;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.database.DataBaseManager;
import com.sun.javafx.collections.MappingChange.Map;


public class BaseModel {
	private DataBaseManager dbManager = null;
	
	private static final HashMap<String, DataBaseManager> dbProviders = new HashMap<String, DataBaseManager>();
	public static final String DBManager_Name = "DBManager";
	
	public static HashMap<String, DataBaseManager> getDbproviders() {
		return dbProviders;
	}
}
