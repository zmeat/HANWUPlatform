package com.ameat.tables;

import java.util.HashMap;
import java.util.Map;

import org.javalite.activejdbc.Model;

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
				record.put("sim_id", sim_id);
				record.put("location", k);
				record.put("farmer_no", i);
				record.put("mu", simulation.get("mu"));
				record.put("learn", simulation.get("learn"));
				record.put("radius", simulation.get("radius"));
				record.put("sense", simulation.get("sense"));
				record.put("farmer_number", Integer.valueOf(v));
				record.put("crop_area", cropAreas.get(k));
				record.put("water_permit", simulation.get("water_limit"));
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
	

}
