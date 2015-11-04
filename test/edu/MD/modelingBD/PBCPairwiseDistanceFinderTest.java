package edu.MD.modelingBD;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.number.NumberFactory;

public class PBCPairwiseDistanceFinderTest {
	
	private static NumberFactory numberFactory;
	private static PBCPairwiseDistanceFinder distanceFinder;

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

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
