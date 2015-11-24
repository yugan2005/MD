package edu.MD.statThermodynamic;

import java.util.Collection;

import edu.MD.modeling.Particle;
import edu.MD.number.MDNumber;
import edu.MD.utility.MDConstants;
import edu.MD.utility.MDVector;

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
