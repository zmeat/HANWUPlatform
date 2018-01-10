package com.database;

import java.util.Properties;

import com.application.Container;
import com.utils.ConfigLoader;

public abstract class DataBaseManager implements DataBaseInterface {
	
	public DataBaseManager(String engine) {
		try{
			Class.forName(engine);
			System.out.println("Success loading "+engine+" !");
		}
		catch(Exception e){
			System.out.print("Error loading "+engine+" !");
			e.printStackTrace();
		}
	}
	
}
