package edu.MD.number;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.MD.number.NumberFactory;

public class NumberFactoryTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void globalInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try{
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}
		catch (Exception ex){
			NumberFactory.destroyInstance();
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}
	}

	@Test
	public void singletonFactoryTest(){
		NumberFactory numFactory1 = NumberFactory.getInstance();
		NumberFactory numFactory2 = NumberFactory.getInstance();
		assertTrue(numFactory1==numFactory2);
	}
	
	@Test
	public void setFactoryTwiceThrowException(){
		exception.expect(UnsupportedOperationException.class);
		NumberFactory.setFactorySetting("JavaDefaultNumberFactory"); 
	}
	
	@Test
	public void destroyInstanceThenReset() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		try{
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}
		catch (Exception ex){
			NumberFactory.destroyInstance();
			NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		}
		NumberFactory.destroyInstance();
		NumberFactory.setFactorySetting("JavaDefaultNumberFactory");


	}

}
