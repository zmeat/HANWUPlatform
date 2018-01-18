package com.ameat.simulation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ameat.application.AppContainer;
import com.ameat.utils.ConfigLoader;


public class TimeController {

	private DateTime startTime;

	private DateTime endTime;
	
	private DateTime currentTime;

	private int stepValue;
	private String stepUnit;
	
	public TimeController() {
		String startStr = ConfigLoader.loadProperties(AppContainer.getConfig("simulationpath")).getProperty("starttime");
		String endStr = ConfigLoader.loadProperties(AppContainer.getConfig("simulationpath")).getProperty("endtime");
		String[] stepStr = ConfigLoader.loadProperties(AppContainer.getConfig("simulationpath")).getProperty("timestep").split("-");
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");  
		this.startTime = DateTime.parse(startStr, formatter);  
		this.endTime = DateTime.parse(endStr, formatter);  
		this.stepValue = Integer.parseInt(stepStr[0]);
		this.stepUnit = stepStr[1];
		
		this.currentTime = this.startTime;
	}
	/**
	 * 
	 * @return DateTime = (current time) + (1 time step)
	 */
	public DateTime nextTime() {
		if (this.stepUnit.equals("day")) this.currentTime = this.currentTime.plusDays(this.stepValue);
		if (this.stepUnit.equals("month")) this.currentTime = this.currentTime.plusMonths(this.stepValue);
		if (this.stepUnit.equals("hour")) this.currentTime = this.currentTime.plusHours(this.stepValue);
		return this.currentTime;
	}
	
	public DateTime getCurrentTime() {
		return this.currentTime;
	}
	
}
