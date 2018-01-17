package com.ameat.models;

import java.util.HashMap;

import org.javalite.activejdbc.Model;

public class Rain extends Model{
	public static HashMap<String, String> tableMap = new HashMap<String, String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("station", "检测站点");
		put("date", "日期");
		put("rallfall", "雨量");
	}};
}
