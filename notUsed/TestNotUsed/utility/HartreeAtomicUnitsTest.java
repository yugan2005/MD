package edu.MD.utility;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import static edu.MD.utility.HartreeAtomicUnits.*;

public class HartreeAtomicUnitsTest {

	@Test
	public void newton2ndLawIsConsistent() {
		double forceInSI = MASS*ACCELERATION;
		double forceInAU = FORCE;
		
		assertThat(forceInSI, closeTo(forceInAU, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	@Test
	public void bohrIsConsistent(){
		double bohr = 5.2917721067e-11; // Length, Bohr radius, in m from NIST
		assertThat(bohr, closeTo(BOHR, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	@Test
	public void hartreeIsConsistent(){
		double hartree = 4.359744650e-18 ; // Energy in J from NIST
		assertThat(hartree, closeTo(HARTREE, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	@Test
	public void boltzmannConstantIsOneInAU(){
		assertThat(KB, closeTo(1.0, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	@Test
	public void velocityFromAUisConsistentFromSI(){
		double a = 0.5; // m.s-2
		double time = 2; // s
		double v0 = 1.5; // m.s-1
		double vFinal = v0+a*time;
		
		double aInAU = accelerationToAU(a);
		double timeInAU = timeToAU(time);
		double v0InAU = velocityToAU(v0);
		double vFinalInAU = v0InAU+aInAU*timeInAU;
		double vFinalInSI = velocityToSI(vFinalInAU);
		
		assertThat(vFinal, closeTo(vFinalInSI, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	@Test
	public void massDensityCalculationIsConsistent(){
		double mass = 1; //Kg
		double volume = 1; //m3
		double density = mass/volume;
		
		double massInAU = massToAU(mass);
		double volumeInAU = volumeToAU(volume);
		double densityInAU = massInAU/volumeInAU;
		double densityInSI = massDensityToSI(densityInAU);
		
		assertThat(density, closeTo(densityInSI, Constants.MACHINE_DOUBLE_ERROR));
	}

}
