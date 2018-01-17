package com.ameat.application;

import com.ameat.database.DataBaseManager;
import com.ameat.repository.RainRepository;
import com.ameat.repository.RiverFlowRepository;
import com.ameat.repository.TemperatureRepository;

public class Bootstrap {
	
	public static void main(String[] args) throws ClassNotFoundException {
		AppContainer appContainer = new AppContainer();
		
		DataBaseManager.open();
//		String excelPath = appContainer.getConfig("excelpath");
//		TemperatureRepository.importExcelData(excelPath);
		TemperatureRepository.exportExcelData();
//		RainRepository.importExcelData(excelPath);
//		RiverFlowRepository.importExcelData(excelPath);
//		
		DataBaseManager.close();
	}
}
