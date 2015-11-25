package edu.MD.modeling;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.modeling.LJForceCalculator;
import edu.MD.number.MDVector;
import edu.MD.number.NumberFactory;
import edu.MD.number.Vector3DCartesian;
import edu.MD.utility.MDConstants;
import edu.MD.utility.MDPotentialConstants;
import globalSettingUtility.NumberFactorySetting;

public class LJForceCalculatorTest {
	private LJForceCalculator forceCalculator;
	private double sigma;
	private double cutoff;

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		cutoff = 5;
		forceCalculator = LJForceCalculator.getInstance("ARGON_ARGON_" + String.valueOf(cutoff));
		sigma = MDPotentialConstants.getSigma("ARGON");
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
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), lessThan(0.0));
		assertTrue(forceOnP1.getCartesianComponent()[1].approximateEqual(0));
		assertTrue(forceOnP1.getCartesianComponent()[2].approximateEqual(0));
	}

	@Test
	public void farParticlesHaveAttractForce() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2 * sigma, 0, 0);
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), greaterThan(0.0));
		assertTrue(forceOnP1.getCartesianComponent()[1].approximateEqual(0));
		assertTrue(forceOnP1.getCartesianComponent()[2].approximateEqual(0));
	}

	@Test
	public void atCutoffParticlesHaveZeroForce() {
		setSmallCutoffRadius(); // set the cutoff radius small
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(sigma * cutoff, 0, 0);
		MDVector p1_p2 = p1.minus(p2);

		MDVector forceOnP1 = forceCalculator.calculate(p1_p2);
		if (NumberFactory.getInstance().getClass().getSimpleName().equals("JavaBigDecimalFactory")) {
			assertTrue(forceOnP1.getCartesianComponent()[0].approximateEqual(0));
			assertTrue(forceOnP1.getCartesianComponent()[1].approximateEqual(0));
			assertTrue(forceOnP1.getCartesianComponent()[2].approximateEqual(0));
		}
		else if (NumberFactory.getInstance().getClass().getSimpleName().equals("JavaDefaultNumberFactory")){
			assertFalse(forceOnP1.getCartesianComponent()[0].approximateEqual(0));
			assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
			assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
			assertThat(forceOnP1.getCartesianComponent()[2].toDouble(), closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
		}
		else {
			fail("reflection does not work!");
		}
	}

	private void setSmallCutoffRadius() {
		cutoff = 1;
		forceCalculator = LJForceCalculator.getInstance("ARGON_ARGON_" + String.valueOf(cutoff));
	}

}
