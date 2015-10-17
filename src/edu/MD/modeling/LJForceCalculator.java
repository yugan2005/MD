package edu.MD.modeling;

import edu.MD.utility.MDVector;

public class LJForceCalculator implements IPairWiseForceCalculator {

	public static final IPairWiseForceCalculator INSTANCE = new LJForceCalculator();

	private LJForceCalculator() {
	}
	
	public MDVector calculate(Particle p1, Particle p2, ISystem system) {
		// potential parameters
		String keyPrefixString = p1.getType() + "_" + p1.getName() + "_" + p2.getType() + "_" + p2.getName() + "_";
		String keyString = keyPrefixString + "sigma_12";
		double sigma12 = system.getPotentialParameters(keyString);
		keyString = keyPrefixString + "sigma_6";
		double sigma6 = system.getPotentialParameters(keyString);
		keyString = keyPrefixString + "epsilon";
		double epsilon = system.getPotentialParameters(keyString);
		// cutoffPotential formula is on note Page1
		keyString = keyPrefixString + "cutoffPotential";
		double cutoffPotential = system.getPotentialParameters(keyString);
		// distance vector
		IDistanceFinder distanceFinder = system.getDistanceFinder();
		MDVector distance = distanceFinder.findDistance(p1, p2, system);
		// force calculation
		double norm = distance.norm();
		double coefficient = 48 * epsilon * sigma12 / Math.pow(norm, 14) - 24 * epsilon * sigma6 / Math.pow(norm, 8)
				+ cutoffPotential / norm;

		return distance.multiply(coefficient);
	}

}
