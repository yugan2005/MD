package edu.MD.modeling;

import edu.MD.number.MDVector;
import edu.MD.utility.PBCCalculator;

public class PBCPairwiseDistanceFinder implements IDistanceFinder {
	public static final PBCPairwiseDistanceFinder INSTANCE = new PBCPairwiseDistanceFinder();
	private static PBCCalculator pbc;

	private PBCPairwiseDistanceFinder() {
		try {
			pbc = PBCCalculator.getInstance();
		} catch (UnsupportedOperationException ex) {
			throw new UnsupportedOperationException(
					"Need set the global setPBCCalculator(MDVector systemBoundary) first");
		}

	}
	
	public static PBCPairwiseDistanceFinder getInstance(){
		return INSTANCE;
	}

	/**
	 * This version applies the PBC and uses the minimum image convention
	 * 
	 * @see edu.MD.modeling.IDistanceFinder#findDistance
	 */
	@Override
	public MDVector getDistance(MDVector p1Position, MDVector p2Position) {
		MDVector distanceVector = p1Position.minus(p2Position);
		pbc.applyMinimumImageConvention(distanceVector);
		return distanceVector;
	}

}
