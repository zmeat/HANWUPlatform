package com.ameat.application;

import org.javalite.activejdbc.DB;

import com.ameat.dataservice.RainService;
import com.ameat.dataservice.RiverFlowService;
import com.ameat.dataservice.TemperatureService;
import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;
import com.ameat.tables.Table;
import com.ameat.utils.ConfigurationLoader;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		try {
			TimeController timeController = new TimeController();
			new Simulation(timeController).run();
//			clearTable();
			
//			tempTest();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeAllConnections();
		}
	}
	
	
	
	public static void clearTable(){
		new Table("FarmerTrace").delete();
		new Table("FarmerAnchor").delete();
		new Table("FarmerInit").delete();
		new Table("Simulation").delete();
		new Table("CountryTrace").delete();
	}
	
	// 临时的测试方法写在这里面
	public static void tempTest() {
		String filePath = ConfigurationLoader.config("application.excelpath");
		TemperatureService.importExcelData(filePath);
		RainService.importExcelData(filePath);
		RiverFlowService.importExcelData(filePath);
		TemperatureService.exportExcelData();
		RainService.exportExcelData();
		RiverFlowService.exportExcelData();
		
	}
}
