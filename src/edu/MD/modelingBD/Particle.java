package edu.MD.modelingBD;

import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDVector;

public abstract class Particle {

	public abstract MDVector getVelocity();
	public abstract MDVector getPosition();
	public abstract void setInitialPosition(MDVector initialPosition);
	public abstract void setInitialVelocity(MDVector initialVelocity);

	public abstract MDNumber getMass();
	public abstract int getDOF();

}
