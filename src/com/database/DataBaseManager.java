package com.database;

import org.javalite.activejdbc.Base;

import com.application.Container;
import com.utils.ConfigLoader;



public abstract class DataBaseManager implements DataBaseInterface {
	
	public DataBaseManager(String type) {
		
		String engine = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type + "engine");;
		String hostName = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type + "hostname");
		String hostPort = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type + "hostport");
		String DBName = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type +  "dbname");
		String userName = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type + "username");
		String password = ConfigLoader.loadProperties(Container.getDBPath()).getProperty(type + "password");
		
		connectDB(engine, "jdbc:"+type+"://"+hostName+":"+hostPort+"/"+DBName, userName, password);
	}
	
	private static void connectDB(String engine, String connectPath, String userName, String password) {
		Base.open(engine, connectPath, userName, password);
	}
	
}
