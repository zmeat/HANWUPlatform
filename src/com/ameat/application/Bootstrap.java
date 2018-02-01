package com.ameat.application;

import org.javalite.activejdbc.DB;

import com.ameat.simulation.TimeController;
import com.ameat.tables.Table;

public class Bootstrap {
	
	public static void main(String[] args) {
		
		try {
			TimeController timeController = new TimeController();
//			new Simulation(timeController).run();
//			clearTable();
			exportTable();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeAllConnections();
		}
	}
	
	
	
	public static void clearTable(){
		new Table("FarmerTrace").delete();
		new Table("FarmerAnchor").delete();
		new Table("FarmerInit").delete();
		new Table("Simulation").delete();
		new Table("CountryTrace").delete();
	}
	
	public static void exportTable() {
		new Table("FarmerTrace").export();
		new Table("FarmerAnchor").export();
		new Table("FarmerInit").export();
		new Table("Simulation").export();
		new Table("CountryTrace").export();
	}
	
}
