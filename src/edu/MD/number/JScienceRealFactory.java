package edu.MD.number;

public final class JScienceRealFactory extends NumberFactory {
	public static final JScienceRealFactory INSTANCE=new JScienceRealFactory();
	
	
	private JScienceRealFactory() {
		JScienceReal.setPrecision(precision);
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
