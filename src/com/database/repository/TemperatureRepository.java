package com.database.repository;

import java.sql.Date;

import org.apache.log4j.Logger;

import com.database.models.Temperature;

public class TemperatureRepository {
	final static Logger logger = Logger.getLogger(TemperatureRepository.class);
	
	public static void create() {
		Temperature t = new Temperature();
		t.set("county", "密云");
		t.set("date", "1993-03-03");
		t.set("degree", -10.2);
		t.save();
	}

}
