package com.ameat.simulation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.ameat.utils.ConfigLoader.config;


public class TimeController {

	private DateTime startTime;

	private DateTime endTime;
	
	private DateTime currentTime;

	private int stepValue;
	private String stepUnit;
	
	public TimeController() {
		String startStr = config("simulation.starttime");
		String endStr = config("simulation.endtime");
		String[] stepStr = config("simulation.timestep").split("-");
		
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
	
	public DateTime getEndTime() {
		return this.endTime;
	}
	
	public DateTime getCurrentTime() {
		return this.currentTime;
	}
	
}
