package com.ameat.app;

import com.ameat.database.DataBaseManager;
import com.ameat.repository.TemperatureRepository;

public class Bootstrap {
	
	public static void main(String[] args) throws ClassNotFoundException {
		AppContainer appContainer = new AppContainer();
		
		DataBaseManager.open();
		String temperatureFilePath = appContainer.getConfig("excelpath");
		TemperatureRepository.importExcelData(temperatureFilePath);
		DataBaseManager.close();
	}
}
