package com.ameat.simulation;

import com.ameat.application.AppContainer;
import com.ameat.component.AgentInterface;

import static com.ameat.utils.ConfigLoader.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Simulation {
	// app container
	private AppContainer appContainer;
	
	// time controller
	private TimeController timeController;
	
	// logger
	private Logger logger;
	
	// simulation components
	private Map<String, String> components;
	private Map<String, AgentInterface> agents;
	
	public Simulation(AppContainer appContainer, TimeController timeController) {
		this.appContainer = appContainer;
		this.timeController = timeController;
		this.agents = new HashMap<String, AgentInterface>();
		this.logger = Logger.getLogger(this.getClass());
		this.setComponents();
		this.setAgents();
	}
	
	public void run() {
		// simulation cycle
		while( this.timeController.getCurrentTime().isBefore(this.timeController.getEndTime()) ) {
			for(int i=0; i<this.agents.size(); i++) {
				AgentInterface agent = this.agents.get(i+"");
				agent.compute(timeController);
			}
			logger.info(this.timeController.getCurrentTime()+" simulation finished !");
			this.timeController.nextTime();
		}
		// simulation finished
		for(int i=0; i<this.agents.size(); i++) {
			AgentInterface agent = this.agents.get(""+i);
			agent.finished();
		}
		logger.info("simulation over !");
	}
	
	private void setAgents() {
		Collection<String> values = this.components.values();
		for(String value: values) {
			try {
				Class<?> claz = Class.forName(value.split(":")[0]);
				AgentInterface agent = (AgentInterface) claz.newInstance();
				this.agents.put(value.split(":")[1].toString(), agent);
				agent.init();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		logger.info("Agent init sucessed !");
	}

	private void setComponents() {
		this.components = configs("simulation.component");
	}

}
