package edu.MD.utility;

import java.util.HashMap;
import java.util.Map;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;

public class MDPotentialConstants {
	public static Map<String, MDNumber> sigma = new HashMap<>();
	public static Map<String, MDNumber> epsilon = new HashMap<>();
	
	static{
		String argon = "ARGON";
		double argonSigma = 3.4e-10; // LJ Length parameter in (m) - The original value
		double argonEpsilon = 1.67e-21; // LJ energy parameter in (J) - The original value
		sigma.put(argon, NumberFactory.getInstance().valueOf(argonSigma)); 
		epsilon.put(argon, NumberFactory.getInstance().valueOf(argonEpsilon));
	}

	public static MDNumber getSigma(String name) {
		if (sigma.get(name)!=null) return sigma.get(name);
		throw new IllegalArgumentException("There is no sigma value for the particle: "+name);
	}

	public static MDNumber getEpsilon(String name) {
		if (epsilon.get(name)!=null) return epsilon.get(name);
		throw new IllegalArgumentException("There is no epsilon value for the particle: "+name);
	}

}
