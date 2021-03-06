package edu.MD.number;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.number.MDVector;
import edu.MD.number.NumberFactory;
import edu.MD.number.Vector3DCartesian;

public class Vector3DCartesianTest {
	private Vector3DCartesian vector3DCartesian;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		vector3DCartesian = new Vector3DCartesian(1, 1, 1);
	}

	@Test
	public void addVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(NumberFactory.getInstance().valueOf(2),
				NumberFactory.getInstance().valueOf(2), NumberFactory.getInstance().valueOf(2));
		MDVector actualResult = vector3DCartesian.plus(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(NumberFactory.getInstance().valueOf(3), NumberFactory.getInstance().valueOf(3),
				NumberFactory.getInstance().valueOf(3));

		assertThat(actualResult, equalTo(expectedResult));
	}

	@Test
	public void subtractVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(NumberFactory.getInstance().valueOf(2),
				NumberFactory.getInstance().valueOf(2), NumberFactory.getInstance().valueOf(2));
		MDVector actualResult = vector3DCartesian.minus(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(NumberFactory.getInstance().valueOf(-1), NumberFactory.getInstance().valueOf(-1),
				NumberFactory.getInstance().valueOf(-1));

		assertThat(actualResult, equalTo(expectedResult));
	}

	@Test
	public void twoEqualVectorsHaveTheSameHashCode() {

		MDVector vector = new Vector3DCartesian(NumberFactory.getInstance().valueOf(1), NumberFactory.getInstance().valueOf(1),
				NumberFactory.getInstance().valueOf(1));

		assertThat(vector.hashCode(), equalTo(vector3DCartesian.hashCode()));
	}
}
