package edu.MD.number;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;

public class JavaDefaultNumberFactoryTest {
	NumberFactory javaDefaultNumberFactory;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set("JavaDefaultNumberFactory");
	}

	@Before
	public void init() {
		javaDefaultNumberFactory = NumberFactory.getInstance();
	}

	/**
	 * Test case from: <a href=
	 * "http://jscience.org/api/org/jscience/mathematics/number/package-summary.html">
	 * Jscience API </a>
	 */
	@Test
	public void javaDefaultDoubleShowsRoundErrorExample1() {
		MDNumber x = javaDefaultNumberFactory.valueOf(10864);
		MDNumber y = javaDefaultNumberFactory.valueOf(18817);
		MDNumber z = javaDefaultNumberFactory.valueOf(9).times(x.pow(4)).minus(y.pow(4))
				.plus(javaDefaultNumberFactory.valueOf(2).times(y.pow(2)));
		MDNumber expected = javaDefaultNumberFactory.valueOf(2); // correct
																	// value
																	// should be
																	// 1
		assertThat(z, equalTo(expected));
	}

	/**
	 * Test case from: <a href=
	 * "http://stackoverflow.com/questions/6320209/javawhy-should-we-use-bigdecimal-instead-of-double-in-the-real-world">
	 * stackoverflow </a>
	 */
	@Test
	public void javaDefaultDoubleShowsRoundErrorExample2() {
		MDNumber x = javaDefaultNumberFactory.valueOf(0.1d);
		MDNumber y = x.plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x).plus(x);
		double calculated = y.toDouble();
		MDNumber expected = javaDefaultNumberFactory.valueOf(1.0d); // correct
																	// value
																	// should be
																	// 1
		double expectedDouble = expected.toDouble();
		assertThat(calculated, not(equalTo(expectedDouble)));
	}

	/**
	 * Test case from: <a href=
	 * "http://stackoverflow.com/questions/6320209/javawhy-should-we-use-bigdecimal-instead-of-double-in-the-real-world">
	 * stackoverflow </a>
	 */
	@Test
	public void javaDefaultDoubleShowsRoundErrorExample3() {
		MDNumber x = javaDefaultNumberFactory.valueOf(1234.0d);
		MDNumber y = javaDefaultNumberFactory.valueOf(1.0e-13d);
		MDNumber z = x.plus(y);
		assertThat(z, equalTo(x));
	}

}
