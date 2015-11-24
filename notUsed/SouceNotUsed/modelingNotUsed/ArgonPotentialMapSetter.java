package edu.MD.modeling;

import java.util.Map;
import edu.MD.utility.PotentialConstants;

public class ArgonPotentialMapSetter implements IPotentialMapSetter {
	public static final ArgonPotentialMapSetter INSTANCE = new ArgonPotentialMapSetter();

	private ArgonPotentialMapSetter() {
	}

	@Override
	public void setPotentialMap(ISystem system) {
		Map<String, Double> map = system.getPotentialMap();
		String keyPrefixString = Argon.TYPE + "_" + Argon.NAME + "_" + Argon.TYPE + "_" + Argon.NAME + "_";
		String keyString = keyPrefixString + "sigma";
		double sigma = PotentialConstants.ARGON_SIGMA;
		map.put(keyString, sigma);
		keyString = keyPrefixString + "sigma12";
		map.put(keyString, Math.pow(sigma, 12));
		keyString = keyPrefixString + "sigma6";
		map.put(keyString, Math.pow(sigma, 6));
		keyString = keyPrefixString + "epsilon";
		double epsilon = PotentialConstants.ARGON_EPSILON;
		map.put(keyString, epsilon);

		// cutoffPotential formula is on note Page1
		keyString = keyPrefixString + "cutoffPotential";
		double cutoffRadius = PotentialConstants.ARGON_CUTOFFRADIUS;
		double cutOffPotential = 4 * epsilon * (-12 * Math.pow(sigma, 12) / Math.pow(cutoffRadius, 13)
				+ 6 * Math.pow(sigma, 6) / Math.pow(cutoffRadius, 7));
		map.put(keyString, cutOffPotential);
	}

}
