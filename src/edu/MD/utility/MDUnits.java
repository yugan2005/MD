package edu.MD.utility;

public class MDUnits {
	
	// The following units are the base units that we choose for MD simulation to reduce machine error
	public static final double LENGTH = 1e-10; 
	public static final double ENERGY = 1e-21; 
	
	// The following units are derived units
	public static final double FORCE = ENERGY/LENGTH;
	
	
	
	public static double lengthToMDU(double len){
		return len/LENGTH;
	}
	
	public static double lengthToSI(double len){
		return len*LENGTH;
	}
	
	public static double energyToMDU(double energy){
		return energy/ENERGY;
	}
	
	public static double energyToSI(double energy){
		return energy*ENERGY;
	}
	
	public static double forceToMDU(double force){
		return force/FORCE;
	}
	
	public static double forceToSI(double force){
		return force*FORCE;
	}

}
