package edu.MD.modelingBD;

import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.PBCCalculator;

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
	public MDVector calculate(MDVector p1Position, MDVector p2Position) {
		MDVector distanceVector = p1Position.minus(p2Position);
		PBCCalculator pbc = PBCCalculator.getInstance();
		pbc.applyMinimumImageConvention(distanceVector);
		return distanceVector;
	}

}
