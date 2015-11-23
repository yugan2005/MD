package edu.MD.utilityBD;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;

public class MDConstants {
	private static NumberFactory numberFactory = NumberFactory.getInstance();

	// Following are General Constant
	public static final double MACHINE_DOUBLE_ERROR = 1e-28; // reference:
																// Argon's atom
																// mass:
																// 6.6335209 ×
																// 10^-26 kg
	public static final double RELATIVE_DOUBLE_ERROR = 1e-6; // This is used for
																// relative
																// error
																// Math.abs(true/approximate-1)

	// Following are Physical Constant in SI units
	public static final double KB = 1.38064852e-23; // Boltzmann Constant from
													// NIST, in SI J�K-1 unit.
													// In AU unit system it
													// should be 1.
	public static final double AVOGADRO = 6.022140857e23; // Avogadro constant
															// from NIST, in
															// mol-1;
	public static final double MOLAR_GAS_CONSTANT = 8.3144598; // Universal
																// Molar Gas
																// Constant from
																// NIST, in
																// J�mol-1�K-1

	// Following are Particle Constant in SI units
	private static Map<String, MDNumber> mass = new HashMap<>();
	private static Map<String, Integer> DOF = new HashMap<>();
	private static Map<String, TreeMap<Double, MDNumber>> vaporDensity = new HashMap<>();
	private static Map<String, TreeMap<Double, MDNumber>> liquidDensity = new HashMap<>();
	private static Map<String, TreeMap<Double, MDNumber>> solidDensity = new HashMap<>();

	public static MDNumber getMass(String name) {
		if (mass.get(name) != null)
			return mass.get(name);
		throw new IllegalArgumentException("There is no mass value for the particle: " + name);
	}

	/**
	 * @param name
	 *            of the particle (All capital letters)
	 * @param temperature
	 *            in SI unit K.
	 * @param phase
	 *            specify the phase for getting the molar density: solid /
	 *            liquid / vapor
	 * @return Molardensity in MDNumber format
	 */
	public static MDNumber getMolarDensity(String name, double temperature, String phase) {
		Map<String, TreeMap<Double, MDNumber>> density;
		switch (phase) {
		case "vapor":
			density = vaporDensity;
			break;
		case "liquid":
			density = liquidDensity;
			break;
		case "solid":
			density = solidDensity;
			break;
		default:
			throw new IllegalArgumentException("Need specify valid phase for obtaining the molar density");
		}

		if (density.get(name) == null || density.get(name).lastKey() < temperature
				|| density.get(name).firstKey() > temperature)
			throw new IllegalArgumentException(
					"Either the particle name is not implemented, or the temperature is out of range");
		TreeMap<Double, MDNumber> particleDensity = density.get(name);
		
		if (particleDensity.get(temperature) != null)
			return particleDensity.get(temperature);
		
		Map.Entry<Double, MDNumber> floor = particleDensity.floorEntry(temperature);
		Map.Entry<Double, MDNumber> ceil = particleDensity.ceilingEntry(temperature);
		return ceil.getValue().minus(floor.getValue()).divide(ceil.getKey() - floor.getKey())
				.times(temperature - floor.getKey()).add(floor.getValue());
	}

	static {
		String argon = "ARGON";
		double argonMass = 6.6331e-26; // Mass of argon atom (Kg) - The original
										// value
		int argonDOF = 3;
		mass.put(argon, numberFactory.valueOf(argonMass));
		DOF.put(argon, argonDOF);

		// set argon's vapor and liquid density

		try {
			NISTSaturationPropertiesReader densityReader = new NISTSaturationPropertiesReader(argon);
			vaporDensity.put(argon, densityReader.getSaturationDensityVSTemperature().get("vapor"));
			liquidDensity.put(argon, densityReader.getSaturationDensityVSTemperature().get("liquid"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getDOF(String name) {
		if (DOF.get(name) != null)
			return DOF.get(name);
		throw new IllegalArgumentException("There is no DOF value for the particle: " + name);
	}

}
