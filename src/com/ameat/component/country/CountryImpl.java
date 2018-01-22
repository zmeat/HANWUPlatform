package com.ameat.component.country;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.interfaces.CompInterface;
import com.ameat.simulation.TimeController;

public class CountryImpl implements CompInterface{
	
	private Logger logger = Logger.getLogger(this.getClass());
	private CountrySchedule countrySchedule;
	private TimeController timeController;
	private Map<String, ComunicationInterface> comunications;

	@Override
	public void init(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.timeController = timeController;
		this.comunications = comunications;
		this.countrySchedule = new CountrySchedule();
	}
	
	@Override
	public void compute() {
		logger.info("CountryImpl cycling ");
		this.countrySchedule.testGetET(comunications);
//		farmerModel.loadToCompute(timeController);
	}
	
	@Override
	public void finished() {
		logger.info("CountryImpl finished ");
	}


	@Override
	public void anchorCompute() {
		// TODO Auto-generated method stub
		
	}

}
