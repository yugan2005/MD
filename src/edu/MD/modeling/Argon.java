package edu.MD.modeling;

import edu.MD.number.MDNumber;
import edu.MD.utility.MDConstants;
import edu.MD.utility.MDVector;

public class Argon extends Particle {
	private static final String NAME = "ARGON";
	private static final MDNumber MASS = MDConstants.getMass(NAME);
	private static final int DOF = MDConstants.getDOF(NAME);;
	private MDVector position;
	private MDVector velocity;

	@Override
	public MDVector getVelocity() {
		if (velocity==null) throw new UnsupportedOperationException("The particle has not been initialized velocity yet");
		
		return velocity;
	}

	@Override
	public MDVector getPosition() {
		if (position==null) throw new UnsupportedOperationException("The particle has not been initialized position yet");

		return position;
	}
	
	@Override
	public MDNumber getMass() {
		return MASS;
	}


	@Override
	public void setInitialPosition(MDVector initialPosition) {
		this.position=initialPosition;
	}

	@Override
	public void setInitialVelocity(MDVector initialVelocity) {
		this.velocity = initialVelocity;
	}

	@Override
	public int getDOF() {
		return DOF;
	}

}
