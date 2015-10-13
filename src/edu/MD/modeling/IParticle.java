package edu.MD.modeling;

public interface IParticle {

	public double[] getCMPosition();

	//TODO bad design decision. Need change
	public ISystem getSystem();

}
