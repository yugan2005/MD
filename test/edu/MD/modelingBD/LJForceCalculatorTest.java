package edu.MD.modelingBD;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utilityBD.Constants;
import edu.MD.utilityBD.PotentialConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Vector3DCartesian;

public class LJForceCalculatorTest {
	private LJForceCalculator forceCalculator;
	private MDNumber sigma;
	private double cutoff;
	private static NumberFactory numFactory;
	
	@BeforeClass
	public void globalInit(){
		try {
			numFactory = NumberFactory.getInstance();
		}
		catch (UnsupportedOperationException ex){
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
			numFactory = NumberFactory.getInstance();
		}
	}

	@Before
	public void init() {
		cutoff = 5;
		forceCalculator = LJForceCalculator.getInstance("ARGON-ARGON-"+String.valueOf(cutoff));
		sigma = PotentialConstants.getSigma("ARGON");
	}

	@Test
	public void p1ForceOnP2IsOppositeOfP2ForceOnP1() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(sigma.times(2),numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);
		MDVector p2_p1 = p2.minus(p1);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		MDVector forceOnP2 = forceCalculator.calculate(p2_p1);
		assertThat(forceOnP1.times(numFactory.valueOf(-1)), equalTo(forceOnP2));
	}

	@Test
	public void closeParticlesHaveRepelForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(sigma.times(0.1),numFactory.valueOf(0), numFactory.valueOf(0));
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
		MDVector p2 = new Vector3DCartesian(sigma.times(cutoff), numFactory.valueOf(0), numFactory.valueOf(0));
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2].toDouble(), closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	private void setSmallCutoffRadius(){
		cutoff = 1;
		forceCalculator = LJForceCalculator.getInstance("ARGON-ARGON-"+String.valueOf(cutoff));
	}


}
