package edu.MD.modeling;

public class LJForceInteraction extends PairwiseForceInteraction {

	@Override
	protected Iterable<IParticle> findInteractingParticles(IParticle particle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Iterable<IParticle> findIncludedParticles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IPairwiseForceCalculator getForceCalculator() {
		IPairwiseForceCalculator ljForceCalculator = LJForceCalculator.getInstance(); 
		return ljForceCalculator;
	}

}
