package edu.MD.modeling;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.MD.utility.MDVector;
import edu.MD.utility.PotentialConstants;
import edu.MD.utility.Vector3DCartesian;
import edu.MD.utility.Constants;
public class LJForceCalculatorTest {
	LJForceCalculator forceCalculator;
	double epsilon, sigma, sigma6, sigma12, cutoffRadius, cutoffPotential;

	@Before
	public void init() {
		
		epsilon = PotentialConstants.ARGON_EPSILON; // This initialize to Argon
		sigma = PotentialConstants.ARGON_SIGMA;

		cutoffRadius = 5 * sigma;

		sigma6 = Math.pow(sigma, 6);
		sigma12 = Math.pow(sigma, 12);
		cutoffPotential = 4 * epsilon
				* (-12 * sigma12 / Math.pow(cutoffRadius, 13) + 6 * sigma6 / Math.pow(cutoffRadius, 7));

		forceCalculator = LJForceCalculator.getInstance(sigma6, sigma12, epsilon, cutoffPotential);
	}

	@Test
	public void p1ForceOnP2IsOppositeOfP2ForceOnP1() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2 * sigma, 0, 0);
		MDVector p1_p2 = p1.minus(p2);
		MDVector p2_p1 = p2.minus(p1);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		MDVector forceOnP2 = forceCalculator.calculate(p2_p1);
		assertThat(forceOnP1.times(-1), equalTo(forceOnP2));
	}

	@Test
	public void closeParticlesHaveRepelForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(0.1 * sigma, 0, 0);
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0], lessThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}

	@Test
	public void farParticlesHaveAttractForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2 * sigma, 0, 0);
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0], greaterThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}

	@Test
	public void atCutoffParticlesHaveZeroForce() {
		setSmallCutoffRadius(); // set the cutoff radius small
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(cutoffRadius, 0, 0);
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[1], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
		assertThat(forceOnP1.getCartesianComponent()[2], closeTo(0.0, Constants.MACHINE_DOUBLE_ERROR));
	}
	
	private void setSmallCutoffRadius(){
		cutoffRadius = 1 * sigma;
		cutoffPotential = 4 * epsilon
				* (-12 * sigma12 / Math.pow(cutoffRadius, 13) + 6 * sigma6 / Math.pow(cutoffRadius, 7));

		forceCalculator = LJForceCalculator.getInstance(sigma6, sigma12, epsilon, cutoffPotential);

	}

}
