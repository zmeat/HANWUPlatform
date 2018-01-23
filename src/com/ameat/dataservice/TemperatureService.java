package com.ameat.dataservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Model;

import com.ameat.tables.Table;
import com.ameat.tables.Temperature;
import com.ameat.utils.Jexcel;

public class TemperatureService {
    private static Logger logger  = Logger.getLogger(TemperatureService.class); 
    
    /**
     * save one item to database
     * @param data
     */
	public static void create(String[] data) {
		Temperature temp = new Temperature();
		temp.set("county", data[0]);
		temp.set("date", data[1]);
		temp.set("degree", data[2]);
		temp.saveIt();
		logger.info("insert county:" +data[0]+ "date:" +data[1]+ "degree:" +data[2]);
	}

	/**
	 * export datas to a excel file
	 * @param filePath
	 */
	public static void exportExcelData() {
		String sheetName = Temperature.getTableName();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = Temperature.getTableName() + "_export_" + df.format(new Date());
		List<Map<String, String>> headers = getExportExcelHeader();
		String getDataFunc = TemperatureService.class.getName() + ":getExportData";
		Object[] args = new Object[0];
		Jexcel.writeExcel(sheetName, fileName, getDataFunc, headers, args);
	}
	
	public static List<Map<String, Object>> getExportData() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		List<Model> datas = Temperature.where("").offset(20).limit(10);

		for (Model item : datas) {
			lists.add(item.toMap());
		}
		
		return lists;
	}

	/**
	 * import excel data
	 * @param filePath
	 */
	public static void importExcelData(String filePath) {
		List<String[]> datas = Jexcel.readExcel(filePath, 1);
		for (int i=1; i<datas.size(); i++) {
			for(int j=3; j<datas.get(i).length; j++) {
				String county = datas.get(0)[j];
				String date = datas.get(i)[0];
				String degree = datas.get(i)[j];
				
				TemperatureService.create(new String[] {county, date, degree});
			}
		}
	}
	
	
	/**
	 * Get Excel Header
	 * @return
	 */
	private static List<Map<String, String>> getExportExcelHeader() {
		List<Map<String, String>> headers = new ArrayList<Map<String, String>>();
		System.out.println(Temperature.getTableName());
		Map<String, String> comments = new Table("Temperature").getComments();
		comments.forEach((k, v) -> {
			Map<String, String> header = new HashMap<String, String>();
			header.put("key", k);
			header.put("value", v);
			headers.add(header);	
		});
		
		return headers;
	}
}
