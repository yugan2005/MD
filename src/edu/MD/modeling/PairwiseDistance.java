package edu.MD.modeling;

public class PairwiseDistance {
	private static PairwiseDistance distanceCalculator;

	private PairwiseDistance() {
	}

	public static PairwiseDistance getInstance() {
		return distanceCalculator;
	}

	/**
	 * @param particle1
	 * @param particle2
	 * @return distance (not direction) in periodic boundary condition
	 * 
	 */
	public static double calculatePBCDistance(IParticle particle1, IParticle particle2) {
		
		// TODO This getSystem method is not a good design decision, it add dependency to the higher level class 
		
		if (particle1.getSystem()!=particle2.getSystem()) throw new IllegalArgumentException(
				"The two particles must come from the same system in order to calculate the distance");
		ISystem particleSystem = particle1.getSystem();
		double[] position1 = particle1.getCMPosition();
		double[] position2 = particle2.getCMPosition();
		double[] systemDimension = particleSystem.getBoundary();

		double distance = 0;
		for (int i = 0; i < position1.length; i++) {

		}
		return distance;

	}
}
