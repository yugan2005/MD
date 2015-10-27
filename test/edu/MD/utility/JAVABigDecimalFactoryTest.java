package edu.MD.utility;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JAVABigDecimalFactoryTest {
	NumberFactory JAVABigDecimalFactory;
	int percision;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		percision = 32;
		NumberFactory.setFactorySetting("JAVABigDecimalFactory", percision); 
		JAVABigDecimalFactory = NumberFactory.getInstance();
	}
	
	@Test
	public void getJScienceRealNumberWithCorrectPrecision(){
		MDNumber x = JAVABigDecimalFactory.valueOf(Math.PI);
		assertThat(x.getPrecision(), equalTo(percision));
		
	}

//	@Test
//	public void getJScienceRealNumberOfPrecision32() {
//		MDNumber x = JScienceRealFactory.valueOf(10864);
//		MDNumber y = JScienceRealFactory.valueOf(18817);;
//		MDNumber z = JScienceRealFactory.valueOf(9).times(x.pow(4).minus(y.pow(4)))) Math.pow(x, 4.0)- Math.pow(y, 4.0) + 2 * Math.pow(y, 2.0);
//        System.out.println("Result : " + z);
//
//		MDNumber JSReal2 = JScienceRealFactory.valueOf(2.0);
//		assertThat(JSReal2.getPrecision(), equalTo(32));
//	}

}
