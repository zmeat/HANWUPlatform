package com.ameat.component.meteorology;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.comunications.MeteorologyToCountry;
import com.ameat.component.interfaces.CompInterface;
import com.ameat.simulation.TimeController;

public class MeteorologyImpl implements CompInterface, MeteorologyToCountry{

	private Logger logger = Logger.getLogger(this.getClass());
	private MeteorologySchedule meteorologySchedule;

	@Override
	public void init(TimeController timeController, Map<String, ComunicationInterface> comunications) {
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

	@Override
	public Evapotranspiration getET() {
		return this.meteorologySchedule.getET();
	}


}
