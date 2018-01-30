package com.ameat.tables;

import java.util.HashMap;
import java.util.Map;

import org.javalite.activejdbc.Model;

import com.ameat.utils.Generator;

public class FarmerInit extends Model {

	public static  void init() {
		Map<String, Object> simulation = getLastSimulation();
		generateFarmers(simulation);
	}
	
	// 获取最后一个simulation数据
	private static Map<String, Object> getLastSimulation() {
		Table simulation = new Table("Simulation");
		return simulation.getOne("id desc");
	}
	
	// 根据配置生成所有的农民
	private static void generateFarmers(Map<String, Object> simulation) {
		int sim_id = Integer.valueOf(simulation.get("id").toString());
		Table FarmerInit = new Table("FarmerInit");
		Map<String, String> farmerNumbers = colonToMap(simulation.get("farmer_number").toString());
		farmerNumbers.forEach((k, v) -> {
			for(int i=1; i<=Integer.valueOf(v); i++) {
				Map<String, Object> record = new HashMap<String, Object>();
				Map<String, String> cropAreas = colonToMap(simulation.get("crop_area").toString());
				Double cv = Double.valueOf(simulation.get("cv").toString());
				Double mu = Generator.Normal(Double.valueOf(simulation.get("mu").toString()), cv);
				Double learn = Generator.Normal(Double.valueOf(simulation.get("learn").toString()), cv);
				Double radius = Generator.Normal(Double.valueOf(simulation.get("radius").toString()), cv);
				Double sense = Generator.Normal(Double.valueOf(simulation.get("sense").toString()), cv);
				Double waterPermit = Double.valueOf(simulation.get("water_limit").toString()) == -1
						? -1: getWaterPermit(simulation, Double.valueOf(cropAreas.get(k)));
					
				record.put("sim_id", sim_id);
				record.put("location", k);
				record.put("farmer_no", i);
				record.put("mu", mu);
				record.put("learn", learn);
				record.put("radius", radius);
				record.put("sense", sense);
				record.put("farmer_number", Integer.valueOf(v));
				record.put("crop_area", cropAreas.get(k));
				record.put("water_permit", waterPermit);
				FarmerInit.insertOne(record);
			}
		});
	}
	
	// 转换形如 k1:v1;k2:v2;k3:v3;  这样的字符串为map
	private static Map<String, String> colonToMap(String colon) {
		Map<String, String> result = new HashMap<String, String>();
		String[] items = colon.split(";");
		for(String item : items) {
			result.put(item.split(":")[0], item.split(":")[1]);
		}
		
		return result;
	}
	
	private static double getWaterPermit(Map<String, Object> simulation, double cropAreasPerFarmer) {
		Map<String, String> cropAreas = colonToMap(simulation.get("crop_area").toString());
		Map<String, String> farmerNumbers = colonToMap(simulation.get("farmer_number").toString());
		double areaCounts = 0.0;
		for(String key : farmerNumbers.keySet()) {
			areaCounts += Double.valueOf(cropAreas.get(key))*Double.valueOf(farmerNumbers.get(key));
		}
		
		return cropAreasPerFarmer*(Double.valueOf(simulation.get("water_limit").toString())/areaCounts);
	}
	

}
