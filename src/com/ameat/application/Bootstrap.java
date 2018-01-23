package com.ameat.application;

import com.ameat.database.DatabaseManager;
import com.ameat.dataservice.TemperatureService;

import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;
import com.ameat.utils.ConfigurationLoader;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		TimeController timeController = new TimeController();
	//	TemperatureService.importExcelData(ConfigurationLoader.config("application.excelpath"));
	//	TemperatureService.exportExcelData();
		new Simulation(timeController).run();
		
		DatabaseManager.close();
	}
}
