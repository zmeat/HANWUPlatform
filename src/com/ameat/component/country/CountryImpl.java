package com.ameat.component.country;

import org.apache.log4j.Logger;

import com.ameat.component.CompInterface;
import com.ameat.simulation.TimeController;

public class CountryImpl implements CompInterface{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void init() {
		logger.info("simulation inited");
	}

	@Override
	public void compute(	TimeController timeController) {
		logger.info("simulation cycling ");
	}
	
	@Override
	public void finished() {
		logger.info("simulation finished ");
	}

}
