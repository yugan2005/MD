package edu.MD.initializationBD;

import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class MonatomicPositionInitializer {
	private int numOfVapor, numOfLiquid;
	private MDNumber vaporMolarDensity, liquidMolarDensity;
	private MDNumber vaporLatticeLength, liquidLatticeLength;
	private MDVector systemBoundary;
	private Iterable<MDVector> positions;

	public MonatomicPositionInitializer(String name, int numOfVapor, int numOfLiquid, double temperature) {
		if (numOfVapor == 0 || numOfLiquid == 0)
			throw new IllegalArgumentException(
					"Need have both vapor and liquid to define a satuation system. If it is not satuation system use other constructor");
		this.numOfLiquid = numOfLiquid;
		this.numOfVapor = numOfVapor;
		vaporMolarDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		liquidMolarDensity = MDConstants.getMolarDensity(name, temperature, "liquid");
		vaporLatticeLength = calLatticeLength(vaporMolarDensity);
		liquidLatticeLength = calLatticeLength(liquidMolarDensity);
	}

	private MDNumber calLatticeLength(MDNumber molarDensity) {
		double NA = MDConstants.AVOGADRO;
		return molarDensity.times(NA).pow(-1.0).times(4).pow(1.0 / 3.0);
	}
	

}
