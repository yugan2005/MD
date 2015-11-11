package edu.MD.utilityBD;

import java.util.HashMap;
import java.util.Map;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;

public class MDConstants {
	private static NumberFactory numberFactory = NumberFactory.getInstance();

	
	// Following are General Constant
	public static final double MACHINE_DOUBLE_ERROR = 1e-28; // reference: Argon's atom mass: 6.6335209 × 10^-26 kg
	public static final double RELATIVE_DOUBLE_ERROR = 1e-6; // This is used for relative error Math.abs(true/approximate-1)
	
	// Following are Physical Constant in SI units
	public static final double KB = 1.38064852e-23; // Boltzmann Constant from NIST, in SI J�K-1 unit. In AU unit system it should be 1. 
	public static final double AVOGADRO = 6.022140857e23; // Avogadro constant from NIST, in mol-1;
	public static final double MOLAR_GAS_CONSTANT = 8.3144598; // Universal Molar Gas Constant from NIST, in J�mol-1�K-1
	
	
	// Following are Particle Constant in SI units
	public static Map<String, MDNumber> mass = new HashMap<>();
	
	
	static{
		String argon = "ARGON";
		double argonMass = 6.6331e-26; //Mass of argon atom (Kg) - The original value
		mass.put(argon, numberFactory.valueOf(argonMass)); 
	}

	public static MDNumber getMass(String name) {
		if (mass.get(name)!=null) return mass.get(name);
		throw new IllegalArgumentException("There is no mass value for the particle: "+name);
	}


}
