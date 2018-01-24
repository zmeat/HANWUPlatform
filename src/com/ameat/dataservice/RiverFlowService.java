package com.ameat.dataservice;

import java.util.List;

import org.apache.log4j.Logger;

import com.ameat.tables.RiverFlow;
import com.ameat.tables.Table;
import com.ameat.utils.Jexcel;

public class RiverFlowService {
	private static Logger logger  = Logger.getLogger(RiverFlowService.class); 
    
    /**
     * save one item to database
     * @param data
     */
	public static void create(String[] data) {
		RiverFlow rf = new RiverFlow();
		rf.set("river_name", data[0]);
		rf.set("date", data[1]);
		rf.set("flow", data[2]);
		rf.saveIt();
		logger.info("insert riverName:" +data[0]+ "date:" +data[1]+ "flow:" +data[2]);
	}


	public static void exportExcelData() {
		new Table("RiverFlow").export();
	}

	/**
	 * import excel data
	 * @param filePath
	 */
	public static void importExcelData(String filePath) {
		List<String[]> datas = Jexcel.readExcel(filePath, 2);
		for (int i=1; i<datas.size(); i++) {
			for(int j=1; j<datas.get(i).length; j++) {
				String riverName = datas.get(0)[j];
				String date = datas.get(i)[0];
				String flow = datas.get(i)[j];
				
				RiverFlowService.create(new String[] {riverName, date, flow});
			}
		}
	}
}
