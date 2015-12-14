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

public class MonatomicYAxialSmoothDensityCalculatorTest {
	private Iterable<MDVector> positions;
	private MDVector systemBoundary;
	private static String name;
	private double temperature;
	private int filmSize, filmThickness, vaporOneSideThickness;
	private int nBins;
	private IDensityCalculator densityCalculator;

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
		nBins = 20;
		MonatomicPositionInitializer positionInitializer = new MonatomicPositionInitializer(name, filmThickness,
				filmSize, vaporOneSideThickness, temperature);
		systemBoundary = positionInitializer.getSystemBoundary();
		positions = positionInitializer.getPositions();
		double molarVaproDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		PBCBoundarySetting.set(systemBoundary);
		
		int totalNumberOfParticles = positionInitializer.getTotalNumberOfParticles();
		densityCalculator = MonatomicYAxialSmoothDensityCalculator.getInstance(totalNumberOfParticles, systemBoundary, molarVaproDensity, nBins);

	}

	@Test
	public void yOfDensityProfileIncreaseOnly() {
		List<List<MDNumber>> densityProfile = densityCalculator.calculate(positions);
		List<MDNumber> yPositions = densityProfile.get(0);
		for (int i=1; i<yPositions.size(); i++){
			assertThat(yPositions.get(i-1), lessThan(yPositions.get(i)));
		}

	}
	
}
