package edu.MD.utility;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.junit.Test;

import edu.MD.utility.MDConstants;

public class MDConstantsTest {

	@Test
	public void obtainArgonDensityAtExactK() {
		double vaporDensity = MDConstants.getMolarDensity("ARGON", 83.806, "vapor");
		double liquidDensity = MDConstants.getMolarDensity("ARGON", 83.806, "liquid");
		double expectedVaporDensity = 101.5;
		double expectedLiquidDensity = 35465;

		assertThat(Math.abs(vaporDensity - expectedVaporDensity) / expectedVaporDensity,
				closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
		assertThat(Math.abs(liquidDensity - expectedLiquidDensity) / expectedLiquidDensity,
				closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
	}

	@Test
	public void obtainArgonDensityWithInterpolation() {
		double vaporDensity = MDConstants.getMolarDensity("ARGON", 150, "vapor");
		double liquidDensity = MDConstants.getMolarDensity("ARGON", 150, "liquid");
		double expectedVaporDensity = 9873.23636363635;
		double expectedLiquidDensity = 17035.636363638;

		assertThat(Math.abs(vaporDensity - expectedVaporDensity) / expectedVaporDensity,
				closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
		assertThat(Math.abs(liquidDensity - expectedLiquidDensity) / expectedLiquidDensity,
				closeTo(0, MDConstants.RELATIVE_DOUBLE_ERROR));
	}

}
