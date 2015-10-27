package edu.MD.utility;

import java.math.MathContext;
import java.math.RoundingMode;

public final class JAVABigDecimalFactory extends NumberFactory {
	private static final MathContext MC = new MathContext(precision, RoundingMode.HALF_EVEN);
	public static final JAVABigDecimalFactory INSTANCE=new JAVABigDecimalFactory();
	
	
	private JAVABigDecimalFactory() {
		JAVABigDecimal.setMathContext(MC);
	}

	@Override
	public MDNumber valueOf(double in) {
		return new JAVABigDecimal(in);
	}

	@Override
	public MDNumber valueOf(int in) {
		return new JAVABigDecimal(in);
	}
	
}
