package edu.MD.initializationBD;

import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class MonatomicPositionInitializer {
	private int filmThickness, filmSize, vaporOneSideThickness;
	private int numOfVapor, numOfLiquid;
	private MDNumber vaporMolarDensity, liquidMolarDensity;
	private MDNumber vaporLatticeSize, vaporLatticeLength, liquidLatticeLength;
	private MDNumber systemSize;
	private MDVector systemBoundary;
	private Iterable<MDVector> positions;

	/**
	 * This constructs a sandwiched liquid + two sides of vapor
	 * 
	 * @param name
	 *            of particle
	 * @param filmThickness
	 *            the number of liquid lattices in the y direction (axial
	 *            direction)
	 * @param filmSize
	 *            the number of liquid lattices in the x and z direction
	 *            (lateral plane)
	 * @param vaporOneSideThickness
	 *            the number of vapor lattices in the y direction on each side
	 *            of the sandwiching vapor (axial plane)
	 * @param temperature
	 *            in SI unit K
	 */
	public MonatomicPositionInitializer(String name, int filmThickness, int filmSize, int vaporOneSideThickness,
			double temperature) {
		if (filmThickness < 1)
			throw new IllegalArgumentException("Film thickness should be at least 1");

		this.filmThickness = filmThickness;
		this.filmSize = filmSize;
		this.vaporOneSideThickness = vaporOneSideThickness;

		vaporMolarDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		liquidMolarDensity = MDConstants.getMolarDensity(name, temperature, "liquid");

		calLiquidLatticeLength();
		systemSize = liquidLatticeLength.times(filmSize);
		calVaporLatticeLength();

		// check the thickness parameters are well defined
		// The vapor lattices should not skewed too much, 10% of difference
		// between lateral direction and axial direction is allowed
		if (vaporLatticeLength.minus(vaporLatticeSize).divide(vaporLatticeSize).abs().toDouble() > 0.1)
			throw new IllegalArgumentException("The defined parameters can not generate well defined lattices");

	}

	private void calLiquidLatticeLength() {
		// Refer to the attached notes
		liquidLatticeLength = liquidMolarDensity.times(MDConstants.AVOGADRO).pow(-1)
				.times((4 * filmThickness + 2) / ((double) filmThickness)).pow(1.0 / 3.0);
	}

	private void calVaporLatticeLength() {
		// Refer to the attached notes
		MDNumber firstApproxLatticeLen = vaporMolarDensity.times(MDConstants.AVOGADRO).pow(-1)
				.times((4 * vaporOneSideThickness - 1) / ((double) vaporOneSideThickness)).pow(1.0 / 3.0);
		int vaporSize = systemSize.divide(firstApproxLatticeLen).round();
		vaporLatticeSize = systemSize.divide(vaporSize);
		vaporLatticeLength = vaporMolarDensity.times(MDConstants.AVOGADRO).times(vaporLatticeSize.pow(2.0)).pow(-1)
				.times((4 * vaporOneSideThickness - 1) / ((double) vaporOneSideThickness));

	}

}
