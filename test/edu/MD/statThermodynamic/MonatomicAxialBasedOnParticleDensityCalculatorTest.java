package edu.MD.statThermodynamic;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.initialization.MonatomicPositionInitializer;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.utility.MDConstants;
import edu.MD.utility.PBCCalculator;

public class MonatomicAxialBasedOnParticleDensityCalculatorTest {
	private Iterable<MDVector> positions;
	private MDVector systemBoundary;
	private static String name;
	private double temperature;
	private int filmSize, filmThickness, vaporOneSideThickness;
	private int nBins;

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		name = "ARGON";
		filmThickness = 3;
		filmSize = 3;
		vaporOneSideThickness = 2;
		temperature = 110;
		nBins = 20;
		MonatomicPositionInitializer positionInitializer = new MonatomicPositionInitializer(name, filmThickness, filmSize, vaporOneSideThickness, temperature);
		systemBoundary = positionInitializer.getSystemBoundary();
		positions = positionInitializer.getPositions();
		double molarVaproDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		
		PBCCalculator.setPBCCalculator(systemBoundary);
		MonatomicAxialBasedOnParticleDensityCalculator.setDensityCalculator(molarVaproDensity, systemBoundary, nBins);
		
	}

	@Test
	public void averageDensityIsConsistent() {
		List<List<MDNumber>> densityProfile = MonatomicAxialBasedOnParticleDensityCalculator.calculate(positions, systemBoundary);

		MDNumber averageDensity = densityProfile.get(1).get(0);
		for (int i=1; i<densityProfile.get(1).size(); i++){
			averageDensity = averageDensity.plus(densityProfile.get(1).get(i));
		}
		// TODO worked to here
	}

}
