package edu.MD.modeling;

public interface IFinder {

	/**
	 * @return Iterable<IParticle>. This returns the particles that need be
	 *         considered for interaction calculation
	 */
	public Iterable<IParticle> findParticles();

	/**
	 * @param IParticle
	 *            particle. This is the particle currently calculating the
	 *            interactions
	 * @return Iterable<IParticle>. This is all the other particles that
	 *         interacting with the current particle
	 */
	public Iterable<IParticle> findInteractingParticles(IParticle particle);

}
