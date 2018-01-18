package com.ameat.component;

import com.ameat.simulation.TimeController;

public interface AgentInterface {
	
	
	public void init();
	
	public void compute(TimeController timeController);
	
	public void finished();
}
