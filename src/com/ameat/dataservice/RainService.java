package com.ameat.dataservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.tables.Rain;
import com.ameat.tables.Table;
import com.ameat.utils.Jexcel;

public class RainService {
	private static Logger logger  = Logger.getLogger(RainService.class); 
    
    /**
     * save one item to database
     * @param data
     */
	public static void create(String[] data) {
		Table table = new Table("Rain");
		Map<String, Object> record = new HashMap<String, Object>();
		
		record.put("station", data[0]);
		record.put("date", data[1]);
		record.put("rainfall", data[2]);
		
		table.save(record);
		
		logger.info("insert station:" +data[0]+ "date:" +data[1]+ "rainfall:" +data[2]);
	}

	
	public static void exportExcelData() {
		new Table("Rain").export();
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
