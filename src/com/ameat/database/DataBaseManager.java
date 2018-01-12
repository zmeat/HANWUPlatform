package com.ameat.database;

import org.javalite.activejdbc.Base;

import com.ameat.app.AppContainer;
import com.ameat.utils.ConfigLoader;


public abstract class DataBaseManager implements DataBaseInterface{
	private static String driver = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.driver);
	private static String url = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.url);
	private static String userName = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.userName);
	private static String password = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.password);
	
	public DataBaseManager() {
		
	}
	
	public static void open() {
		Base.open(driver, url, userName, password);
	}
	
	public static void close() {
		Base.close();
	}
	
}
