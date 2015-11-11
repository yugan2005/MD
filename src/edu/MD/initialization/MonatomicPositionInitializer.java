package edu.MD.initialization;

import edu.MD.number.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class MonatomicPositionInitializer {
	private int numOfVapor, numOfLiquid;
	private MDNumber molarDensityOfVapor, molarDensityOfLiquid;
	private MDNumber latticeLengthOfVapor, latticeLengthOfLiquid;
	private MDVector systemBoundary;
	
	public MonatomicPositionInitializer(String name, int numOfVapor, int numOfLiquid, double temperature){
		this.numOfLiquid = numOfLiquid;
		this.numOfVapor = numOfVapor;
		molarDensity = MDConstants.getMolarDensity(name, temperature);
	}
	
	private MDNumber getLatticeLength(MDNumber molarDensity){
		double NA = MDConstants.AVOGADRO;
		return molarDensity.times(NA).pow(-1.0).times(4).pow(1.0/3.0);
	}
	

}
