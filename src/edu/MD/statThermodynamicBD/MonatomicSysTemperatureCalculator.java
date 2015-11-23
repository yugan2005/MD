package edu.MD.statThermodynamicBD;

import java.util.Collection;

import edu.MD.modelingBD.Particle;
import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;

public class MonatomicSysTemperatureCalculator {

	public static MDNumber calculate(Iterable<Particle> particles) {
		MDNumber kinecticE = MonatomicSysKineticECalculator.calculate(particles);

		Particle sampleParticle = particles.iterator().next();
		int DOF = sampleParticle.getDOF();

		int counter = iterableSize(particles);

		return kinecticE.times(2.0 / (counter * DOF * MDConstants.KB));
	}

	public static MDNumber calculate(Iterable<MDVector> velocities, String name) {
		MDNumber kinecticE = MonatomicSysKineticECalculator.calculate(velocities, MDConstants.getMass(name));
		int counter = iterableSize(velocities);
		int DOF = MDConstants.getDOF(name);

		return kinecticE.times(2.0 / (counter * DOF * MDConstants.KB));
	}

	private static int iterableSize(Iterable<?> iterable) {
		// This is the trick used by Iterables.size(Iterable) of Guava
		int counter = 0;
		if (iterable instanceof Collection<?>) {
			counter = ((Collection<?>) iterable).size();
		} else {
			for (@SuppressWarnings("unused")
			Object object : iterable)
				counter++;
		}
		return counter;
	}

}
