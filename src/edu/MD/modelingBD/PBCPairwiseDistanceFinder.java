package edu.MD.modelingBD;

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
		// double[] systemBoundary = system.getSystemBoundary()
		// .getCartesianComponent();
		// double[] p1Position = p1.getPosition().getCartesianComponent();
		// double[] p2Position = p2.getPosition().getCartesianComponent();
		//
		// double[] distanceVector = new double[p1Position.length];
		//
		// for (int i = 0; i < p1Position.length; i++) {
		// // This minimum image convention algorithm has been verified at my
		// // note page 2
		// distanceVector[i] = p1Position[i]
		// - p2Position[i]
		// - systemBoundary[i]
		// * Math.floor(0.5 + (p1Position[i] - p2Position[i])
		// / systemBoundary[i]);
		// }
		//
		// return new Vector3DCartesian(distanceVector);

		MDVector distanceVector = p1Position.minus(p2Position).minus(systemBoundary
				.elementwiseTimes(p1Position.minus(p2Position).elementwiseDivide(systemBoundary).add(0.5).floor()));
		return distanceVector;
	}

}
