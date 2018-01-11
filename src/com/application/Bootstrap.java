package com.application;

import org.javalite.activejdbc.Base;

import com.database.DataBaseManager;
import com.database.repository.TemperatureRepository;

public class Bootstrap {
	private static Logger log = Logger.getLogger(Bootstrap.class);
	public static void main(String[] args) throws ClassNotFoundException {
		log.debug("the bootsrap is begin");
		Container container = new Container();
		
		//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/hanwu", "root", "");
		TemperatureRepository.create();
	}
}

