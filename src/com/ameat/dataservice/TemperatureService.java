package com.ameat.dataservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
		Table table = new Table("Temperature");
		Map<String, Object> record = new HashMap<String, Object>();
		
		record.put("county", data[0]);
		record.put("date", data[1]);
		record.put("degree", data[2]);
		
		table.save(record);
		logger.info("insert county:" +data[0]+ "date:" +data[1]+ "degree:" +data[2]);
	}


	public static void exportExcelData() {
		new Table("Temperature").export();
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

}
