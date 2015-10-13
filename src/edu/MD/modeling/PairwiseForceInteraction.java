package edu.MD.modeling;

import edu.MD.utility.Utility;

public abstract class PairwiseForceInteraction implements IInteraction {

	@Override
	public IUpdater interaction(ISystem particleSystem) {

		IPairwiseForceCalculator forceCalculator = getForceCalculator();
		Iterable<IParticle> includedParticles = findIncludedParticles();

		IUpdater updater = new forceUpdater(includedParticles);

		for (IParticle particle : includedParticles) {
			Iterable<IParticle> interactingParticles = findInteractingParticles(particle);
			double[] force = new double[3];
			for (IParticle interactingParticle : interactingParticles) {
				force = Utility.forceCombiner(force, forceCalculator.calculate(particle, interactingParticle));
			}
			updater.add(particle, force);
		}
		return updater;
	}

	protected abstract Iterable<IParticle> findInteractingParticles(IParticle particle);

	protected abstract Iterable<IParticle> findIncludedParticles();

	protected abstract IPairwiseForceCalculator getForceCalculator();

}
