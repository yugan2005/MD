package edu.MD.modeling;

import edu.MD.utility.MDVector;

public interface IPairWiseForceCalculator {

	/**
	 * @param p1
	 *            Particle 1.
	 * @param p2
	 *            Particle 2.
	 * @param system
	 *            from which both particles come from.
	 * @return Force vector of particle p1. This is the force acting on p1 and
	 *         is exerted by p2
	 */
	MDVector calculate(Particle p1, Particle p2, ISystem system);

}
