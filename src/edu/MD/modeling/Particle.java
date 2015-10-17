package edu.MD.modeling;

import edu.MD.utility.MDVector;

public abstract class Particle {
	protected MDVector position; 
	
	abstract String getType();
	abstract String getName();
	public MDVector getPosition() {
		return position;
	}
	
	
	
	


}
