package com.ameat.database;

import org.javalite.activejdbc.Base;

import com.ameat.application.AppContainer;
import com.ameat.utils.ConfigLoader;


public abstract class DataBaseManager implements DataBaseInterface{
	private static String dbPath = AppContainer.getConfig("dbpath");
	private static String driver = ConfigLoader.loadProperties(dbPath).getProperty(DataBaseInterface.driver);
	private static String url = ConfigLoader.loadProperties(dbPath).getProperty(DataBaseInterface.url);
	private static String userName = ConfigLoader.loadProperties(dbPath).getProperty(DataBaseInterface.userName);
	private static String password = ConfigLoader.loadProperties(dbPath).getProperty(DataBaseInterface.password);
	
	public DataBaseManager() {
		
	}
	
	public static void open() {
		Base.open(driver, url, userName, password);
	}
	
	public static void close() {
		Base.close();
	}
	
}
