package com.ameat.component.meteorology;

import org.apache.log4j.Logger;

import com.ameat.simulation.TimeController;

public class Evapotraspiration {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private TimeController tc;
	private static final String lat = "N41°";  
	private static final String lon = "E116°";  
	
	public Evapotraspiration(TimeController tc) {
		this.tc = tc;
	}
	
	/**
	 * 计算参考作物腾发量：缺少气象数据是ETo的计算
	 * @param location：地点
	 * @return
	 */
	public Double getETo(String location) {
		double[] temperature = getgetTemperature();
		if (temperature == null || temperature.length < 1) {
			logger.error("could not get " + location + "'s temperature");
			return -1.0;
		}
		double Tmin = temperature[0];
		double Tmax = temperature[1];
		return 0.0;
	}
	

	private double[] getgetTemperature() {
		
		return null;
	}

	/**
	 * 计算Kc作物的腾发量
	 * @param Kc：同一作物不同阶段具有不同的Kc值
	 * @return
	 */
	public Double getETc() {
		
		return -8.0;
	}
}
