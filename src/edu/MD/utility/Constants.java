package edu.MD.utility;

public class Constants {
	
	// Following are General Constant
	public static final double MACHINE_DOUBLE_ERROR = 1e-30;
	
	// Following are Physical Constant in SI units
	public static final double KB = 1.38064852e-23; // Boltzmann Constant from NIST, in SI J·K-1 unit. In AU unit system it should be 1. 
	public static final double AVOGADRO = 6.022140857e23; // Avogadro constant from NIST, in mol-1;
	public static final double MOLAR_GAS_CONSTANT = 8.3144598; // Universal Molar Gas Constant from NIST, in J·mol-1·K-1
	
	public static boolean doubleEqual(double input1, double input2){
		double epsilon = 10* Math.max(Math.ulp(input1), Math.ulp(input2));
		return Math.abs(input1-input2)<epsilon*Math.max(Math.abs(input1), Math.abs(input2));
	}
	
	

}
