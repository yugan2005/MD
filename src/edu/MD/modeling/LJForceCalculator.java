package edu.MD.modeling;

import edu.MD.utility.Vector;

public class LJForceCalculator implements IForceCalculator {

	public static final IForceCalculator INSTANCE = new LJForceCalculator();
	
	private LJForceCalculator(){}

	@Override
	public Vector calculate(IParticle p1, IParticle p2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
