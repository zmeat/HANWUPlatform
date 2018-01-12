package com.ameat.repository;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ameat.models.Temperature;
import com.ameat.utils.Jexcel;

public class TemperatureRepository {
    private static Logger logger  = Logger.getLogger(TemperatureRepository.class); 
	
	public void create() {
		Temperature temp = new Temperature();
		temp.set("county", "hheh");
		temp.set("date", "1992-03-03");
		temp.save();
	}

	public static void exportExcelData() {
		System.out.println("呵呵");
	}

	public static void importExcelData(String filePath) {
		try {
			List<String[]> datas = Jexcel.readExcel(filePath);
			logger.info(datas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
