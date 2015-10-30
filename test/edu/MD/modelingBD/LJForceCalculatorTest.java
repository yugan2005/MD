package edu.MD.modelingBD;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utility.Constants;
import edu.MD.utility.PotentialConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Vector3DCartesian;

public class LJForceCalculatorTest {
	LJForceCalculator forceCalculator;
	MDNumber epsilon, sigma, sigma6, sigma12, cutoffRadius, cutoffPotential;
	NumberFactory numFactory;

	@BeforeClass
	public static void globalInit() {
		NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
	}

	@Before
	public void init() {
		numFactory = NumberFactory.getInstance();
		epsilon = numFactory.valueOf(PotentialConstants.ARGON_EPSILON);
		sigma = numFactory.valueOf(PotentialConstants.ARGON_SIGMA);
		sigma6 = sigma.pow(6);
		sigma12 = sigma.pow(12);

		cutoffRadius = numFactory.valueOf(5.0).times(sigma);
		cutoffPotential = numFactory.valueOf(4.0).times(epsilon).times(numFactory.valueOf(-12.).times(sigma12)
				.divide(cutoffRadius.pow(13)).add(numFactory.valueOf(6.).times(sigma6).divide(cutoffRadius.pow(7))));
		
		forceCalculator = LJForceCalculator.getInstance(sigma6, sigma12, epsilon, cutoffPotential);
	}

	@Test
	public void p1ForceOnP2IsOppositeOfP2ForceOnP1() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(numFactory.valueOf(2).times(sigma),numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);
		MDVector p2_p1 = p2.minus(p1);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		MDVector forceOnP2 = forceCalculator.calculate(p2_p1);
		assertThat(forceOnP1.times(numFactory.valueOf(-1)), equalTo(forceOnP2));
	}

	@Test
	public void closeParticlesHaveRepelForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(numFactory.valueOf(0.1).times(sigma),numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), lessThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}

	@Test
	public void farParticlesHaveAttractForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(numFactory.valueOf(2).times(sigma),numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), greaterThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}

	@Test
	public void atCutoffParticlesHaveZeroForce() {
		setSmallCutoffRadius(); // set the cutoff radius small
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(cutoffRadius, numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	private void setSmallCutoffRadius(){
		cutoffRadius = sigma;
		cutoffPotential = numFactory.valueOf(4.0).times(epsilon).times(numFactory.valueOf(-12.).times(sigma12)
				.divide(cutoffRadius.pow(13)).add(numFactory.valueOf(6.).times(sigma6).divide(cutoffRadius.pow(7))));

		forceCalculator = LJForceCalculator.getInstance(sigma6, sigma12, epsilon, cutoffPotential);
	}


}
