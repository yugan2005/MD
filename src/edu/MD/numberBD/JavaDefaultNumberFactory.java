package edu.MD.numberBD;

public final class JavaDefaultNumberFactory extends NumberFactory {
	public static final JavaDefaultNumberFactory INSTANCE=new JavaDefaultNumberFactory();
	
	
	private JavaDefaultNumberFactory() {
	}

	@Override
	public MDNumber valueOf(double in) {
		return new JavaDefaultNumber(in);
	}
	
}
