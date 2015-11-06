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
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		return new JavaBigDecimal(num.add(javaBigDecimalIn.num, mc));
	}

	@Override
	public JavaBigDecimal minus(MDNumber in) {
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		return new JavaBigDecimal(num.subtract(javaBigDecimalIn.num, mc));
	}

	@Override
	public JavaBigDecimal times(MDNumber in) {
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		return new JavaBigDecimal(num.multiply(javaBigDecimalIn.num, mc));
	}

	@Override
	public JavaBigDecimal divide(MDNumber in) {
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		return new JavaBigDecimal(num.divide(javaBigDecimalIn.num, mc));
	}

	@Override
	public JavaBigDecimal pow(MDNumber in) {
		throw new UnsupportedOperationException("Only int power is support for JAVABigDecimal!");
	}

	@Override
	public JavaBigDecimal pow(int in) {
		return new JavaBigDecimal(num.pow(in, mc));
	}

	private JavaBigDecimal checkinput(MDNumber in) {
		if (!(in instanceof JavaBigDecimal))
			throw new IllegalArgumentException("The number type is not compatible!");
		return (JavaBigDecimal) in;
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
	public int hashCode() {
		return num.hashCode();
	}

	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (this == that)
			return true;
		if (!(that instanceof JavaBigDecimal))
			return false;
		JavaBigDecimal input = (JavaBigDecimal) that;
		return this.num.equals(input.num);
	}

	@Override
	public double toDouble() {
		return num.doubleValue();
	}

	@Override
	public MDNumber zero() {
		return ZERO;
	}

	@Override
	public MDNumber one() {
		return ONE;
	}

	@Override
	public MDNumber add(double in) {
		return new JavaBigDecimal(num.add(new BigDecimal(in, mc), mc));
	}

	@Override
	public MDNumber minus(double in) {
		return new JavaBigDecimal(num.subtract(new BigDecimal(in, mc), mc));
	}

	@Override
	public MDNumber times(double in) {
		return new JavaBigDecimal(num.multiply(new BigDecimal(in, mc), mc));
	}

	@Override
	public MDNumber divide(double in) {
		return new JavaBigDecimal(num.divide(new BigDecimal(in, mc), mc));
	}

	@Override
	public MDNumber floor() {
		return new JavaBigDecimal(Math.floor(this.toDouble()));
	}

	@Override
	public int compareTo(MDNumber in) {
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		return this.num.compareTo(javaBigDecimalIn.num);
	}

	@Override
	public MDNumber mod(MDNumber in) {
		JavaBigDecimal javaBigDecimalIn = checkinput(in);
		BigDecimal thisNum = this.num;
		BigDecimal inNum = javaBigDecimalIn.num;
		BigDecimal result = thisNum.remainder(inNum, mc);
		if (result.compareTo(BigDecimal.ZERO) < 0)
			result = result.add(inNum, mc);
		return new JavaBigDecimal(result);
	}
}
