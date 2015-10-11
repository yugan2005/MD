<<<<<<< HEAD
package edu.MD.modeling;

public class InteractorFactory {
	
	public static IInteractor getInteractor(IParticleSystem particleSystem){
		return getConcreteInteractor((PairwiseSystem) particleSystem);
	}

	private static IInteractor getConcreteInteractor(PairwiseSystem particleSystem) {
		return null;
	}

}
=======
package edu.MD.modeling;

public class InteractorFactory {
	
	public static IInteractor getInteractor(IParticleSystem particleSystem){
		return getConcreteInteractor((PairwiseSystem) particleSystem);
	}

	private static IInteractor getConcreteInteractor(PairwiseSystem particleSystem) {
		return null;
	}

}
>>>>>>> f502b2ea9809189fba6739402c4694c0698d187d
