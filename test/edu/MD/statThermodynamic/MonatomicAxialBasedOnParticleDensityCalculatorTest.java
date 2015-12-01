package edu.MD.statThermodynamic;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.globalSetting.PBCBoundarySetting;
import edu.MD.initialization.MonatomicPositionInitializer;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.utility.MDConstants;

public class MonatomicAxialBasedOnParticleDensityCalculatorTest {
	private Iterable<MDVector> positions;
	private MDVector systemBoundary;
	private static String name;
	private double temperature;
	private int filmSize, filmThickness, vaporOneSideThickness;
	private List<List<MDNumber>> densityProfile;

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		name = "ARGON";
		filmThickness = 5;
		filmSize = 5;
		vaporOneSideThickness = 2;
		temperature = 110;
		MonatomicPositionInitializer positionInitializer = new MonatomicPositionInitializer(name, filmThickness,
				filmSize, vaporOneSideThickness, temperature);
		systemBoundary = positionInitializer.getSystemBoundary();
		positions = positionInitializer.getPositions();

		PBCBoundarySetting.set(systemBoundary);

		densityProfile = MonatomicAxialBasedOnParticleDensityCalculator.calculate(positions, systemBoundary);

	}

	@Test
	public void yOfDensityProfileIncreaseOnly() {
		List<MDNumber> yPositions = densityProfile.get(0);
		for (int i = 1; i < yPositions.size(); i++) {
			assertThat(yPositions.get(i - 1), lessThan(yPositions.get(i)));
		}

	}

	// TODO Adding more testing method to verify that it is correct
	@Test
	public void densityProfileOfInitialPositionIsAsExpected() {
		List<MDNumber> yPositions = densityProfile.get(0);
		List<MDNumber> densityValues = densityProfile.get(1);

		for (int i = 0; i < yPositions.size(); i++) {
			System.out.println(String.format("y: %.3e; density: %.3e", yPositions.get(i).toDouble(),
					densityValues.get(i).toDouble()));
		}
		System.out.println(String.format("TotalY: %.3e", systemBoundary.getCartesianComponent()[1].toDouble()));
		System.out.println(String.format("vapor density: %.3e", MDConstants.getMolarDensity(name, temperature, "vapor")));
		System.out.println(String.format("liquid density: %.3e", MDConstants.getMolarDensity(name, temperature, "liquid")));
	}

}
