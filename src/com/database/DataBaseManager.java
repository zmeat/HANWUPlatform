package com.database;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import com.application.AppContainer;
import com.utils.ConfigLoader;



public abstract class DataBaseManager extends Model implements DataBaseInterface{
	private static String dbType = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.dbType);;
	private static String engine = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.engine);
	private static String hostName = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.hostName);
	private static String hostPort = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.hostPort);
	private static String dbName = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.dbName);
	private static String userName = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.userName);
	private static String password = ConfigLoader.loadProperties(AppContainer.getDBPath()).getProperty(DataBaseInterface.password);
	public DataBaseManager() {
	}
	
	public static void open() {
		Base.open(engine, "jdbc:"+dbType+"://"+hostName+":"+hostPort+"/"+dbName, userName, password);
	}
	
	public static void close() {
		Base.close();
	}
	
}
