package com.ameat.component.meteorology;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ameat.component.country.Crop;
import com.ameat.simulation.TimeController;

public class Evapotraspiration {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private TimeController tc;
	private Double previousETo;
	private Double currentETo;
	private Map<String, Location> locationInfos;
	private Map<String, Crop> cropInfos;
	
	public Evapotraspiration(TimeController tc) {
		this.tc = tc;
		this.locationInfos = new HashMap<String, Location>();
		this.locationInfos.put("LuanPing", new LuanPing());
		this.locationInfos.put("ChiCheng", new ChiCheng());
		this.locationInfos.put("FengNing", new FengNing());
		this.cropInfos = new HashMap<String, Crop>();
		this.previousETo = this.currentETo = 0.0;
	}
	
	/**
	 * 计算参考作物腾发量：缺少气象数据是ETo的计算，根据传入的位置从数据库中取出位置信息，从location对象中获取该位置的其他参数
	 * @param locationStr
	 * @return
	 */
	public Double getETo(String locationStr) {
		// 上次ETo和当前ETo不相等，则说明ETo已更新，直接返回当前ETo
		if( Math.abs(this.previousETo-this.currentETo) > 0.000001 ) return this.currentETo;
		
		Location location = locationInfos.get(locationStr);
		// 上次ETo和当前ETo相等，更新当前ETo
		// 从数据库中获取最小温度和最大温度
		double Tmin = location.getTmin(this.tc.getCurrentTime());
		double Tmax = location.getTmax(this.tc.getCurrentTime());
		double Tmean = (Tmin + Tmax) / 2;
		double delta = getDeltaByT(Tmean); 
		// 从海拔高度获得gamma
		double gamma = location.getGamma();
		
		// 获取湿度数据估计
		double Tdew = Tmin;
		double ea = getE(Tdew);
		double eTmax = getE(Tmax);
		double eTmin = getE(Tmin);
		double es = (eTmax + eTmin) / 2.0;
		double RHmax = (100*ea) / eTmin; // %
		double RHmin = (100*ea) / eTmax; // %
		double RHmean = (RHmin + RHmax) / 2.0;
		
		// 获取辐射数据估计
		double Ra = location.getRa(this.tc.getCurrentTime());
		double Rs = location.getKrs() * Math.sqrt(Tmax-Tmin) * Ra;
//		double Rs = 0.19 * Math.sqrt(Tmax-Tmin) * Ra;
		double Rso = (0.75+(2*200)/100000)*Ra;
		double Rns = (1-0.23)*Rs;
		
		double Tmaxk = Tmax + 273.16;
		double Tmink = Tmin + 273.16;
		double sigma = 4.903*Math.pow(10, -9);
		double Rnl = sigma * ((Math.pow(Tmaxk, 4)+Math.pow(Tmink, 4))/2)*
				(0.34-(0.14*Math.sqrt(ea))) * ((1.35*Rs/Rso)-0.35);
		double Rn = Rns - Rnl;
		double G = 0; //以1天或10天为单位
		
		double top = (0.408*delta*(Rn-G))+(900.0/(Tmean+273)*gamma*location.getU2()*(es-ea));
		double down = (delta+(gamma*(0.34*location.getU2() + 1)));
		double ETo = top/down;
		this.currentETo = ETo;
		return ETo;
	}
	
	public void updateETo() {
		this.previousETo = this.currentETo;
	}

	/**
	 * @param T : ℃
	 * @return e : Kpa：saturation vapour pressure at the air temperature T [kPa]
	 */
	private double getE(double t) {
		double c = (17.27*t) / (t+237.3);
		double e = 0.6108*Math.exp(c);
		return e;
	}
	/**
	 * @param Tmean
	 * @return delta : KPa / C
	 */
	private double getDeltaByT(double Tmean) {
		double p = 4098 * ( 0.6108*Math.exp((17.27*Tmean)/(Tmean+237.3)) );
		double t = (Tmean+237.3)*(Tmean+237.3);
		return p/t;
	}
}
