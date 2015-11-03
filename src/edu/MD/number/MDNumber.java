package edu.MD.number;

import edu.MD.utility.Constants;

public interface MDNumber {

	public MDNumber abs();

	public MDNumber sqrt();

	public MDNumber add(MDNumber in);

	public MDNumber add(double in);

	public MDNumber minus(MDNumber in);

	public MDNumber minus(double in);

	public MDNumber times(MDNumber in);

	public MDNumber times(double in);

	public MDNumber divide(MDNumber in);

	public MDNumber divide(double in);

	public MDNumber pow(MDNumber in);

	public MDNumber pow(int in);

	public MDNumber zero();

	public MDNumber one();

	public int getPrecision();

	@Override
	public String toString();

	@Override
	public boolean equals(Object that);

	@Override
	public int hashCode();

	public double toDouble();

	public default boolean approximateEqual(MDNumber that) {
		return Math.abs(this.toDouble() - that.toDouble()) < Constants.MACHINE_DOUBLE_ERROR;
	}
}
