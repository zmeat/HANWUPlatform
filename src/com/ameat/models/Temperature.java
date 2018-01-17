package com.ameat.models;

import java.util.HashMap;

import org.javalite.activejdbc.Model;

public class Temperature extends Model {
	public static HashMap<String, String> tableMap = new HashMap<String, String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("county", "县级区域");
		put("date", "日期");
		put("degree", "温度");
	}};
}
