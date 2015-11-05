package edu.MD.modelingBD;

import java.util.HashMap;
import java.util.Map;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Constants;

public class VelocityUpdater {
	private static NumberFactory numberFactory = NumberFactory.getInstance();
	private static Map<String, VelocityUpdater> instances = new HashMap<>();

	private MDNumber mass, dt;

	private VelocityUpdater(MDNumber mass, MDNumber dt) {
		this.mass = mass;
		this.dt = dt;
	}

	/**
	 * Factory method, get a configured VelocityUpdater
	 * 
	 * @param type
	 *            In form of 'ARGON_1e-15'. The last 1e-15 means the delta time
	 *            is 1e-15 second.
	 * @return VelocityUpdater instance (from the HashMap)
	 */
	public static VelocityUpdater getInstance(String type) {
		if (instances.get(type) == null) {
			String name = type.split("_")[0];
			MDNumber dt = numberFactory.valueOf(Double.parseDouble(type.split("_")[1]));
			MDNumber mass;
			try {
				mass = Constants.getMass(name);
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("The type name is not correct, should be like 'ARGON_1e-15");
			}
			instances.put(type, new VelocityUpdater(mass, dt));
		}
		return instances.get(type);
	}
	
	/**
	 * This method uses the full timestep dt instead of dt/2 like in velocity Verlet algorithm
	 * @param oldVelocity
	 * @param forceVector
	 * @return newVelocity
	 */
	public MDVector calculate(MDVector oldVelocity, MDVector forceVector){
		return oldVelocity.add(forceVector.divide(mass).times(dt));
	}

}
