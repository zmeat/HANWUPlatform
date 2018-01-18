package com.ameat.application;

import com.ameat.database.DataBaseManager;
import com.ameat.dataservice.RainService;
import com.ameat.dataservice.RiverFlowService;
import com.ameat.dataservice.TemperatureService;
import com.ameat.simulation.Simulation;
import com.ameat.simulation.TimeController;

public class Bootstrap {
	
	public static void main(String[] args) throws ClassNotFoundException {
		AppContainer appContainer = new AppContainer();
		
		TimeController timeController = new TimeController();
		System.out.println(timeController.getCurrentTime());
		System.out.println(timeController.nextTime());
		
		
		DataBaseManager.open();
//		String excelPath = appContainer.getConfig("excelpath");
//		TemperatureRepository.importExcelData(excelPath);
//		TemperatureRepository.exportExcelData();
//		RainRepository.importExcelData(excelPath);
//		RiverFlowRepository.importExcelData(excelPath);
		DataBaseManager.close();
	}
}
