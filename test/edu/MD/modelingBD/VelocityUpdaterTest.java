package edu.MD.modelingBD;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.number.MDNumber;
import edu.MD.number.NumberFactory;
import edu.MD.utilityBD.Vector3DCartesian;
import edu.MD.utilityBD.Constants;
import edu.MD.utilityBD.MDVector;

public class VelocityUpdaterTest {
	private static VelocityUpdater velocityUpdater;
	private static NumberFactory numberFactory;
	private MDVector forceVector, oldVelocityVector;
	
	@BeforeClass
	public static void globalInit(){
		try {
			numberFactory = NumberFactory.getInstance();
		}
		catch (UnsupportedOperationException ex){
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
			numberFactory = NumberFactory.getInstance();
		}
		velocityUpdater = VelocityUpdater.getInstance("ARGON_1e-5");
	}
	
	@Before
	public void init(){
		MDNumber mass = Constants.getMass("ARGON");
		forceVector = new Vector3DCartesian(mass.times(2),mass.times(2), numberFactory.valueOf(0));
		oldVelocityVector = new Vector3DCartesian(0,0,0);
		
	}

	@Test
	public void calculateNewVelocityInNormalCase() {
		MDVector newVelocityVector = velocityUpdater.calculate(oldVelocityVector, forceVector);
		MDVector newVelocityExpected = new Vector3DCartesian(2e-5,2e-5,0);
		assertTrue(newVelocityVector.approximateEqual(newVelocityExpected));
	}
	
	@Test
	public void oppositeForceSlowVelocity() {
		oldVelocityVector = new Vector3DCartesian(-1,1,0);
		MDVector newVelocityVector = velocityUpdater.calculate(oldVelocityVector, forceVector);
		assertThat(oldVelocityVector.getCartesianComponent()[0].abs().toDouble(), greaterThan(newVelocityVector.getCartesianComponent()[0].abs().toDouble()));
		assertThat(oldVelocityVector.getCartesianComponent()[1].abs().toDouble(), lessThan(newVelocityVector.getCartesianComponent()[1].abs().toDouble()));
	}
	
	

}
