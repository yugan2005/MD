package edu.MD.utilityBD;

import edu.MD.numberBD.MDNumber;

public interface MDVector {
	public int getDimension();

	public MDNumber[] getCartesianComponent();

	public MDNumber getCartesianDistance(MDVector vector);

	public MDVector add(MDVector vector);

	public MDVector add(MDNumber c);

	public MDVector add(double c);

	public MDVector minus(MDVector vector);

	public MDVector minus(double c);

	@Override
	public boolean equals(Object obj);

	public boolean approximateEqual(MDVector vector);

	@Override
	public int hashCode();

	public MDNumber norm();

	public MDVector times(MDNumber c);

	public MDVector times(double c);

	public MDVector divide(MDNumber c);

	public MDVector divide(double c);

	public MDVector elementwiseTimes(MDVector vector);

	public MDVector elementwiseDivide(MDVector vector);

	public MDVector floor();

}
