package edu.MD.modelingBD;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utilityBD.Vector3DCartesian;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.PotentialConstants;

public class PBCPairwiseDistanceFinderTest {

	private static NumberFactory numberFactory;
	private static PBCPairwiseDistanceFinder distanceFinder;
	private MDVector systemBoundary;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void globalInit() {
		try {
			numberFactory = NumberFactory.getInstance();
		} catch (UnsupportedOperationException ex) {
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
			numberFactory = NumberFactory.getInstance();
		}
		distanceFinder = PBCPairwiseDistanceFinder.INSTANCE;
	}

	@Before
	public void init() {
		systemBoundary = new Vector3DCartesian(5, 5, 5);
	}

	@Test
	public void p1ToP2isOppositeOfP2ToP1() {
		MDVector p1 = new Vector3DCartesian(4.5, 4.5, 4.5);
		MDVector p2 = new Vector3DCartesian(2, 2, 2);
		MDVector p2ToP1 = distanceFinder.findDistance(p1, p2, systemBoundary);
		MDVector p1ToP2 = distanceFinder.findDistance(p2, p1, systemBoundary);

		assertTrue(p2ToP1.approximateEqual(p1ToP2.times(-1)));
	}

	@Test
	public void distanceLessThanHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2, 2, 2);
		MDVector p2ToP1 = distanceFinder.findDistance(p1, p2, systemBoundary);
		MDVector p2ToP1Expected = p1.minus(p2);

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceMoreThanHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(3, 3, 3);
		MDVector p2ToP1 = distanceFinder.findDistance(p1, p2, systemBoundary);
		MDVector p2ToP1Expected = new Vector3DCartesian(2, 2, 2);
		;

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceAtHalfOfSystemBoundary() {
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(2.5, 2.5, 2.5);
		MDVector p2ToP1 = distanceFinder.findDistance(p1, p2, systemBoundary);
		MDVector p2ToP1Expected = p2.times(-1);

		assertTrue(p2ToP1.approximateEqual(p2ToP1Expected));
	}

	@Test
	public void distanceUsedInForceCalculation() {
		LJForceCalculator forceCalculator = LJForceCalculator.getInstance("ARGON-ARGON-5.0");
		MDNumber sigma = PotentialConstants.getSigma("ARGON");
		systemBoundary = new Vector3DCartesian(sigma.times(6), sigma.times(6), sigma.times(6));
		MDVector p1 = new Vector3DCartesian(0, 0, 0);
		MDVector p2 = new Vector3DCartesian(sigma.times(0.1), sigma.times(5.9), sigma.times(0));
		MDVector p2ToP1 = distanceFinder.findDistance(p1, p2, systemBoundary);
		MDVector forceOnP1 = forceCalculator.calculate(p2ToP1);

		assertThat(forceOnP1.getCartesianComponent()[0].toDouble(), lessThan(0.0));
		assertThat(forceOnP1.getCartesianComponent()[1].toDouble(), greaterThan(0.0));
		assertTrue(forceOnP1.getCartesianComponent()[0].abs()
				.approximateEqual(forceOnP1.getCartesianComponent()[1].abs()));
		assertTrue(forceOnP1.getCartesianComponent()[2].approximateEqual(0));
	}

}
