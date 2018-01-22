package com.ameat.dataservice;

import java.util.List;

import org.apache.log4j.Logger;

import com.ameat.tables.Rain;
import com.ameat.utils.Jexcel;

public class RainService {
	private static Logger logger  = Logger.getLogger(RainService.class); 
    
    /**
     * save one item to database
     * @param data
     */
	public static void create(String[] data) {
		Rain rain = new Rain();
		rain.set("station", data[0]);
		rain.set("date", data[1]);
		rain.set("rainfall", data[2]);
		rain.saveIt();
		logger.info("insert station:" +data[0]+ "date:" +data[1]+ "rainfall:" +data[2]);
	}

	/**
	 * export datas to a excel file
	 * @param filePath
	 */
	public static void exportExcelData(String filePath) {
		System.out.println("呵呵");
	}

	/**
	 * import excel data
	 * @param filePath
	 */
	public static void importExcelData(String filePath) {
		List<String[]> datas = Jexcel.readExcel(filePath, 0);
		for (int i=1; i<datas.size(); i++) {
			for(int j=3; j<datas.get(i).length; j++) {
				String station = datas.get(0)[j];
				String date = datas.get(i)[0];
				String rainfall = datas.get(i)[j];
				
				RainService.create(new String[] {station, date, rainfall});
			}
		}
	}
}
