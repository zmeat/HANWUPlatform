package com.ameat.component.country;

public class Farmer {
	private int simId;
	private int simYear;
	private int farmerId;
	private String location;
	
	private double mu;        // 旱地占总耕种面积的比例
	private double learn;     // 学习因子
	private double radius;    // 信息半径
	private double sense;     // 敏感因子
	// 土地及每亩地的耕种成本
	private double cropArea;
	
	private double totalIrrigation;
	private double irrigation;
	
	// 每年的累计生产
	private double riceYield;       
	private double maizeYield;
	private double income;
	
	public Farmer(int simId, int simYear, int farmerId, String location, double mu, double learn, double radius, double senes, double cropArea) {
		this.simId = simId;
		this.simYear = simYear;
		this.farmerId = farmerId;
		this.location = location;
		
		this.mu = mu;
		this.learn = learn;
		this.radius = radius;
		this.sense = senes;
		
		this.cropArea = cropArea;
		this.irrigation = 0.0;
		this.totalIrrigation = 0.0;
		
		this.income = 0.0;
		this.riceYield = 0.0;
		this.maizeYield = 0.0;
	}
	
	
	
	
	
	/**
	 * 获取 每一亩的 玉米补助
	 * @return double : 元/亩
	 */
	private double getMaizeSubsidy() {
		double subsidy = 0.0;
		if(simYear==2007) subsidy = 450.0;   // 6750 yuan/hm2 
		if(simYear > 2007) subsidy = 550.0;  // 8250 yuan/hm2
		return subsidy;
	}
	/**
	 * 获取玉米价格 
	 * @return double : 元/kg
	 */
	private double getMaizePrice() {
		return 2.172;
	}
	/**
	 * 获取水稻价格
	 * @return double : 元/kg
	 */
	private double getRicePrice() {
		return 2.957;
	}
	/**
	 * 水稻每亩的成本：种子、化肥、农药
	 * @return double : 元/亩
	 */
	private double getRiceCost() {
		return 10643.60/15;
	}
	/**
	 * 玉米每亩的成本：种子、化肥、农药
	 * @return double : 元/亩
	 */
	private double getMaizeCost() {
		return 5265.25/15;
	}
}
