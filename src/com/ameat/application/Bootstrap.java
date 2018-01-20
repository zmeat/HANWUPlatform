package com.ameat.application;

import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		TimeController timeController = new TimeController();
//		DataBaseManager.open();
		new Simulation(timeController).run();
//		DataBaseManager.close();
	}
}
