package edu.MD.modeling;

public class LJForceCalculator implements IPairwiseForceCalculator {
	private static final LJForceCalculator ljForceCalculator = new LJForceCalculator();
	
	private LJForceCalculator() {
	}
	
	public static LJForceCalculator getInstance(){
		return ljForceCalculator;
	}

	@Override
	public double[] calculate(IParticle particle, IParticle interactingParticle) {
		
		return null;
	}

}
