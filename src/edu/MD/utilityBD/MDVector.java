package edu.MD.utilityBD;

import org.apfloat.Apfloat;

public interface MDVector {
	public int getDimension();
	public Apfloat[] getCartesianComponent();
	public Apfloat getCartesianDistance(MDVector vector);
	public MDVector addition(MDVector vector);
	public MDVector substraction(MDVector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	public Apfloat norm();
	public MDVector multiply(double c);

}
