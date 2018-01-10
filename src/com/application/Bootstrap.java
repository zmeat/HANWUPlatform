package com.application;

import com.database.repository.TemperatureRepository;

public class Bootstrap {
	
	public static void main(String[] args) throws ClassNotFoundException {
		Container container = new Container();
		
		TemperatureRepository.create();
	}
}
