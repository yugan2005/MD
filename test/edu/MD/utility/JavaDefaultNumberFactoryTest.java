package edu.MD.utility;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JavaDefaultNumberFactoryTest {
	NumberFactory javaDefaultNumberFactory;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void initGlobalFactory() {
		NumberFactory.setFactorySetting("JavaDefaultNumberFactory");
	}

	@Before
	public void init() {
		javaDefaultNumberFactory = NumberFactory.getInstance();
	}


	@Test
	public void javaDefaultDoubleShowsRoundError() {
		MDNumber x = javaDefaultNumberFactory.valueOf(10864);
		MDNumber y = javaDefaultNumberFactory.valueOf(18817);
		MDNumber z = javaDefaultNumberFactory.valueOf(9).times(x.pow(4)).minus(y.pow(4))
				.add(javaDefaultNumberFactory.valueOf(2).times(y.pow(2)));
		MDNumber expected = javaDefaultNumberFactory.valueOf(2); // correct value should be 1
		double calculatedDouble = z.toDouble();
		double expectedDouble = expected.toDouble();
		assertThat(calculatedDouble, equalTo(expectedDouble));
	}

}
