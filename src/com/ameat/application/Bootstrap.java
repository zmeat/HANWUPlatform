package com.ameat.application;

import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		AppContainer appContainer = new AppContainer();
		TimeController timeController = new TimeController();
		
		new Simulation(appContainer, timeController).run();
	}
}
