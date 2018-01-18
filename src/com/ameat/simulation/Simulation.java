package com.ameat.simulation;

import com.ameat.component.CompInterface;
import com.ameat.utils.ConfigurationLoader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Simulation {
	
	private TimeController timeController;
	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, CompInterface> components;
	
	public Simulation(TimeController timeController) {
		this.timeController = timeController;
		this.components = new HashMap<String, CompInterface>();
		this.registerComponents();
	}
	
	public void run() {
		// simulation cycle
		while( this.timeController.getCurrentTime().isBefore(this.timeController.getEndTime()) ) {
			for(int i=0; i<this.components.size(); i++) {
				CompInterface agent = this.components.get(i+"");
				agent.compute(timeController);
			}
			logger.info(this.timeController.getCurrentTime()+" ->");
			this.timeController.nextTime();
		}
		// simulation finished
		for(int i=0; i<this.components.size(); i++) {
			CompInterface agent = this.components.get(""+i);
			agent.finished();
		}
		logger.info("simulation over !");
	}
	
	private void registerComponents() {
		Collection<String> values = ConfigurationLoader.configs("simulation.component").values();
		for(String value: values) {
			try {
				Class<?> claz = Class.forName(value.split(":")[0]);
				CompInterface agent = (CompInterface) claz.newInstance();
				this.components.put(value.split(":")[1].toString(), agent);
				agent.init();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		logger.info("Agent init sucessed !");
	}

}
