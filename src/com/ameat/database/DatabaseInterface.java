package com.ameat.database;

import org.javalite.activejdbc.Configuration;

public interface DatabaseInterface {
	public String environment = Configuration.getEnv();
	
	public String driver   = environment + ".driver";
	public String url      = environment + ".url";
	public String userName = environment + ".username";
	public String password = environment + ".password";

}