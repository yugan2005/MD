package edu.MD.utility;

public interface MDVector {
	public int getDimension();
	public double[] getCartesianComponent();
	public double getCartesianDistance(MDVector vector);
	public MDVector add(MDVector vector);
	public MDVector minus(MDVector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	public double norm();
	public MDVector times(double c);

}
