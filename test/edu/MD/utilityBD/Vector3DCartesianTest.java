package edu.MD.utilityBD;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.numberBD.NumberFactory;
import edu.MD.utilityBD.Vector3DPolar;

public class Vector3DCartesianTest {
	private Vector3DCartesian vector3DCartesian;
	private static NumberFactory numberFactory;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void globalInit(){
		try {
			numberFactory = NumberFactory.getInstance();
		}
		catch (UnsupportedOperationException ex){
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
			numberFactory = NumberFactory.getInstance();
		}
	}

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
		vector3DCartesian.add(unsupportedVector);

	}

	@Test
	public void addVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(numberFactory.valueOf(2), numberFactory.valueOf(2), numberFactory.valueOf(2));
		MDVector actualResult = vector3DCartesian.add(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(numberFactory.valueOf(3), numberFactory.valueOf(3), numberFactory.valueOf(3));

		assertThat(actualResult, equalTo(expectedResult));
	}

	@Test
	public void subtractUnsupportedVectorThrowException() {

		Vector3DPolar unsupportedVector = new Vector3DPolar();

		// Note this test should fail after I add the support for Polar and
		// Cartesian addition.
		exception.expect(IllegalArgumentException.class);
		vector3DCartesian.minus(unsupportedVector);

	}

	@Test
	public void subtractVectorWithCorrectDeclaredClass() {

		MDVector vectorWithCorrectDeclaredClass = new Vector3DCartesian(numberFactory.valueOf(2), numberFactory.valueOf(2), numberFactory.valueOf(2));
		MDVector actualResult = vector3DCartesian.minus(vectorWithCorrectDeclaredClass);

		MDVector expectedResult = new Vector3DCartesian(numberFactory.valueOf(-1), numberFactory.valueOf(-1), numberFactory.valueOf(-1));

		assertThat(actualResult, equalTo(expectedResult));
	}
	

	@Test
	public void twoEqualVectorsHaveTheSameHashCode() {

		MDVector vector = new Vector3DCartesian(numberFactory.valueOf(1), numberFactory.valueOf(1), numberFactory.valueOf(1));

		assertThat(vector.hashCode(), equalTo(vector3DCartesian.hashCode()));
	}
}
