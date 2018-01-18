package com.ameat.database;

import org.javalite.activejdbc.Base;

import com.ameat.application.AppContainer;
import static com.ameat.utils.ConfigLoader.config;



public abstract class DataBaseManager implements DataBaseInterface{
	private static String driver = config("database."+DataBaseInterface.driver);
	private static String url = config("database."+DataBaseInterface.url);
	private static String userName = config("database."+DataBaseInterface.userName);
	private static String password = config("database."+DataBaseInterface.password);
	
	public DataBaseManager() {
		
	}
	
	public static void open() {
		Base.open(driver, url, userName, password);
	}
	
	public static void close() {
		Base.close();
	}
	
}
