package com.ameat.component.meteorology;

import org.joda.time.DateTime;

import com.ameat.simulation.TimeController;

public abstract class Location {
	protected String chineseName;
	protected String englisName;
	// 2m/s
	protected double u2 = 2;
	// 调整系数:靠海会受气影响Krs=0.19，内陆为Krs=0.16
	protected double Krs = 0.16;
	// 土质类型：沙壤土确定田间持水量和凋萎点水量 m3 / m-3  (FAO-56) 
	protected double FC = 0.15;
	protected double WP = 0.06;
	
	// m
	protected double H;
	// latitude
	protected double angle;
	protected double cent;
	protected boolean isNorth;
	
	// Kpa/℃
	protected double gamma;
	// Ra
	protected double Ra;
	
	protected double Tmin;
	protected double Tmax;
	
	public abstract double getTmin(DateTime time);
	public abstract double getTmax(DateTime time);
	public abstract double getPrecip(DateTime time);
	
	
	/**
	 * 获取该地区土壤类型的田间持水量 m3 / m-3
	 * @return 
	 */
	public double getFC() {
		return this.FC;
	}
	/**
	 * 获取该地区土壤类型的枯萎点水量 m3 / m-3
	 * @return
	 */
	public double getWP() {
		return this.WP;
	}
	
	public double getH() {
		return this.H;
	}
	public double getU2() {
		return this.u2;
	}
	public double getGamma() {
		double p = 101.3 * Math.pow((293 - (0.0065*1800))/293, 5.26);
		this.gamma = 0.665*0.001*p;
		return this.gamma;
	}
	public double getKrs() {
		return this.Krs;
	}

	/**
	 * 根据时间获取点前位置天顶辐射
	 * @param time ： DateTime
	 * @return Ra : double MJ/m2/day
	 */
	public double getRa(DateTime time) {
		if(this.isNorth) return getNRa(this.angle, this.cent, time);
		else return getSRa(this.angle, this.cent, time);
	}
	
	/**
	 * 根据时间和地点(纬度)获取北半球天顶辐射
	 * @param angle
	 * @param cent
	 * @param time 
	 * @return Ra : MJ/m2/day
	 */
	public static double getNRa(double angle, double cent, DateTime time) {
		double decimal = angle + (cent/60);
		double radian = decimal * (Math.PI/180);
		
		double Gsc = 0.0820; // MJ/m2/min
		double dr = 1 + (0.033*Math.cos(((2*Math.PI)/365.0)*time.getDayOfYear()));
		double delt = 0.409*Math.sin(((2*Math.PI)/365.0)*time.getDayOfYear() - 1.39);
		double ws = Math.acos(-(Math.tan(radian)*Math.tan(delt)));
		
		double Ra = (24*60/Math.PI) * Gsc*dr*( (ws*Math.sin(radian)*Math.sin(delt)) + 
				(Math.cos(radian)*Math.cos(delt)*Math.sin(ws)) );
				
		return Ra;
	}
	public static double getSRa(double angle, double cent, DateTime time) {
		return getNRa(-angle, -cent, time);
	}
	
	
//	public static void main(String[] args) {
//		Evapotraspiration et = new Evapotraspiration(new TimeController());
//		System.out.println("ETo="+et.getETo("LuanPing"));
//	}
}



class LuanPing extends Location{
	public LuanPing() {
		this.H = 700.0;
		this.angle = 40.0;
		this.cent = 50.0;
		this.chineseName = "滦平";
		this.englisName = "LuanPing";
//		this.H = 2;
//		this.angle=13;
//		this.cent=44;
		this.isNorth=true;
	}

	@Override
	public double getTmin(DateTime time) {
		// TODO Auto-generated method stub
		return 25.6;
	}

	@Override
	public double getTmax(DateTime time) {
		// TODO Auto-generated method stub
		return 34.8;
	}

	@Override
	public double getPrecip(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}
}

class ChiCheng extends Location{
	public ChiCheng() {
		this.chineseName = "赤城";
		this.englisName = "ChiCheng";
		this.H = 945.0;
		this.angle = 40.0;
		this.cent = 55.0;
		this.isNorth = true;
	}

	@Override
	public double getTmin(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTmax(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPrecip(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

class FengNing extends Location{
	public FengNing() {
		this.chineseName = "丰宁";
		this.englisName = "FengNing";
		this.H = 540.0;
		this.angle = 41.0;
		this.cent = 12.0;
		this.isNorth = true;
	}

	@Override
	public double getTmin(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTmax(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPrecip(DateTime time) {
		// TODO Auto-generated method stub
		return 0;
	}
}


