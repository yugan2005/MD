package edu.MD.modeling;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.globalSetting.PBCBoundarySetting;
import edu.MD.modeling.LJForceCalculator;
import edu.MD.modeling.PBCDistanceCalculator;
import edu.MD.number.MDVector;
import edu.MD.number.Vector3DCartesian;
import edu.MD.utility.MDPotentialConstants;

public class PBCDistanceCalculatorTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		setSystemBoundary(5, 5, 5);
	}

	private void setSystemBoundary(double x, double y, double z) {
		MDVector systemBoundary = new Vector3DCartesian(x, y, z);
		PBCBoundarySetting.set(systemBoundary);
	}

	@Test
	public void p1ToP2isOppositeOfP2ToP1() {
		MDVector p1 = new Vector3DCartesian(4.5, 4.5, 4.5);
		MDVector p2 = new Vector3DCartesian(2, 2, 2);
		MDVector p2ToP1 = PBCDistanceCalculator.calculate(p1, p2);
		MDVector p1ToP2 = PBCDistanceCalculator.calculate(p2, p1);

		assertTrue(p2ToP1.approximateEqual(p1ToP2.times(-1)));
	}

	@Test
	public void distanceLessThanHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2, 2, 2);
		MDVector p2ToP1 = PBCDistanceCalculator.calculate(p1, p2);
		MDVector p2ToP1Expected = p1.minus(p2);

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceMoreThanHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(3, 3, 3);
		MDVector p2ToP1 = PBCDistanceCalculator.calculate(p1, p2);
		MDVector p2ToP1Expected = new Vector3DCartesian(2, 2, 2);
		;

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceAtHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2.5, 2.5, 2.5);
		MDVector p2ToP1 = PBCDistanceCalculator.calculate(p1, p2);
		MDVector p2ToP1Expected = p2.times(-1);

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceUsedInForceCalculation() {
		LJForceCalculator forceCalculator = LJForceCalculator.getInstance("ARGON_ARGON_5.0");
		double sigma = MDPotentialConstants.getSigma("ARGON");
		setSystemBoundary(6*sigma, 6*sigma, 6*sigma);
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(0.1*sigma, 5.9*sigma, 0);
		MDVector p2ToP1 = PBCDistanceCalculator.calculate(p1, p2);
		MDVector forceOnP1 = forceCalculator.calculate(p2ToP1);

		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), lessThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), greaterThan(0.0));
		assertTrue(forceOnP1.getCartesianComponent()[0].abs()
				.approximateEqual(forceOnP1.getCartesianComponent()[1].abs()));
		assertTrue(forceOnP1.getCartesianComponent()[2].approximateEqual(0));
	}

}
