package edu.MD.initializationBD;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.PBCCalculator;
import edu.MD.utilityBD.Vector3DCartesian;

public class MonatomicPositionInitializerTest {
	private MonatomicPositionInitializer initializer;
	private static NumberFactory numFactory;
	private String name;
	private int filmThickness, filmSize, vaporOneSideThickness;
	private double temperature;

	@BeforeClass
	public static void globalInit() {
		try {
			numFactory = NumberFactory.getInstance();
		} catch (UnsupportedOperationException ex) {
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
			numFactory = NumberFactory.getInstance();
		}
	}

	@Before
	public void init() {
		name = "ARGON";
		filmThickness = 10;
		filmSize = 20;
		vaporOneSideThickness = 12;
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
		//TODO finish the PBC testing

	}

}
