package edu.MD.modelingBD;

import edu.MD.utilityBD.MDVector;

public interface IDistanceFinder {

	/**
	 * @param p1Position
	 * @param p2Position
	 * @param systemBoundary The origin of the System Boundary is (0,0,0) 
	 * @return Vector pointing from particle p2 to p1. So it is the (p1-p2) vector.
	 */
	MDVector calculate(MDVector p1Position, MDVector p2Position, MDVector systemBoundary);

}
