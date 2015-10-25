package edu.MD.utility;

import org.jscience.mathematics.number.Real;

public class JScienceRealFactory extends NumberFactory {
	
	static {
		Real.setExactPrecision(PRECISION);
	}

	@Override
	public MDNumber valueOf(double in) {
		return new JScienceReal(in);
	}

	@Override
	public MDNumber valueOf(int in) {
		return new JScienceReal(in);
	}

}
