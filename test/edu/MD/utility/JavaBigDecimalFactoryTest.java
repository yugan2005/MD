package edu.MD.utility;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JavaBigDecimalFactoryTest {
	NumberFactory javaBigDecimalFactory;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void initGlobalFactory() {
		NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
	}

	@Before
	public void init() {
		javaBigDecimalFactory = NumberFactory.getInstance();
	}

	/**
	 * Test case from: <a href=
	 * "http://jscience.org/api/org/jscience/mathematics/number/package-summary.html">
	 * Jscience API </a>
	 */
	@Test
	public void javaBigDecimalCalculationTest() {
		MDNumber x = javaBigDecimalFactory.valueOf(10864);
		MDNumber y = javaBigDecimalFactory.valueOf(18817);
		MDNumber z = javaBigDecimalFactory.valueOf(9).times(x.pow(4)).minus(y.pow(4))
				.add(javaBigDecimalFactory.valueOf(2).times(y.pow(2)));
		MDNumber expected = javaBigDecimalFactory.valueOf(1);
		assertThat(z, equalTo(expected));
	}
}
