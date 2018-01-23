package com.ameat.tables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Configuration;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;

import static org.javalite.activejdbc.ModelDelegate.metaModelFor;
import static org.javalite.activejdbc.ModelDelegate.metaModelOf;

import com.ameat.database.DatabaseManager;
import com.ameat.utils.CamelCaseHelper;
import com.ameat.utils.ConfigurationLoader;

// this class is write as a model constructor, do not use other tables directly
public class Table{
	private String tableClassPrefix = "com.ameat.tables.";

	private String tableName;

	private Model model;

	private Class<? extends Model> claz;

	public Table(String tableName){
		this.tableName = tableName;
		this.checkConection();

		try {
			@SuppressWarnings("unchecked")
			Class<? extends Model> claz = (Class<? extends Model>) Class.forName(tableClassPrefix+tableName);
			this.model = (Model) claz.newInstance();
			this.claz = claz;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkConection() {
		if(!DB.connections().containsKey("default")) {
			DatabaseManager.open();
		}
	}


	/**
	 * Insert a piece of data and return id
	 * @param item
	 * @return
	 */
	public int insertReturnKey(Map<String, Object> record) {
		this.model.fromMap(record);
		this.model.insert();
		int key = Integer.valueOf(this.model.getId().toString());

		return key;
	}

	/**
	 * Insert a piece of data
	 * @param item
	 */
	public boolean insertOne(Map<String, Object> record) {
		this.model.fromMap(record);

		return this.model.insert();
	}

	/**
	 * if the record exists, update it, if the record does not exit, insert one new record
	 * @param item
	 * @return
	 */
	public boolean save(Map<String, Object> record) {
		Object id = record.get("id");
		boolean signal = true;

		if(id != null) {
			Model dbRecord = this.model.findById(id);
			if(dbRecord.exists()) {
				signal = this.updateById(Integer.valueOf(id.toString()), record);
			}else {
				signal = this.insertOne(record);
			}
		} else {
			signal = this.insertOne(record);
		}

		return signal;
	}

	/**
	 * update one record by id
	 * @param id
	 * @param record
	 * @return
	 */
	public boolean updateById(int id, Map<String, Object> record) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		String query = this.buildQuery(conditions);
		String params = this.buildParams(record);
		System.out.println(params);
		System.out.println(query);
		int count = ModelDelegate.update(this.claz, params, query, new Object[0]);

		return count >= 1;
	}
	
//	public boolean update(Map<String, Object> record) {
//		boolean sign = true;
//		
//	}

	/**
	 *  Get table columns' comments  eg:
	 *  		{date=日期, updated_at=更新时间, county=县级区域, degree=温度值, created_at=创建时间, id=}
	 * @param tableName
	 * @return
	 */
	public Map<String, String> getComments() {
		DB db = new DB(Table.class.getSimpleName());
		String environment = Configuration.getEnv();
		String driver = ConfigurationLoader.config("database."+environment+".driver");
		String url = ConfigurationLoader.config("database."+environment+".url").
				substring(0, ConfigurationLoader.config("database."+environment+".url").
						lastIndexOf('/')+1)+"information_schema";
		String username = ConfigurationLoader.config("database."+environment+".username");
		String password = ConfigurationLoader.config("database."+environment+".password");
		db.open(driver, url, username, password);
		Map<String, String> comments = new HashMap<String, String>();
		String query = "select * from COLUMNS where TABLE_NAME = '" +CamelCaseHelper.camelToSnake(this.tableName)+ "s';";
		List<Map> lists = db.all(query);
		db.close();
		for(Map item : lists) {
			comments.put(item.get("COLUMN_NAME").toString(), item.get("COLUMN_COMMENT").toString());
		}
		return comments;
	}

	private String buildQuery(Map<String, Object> conditions) {
		StringBuffer querySb = new StringBuffer();
		conditions.forEach((k, v) -> {
			querySb.append(k+" = '"+v.toString()+"', ");
		});
		String queryStr = querySb.toString();

		return queryStr.substring(0, queryStr.lastIndexOf(','));
	}

	private String buildParams(Map<String, Object> record) {
		StringBuffer paramsSb = new StringBuffer();
		record.forEach((k, v) -> {
			if(k != "id") {
				paramsSb.append(k+" = '"+v.toString()+"', ");
			}
		});
		String paramsStr = paramsSb.toString();

		return paramsStr.substring(0, paramsStr.lastIndexOf(','));
	}


	public static void main(String args[]) {
		HashMap<String, Object> record = new HashMap<String, Object>();
		record.put("date", "2003-02-02");
		record.put("county", "银川");
		record.put("degree", "50");
		new Table("Temperature").updateById(1, record);
		new Table("Temperature").insertReturnKey(record);
		new Table("Temperature").insertOne(record);
		new Table("Temperature").save(record);

	}

}
