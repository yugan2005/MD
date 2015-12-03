package edu.MD.modeling;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.globalSetting.PBCBoundarySetting;
import edu.MD.modeling.PositionCalculator;
import edu.MD.number.MDVector;
import edu.MD.number.Vector3DCartesian;
import edu.MD.utility.MDConstants;
import edu.MD.utility.MDPotentialConstants;

public class PositionCalculatorTest {
	private PositionCalculator positionCalculator;

	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();
	}

	@Before
	public void init() {
		setSystemBoundary(5, 5, 5);
		positionCalculator = PositionCalculator.getInstance("ARGON_1e-5");
	}

	@Test
	public void calculateNewPositionWithinBound() {
		MDVector oldPoistion = new Vector3DCartesian(1, 0, 0);
		MDVector oldVelocity = new Vector3DCartesian(1, 0, 1);
		MDVector forceVector = (new Vector3DCartesian(1, 2, 3)).times(MDConstants.getMass("ARGON"));
		MDVector newPosition = positionCalculator.calculate(oldPoistion, oldVelocity, forceVector);
		MDVector expectedNewPosition = new Vector3DCartesian((1 + 1e-5 + 0.5e-10), 1e-10, (1e-5 + 1.5e-10));
		assertTrue(newPosition.approximateEqual(expectedNewPosition));
	}

	@Test
	public void calculateNewPositionOutOfBound() {
		double sigma = MDPotentialConstants.getSigma("ARGON");
		setSystemBoundary(sigma, 3 * sigma, 2 * sigma);
		positionCalculator = PositionCalculator.getInstance("ARGON_2");

		MDVector oldPoistion = (new Vector3DCartesian(0.9, 2.9, 0.9)).times(MDPotentialConstants.getSigma("ARGON"));
		MDVector oldVelocity = (new Vector3DCartesian(1, 0, 1)).times(MDPotentialConstants.getSigma("ARGON"));
		MDVector forceVector = (new Vector3DCartesian(1, 2, 3)).times(MDConstants.getMass("ARGON"))
				.times(MDPotentialConstants.getSigma("ARGON"));

		MDVector newPosition = positionCalculator.calculate(oldPoistion, oldVelocity, forceVector);
		MDVector expectedNewPosition = (new Vector3DCartesian(0.9, 0.9, 0.9))
				.times(MDPotentialConstants.getSigma("ARGON"));
		assertTrue(newPosition.approximateEqual(expectedNewPosition));
	}

	private void setSystemBoundary(double x, double y, double z) {
		MDVector systemBoundary = new Vector3DCartesian(x, y, z);
		PBCBoundarySetting.set(systemBoundary);
	}

}
