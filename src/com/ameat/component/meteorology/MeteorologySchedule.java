package com.ameat.component.meteorology;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.simulation.TimeController;

public class MeteorologySchedule {

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, ComunicationInterface> connections;
	private TimeController timeSchedule;

	public MeteorologySchedule(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.connections = comunications;
		this.timeSchedule = timeController;
	}
	
	
	
	
}
