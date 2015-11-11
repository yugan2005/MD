package edu.MD.modelingBD;

import java.util.HashMap;
import java.util.Map;

import edu.MD.number.*;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.MDPotentialConstants;

public class LJForceCalculator {

	private static Map<String, LJForceCalculator> instances = new HashMap<>();

	private MDNumber sigma6, sigma12, epsilon, cutoffPotential;

	private LJForceCalculator(MDNumber sigma6, MDNumber sigma12, MDNumber epsilon,
			MDNumber cutoffPotential) {
		this.sigma6 = sigma6;
		this.sigma12 = sigma12;
		this.epsilon = epsilon;
		this.cutoffPotential = cutoffPotential;
	}

	/**
	 * Factory method, get a configured LJForceCalculator
	 * 
	 * @param "type":
	 *            String: in form of 'ARGON_ARGON_5.0'. The last 5.0 means the
	 *            cutoff radius is 5 times its averaged sigma potential
	 *            parameter.
	 * @return LJForceCalculator instance (from the HashMap)
	 */
	public static LJForceCalculator getInstance(String type) {
		if (instances.get(type) == null) {
			String p1Name = type.split("_")[0];
			String p2Name = type.split("_")[1];
			double cutoff = Double.parseDouble(type.split("_")[2]);
			MDNumber p1Sigma, p2Sigma, p1Epsilon, p2Epsilon;
			try {
				p1Sigma = MDPotentialConstants.getSigma(p1Name);
				p2Sigma = MDPotentialConstants.getSigma(p2Name);
				p1Epsilon = MDPotentialConstants.getEpsilon(p1Name);
				p2Epsilon = MDPotentialConstants.getEpsilon(p2Name);
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("The type name is not correct, should be like 'ARGON_ARGON_5.0");
			}

			MDNumber sigma = p1Sigma.add(p2Sigma).divide(2);
			MDNumber epsilon = p1Epsilon.add(p2Epsilon).divide(2);
			MDNumber sigma6 = sigma.pow(6);
			MDNumber sigma12 = sigma.pow(12);
			MDNumber cutoffRadius = sigma.times(cutoff);
			MDNumber cutoffPotential = epsilon.times(4).times(
					(sigma12.times(-12).divide(cutoffRadius.pow(13)).add(sigma6.times(6).divide(cutoffRadius.pow(7)))));
			instances.put(type, new LJForceCalculator(sigma6, sigma12, epsilon, cutoffPotential));
		}
		return instances.get(type);
	}
	

	/**
	 * The distance vector should be the output of the distanceFinder.</n>
	 * 
	 * If in PBC system, the minimum image convention should already be
	 * applied</n>
	 * 
	 * @param p1_p2
	 *            Distance vector from p2 to p1, i.e. vector p1_p2. </n>
	 * @return Force vector of particle p1, i.e. the force acting on p1 and is
	 *         exerted by p2
	 */
	public MDVector calculate(MDVector p1_p2) {
		MDNumber norm = p1_p2.norm();
		MDNumber coefficient = epsilon.times(48).times(sigma12).divide(norm.pow(14))
				.minus(epsilon.times(24).times(sigma6).divide(norm.pow(8))).add(cutoffPotential.divide(norm));
		return p1_p2.times(coefficient);
	}

}
