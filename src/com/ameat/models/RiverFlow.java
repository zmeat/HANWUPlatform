package com.ameat.models;

import java.util.HashMap;

import org.javalite.activejdbc.Model;

public class RiverFlow extends Model {
	public static HashMap<String, String> tableMap = new HashMap<String, String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("river_name", "河流名称");
		put("date", "日期");
		put("flow", "流量");
	}};
}
