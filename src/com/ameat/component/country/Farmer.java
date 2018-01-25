package com.ameat.component.country;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.ameat.component.meteorology.Evapotranspiration;
import com.ameat.component.meteorology.Location;
import com.ameat.simulation.TimeController;
import com.ameat.utils.Generator;
/**
 * 每年做两件事儿：归零+调整策略
 * @author yanmeng
 *
 */
public class Farmer {
	private TimeController tc;
	private String rice = "rice";
	private String maize = "maize";
	
	// 一次定型
	private int simId;
	private int farmerId; //每个村按照0开始编码
	private String locationEn;
	private double cropArea;             // 作物面积
	private int farmerNum;
	
	private double learn;         // 学习因子
	private double radius;        // 信息半径
	private double sense;         // 敏感因子
	
	// 每天不变，年度更新
	private double mu;            // 旱地占总耕种面积的比例
	private double muPre;
	private double riceArea;      
	private double maizeArea;     
	// 每天没有，年底结算
	private double cropIncome;    // 作物中收入，包含补贴
	private double riceIncome;    
	private double maizeIncome; 
	private double consumeWater;
	
	// 每天累加，年度清零
	private double precipitation;        // 年度累计降雨量
	private double riceYield;              // 水稻累计亩产量
	private double riceIrrigation;         // 水稻累计灌溉量
	private double maizeYield;             // 玉米累计亩产量
	private double maizeIrrigation;        // 玉米累计灌溉量
	
	// 每天更新，年度清零
	private double precip;           // 该地区每天的降雨情况
	private double riceIrri;         // 水稻灌溉
	private double riceDr;           // 当天根系层水量消耗
	private double maizeIrri;
	private double maizeDr; 
	
	public Farmer(TimeController tc, int simId, int farmerId, String location, int farmerNum, double cropArea, double mu, double learn, double radius, double senes) {
		// 初始化表示参数
		this.tc =  tc;
		this.simId = simId;
		this.farmerId = farmerId;
		this.locationEn = location;
		
		this.mu = mu;
		this.muPre = Generator.Normal(mu, 0.2);
		while(this.muPre < 0.0 || this.muPre > 1.0) this.muPre = Generator.Normal(mu, 0.2);
		this.learn = learn;
		this.radius = radius;
		this.sense = senes;
		this.cropArea = cropArea;
		this.farmerNum = farmerNum;
		// 初始化每年的调整参数
		this.maizeArea = mu*cropArea;
		this.cropArea = cropArea - this.maizeArea;
		// 清零
		yearValueToZero();
	}
	
	
	/**
	 * 年尾总结：计算扣除投入以及结合当前情况下的 总收入
	 */
	protected void summaryYearEnd() {
		this.maizeIncome = (this.maizeYield * this.maizeArea * this.getMaizePrice()) - (this.maizeArea*this.getMaizeCost())
				+ this.getMaizeSubsidy();
		this.riceIncome = (this.riceYield * this.riceArea * this.getRicePrice()) - (this.riceArea*this.getRiceCost());
		this.cropIncome = this.maizeIncome + this.riceIncome;
		this.consumeWater = (this.maizeIrrigation * this.maizeArea) + (this.riceIrrigation * this.riceArea);
	}
	
	/**
	 * 农民每年会根据年尾总结情况进行策略调整(包含下一年所有变量的初始化)
	 * @param farmers
	 */
	protected void updateStrategy(Map<String, List<Farmer>> totalFarmers) {
		double incomePerArea = this.cropIncome / this.cropArea; 
		List<Farmer> farmers = totalFarmers.get(this.locationEn);
		int r = (int) this.radius;
		// 对高出的差价敏感
		int count1 = 0;
		double muAccumu1 = 0.0;
		// 对达到对数量敏感
		int count2 = 0;
		double muAccumu2 = 0.0;
		
		int priority = 0;
		
		// 遍历邻居，进行学习
		for(int i=0; i<r; i++) {
			int index = (this.farmerId + (i+1)) % farmers.size();
			double incomePerAreaI =  farmers.get(index).getCropIncome() / farmers.get(index).getCropArea();
			double muI = farmers.get(index).getMu();
			if(incomePerAreaI > incomePerArea*2*this.sense && incomePerAreaI > incomePerArea) {
				muAccumu1 += muI;
				count1++;
				priority=1;
			}
			
			if(incomePerAreaI > incomePerArea) {
				muAccumu2 += muI;
				count2++;
			}
		}
		
		double muTemp = this.mu;
		if(priority == 1) {
			this.mu = this.learn*((muAccumu1/count1) - this.mu) + this.muPre;
		}else {
			if( (count2 / r) > (0.5*this.sense) ) this.mu = this.learn*((muAccumu2/count2) - this.mu) + this.muPre;
			else this.mu = this.learn*(this.mu - this.muPre) + this.muPre;
		}
		this.muPre = muTemp;
		
		this.maizeArea = this.cropArea * this.mu;	
		this.riceArea = this.cropArea - this.maizeArea;
		yearValueToZero();
	}
	
	/**
	 * 农民根据气象数据更新每天参数
	 * @param ET
	 */
	protected void daysWithoutWaterPress(Evapotranspiration ET, Map<String, Crop> cropInfos) {
		double ETo = ET.getETo(this.locationEn);
		Location location = ET.getLocation(this.locationEn);
		// 更新降雨
		this.precip = location.getPrecip(tc.getCurrentTime());
		// 更新水稻灌溉 、 消耗
		if(this.riceDr < cropInfos.get(rice).getRAW(location, ETo)) {
			this.riceIrri = 0.0;
		} else {
			this.riceIrri = this.riceDr - this.precip;
			if(this.riceIrri < 0.0) this.riceIrri = 0.0;
		}
		this.riceDr = this.riceDr - this.precip - this.riceIrri + ETo*cropInfos.get(rice).getKc();
		if(this.riceDr < 0.0) this.riceDr = 0.0;
		// 更新玉米灌溉 、 消耗
		if(this.maizeDr < cropInfos.get(maize).getRAW(location, ETo)) {
			this.maizeIrri = 0.0;
		}else {
			this.maizeIrri = this.maizeDr - this.precip;
			if(this.maizeIrri < 0.0) this.maizeIrri = 0.0;
		}
		this.maizeDr = this.maizeDr - this.precip - this.maizeIrri + ETo*cropInfos.get(maize).getKc();
		if(this.maizeDr < 0.0) this.maizeDr = 0.0;
		
		// 更新累计
		
		
	}
	
	public double getMu() {
		return this.mu;
	}
	protected double getCropArea() {
		return this.cropArea;
	}
	protected double getCropIncome() {
		return this.cropIncome;
	}
	
	private void yearValueToZero() {
		this.cropIncome = 0.0;
		this.riceIncome = 0.0;
		this.maizeIncome = 0.0;
		this.consumeWater = 0.0;
		
		this.precipitation = 0.0;
		this.riceYield = 0.0;
		this.riceIrrigation = 0.0;
		this.maizeYield = 0.0;
		this.maizeIrrigation = 0.0;
		
		this.precip = 0.0;
		this.riceIrri = 0.0;
		this.riceDr = 0.0;
		this.maizeIrri = 0.0;
		this.maizeDr = 0.0;
	}
	
	/**
	 * 获取 每一亩的 玉米补助
	 * @return double : 元/亩
	 */
	private double getMaizeSubsidy() {
		double subsidy = 0.0;
		if(this.tc.getCurrentTime().getYear()==2007) subsidy = 450.0;   // 6750 yuan/hm2 
		if(this.tc.getCurrentTime().getYear() > 2007) subsidy = 550.0;  // 8250 yuan/hm2
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
