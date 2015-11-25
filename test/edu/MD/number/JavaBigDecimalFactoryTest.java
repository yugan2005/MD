package edu.MD.number;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;

public class JavaBigDecimalFactoryTest {
	NumberFactory javaBigDecimalFactory;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		NumberFactorySetting.set("JavaBigDecimalFactory", 32);
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
	public void javaBigDecimalCalculationTest1() {
		MDNumber x = javaBigDecimalFactory.valueOf(10864);
		MDNumber y = javaBigDecimalFactory.valueOf(18817);
		MDNumber z = javaBigDecimalFactory.valueOf(9).times(x.pow(4)).minus(y.pow(4))
				.plus(javaBigDecimalFactory.valueOf(2).times(y.pow(2)));
		MDNumber expected = javaBigDecimalFactory.valueOf(1);
		assertThat(z, equalTo(expected));
	}
	
	/**
	 * Test case from: <a href=
	 * "http://stackoverflow.com/questions/6320209/javawhy-should-we-use-bigdecimal-instead-of-double-in-the-real-world">
	 * stackoverflow </a>
	 */
	@Test
	public void javaBigDecimalCalculationTest2(){
		MDNumber x = javaBigDecimalFactory.valueOf(0.1);
		MDNumber y = x.plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x);
		double calculated = y.toDouble();
		MDNumber expected = javaBigDecimalFactory.valueOf(1.0d); // correct value should be 1
		double expectedDouble = expected.toDouble();
		assertThat(calculated, equalTo(expectedDouble));
	}
	
	/**
	 * Test case from: <a href=
	 * "http://stackoverflow.com/questions/6320209/javawhy-should-we-use-bigdecimal-instead-of-double-in-the-real-world">
	 * stackoverflow </a>
	 */
	@Test
	public void javaBigDecimalCalculationTest3(){
		MDNumber x = javaBigDecimalFactory.valueOf(1234.0d);
		MDNumber y = javaBigDecimalFactory.valueOf(1.0e-13d);
		MDNumber z = x.plus(y);
		assertThat(z, not(equalTo(x)));
	}
}
