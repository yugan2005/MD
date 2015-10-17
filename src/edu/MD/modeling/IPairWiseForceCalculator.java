package edu.MD.modeling;

import edu.MD.utility.MDVector;

public interface IPairWiseForceCalculator {

	MDVector calculate(Particle p2, Particle p1, ISystem system);
	

}
