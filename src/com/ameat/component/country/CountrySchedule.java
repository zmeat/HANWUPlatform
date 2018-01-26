package com.ameat.component.country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.comunications.MeteorologyToCountry;
import com.ameat.component.meteorology.Evapotranspiration;
import com.ameat.simulation.TimeController;

public class CountrySchedule{
	private TimeController timeController;
	private Map<String, ComunicationInterface> comunications;
	private Map<String, Crop> cropInfos;
	private Map<String, List<Farmer>> farmerInfos;
	private double totalArea;
	
	public CountrySchedule(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.timeController = timeController;
		this.comunications = comunications;
		this.cropInfos = new HashMap<String, Crop>();
		this.cropInfos.put("rice", new Rice(timeController));
		this.cropInfos.put("maize", new Maize(timeController));
		// 根据simulation table生成农民信息并存放在farmer_init表中,再构造赋值给所有的farmer -------------------未实现
		this.farmerInfos = constructFarmers();
	}


	private Map<String, List<Farmer>> constructFarmers() {
		// TODO Auto-generated method stub
		return null;
	}


	protected void loadToCompute() {
		
		
		
	}
	
	
	protected void loadToAnchorCompute() {
		
	}
	
	
}
