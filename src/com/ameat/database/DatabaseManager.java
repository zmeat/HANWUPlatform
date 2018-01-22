package com.ameat.database;

import org.javalite.activejdbc.Base;
import static com.ameat.utils.ConfigurationLoader.config;



public abstract class DatabaseManager implements DatabaseInterface{
	private static String driver = config("database."+DatabaseInterface.driver);
	private static String url = config("database."+DatabaseInterface.url);
	private static String userName = config("database."+DatabaseInterface.userName);
	private static String password = config("database."+DatabaseInterface.password);
	
	public DatabaseManager() {
		
	}
	
	public static void open() {
		Base.open(driver, url, userName, password);
	}
	
	public static void close() {
		Base.close();
	}
	
}
