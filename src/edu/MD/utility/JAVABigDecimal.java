package edu.MD.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import org.nevec.rjm.BigDecimalMath;

public class JAVABigDecimal implements MDNumber {
	private static MathContext mc;
	private BigDecimal num;
	
	public static void setMathContext(MathContext mathContext) {
		mc = mathContext;
	}
	
	public JAVABigDecimal(double in) {
		num = new BigDecimal(in, mc);
	}

	public JAVABigDecimal(int in) {
		num = new BigDecimal(in, mc);
	}

	public JAVABigDecimal(BigDecimal in) {
		num = in;
	}

	@Override
	public JAVABigDecimal abs() {
		return new JAVABigDecimal(num.abs(mc));
	}

	@Override
	public JAVABigDecimal sqrt() {
		return new JAVABigDecimal(BigDecimalMath.sqrt(num, mc));
	}

	@Override
	public JAVABigDecimal add(MDNumber in) {
		checkinput(in);
		return new JAVABigDecimal(num.add(((JAVABigDecimal) in).num, mc));
	}

	@Override
	public JAVABigDecimal minus(MDNumber in) {
		checkinput(in);
		return new JAVABigDecimal(num.subtract(((JAVABigDecimal) in).num, mc));
	}

	@Override
	public JAVABigDecimal times(MDNumber in) {
		checkinput(in);
		return new JAVABigDecimal(num.multiply(((JAVABigDecimal) in).num, mc));
	}

	@Override
	public JAVABigDecimal divide(MDNumber in) {
		checkinput(in);
		return new JAVABigDecimal(num.divide(((JAVABigDecimal) in).num, mc));
	}

	@Override
	public JAVABigDecimal pow(MDNumber in) {
		checkinput(in);
		throw new UnsupportedOperationException("Only int power is support for JAVABigDecimal!");
	}

	@Override
	public JAVABigDecimal pow(int in) {
		return new JAVABigDecimal(num.pow(in, mc));
	}

	private void checkinput(MDNumber in) {
		if (!(in instanceof JAVABigDecimal))
			throw new IllegalArgumentException("The number type is not compatible!");
	}

	@Override
	public int getPrecision() {
		return num.precision();
	}
	
	@Override
	public String toString(){
		return num.toString();
	}
}
