package edu.MD.modeling;

public abstract class PairwiseInteraction implements IInteraction {
	
	public double[] interaction(IParticle particle1, IParticleSystem system){
		return null;
	}
	
	protected abstract double[] interaction(IParticle particle1, IParticle particle2);  

}
