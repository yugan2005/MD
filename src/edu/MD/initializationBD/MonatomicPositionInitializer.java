package edu.MD.initializationBD;

import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class MonatomicPositionInitializer {
	private int filmThickness, filmSize, vaporLength;
	private int numOfVapor, numOfLiquid;
	private MDNumber vaporMolarDensity, liquidMolarDensity;
	private MDNumber vaporLatticeLength, liquidLatticeLength;
	private MDVector systemBoundary;
	private Iterable<MDVector> positions;

	public MonatomicPositionInitializer(String name, int filmThickness, int filmSize, int vaporLength,
			double temperature) {
		if (filmThickness < 1)
			throw new IllegalArgumentException("Film thickness should be at least 1");

		this.filmThickness = filmThickness;
		this.filmSize = filmSize;
		this.vaporLength = vaporLength;

		vaporMolarDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		liquidMolarDensity = MDConstants.getMolarDensity(name, temperature, "liquid");

		liquidLatticeLength = calLiquidLatticeLength();
		MDNumber systemSize = liquidLatticeLength.times(filmSize);

	}

	private MDNumber calLiquidLatticeLength() {
		// Refer to the attached notes
		return liquidMolarDensity.times(MDConstants.AVOGADRO).pow(-1)
				.times((4 * filmThickness + 2) / ((double) filmThickness)).pow(1.0 / 3.0);
	}

	private MDNumber calVaporLatticeLength() {
		MDNumber liquidLatticeLength;
		// TODO to here
		return null;

	}

}
