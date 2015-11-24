package edu.MD.modeling;

import java.util.HashMap;
import java.util.Map;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utility.MDConstants;
import edu.MD.utility.MDVector;
import edu.MD.utility.PBCCalculator;

public class VerletPositionUpdater {
	private static NumberFactory numberFactory = NumberFactory.getInstance();
	private static Map<String, VerletPositionUpdater> instances = new HashMap<>();

	private MDNumber mass, dt;

	private VerletPositionUpdater(MDNumber mass, MDNumber dt) {
		this.mass = mass;
		this.dt = dt;
	}

	/**
	 * Factory method, get a configured VerletPositionUpdater
	 * 
	 * @param type
	 *            In form of 'ARGON_1e-15'. The last 1e-15 means the delta time
	 *            is 1e-15 second.
	 * @return VerletPositionUpdater instance (from the HashMap)
	 */
	public static VerletPositionUpdater getInstance(String type) {
		if (instances.get(type) == null) {
			String name = type.split("_")[0];
			MDNumber dt = numberFactory.valueOf(Double.parseDouble(type.split("_")[1]));
			MDNumber mass;
			try {
				mass = MDConstants.getMass(name);
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("The particle name is not correct, should be like 'ARGON_1e-15");
			}
			instances.put(type, new VerletPositionUpdater(mass, dt));
		}
		return instances.get(type);
	}

	/**
	 * Refer to my PhD Thesis paper flow chart Fig3.11 P89 of 139. P.B.C is
	 * applied to the new Position vector. Also note that the full timestep dt
	 * is used
	 * 
	 * @param oldPosition
	 * @param oldVelocity
	 * @param forceVector
	 * @return newPosition
	 */
	public MDVector calculate(MDVector oldPosition, MDVector oldVelocity, MDVector forceVector) {
		PBCCalculator pbc = PBCCalculator.getInstance();
		MDVector newPosition = oldPosition.add(oldVelocity.times(dt))
				.add(forceVector.times(dt.pow(2)).divide(mass.times(2)));
		pbc.applyPBC(newPosition);
		return newPosition;
	}

}
