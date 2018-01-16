package com.ameat.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.ameat.models.RiverFlow;
import com.ameat.utils.Jexcel;

public class RiverFlowRepository {
	private static Logger logger  = Logger.getLogger(RiverFlowRepository.class); 
    
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
		List<String[]> datas = Jexcel.readExcel(filePath, 2);
		for (int i=1; i<datas.size(); i++) {
			for(int j=1; j<datas.get(i).length; j++) {
				String riverName = datas.get(0)[j];
				String date = datas.get(i)[0];
				String flow = datas.get(i)[j];
				
				RiverFlowRepository.create(new String[] {riverName, date, flow});
			}
		}
	}
}
