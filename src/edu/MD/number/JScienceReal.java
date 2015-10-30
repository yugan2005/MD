package edu.MD.number;

import org.jscience.mathematics.number.*;

import edu.MD.utility.Constants;

public class JScienceReal implements MDNumber {
	private Real num;
	public static final JScienceReal ONE = new JScienceReal(Real.ONE);
	public static final JScienceReal ZERO = new JScienceReal(Real.ZERO);
	

	public JScienceReal(double in) {
		num = Real.valueOf(in);
	}

	public JScienceReal(Real in) {
		num = in;
	}

	@Override
	public JScienceReal abs() {
		return new JScienceReal(num.abs());
	}

	@Override
	public JScienceReal sqrt() {
		return new JScienceReal(num.sqrt());
	}

	@Override
	public JScienceReal add(MDNumber in) {
		checkinput(in);
		return new JScienceReal(num.plus(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal minus(MDNumber in) {
		checkinput(in);
		return new JScienceReal(num.minus(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal times(MDNumber in) {
		checkinput(in);
		return new JScienceReal(num.times(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal divide(MDNumber in) {
		checkinput(in);
		return new JScienceReal(num.divide(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal pow(MDNumber in) {
		checkinput(in);
		throw new UnsupportedOperationException("Only int power is support for JScienceReal!");
	}

	@Override
	public JScienceReal pow(int in) {
		if (in == 0 && num.equals(Real.ZERO))
			return new JScienceReal(Real.NaN);
		else {
			if (in == 0)
				return new JScienceReal(Real.ONE);
			if (num.equals(Real.valueOf(0.0)))
				return new JScienceReal(Real.ZERO);
		}

		Real base = num;

		if (in < 0) {
			base = num.inverse();
			in = -in;
		}

		Real result = Real.ONE;

		while (in != 0) {

			if ((in & 1) != 0)
				result = result.times(base);
			in >>= 1;
			base = base.times(base);
		}

		return new JScienceReal(result);
	}

	private void checkinput(MDNumber in) {
		if (in == null) throw new IllegalArgumentException("The input cannot be null");
		if (!(in instanceof JScienceReal))
			throw new IllegalArgumentException("The number type is not compatible!");
	}

	@Override
	public int getPrecision() {
		return num.getPrecision();
	}

	public static void setPrecision(int precision) {
		Real.setExactPrecision(precision);
	}
	
	@Override
	public String toString(){
		return num.toString();
	}
	
	@Override
	public int hashCode(){
		return num.hashCode();
	}

	@Override
	public boolean equals(Object that) {
		if (that==null) return false;
		if (this==that) return true;
		if (!(that instanceof JScienceReal)) return false;
		JScienceReal input = (JScienceReal) that;
		
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

}