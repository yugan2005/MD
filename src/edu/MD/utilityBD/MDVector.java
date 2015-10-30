package edu.MD.utilityBD;

import edu.MD.number.MDNumber;

public interface MDVector {
	public int getDimension();
	public MDNumber[] getCartesianComponent();
	public MDNumber getCartesianDistance(MDVector vector);
	public MDVector add(MDVector vector);
	public MDVector minus(MDVector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	public MDNumber norm();
	public MDVector times(MDNumber c);

}
