package edu.MD.modeling;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.modeling.VelocityCalculator;
import edu.MD.number.MDVector;
import edu.MD.number.Vector3DCartesian;
import edu.MD.utility.MDConstants;

public class VelocityCalculatorTest {
	private static VelocityCalculator velocityCalculator;
	private MDVector forceVector, oldVelocityVector;

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
		velocityCalculator = VelocityCalculator.getInstance("ARGON_1e-5");
	}

	@Before
	public void init() {
		double mass = MDConstants.getMass("ARGON");
		forceVector = new Vector3DCartesian(2 * mass, 2 * mass, 0);
		oldVelocityVector = new Vector3DCartesian(0, 0, 0);

	}

	@Test
	public void calculateNewVelocityInNormalCase() {
		MDVector newVelocityVector = velocityCalculator.calculate(oldVelocityVector, forceVector);
		MDVector newVelocityExpected = new Vector3DCartesian(2e-5, 2e-5, 0);
		assertTrue(newVelocityVector.approximateEqual(newVelocityExpected));
	}

	@Test
	public void oppositeForceSlowVelocity() {
		oldVelocityVector = new Vector3DCartesian(-1, 1, 0);
		MDVector newVelocityVector = velocityCalculator.calculate(oldVelocityVector, forceVector);
		assertThat(oldVelocityVector.getCartesianComponent()[0].abs().toDouble(),
				greaterThan(newVelocityVector.getCartesianComponent()[0].abs().toDouble()));
		assertThat(oldVelocityVector.getCartesianComponent()[1].abs().toDouble(),
				lessThan(newVelocityVector.getCartesianComponent()[1].abs().toDouble()));
	}

}
