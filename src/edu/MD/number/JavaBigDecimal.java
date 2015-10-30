package edu.MD.number;

import java.math.BigDecimal;
import java.math.MathContext;
import org.nevec.rjm.BigDecimalMath;

public class JavaBigDecimal implements MDNumber {
	private static MathContext mc;
	private BigDecimal num;
	public static final JavaBigDecimal ONE = new JavaBigDecimal(BigDecimal.ONE);
	public static final JavaBigDecimal ZERO = new JavaBigDecimal(BigDecimal.ZERO);
	

	public static void setMathContext(MathContext mathContext) {
		mc = mathContext;
	}

	public JavaBigDecimal(double in) {
		num = new BigDecimal(in, mc);
	}

	public JavaBigDecimal(int in) {
		num = new BigDecimal(in, mc);
	}

	public JavaBigDecimal(BigDecimal in) {
		num = in;
	}

	@Override
	public JavaBigDecimal abs() {
		return new JavaBigDecimal(num.abs(mc));
	}

	@Override
	public JavaBigDecimal sqrt() {
		return new JavaBigDecimal(BigDecimalMath.sqrt(num, mc));
	}

	@Override
	public JavaBigDecimal add(MDNumber in) {
		checkinput(in);
		return new JavaBigDecimal(num.add(((JavaBigDecimal) in).num, mc));
	}

	@Override
	public JavaBigDecimal minus(MDNumber in) {
		checkinput(in);
		return new JavaBigDecimal(num.subtract(((JavaBigDecimal) in).num, mc));
	}

	@Override
	public JavaBigDecimal times(MDNumber in) {
		checkinput(in);
		return new JavaBigDecimal(num.multiply(((JavaBigDecimal) in).num, mc));
	}

	@Override
	public JavaBigDecimal divide(MDNumber in) {
		checkinput(in);
		return new JavaBigDecimal(num.divide(((JavaBigDecimal) in).num, mc));
	}

	@Override
	public JavaBigDecimal pow(MDNumber in) {
		checkinput(in);
		throw new UnsupportedOperationException("Only int power is support for JAVABigDecimal!");
	}

	@Override
	public JavaBigDecimal pow(int in) {
		return new JavaBigDecimal(num.pow(in, mc));
	}

	private void checkinput(MDNumber in) {
		if (!(in instanceof JavaBigDecimal))
			throw new IllegalArgumentException("The number type is not compatible!");
	}

	@Override
	public int getPrecision() {
		return num.precision();
	}

	@Override
	public String toString() {
		return num.toString();
	}

	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (this==that) return true;
		if (!(that instanceof JavaBigDecimal)) return false;
		JavaBigDecimal input = (JavaBigDecimal) that;
		
		return this.num.equals(input.num);
	}

	@Override
	public double toDouble() {
		return num.doubleValue();
	}

	@Override
	public int toInt() {
		return num.intValue();
	}

	@Override
	public MDNumber zero() {
		return ZERO;
	}

	@Override
	public MDNumber one() {
		return ONE;
	}
}
