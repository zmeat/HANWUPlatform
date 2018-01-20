package com.ameat.simulation;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.interfaces.CompInterface;
import com.ameat.utils.ConfigurationLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Simulation {
	
	private TimeController timeController;
	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, CompInterface> components;
	private Map<String, ComunicationInterface> comunications;
	private ArrayList<Integer> sequence = new ArrayList<Integer>();
	
	public Simulation(TimeController timeController) {
		this.timeController = timeController;
		this.components = new HashMap<String, CompInterface>();
		this.comunications = new HashMap<String, ComunicationInterface>();
		this.register();
	}
	


	public void run() {
		
		// simulation start, components init;
		this.simulationStart();
		
		// simulation cycle
		while( this.timeController.getCurrentTime().isBefore(this.timeController.getEndTime()) ) {
			for(Integer i : this.sequence) {
				CompInterface component = this.components.get(i+"");
				if(this.timeController.getAnchorTime().isBefore(this.timeController.getCurrentTime())) {
					component.anchorCompute();
					this.timeController.nextAnchorTime();
				}
				component.compute();
			}
			logger.info(this.timeController.getCurrentTime()+" ->");
			this.timeController.nextStepTime();
		}
		
		// simulation end, components finished
		this.simulationEnd();
	}
	
	private void simulationEnd() {
		for(Integer i : this.sequence) {
			CompInterface component = this.components.get(""+i);
			component.finished();
		}
		logger.info("simulation end, components finished !");
	}

	private void simulationStart() {
		for(Integer i : this.sequence) {
			CompInterface component = this.components.get(""+i);
			component.init(this.timeController, this.comunications);
		}
		logger.info("simulation start, components are initialized !");
	}

	/**
	 * register component , register comunications, register sequence
	 */
	private void register() {
		Collection<String> values = ConfigurationLoader.configs("simulation.component").values();
		for(String value: values) {
			try {
				Class<?> claz = Class.forName(value.split(":")[0]);
				CompInterface component = (CompInterface) claz.newInstance();
				this.components.put(value.split(":")[1].toString(), component);
				this.sequence.add(Integer.valueOf(value.split(":")[1].toString()));
				String ClazName = value.substring(value.lastIndexOf(".")+1, value.indexOf(":"));
				String subKey = ClazName.substring(0, ClazName.indexOf("Impl"));
				File subFile = new File("src/com/ameat/component/comunications");
				File[] subListFiles = subFile.listFiles();
				for(File f : subListFiles) {
					if(f.getName().startsWith(subKey+"To")) {
						comunications.put(subKey, component);
					}
				}
				logger.info(component + "has been registered !");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		Collections.sort(this.sequence);
	}
	
	
	public static void main(String[] args) {
		Simulation s = new Simulation(new TimeController());
		s.run();
	}

}
