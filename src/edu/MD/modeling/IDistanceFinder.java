package edu.MD.modeling;

import edu.MD.utility.MDVector;

public interface IDistanceFinder {

	/**
	 * @param p1
	 * @param p2
	 * @param system
	 * @return Vector pointing from particle p2 to p1. So it is the (p1-p2) vector. 
	 */
	public MDVector findDistance(Particle p1, Particle p2, ISystem system);

}
