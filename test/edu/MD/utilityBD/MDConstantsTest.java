package edu.MD.utilityBD;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;

public class MDConstantsTest {
	
	@BeforeClass
	public static void globalInit(){
		try {
			NumberFactory numFactory = NumberFactory.getInstance();
		}
		catch (UnsupportedOperationException ex){
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}
	}

	@Test
	public void obtainArgonDensityAtExactK() {
		MDNumber vaporDensity = MDConstants.getMolarDensity("ARGON", 83.806, "vapor");
		MDNumber liquidDensity = MDConstants.getMolarDensity("ARGON", 83.806, "liquid");
		assertTrue(vaporDensity.approximateEqual(101.5));
		assertTrue(liquidDensity.approximateEqual(35465));
	}
	
	@Test
	public void obtainArgonDensityWithInterpolation() {
		MDNumber vaporDensity = MDConstants.getMolarDensity("ARGON", 150, "vapor");
		MDNumber liquidDensity = MDConstants.getMolarDensity("ARGON", 150, "liquid");
		assertTrue(vaporDensity.approximateEqual(9873.23636363637));
		assertTrue(liquidDensity.approximateEqual(17035.6363636364));
	}


}
