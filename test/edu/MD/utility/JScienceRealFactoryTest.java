package edu.MD.utility;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jscience.mathematics.number.Real;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JScienceRealFactoryTest {
	NumberFactory jScienceRealFactory;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void initGlobalFactory() {
		NumberFactory.setFactorySetting("JScienceRealFacotry", 32);
	}

	@Before
	public void init() {
		jScienceRealFactory = NumberFactory.getInstance();
	}

	/**
	 * Test case from: <a href=
	 * "http://jscience.org/api/org/jscience/mathematics/number/package-summary.html">
	 * Jscience API </a>
	 */
	@Test
	public void jRealFactoryCalculationTest() {
		MDNumber x = jScienceRealFactory.valueOf(10864);
		MDNumber y = jScienceRealFactory.valueOf(18817);
		MDNumber z = jScienceRealFactory.valueOf(9).times(x.pow(4)).minus(y.pow(4))
				.add(jScienceRealFactory.valueOf(2).times(y.pow(2)));
		MDNumber expected = jScienceRealFactory.valueOf(1);
		double calculatedDouble = z.toDouble();
		double expectedDouble = expected.toDouble();
		assertThat(calculatedDouble, equalTo(expectedDouble));
	}
}
