package com.ameat.application;

import com.ameat.database.DatabaseManager;
import com.ameat.dataservice.TemperatureService;

import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		DatabaseManager.open();
		TimeController timeController = new TimeController();
		TemperatureService.exportExcelData();
		new Simulation(timeController).run();
		DatabaseManager.close();
	}
}
