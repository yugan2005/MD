package edu.MD.initializationBD;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.modelingBD.PBCPairwiseDistanceFinder;
import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.PBCCalculator;
import edu.MD.utilityBD.Vector3DCartesian;

public class MonatomicPositionInitializerTest {
	private MonatomicPositionInitializer initializer;
	private String name;
	private int filmThickness, filmSize, vaporOneSideThickness;
	private double temperature;

	@BeforeClass
	public static void globalInit() {
		try {
			NumberFactory.getInstance();
		} catch (UnsupportedOperationException ex) {
			NumberFactory.setFactorySetting("JavaDefaultNumberFactory");
			NumberFactory.getInstance();
		}
	}

	@Before
	public void init() {
		name = "ARGON";
		filmThickness = 5;
		filmSize = 10;
		vaporOneSideThickness = 8;
		temperature = 110;
		initializer = new MonatomicPositionInitializer(name, filmThickness, filmSize, vaporOneSideThickness,
				temperature);
	}

	@Test
	public void initializerGiveCorrectNumberOfLiquid() {
		int numOfLiquid = initializer.getNumOfLiquidParticles();

		MDNumber liquidMolarDensity = MDConstants.getMolarDensity(name, temperature, "liquid");
		MDNumber liquidLatticeLength = initializer.getSystemBoundary().getCartesianComponent()[0].divide(filmSize);
		MDNumber liquidVolume = liquidLatticeLength.pow(3).times(filmSize * filmSize * filmThickness);

		int numOfLiquidExpected = liquidVolume.times(liquidMolarDensity).times(MDConstants.AVOGADRO).round();
		assertThat(numOfLiquid, equalTo(numOfLiquidExpected));
	}

	@Test
	public void initializerGiveCorrectNumberOfVapor() {
		int numOfVapor = initializer.getNumOfVaporParticles();

		MDNumber totalVolume = initializer.getSystemBoundary().cuboidVolume(new Vector3DCartesian(0, 0, 0));
		MDNumber liquidLatticeLength = initializer.getSystemBoundary().getCartesianComponent()[0].divide(filmSize);
		MDNumber liquidVolume = liquidLatticeLength.pow(3).times(filmSize * filmSize * filmThickness);
		MDNumber vaporVolume = totalVolume.minus(liquidVolume);
		MDNumber vaporMolarDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		int numOfVaporExpected = vaporVolume.times(vaporMolarDensity).times(MDConstants.AVOGADRO).round();

		assertThat(numOfVapor, equalTo(numOfVaporExpected));
	}

	@Test
	public void correctSeperationAfterPBC() {
		PBCCalculator.setPBCCalculator(initializer.getSystemBoundary());
		PBCPairwiseDistanceFinder distFinder = PBCPairwiseDistanceFinder.getInstance();
		MDNumber minDistance = null;
		MDNumber maxDistance = null;
		for (int i = 0; i < initializer.getPositions().size(); i++) {
			for (int j = i + 1; j < initializer.getPositions().size(); j++) {
				MDNumber distance = distFinder
						.getDistance(initializer.getPositions().get(i), initializer.getPositions().get(j)).norm();
				if (minDistance == null || distance.compareTo(minDistance) < 0)
					minDistance = distance;
				if (maxDistance == null || distance.compareTo(minDistance) > 0)
					maxDistance = distance;
			}
		}

		MDNumber liquidLatticeLength = initializer.getSystemBoundary().getCartesianComponent()[0].divide(filmSize);
		MDNumber minDistanceExpected = liquidLatticeLength.times(Math.sqrt(2) / 2);
		MDNumber maxDistanceExpected = initializer.getSystemBoundary().norm().divide(2.0);

		assertTrue(minDistance.approximateEqual(minDistanceExpected));
		assertTrue(maxDistanceExpected.compareTo(maxDistance) > 0);

	}

}
