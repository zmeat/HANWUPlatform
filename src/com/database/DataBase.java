package com.database;

import com.application.Container;
import com.utils.ConfigLoader;

public class DataBase extends DataBaseManager {
	public DataBase() {
		super(ConfigLoader.loadProperties(Container.getDBPath()).getProperty(engine));
	}

}
