package edu.MD.utility;

public final class JScienceRealFactory extends NumberFactory {
	public static final JScienceRealFactory INSTANCE=new JScienceRealFactory();
	
	
	private JScienceRealFactory() {
	}

	@Override
	public MDNumber valueOf(double in) {
		return new JScienceReal(in, precision);
	}

	@Override
	public MDNumber valueOf(int in) {
		return new JScienceReal(in, precision);
	}
	
}
