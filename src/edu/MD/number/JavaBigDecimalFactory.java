package edu.MD.number;

import java.math.MathContext;
import java.math.RoundingMode;

public final class JavaBigDecimalFactory extends NumberFactory {
	public static JavaBigDecimalFactory instance;
	private static MathContext mc;

	@Override
	public MDNumber valueOf(double in) {
		return new JavaBigDecimal(in, getMC());
	}

	public static MathContext getMC() {
		return mc;
	}

	public static JavaBigDecimalFactory getInstance() {
		if (instance == null) {
			mc = new MathContext(precision, RoundingMode.HALF_EVEN);
			instance = new JavaBigDecimalFactory();
			return instance;
		}

		if (mc.getPrecision() != precision)
			throw new UnsupportedOperationException(
					"The current setting of JavaBigDecimalFactory has a different precision setting than requested. Need destroy the current factory before you can get the one that you need");

		return instance;
	}

	public static void destroyInstance() {
		if (instance == null) {
			throw new UnsupportedOperationException("The instance has not been initialized yet, noting to destroy");
		}
		
		instance = null;
		mc = null;
	}

}
