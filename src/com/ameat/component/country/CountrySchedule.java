package com.ameat.component.country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.comunications.MeteorologyToCountry;
import com.ameat.component.meteorology.Evapotranspiration;
import com.ameat.component.meteorology.MeteorologyImpl;
import com.ameat.simulation.TimeController;
import com.ameat.tables.Table;
import com.ameat.utils.Generator;

public class CountrySchedule{
	private TimeController timeController;
	private Map<String, ComunicationInterface> comunications;
	private Evapotranspiration ET;
	private Map<String, Crop> cropInfos;
	private Map<String, List<Farmer>> farmers;
	private boolean isWaterLimited = false;
	
	public CountrySchedule(TimeController timeController, Map<String, ComunicationInterface> comunications) {
		this.timeController = timeController;
		this.comunications = comunications;
		this.cropInfos = new HashMap<String, Crop>();
		this.cropInfos.put("rice", new Rice(timeController));
		this.cropInfos.put("maize", new Maize(timeController));
		// 根据farmer_init表生成农民对象，注：内部用到成员变量timeController，cropInfos
		this.farmers = constructFarmers();
		MeteorologyToCountry comunicationInterface = (MeteorologyToCountry) comunications.get("meteorology");
		this.ET = comunicationInterface.getET();
	}

	protected void loadToCompute() {
		
		Set<String> locations = this.farmers.keySet();
		for(String location : locations) {
			List<Farmer> farmers = this.farmers.get(location);
			for(Farmer farmer : farmers) {
				// 更新数据
				farmer.dayByDay(ET, this.isWaterLimited);
				// 插入数据库
				farmer.recordToFarmerTrace();
			}
		}
		
	}
	
	
	
	protected void loadToAnchorCompute() {
		
		Set<String> locations = this.farmers.keySet();
		for(String location : locations) {
			List<Farmer> farmers = this.farmers.get(location);
			for(Farmer farmer : farmers) {
				// 年终总结
				farmer.summaryYearEnd();
				// 写入数据库
				farmer.recordToFarmerAnchor();
			    // 更新策略(需要根据所有农民的状况来评估自己是否更新策略)
				farmer.updateStrategy(this.farmers);
			}
		}
		
	}
	
	protected void loadToFinish() {
		
	}
	
	
	
	
	
	
	/**
	 * 根据farmer_init表生成农民对象,并且确定是否有用水限制
	 * @return  Map<String, List<Farmer>>     location : List<Farmer> 
	 */
	private Map<String, List<Farmer>> constructFarmers() {
		Map<String, List<Farmer>> farmers = new HashMap<String, List<Farmer>>();
		
		Table farmerInit = new Table("FarmerInit");
		Table simulation = new Table("Simulation");
		Map<String, Object> record = simulation.getOne("id desc");
		int simulationId = Integer.parseInt(record.get("sim_id").toString());
		double waterLimit = Double.parseDouble(record.get("water_limit").toString());
		if(waterLimit > 0.0) this.isWaterLimited = true;
		
		List<Map<String, Object>> records = farmerInit.gets("location = ChiCheng", "sim_id="+simulationId);
		farmers.put("ChiCheng", this.convertRecordsToFarmer(records));
		List<Map<String, Object>> records1 = farmerInit.gets("location = ChiCheng", "sim_id="+simulationId);
		farmers.put("FengNing", this.convertRecordsToFarmer(records1));
		List<Map<String, Object>> records2 = farmerInit.gets("location = ChiCheng", "sim_id="+simulationId);
		farmers.put("LuanPing", this.convertRecordsToFarmer(records2));
		
		return farmers;
	}
	
	private List<Farmer> convertRecordsToFarmer(List<Map<String, Object>> records){
		List<Farmer> farmers = new ArrayList<Farmer>();
		for(Map<String, Object> record : records) {
			farmers.add(new Farmer(this.timeController, this.cropInfos, Integer.valueOf(record.get("sim_id").toString()), 
					Integer.valueOf(record.get("farmer_no").toString()), record.get("location").toString(),
					Integer.parseInt(record.get("farmer_number").toString()), Double.valueOf(record.get("crop_area").toString()), 
					Double.valueOf(record.get("mu").toString()), Double.valueOf(record.get("learn").toString()), 
					Double.valueOf(record.get("radius").toString()), Double.valueOf(record.get("senes").toString()), 
					Double.valueOf(record.get("water_permit").toString())));
		}
		return farmers;
	}
	
}
