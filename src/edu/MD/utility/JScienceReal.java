package edu.MD.utility;

import org.jscience.mathematics.number.*;

public class JScienceReal implements MDNumber {
	private Real num;

	public JScienceReal(double in, int precision) {
		Real.setExactPrecision(precision);
		num = Real.valueOf(in);
	}

	public JScienceReal(int in, int precision) {
		Real.setExactPrecision(precision);
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
		if (!(in instanceof JScienceReal))
			throw new IllegalArgumentException("The number type is not compatible!");
	}

	@Override
	public int getPrecision() {
		return num.getPrecision();
	}
}