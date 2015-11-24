package edu.MD.modeling;

import edu.MD.utility.MDVector;
import edu.MD.utility.Vector3DCartesian;

public class PBCPairwiseDistanceFinder implements IDistanceFinder {
	public static final PBCPairwiseDistanceFinder INSTANCE = new PBCPairwiseDistanceFinder();

	private PBCPairwiseDistanceFinder() {
	};

	/**
	 * This version applies the PBC and uses the minimum image convention
	 * 
	 * @see edu.MD.modeling.IDistanceFinder#findDistance
	 */
	@Override
	public MDVector findDistance(Particle p1, Particle p2, ISystem system) {
		double[] systemBoundary = system.getSystemBoundary()
				.getCartesianComponent();
		double[] p1Position = p1.getPosition().getCartesianComponent();
		double[] p2Position = p2.getPosition().getCartesianComponent();

		double[] distanceVector = new double[p1Position.length];
		for (int i = 0; i < p1Position.length; i++) {
			// This minimum image convention algorithm has been verified at my
			// note page 2
			distanceVector[i] = p1Position[i]
					- p2Position[i]
					- systemBoundary[i]
					* Math.floor(0.5 + (p1Position[i] - p2Position[i])
							/ systemBoundary[i]);
		}

		return new Vector3DCartesian(distanceVector);
	}

}
