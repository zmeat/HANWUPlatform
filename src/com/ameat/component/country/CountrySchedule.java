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
	private List<Farmer> farmerInfos;
	
	public CountrySchedule(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.timeController = timeController;
		this.comunications = comunications;
		this.cropInfos = new HashMap<String, Crop>();
		this.cropInfos.put("rice", new Rice(timeController.getCurrentTime()));
		this.cropInfos.put("maize", new Maize(timeController.getCurrentTime()));
		// 根据simulation table生成农民信息并存放在farmer_init表中,再构造赋值给所有的farmer -------------------未实现
		this.farmerInfos = constructFarmers();
	}




	protected void loadToCompute() {
		
		
		
	}
//	protected void testGetET(Map<String, ComunicationInterface> comunications) {
//		MeteorologyToCountry mTc = (MeteorologyToCountry)comunications.get("Meteorology");
////		System.out.println("=-=-=-=-=-test+comunications==="+mTc.getETo("LuanPing"));
//	}
	private List<Farmer> constructFarmers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
