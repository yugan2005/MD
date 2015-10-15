package edu.MD.modeling;

import edu.MD.utility.Vector;

public interface IForceCalculator {
	
	public Vector calculate(IParticle p1, IParticle p2);

}
