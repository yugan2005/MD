package edu.MD.utility;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NumberFactoryTest {
	
	@Before
	public void init(){
		NumberFactory.setFactorySetting("JScienceRealFacotry", 32); 
	}

	@Test
	public void singletonFactoryTest(){
		NumberFactory jRealFactory1 = NumberFactory.getInstance();
		NumberFactory jRealFactory2 = NumberFactory.getInstance();
		assertTrue(jRealFactory1==jRealFactory2);
	}

}
