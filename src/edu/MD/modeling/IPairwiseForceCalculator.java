package edu.MD.modeling;

public interface IPairwiseForceCalculator {

	public double[] calculate(IParticle particle, IParticle interactingParticle);

}
