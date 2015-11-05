package edu.MD.modelingBD;

import edu.MD.number.MDNumber;
import edu.MD.utilityBD.MDVector;

public class PBCPairwiseDistanceFinder implements IDistanceFinder {
	public static final PBCPairwiseDistanceFinder INSTANCE = new PBCPairwiseDistanceFinder();

	private PBCPairwiseDistanceFinder() {
	};

	/**
	 * This version applies the PBC and uses the minimum image convention
	 * 
	 * @see edu.MD.modelingBD.IDistanceFinder#findDistance
	 */
	@Override
	public MDVector findDistance(MDVector p1Position, MDVector p2Position, MDVector systemBoundary) {

		MDVector distanceVector = p1Position.minus(p2Position);
		// corner case, when the distance is right at the middle of the System
		// Boundary
		MDNumber[] dist = distanceVector.getCartesianComponent();
		MDNumber[] bound = systemBoundary.getCartesianComponent();
		for (int i = 0; i < distanceVector.getDimension(); i++) {
			if (!dist[i].approximateEqual(bound[i].times(0.5)))
				dist[i] = dist[i].minus(bound[i].times(dist[i].divide(bound[i]).add(0.5).floor()));
		}
		return distanceVector;
	}

}
