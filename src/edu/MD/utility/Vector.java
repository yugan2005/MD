package edu.MD.utility;

public interface Vector {
	public int getDimension();
	public double[] getCartesianComponent();
	public double getCartesianDistance(Vector vector);
	public Vector addition(Vector vector);
	public Vector substraction(Vector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();

}
