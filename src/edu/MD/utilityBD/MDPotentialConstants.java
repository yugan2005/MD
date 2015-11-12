package edu.MD.utilityBD;

import java.util.HashMap;
import java.util.Map;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;

public class MDPotentialConstants {
	private static NumberFactory numberFactory = NumberFactory.getInstance();
	public static Map<String, MDNumber> sigma = new HashMap<>();
	public static Map<String, MDNumber> epsilon = new HashMap<>();
	
	static{
		String argon = "ARGON";
		double argonSigma = 3.4e-10; // LJ Length parameter in (m) - The original value
		double argonEpsilon = 1.67e-21; // LJ energy parameter in (J) - The original value
		sigma.put(argon, numberFactory.valueOf(argonSigma)); 
		epsilon.put(argon, numberFactory.valueOf(argonEpsilon));
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
