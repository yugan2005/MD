package edu.MD.utilityBD;

import edu.MD.number.MDNumber;

public interface MDVector {
	public int getDimension();
	public MDNumber[] getCartesianComponent();
	public MDNumber getCartesianDistance(MDVector vector);
	public MDVector add(MDVector vector);
	public MDVector add(MDNumber c);
	public MDVector add(double c);
	public MDVector minus(MDVector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	public MDNumber norm();
	public MDVector times(MDNumber c);
	public MDVector elementwiseTimes(MDVector vector);
	public MDVector elementwiseDivide(MDVector vector);
	public MDVector floor();

}
