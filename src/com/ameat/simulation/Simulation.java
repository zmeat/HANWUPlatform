package com.ameat.simulation;

import com.ameat.application.AppContainer;
import com.ameat.utils.ConfigLoader;

public class Simulation {
	
	private String timeStep;
	private String simT;
	
	public Simulation() {
		this.timeStep = ConfigLoader.loadProperties(AppContainer.getConfig("simulationpath")).getProperty("timestep");
		this.simT = ConfigLoader.loadProperties(AppContainer.getConfig("simulationpath")).getProperty("simT");
	}

}
