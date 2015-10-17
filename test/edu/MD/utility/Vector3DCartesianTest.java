package edu.MD.utility;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Vector3DCartesianTest {
	private Vector3DCartesian vector3DCartesian;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void init() {
		vector3DCartesian = new Vector3DCartesian(1, 1, 1);
	}

	@Test
	public void addUnsupportedVectorThrowException() {

		Vector3DPolar unsupportedVector = new Vector3DPolar();

		// Note this test should fail after I add the support for Polar and
		// Cartesian addition.
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(unsupportedVector.getClass().getName()+" is not supported for vector operation.");
		vector3DCartesian.addition(unsupportedVector);

	}

	@Test
	public void addVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(2, 2, 2);
		MDVector actualResult = vector3DCartesian.addition(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(3, 3, 3);

		assertThat(actualResult, equalTo(expectedResult));
	}

	@Test
	public void subtractUnsupportedVectorThrowException() {

		Vector3DPolar unsupportedVector = new Vector3DPolar();

		// Note this test should fail after I add the support for Polar and
		// Cartesian addition.
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(unsupportedVector.getClass().getName()+" is not supported for vector operation.");
		vector3DCartesian.substraction(unsupportedVector);

	}

	@Test
	public void subtractVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(2, 2, 2);
		MDVector actualResult = vector3DCartesian.substraction(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(-1, -1, -1);

		assertThat(actualResult, equalTo(expectedResult));
	}
	

	@Test
	public void twoEqualVectorsHaveTheSameHashCode() {

		MDVector vector = new Vector3DCartesian(1, 1, 1);

		assertThat(vector.hashCode(), equalTo(vector3DCartesian.hashCode()));
	}
}
