package com.database.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.SuperLazyList;
import com.database.DataBaseManager;

import com.database.DataBaseManager;

public class Rain extends BaseModel{
	private DataBaseManager dataBaseManager = null;
	private Rain() {
	}
	public static Rain newInstance() {
		DataBaseManager dataBaseManager = BaseModel.getDbproviders().get(DBManager_Name);
		if(dataBaseManager == null) {
			dataBaseManager = new DataBaseManager();
			BaseModel.getDbproviders().put(DBManager_Name, dataBaseManager);
		} 
		return new Rain();
	}
}
