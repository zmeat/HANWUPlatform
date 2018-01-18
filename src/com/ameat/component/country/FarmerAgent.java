package com.ameat.component.country;

import com.ameat.component.AgentInterface;
import com.ameat.simulation.TimeController;

public class FarmerAgent implements AgentInterface{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("init !");
	}

	@Override
	public void compute(	TimeController timeController) {
		System.out.println("i cycle");
	}
	
	@Override
	public void finished() {
		// TODO Auto-generated method stub
		System.out.println("simulation finished");
	}

}
