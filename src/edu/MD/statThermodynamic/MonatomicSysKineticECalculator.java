package edu.MD.statThermodynamic;

import edu.MD.modeling.Particle;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.number.NumberFactory;

public class MonatomicSysKineticECalculator {
	private static final NumberFactory numFactory = NumberFactory.getInstance();

	public static MDNumber calculate(Iterable<MDVector> velocities, MDNumber mass) {
		MDNumber kinecticE = numFactory.valueOf(0);

		for (MDVector velocity : velocities) {
			kinecticE = kinecticE.add(velocity.normSquare());
		}

		return kinecticE.times(mass).times(0.5);
	}

	public static MDNumber calculate(Iterable<Particle> particles) {
		Particle sampleParticle = particles.iterator().next();
		MDNumber mass = sampleParticle.getMass();

		MDNumber kinecticE = numFactory.valueOf(0);

		for (Particle particle : particles) {
			kinecticE = kinecticE.add(particle.getVelocity().normSquare());
		}

		return kinecticE.times(mass).times(0.5);
	}

}
