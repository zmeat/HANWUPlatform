package com.application;

import org.javalite.activejdbc.Base;

import com.database.DataBaseManager;
import com.database.repository.TemperatureRepository;

public class Bootstrap {
	
	public static void main(String[] args) throws ClassNotFoundException {
		AppContainer appContainer = new AppContainer();
		
		//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/hanwu", "root", "");
		TemperatureRepository.create();
	}
}
