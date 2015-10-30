package edu.MD.number;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.number.NumberFactory;

public class NumberFactoryTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void init(){
		NumberFactory.setFactorySetting("JScienceRealFacotry", 32); 
	}

	@Test
	public void singletonFactoryTest(){
		NumberFactory jRealFactory1 = NumberFactory.getInstance();
		NumberFactory jRealFactory2 = NumberFactory.getInstance();
		assertTrue(jRealFactory1==jRealFactory2);
	}
	
	@Test
	public void setFactoryTwiceThrowException(){
		exception.expect(UnsupportedOperationException.class);
		NumberFactory.setFactorySetting("JavaDefaultNumberFactory", 32); 
	}

}
