package edu.MD.utility;

import org.jscience.mathematics.number.*;

public class JScienceReal implements MDNumbers {
	private Real num;
	private static int precision = 32;

	private JScienceReal(double in) {
		Real.setExactPrecision(precision);
		num = Real.valueOf(in);
	}

	private JScienceReal(int in) {
		Real.setExactPrecision(precision);
		num = Real.valueOf(in);
	}

	private JScienceReal(Real in) {
		num = in;
	}

	@Override
	public JScienceReal abs(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(((JScienceReal) in).num.abs());
	}


	@Override
	public JScienceReal sqrt(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(((JScienceReal) in).num.sqrt());
	}

	@Override
	public JScienceReal add(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(num.plus(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal minus(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(num.minus(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal times(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(num.times(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal divide(MDNumbers in) {
		checkinput(in);
		return new JScienceReal(num.divide(((JScienceReal) in).num));
	}

	@Override
	public JScienceReal pow(MDNumbers in) {
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
	
	public static JScienceReal getJScienceReal(double in){
		return new JScienceReal(in);
	}
	
	public static JScienceReal getJScienceReal(int in){
		return new JScienceReal(in);
	}
	

	private void checkinput(MDNumbers in) {
		if (!(in instanceof JScienceReal)) throw new IllegalArgumentException("The number type is not compatible!");
	}

	@Override
	public MDNumbers valueOf(int in) {
		return new JScienceReal(in);
	}

	@Override
	public MDNumbers valueOf(double in) {
		return new JScienceReal(in);
	}

}