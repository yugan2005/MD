package edu.MD.modeling;

import edu.MD.utility.MDVector;

public class Argon extends Particle {
	public static final String TYPE = "LJParticle";
	public static final String NAME = "Argon";
	
	public Argon(MDVector position){
		this.position = position;
	}
	
	@Override
	String getType() {
		return TYPE;
	}
	@Override
	String getName() {
		return NAME;
	}

}
