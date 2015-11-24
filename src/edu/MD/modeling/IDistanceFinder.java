package edu.MD.modeling;

import edu.MD.number.MDVector;

public interface IDistanceFinder {

	/**
	 * @param p1Position
	 * @param p2Position
	 * @return Vector pointing from particle p2 to p1. So it is the (p1-p2) vector.
	 */
	MDVector getDistance(MDVector p1Position, MDVector p2Position);

}
