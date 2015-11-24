package edu.MD.number;

import java.math.MathContext;
import java.math.RoundingMode;

public final class JavaBigDecimalFactory extends NumberFactory {
	private static final MathContext MC = new MathContext(precision, RoundingMode.HALF_EVEN);
	public static final JavaBigDecimalFactory INSTANCE=new JavaBigDecimalFactory();
	
	
	private JavaBigDecimalFactory() {
		JavaBigDecimal.setMathContext(MC);
	}

	@Override
	public MDNumber valueOf(double in) {
		return new JavaBigDecimal(in);
	}
	
}
