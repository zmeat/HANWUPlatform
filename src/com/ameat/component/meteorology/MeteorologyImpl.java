package com.ameat.component.meteorology;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.comunications.MeteorologyToCountry;
import com.ameat.component.interfaces.CompInterface;
import com.ameat.simulation.TimeController;

public class MeteorologyImpl implements CompInterface, MeteorologyToCountry{

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, ComunicationInterface> connections;
	private TimeController timeSchedule;
	private MeteorologySchedule meteorologySchedule;

	@Override
	public void init(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.connections = comunications;
		this.timeSchedule = timeController;
		this.meteorologySchedule = new MeteorologySchedule(timeController, comunications);
	}

	@Override
	public void compute() {
		
	}

	@Override
	public void anchorCompute() {
		
	}

	@Override
	public void finished() {
		
	}


	/**
	 * provide information for other comunications:
	 */
	@Override
	public Double getETo(String location) {
		return this.meteorologySchedule.getET().getETo(location);
	}
}
