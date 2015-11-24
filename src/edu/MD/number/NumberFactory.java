package edu.MD.number;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a example of threadsafe singleton factory class that I looked for.
 * The reference is <a href=
 * "http://www.javaworld.com/article/2073352/core-java/simply-singleton.html?page=2">
 * here</a>.
 * <p>
 * Note that I also modified the design a little bit to add the requirement of
 * set PRECISION once for all
 * 
 * @author yug
 *
 */
public abstract class NumberFactory {
	private static final List<String> NUMBER_TYPE = new ArrayList<String>(
			Arrays.asList("JavaBigDecimalFactory", "JavaDefaultNumberFactory"));
	private static NumberFactory numberFactory;
	protected static int precision;
	private static final MathContext MC = new MathContext(precision, RoundingMode.HALF_EVEN);


	protected NumberFactory() {
	}

	/**
	 * No argument version to get the singleton instance
	 * 
	 * @return the singleton factory
	 */
	public static synchronized NumberFactory getInstance() {
		if (numberFactory != null)
			return numberFactory;
		else
			throw new UnsupportedOperationException(
					"The number facotry has not been initialized yet, use setFactorySetting(String numType, int precision) method first!");
	}

	/**
	 * Initialize the NumberFactory singleton</n>
	 * 
	 * @param numType
	 *            String - Type of the numberfactory
	 * @param setPrecision
	 *            int - Number of precision digits. As a reference, double has
	 *            precision of 16 digits
	 */
	public static synchronized void setFactorySetting(String numType, int setPrecision) {
		if (numberFactory != null)
			throw new UnsupportedOperationException(
					"The number facotry has been set and cannot be set again, use getInstance() to get the singleton instance");
		precision = setPrecision;
		if (!NUMBER_TYPE.contains(numType))
			throw new IllegalArgumentException(
					"The type of number specified by numType has not been defined or added to the registry yet");
		switch (numType) {
		case "JavaBigDecimalFactory":
			numberFactory = JavaBigDecimalFactory.INSTANCE;
			break;
		case "JavaDefaultNumberFactory":
			throw new IllegalArgumentException("JavaDefaultNumberFactory should be set without precision");
		}
	}

	public static synchronized void setFactorySetting(String numType) {
		if (numberFactory != null)
			throw new UnsupportedOperationException(
					"The number facotry has been set and cannot be set again, use getInstance() to get the singleton instance");
		if (!(numType.equals("JavaDefaultNumberFactory")))
			throw new IllegalArgumentException(
					"The type of number specified by numType need specify the precision too. Only JavaDefaultNumberFactory can be set without precision.");

		numberFactory = JavaDefaultNumberFactory.INSTANCE;
	}

	public static String getFactorySetting() {
		if (numberFactory == null)
			return "The number factory has not been initialized yet";
		String currentSetting = "Number factory Type is: " + numberFactory.getClass().getName() + "/n";
		if (!(numberFactory instanceof JavaDefaultNumberFactory))
			currentSetting += "precision is: " + precision + "/n";
		return currentSetting;
	}

	public static MathContext getMC(){
		return MC;
	}
	
	
	public abstract MDNumber valueOf(double in);
}
