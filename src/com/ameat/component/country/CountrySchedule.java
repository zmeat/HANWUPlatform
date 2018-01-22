package com.ameat.component.country;

import java.util.Map;

import com.ameat.component.comunications.ComunicationInterface;
import com.ameat.component.comunications.MeteorologyToCountry;
import com.ameat.component.meteorology.Evapotraspiration;
import com.ameat.simulation.TimeController;

public class CountrySchedule{
	
	protected void loadToCompute(TimeController tc) {
	}
	protected void testGetET(Map<String, ComunicationInterface> comunications) {
		MeteorologyToCountry mTc = (MeteorologyToCountry)comunications.get("Meteorology");
		System.out.println("=-=-=-=-=-test+comunications==="+mTc.getETo("LuanPing"));
	}
}
