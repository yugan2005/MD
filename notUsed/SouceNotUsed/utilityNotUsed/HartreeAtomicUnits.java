package edu.MD.utility;

/**
 * @author YG
 * This unit system is used in the MD simulation to reduce machine errors
 * Reference: <a href="https://en.wikipedia.org/wiki/Atomic_units">Wiki</a>
 * Reference for physic constant: <a href="http://physics.nist.gov/cuu/Constants/index.html">NIST</a>
 *
 */
public class HartreeAtomicUnits {
	// These four are the base units
	public static final double MASS = 9.10938356e-31; // Electron rest mass in kg from NIST
	public static final double CHARGE = 1.6021766208e-19; // Elementary charge in C from NIST
	public static final double ACTION = 1.054571800e-34; // Reduced Planck's constant in J·s from NIST
	public static final double ELECTRIC_CONSTANT = 8.854187817e-12; // Electric constant, Vacuum permittivity from NIST
	public static final double ELECTROSTATIC_CONSTANT = 1/(4*Math.PI*ELECTRIC_CONSTANT); // Coulomb's constant, the electric force constant in kg·m3·s−2·C−2 (or, N·m2·C−2)
	
	//The following are derived units
	public static final double BOHR = ACTION*ACTION/(ELECTROSTATIC_CONSTANT*MASS*CHARGE*CHARGE); // Length (--> m)
	public static final double HARTREE = MASS*Math.pow(CHARGE, 4)*ELECTROSTATIC_CONSTANT*ELECTROSTATIC_CONSTANT/(ACTION*ACTION); // Energy ( --> J)
	public static final double TIME = ACTION/HARTREE;
	public static final double VELOCITY = BOHR*HARTREE/ACTION;
	public static final double FORCE = HARTREE/BOHR; // (--> N)
	public static final double TEMPERATURE = HARTREE/Constants.KB; // (--> K)
	public static final double PRESSURE = HARTREE/Math.pow(BOHR, 3); // (--> Pa)
	public static final double ELECTRIC_FIELD = HARTREE/(BOHR*CHARGE); // (--> V·m−1)
	public static final double ELECTRIC_POTENTIAL = HARTREE/CHARGE; // (--> V)
	public static final double ELECTRIC_DIPOLE_MOMENT = CHARGE*BOHR; // (-->  C·m)
	public static final double ACCELERATION = BOHR/(TIME*TIME);
	
	//The following are converted physical constants
	public static final double AVOGADRO = Constants.AVOGADRO;
	public static final double MOLAR_GAS_CONSTANT = Constants.MOLAR_GAS_CONSTANT*TEMPERATURE/HARTREE; // energy·mol-1·temperature-1
	public static final double KB = MOLAR_GAS_CONSTANT/AVOGADRO;
	
	
	
	public static double lengthToSI(double length){
		return length*BOHR;
	}
	
	public static double lengthToAU(double length){
		return length/BOHR;
	}
	
	public static double forceToSI(double force){
		return force*FORCE;
	}
	
	public static double forceToAU(double force){
		return force/FORCE;
	}
	
	public static double timeToSI(double time){
		return time*TIME;
	}
	
	public static double timeToAU(double time){
		return time/TIME;
	}
	
	public static double velocityToSI(double velocity){
		return velocity*VELOCITY;
	}
	
	public static double velocityToAU(double velocity){
		return velocity/VELOCITY;
	}
	
	public static double energyToSI(double energy){
		return energy*HARTREE;
	}
	
	public static double energyToAU(double energy){
		return energy/HARTREE;
	}
	
	public static double pressureToSI(double pressure){
		return pressure*PRESSURE;
	}
	
	public static double pressureToAU(double pressure){
		return pressure/PRESSURE;
	}
	
	public static double massToSI(double mass){
		return mass*MASS;
	}
	
	public static double massToAU(double mass){
		return mass/MASS;
	}
	
	public static double accelerationToSI(double acc){
		return acc*ACCELERATION;
	}
	
	public static double accelerationToAU(double acc){
		return acc/ACCELERATION;
	}
	
	public static double massDensityToSI(double den){
		return den*(MASS/Math.pow(BOHR, 3));
	}
	
	public static double massDensityToAU(double den){
		return den/(MASS/Math.pow(BOHR, 3));
	}
	
	public static double volumeToSI(double vol){
		return vol*Math.pow(BOHR, 3);
	}
	
	public static double volumeToAU(double vol){
		return vol/Math.pow(BOHR, 3);
	}
}
