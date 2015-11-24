package edu.MD.number;

public final class JavaBigDecimalFactory extends NumberFactory {
	public static final JavaBigDecimalFactory INSTANCE=new JavaBigDecimalFactory();

	@Override
	public MDNumber valueOf(double in) {
		return new JavaBigDecimal(in, getMC());
	}
	
}
