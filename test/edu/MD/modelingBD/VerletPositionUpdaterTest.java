package edu.MD.modelingBD;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.PBCCalculator;
import edu.MD.utilityBD.MDPotentialConstants;
import edu.MD.utilityBD.Vector3DCartesian;

public class VerletPositionUpdaterTest {
	private VerletPositionUpdater positionUpdater;

	@BeforeClass
	public static void globalInit() {
		try {
			NumberFactory.getInstance();
		} catch (UnsupportedOperationException ex) {
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}

	}

	@Before
	public void init() {
		setSystemBoundary(5, 5, 5);
		positionUpdater = VerletPositionUpdater.getInstance("ARGON_1e-5");
	}

	@Test
	public void calculateNewPositionWithinBound() {
		MDVector oldPoistion = new Vector3DCartesian(1, 0, 0);
		MDVector oldVelocity = new Vector3DCartesian(1, 0, 1);
		MDVector forceVector = (new Vector3DCartesian(1, 2, 3)).times(MDConstants.getMass("ARGON"));
		MDVector newPosition = positionUpdater.calculate(oldPoistion, oldVelocity, forceVector);
		MDVector expectedNewPosition = new Vector3DCartesian((1 + 1e-5 + 0.5e-10), 1e-10, (1e-5 + 1.5e-10));
		assertTrue(newPosition.approximateEqual(expectedNewPosition));
	}

	@Test
	public void calculateNewPositionOutOfBound() {
		setSystemBoundary(MDPotentialConstants.getSigma("ARGON"), MDPotentialConstants.getSigma("ARGON").times(3),
				MDPotentialConstants.getSigma("ARGON").times(2));
		positionUpdater = VerletPositionUpdater.getInstance("ARGON_2");

		MDVector oldPoistion = (new Vector3DCartesian(0.9, 2.9, 0.9)).times(MDPotentialConstants.getSigma("ARGON"));
		MDVector oldVelocity = (new Vector3DCartesian(1, 0, 1)).times(MDPotentialConstants.getSigma("ARGON"));
		MDVector forceVector = (new Vector3DCartesian(1, 2, 3)).times(MDConstants.getMass("ARGON"))
				.times(MDPotentialConstants.getSigma("ARGON"));

		MDVector newPosition = positionUpdater.calculate(oldPoistion, oldVelocity, forceVector);
		MDVector expectedNewPosition = (new Vector3DCartesian(0.9, 0.9, 0.9))
				.times(MDPotentialConstants.getSigma("ARGON"));
		assertTrue(newPosition.approximateEqual(expectedNewPosition));
	}

	private void setSystemBoundary(double x, double y, double z) {
		MDVector systemBoundary = new Vector3DCartesian(x, y, z);
		PBCCalculator.setPBCCalculator(systemBoundary);
	}

	private void setSystemBoundary(MDNumber x, MDNumber y, MDNumber z) {
		MDVector systemBoundary = new Vector3DCartesian(x, y, z);
		PBCCalculator.setPBCCalculator(systemBoundary);
	}

}
