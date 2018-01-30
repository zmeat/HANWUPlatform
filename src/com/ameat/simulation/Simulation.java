package com.ameat.simulation;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.interfaces.CompInterface;
import com.ameat.tables.Table;

import static com.ameat.utils.ConfigurationLoader.config;
import static com.ameat.utils.ConfigurationLoader.configs;

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
		// record this time simulation record to database
		this.recordSimulation();
		
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
		Collection<String> values = configs("simulation.component").values();
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
						comunications.put(subKey.toLowerCase(), component);
					}
				}
				logger.info(component + "has been registered !");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		Collections.sort(this.sequence);
	}
	
	private void recordSimulation() {
		Table sim = new Table("Simulation");
		Map<String, Object> record = new HashMap<String, Object>();
		StringBuffer comps = new StringBuffer();
		StringBuffer crop_areas = new StringBuffer();
		StringBuffer farmer_numbers = new StringBuffer();
	
		record.put("start_time", config("simulation.starttime"));
		record.put("end_time", config("simulation.endtime"));
		record.put("time_step", config("simulation.timestep"));
		record.put("anchor_time", config("simulation.anchortime"));
		record.put("mu", config("simulation.mu"));
		record.put("learn", config("simulation.learn"));
		record.put("radius", config("simulation.radius"));
		record.put("sense", config("simulation.sense"));
		record.put("cv", config("simulation.cv"));
		this.components.forEach((k, v) -> {
			comps.append(v.toString().split("@")[0]+":"+k+";");
		});
		record.put("components", comps.toString());
		configs("simulation.crop_area").forEach((k, v) -> {
			crop_areas.append(k+":"+v.toString()+";");
		});
		record.put("crop_area", crop_areas.toString());
		configs("simulation.farmer_number").forEach((k, v) -> {
			farmer_numbers.append(k+":"+v.toString()+";");
		});
		record.put("farmer_number", farmer_numbers.toString());
		record.put("water_limit", config("simulation.water_limit"));
		
		sim.insertReturnKey(record);
	}
	
}
