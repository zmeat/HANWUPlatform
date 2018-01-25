package com.ameat.component.comunications;

import com.ameat.component.meteorology.Evapotranspiration;

public interface MeteorologyToCountry extends ComunicationInterface{
	public Evapotranspiration getET();
}
