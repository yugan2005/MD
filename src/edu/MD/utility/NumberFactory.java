package edu.MD.utility;

import java.util.HashMap;

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
	private static HashMap<String, NumberFactory> registry = new HashMap<>();
	protected static int PRECISION = -1;

	protected NumberFactory() {
	}

	public static synchronized NumberFactory getInstance(String numType) {
		if (numType == null)
			throw new IllegalArgumentException("Need specify the number type name to get numFactory");
		if (PRECISION == -1)
			throw new IllegalArgumentException(
					"The global precision parameter has not been set yet. setPrecision() first");
		NumberFactory numFactory = registry.get(numType);
		if (numFactory != null)
			return numFactory; // Return the singleton of correct Type

		try {
			// using Reflection
			numFactory = (NumberFactory) Class.forName(numType).newInstance();
		} catch (ClassNotFoundException cnfe) {
			throw new IllegalArgumentException("The type of number specified by numType has not been defined yet");
		} catch (InstantiationException ie) {
			throw new IllegalArgumentException("Can't instantiate the class specified by numType");
		} catch (IllegalAccessException iae) {
			throw new IllegalArgumentException("Can't access the class specified by numType");
		}

		registry.put(numType, numFactory);
		return numFactory;
	}

	public static void setPrecision(int precision) {
		if (PRECISION != -1 && PRECISION != precision)
			throw new IllegalArgumentException("The precision has already been set as: " + PRECISION
					+ " , which is not consistent with the precision requested by this call");
		PRECISION = precision;
	}

	public abstract MDNumber valueOf(double in);

	public abstract MDNumber valueOf(int in);
}
