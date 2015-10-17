package edu.MD.modeling;

import edu.MD.utility.MDVector;

public interface IDistanceFinder {

	/**
	 * @param p1
	 * @param p2
	 * @param system
	 * @return Vector pointing from particle p1 to p2. For
	 *         PBCPairwiseDistanceFinder implement, the PBC and minimum image
	 *         convention is used.
	 */
	public MDVector findDistance(Particle p1, Particle p2, ISystem system);

}
