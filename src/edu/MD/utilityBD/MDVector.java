package edu.MD.utilityBD;

import java.math.BigDecimal;

public interface MDVector {
	public int getDimension();
	public BigDecimal[] getCartesianComponent();
	public BigDecimal getCartesianDistance(MDVector vector);
	public MDVector addition(MDVector vector);
	public MDVector substraction(MDVector vector);
	
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	public BigDecimal norm();
	public MDVector multiply(double c);

}
