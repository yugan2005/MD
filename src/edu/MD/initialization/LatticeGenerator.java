package edu.MD.initialization;

import edu.MD.number.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class LatticeGenerator {
	private MDNumber molarDensity;
	private MDNumber latticeLength = getLatticeLength();
	
	public MDNumber getLatticeLength(){
		double NA = MDConstants.AVOGADRO;
		return molarDensity.times(NA).pow(-1.0).times(4).pow(1.0/3.0);
	}
	
	public Iterable<MDVector> getCornerPoints(){
		
	}

}
