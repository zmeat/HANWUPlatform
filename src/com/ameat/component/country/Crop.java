package com.ameat.component.country;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public abstract class Crop {
	protected Map<String, Integer> L;
	protected Map<String, Double> K;
	protected Integer totalDay;
	protected DateTime currentTime;
	protected DateTime sowingTime;
	protected DateTime harvestTime;
	
	/**
	 * 根据当前时间获取本年的收获时间
	 * @param currentTime
	 * @return
	 */
	public DateTime getHarvestTime() {
		while(this.harvestTime.getYear() < this.currentTime.getYear()) this.harvestTime.plusYears(1);
		return this.harvestTime;
	}
	/**
	 * 根据当前时间获取本年度播种时间
	 * @param currentTime
	 * @return
	 */
	public DateTime getSowingTime() {
		while(this.sowingTime.getYear() < currentTime.getYear()) this.sowingTime.plusYears(1);
		return this.sowingTime;
	}
	/**
	 * 获取当前时间的作物系数
	 * @return 
	 */
	public Double getKc() {
		double Kc = -1.0;
		int currentDay = this.currentTime.getDayOfYear() - this.getSowingTime().getDayOfYear();
		if(currentDay < 0 || currentDay > this.totalDay) return Kc;
		else {
			int lgrow = this.totalDay - this.L.get("Lini") - this.L.get("Lmid") - this.L.get("Llate");
			if( currentDay < this.L.get("Lini") ) Kc = this.K.get("Kcini");
			else if( currentDay < (this.L.get("Lini")+lgrow) ) 
				Kc = this.K.get("Kcini") + ( (currentDay-this.L.get("Lini")) * (this.K.get("Kcmid") - this.K.get("Kcini"))/lgrow );
			else if( currentDay < (this.L.get("Lini")+this.L.get("Lmid") + lgrow)) Kc = this.K.get("Kcmid");
			else Kc = this.K.get("Kcmid") + (currentDay - (this.L.get("Lini")+this.L.get("Lmid") + lgrow))
					* (this.K.get("Kcend")-this.K.get("Kcmid"))/this.L.get("Llate");
		}
		return Kc;
	}
	
}

class Rice extends Crop{
	public Rice(DateTime currentTime) {
		this.currentTime = currentTime;
		this.L = new HashMap<String, Integer>();
		this.L.put("Lini", 30);
		this.L.put("Lmid", 60);
		this.L.put("Llate", 30);
		this.K = new HashMap<String, Double>();
		this.K.put("Kcini", 1.05);
		this.K.put("Kcmid", 1.20);
		this.K.put("Kcend", 0.75);
		this.totalDay = 150;
		this.sowingTime = new DateTime(this.currentTime.getYear(),4,20,0,0);
		this.harvestTime = this.sowingTime.plusDays(this.totalDay);
	}
	
}

class Maize extends Crop{
	public Maize(DateTime currentTime) {
		this.currentTime = currentTime;
		this.L = new HashMap<String, Integer>();
		this.L.put("Lini", 30);
		this.L.put("Lmid", 50);
		this.L.put("Llate", 30);
		this.K = new HashMap<String, Double>();
		this.K.put("Kcini", 0.30);
		this.K.put("Kcmid", 1.20);
		this.K.put("Kcend", 0.35);
		this.totalDay = 150;
		this.sowingTime = new DateTime(this.currentTime.getYear(),4,20,0,0);
		this.harvestTime = this.sowingTime.plusDays(this.totalDay);
	}
	
}