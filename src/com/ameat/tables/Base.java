package com.ameat.tables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Configuration;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.Model;

import com.ameat.utils.ConfigurationLoader;

public class Base extends Model {
	
	public static Map<String, String> getComments(String tableName) {
		DB db = new DB(Base.class.getSimpleName());
		String environment = Configuration.getEnv();
		String driver = ConfigurationLoader.config("database."+environment+".driver");
		String url = ConfigurationLoader.config("database."+environment+".url").
				substring(0, ConfigurationLoader.config("database."+environment+".url").
						lastIndexOf('/')+1)+"information_schema";
		String username = ConfigurationLoader.config("database."+environment+".username");
		String password = ConfigurationLoader.config("database."+environment+".password");
		db.open(driver, url, username, password);
		Map<String, String> comments = new HashMap<String, String>();
		String query = "select * from COLUMNS where TABLE_NAME = '" + tableName + "';";
		List<Map> lists = db.all(query);
		System.out.println(lists);
		db.close();
		System.exit(0);
		return comments;
	}
	
	
}
