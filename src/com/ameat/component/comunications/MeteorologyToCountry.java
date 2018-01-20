package com.ameat.component.comunications;

import com.ameat.component.meteorology.Evapotraspiration;

public interface MeteorologyToCountry extends ComunicationInterface{
	public Evapotraspiration getEvaporeaspiration(String location);
}
