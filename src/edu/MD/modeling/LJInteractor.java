package edu.MD.modeling;

public class LJInteractor implements IInteractor {
	private IParticleFinder particleFinder;
	private IDistanceFinder distanceFinder;
	private IForceCalculator forceCalculator;
	public static final LJInteractor INSTANCE = new LJInteractor();
	
	private LJInteractor() {
		distanceFinder = PBCDistanceFinder.INSTANCE;
		particleFinder = CutoffPairwiseParticleFinder.INSTANCE;
		forceCalculator = LJForceCalculator.INSTANCE;
	} 

	@Override
	public IUpdater interaction(ISystem system) {
		// TODO the template for calculate the LJ interactions
		return null;
	}

}
