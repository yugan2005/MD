package edu.MD.modeling;

public class InteractorFactory {
	
	public static IInteractor getInteractor(IParticleSystem particleSystem){
		return getConcreteInteractor((PairwiseSystem) particleSystem);
	}

	private static IInteractor getConcreteInteractor(PairwiseSystem particleSystem) {
		return null;
	}

}
