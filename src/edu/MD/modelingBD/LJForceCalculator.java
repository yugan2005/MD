package edu.MD.modelingBD;

import java.math.BigDecimal;

import edu.MD.number.JScienceReal;
import edu.MD.number.MDNumber;
import edu.MD.utilityBD.MDVector;

public class LJForceCalculator {

	private final MDNumber sigma6, sigma12, epsilon, cutoffPotential;

	private LJForceCalculator(final MDNumber sigma6, final MDNumber sigma12, final MDNumber epsilon,
			final MDNumber cutoffPotential) {
		this.sigma6 = sigma6;
		this.sigma12 = sigma12;
		this.epsilon = epsilon;
		this.cutoffPotential = cutoffPotential;
	}

	/**
	 * @param p1_p2
	 *            Distance vector from p2 to p1, i.e. vector p1-p2. If in PBC
	 *            system, the minimum image convention should already be applied
	 *            to the distance vector
	 * @return Force vector of particle p1. This is the force acting on p1 and is
	 *         exerted by p2
	 */
	public MDVector calculate(MDVector p1_p2) {
		MDNumber norm = p1_p2.norm();
		MDNumber coefficient = (JScienceReal.getJScienceReal(48)).times(epsilon).times(sigma12).divide(norm.pow(14)).minus((new BigDecimal(24).multiply(epsilon).multiply(sigma6).divide(norm.pow(8)))).add(cutoffPotential.divide(norm));
		return p1_p2.multiply(coefficient);
	}

	/**
	 * Factory method, get a configured LJForceCalculator
	 * 
	 * @param sigma6 LJ length parameter to the 6th power
	 * @param sigma12 LJ length parameter to the 12th power
	 * @param epsilon LJ energy parameter
	 * @param cutoffPotential calculated using cutoff radius
	 * @return LJForceCalculator
	 */
	public static LJForceCalculator getInstance(double sigma6, double sigma12, double epsilon, double cutoffPotential) {
		return new LJForceCalculator(sigma6, sigma12, epsilon, cutoffPotential);
	}

	// TODO Move to other class, this is the way to obtain parameters for
	// LJForceCalculator
	// public MDVector calculate(Particle p1, Particle p2, ISystem system) {
	// // potential parameters
	// String keyPrefixString = p1.getType() + "_" + p1.getName() + "_" +
	// p2.getType() + "_" + p2.getName() + "_";
	// String keyString = keyPrefixString + "sigma_12";
	// double sigma12 = system.getPotentialParameters(keyString);
	// keyString = keyPrefixString + "sigma_6";
	// double sigma6 = system.getPotentialParameters(keyString);
	// keyString = keyPrefixString + "epsilon";
	// double epsilon = system.getPotentialParameters(keyString);
	// // cutoffPotential formula is on note Page1
	// keyString = keyPrefixString + "cutoffPotential";
	// double cutoffPotential = system.getPotentialParameters(keyString);
	// // distance vector
	// IDistanceFinder distanceFinder = system.getDistanceFinder();
	// MDVector distance = distanceFinder.findDistance(p1, p2, system);
	// }

}
