package edu.MD.number;

import edu.MD.utilityBD.Constants;

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

	/**
	 * Use Constants.RELATIVE_DOUBLE_ERROR to decide whether they are equal
	 */
	public default boolean approximateEqual(double in) {
		if (in == 0)
			return Math.abs(this.toDouble()) < Constants.MACHINE_DOUBLE_ERROR;
		return Math.abs(this.divide(in).minus(1).toDouble()) < Constants.RELATIVE_DOUBLE_ERROR;
	}

	/**
	 * Use Constants.RELATIVE_DOUBLE_ERROR to decide whether they are equal
	 */
	public default boolean approximateEqual(MDNumber in) {
		if (in.toDouble() == 0)
			return Math.abs(this.toDouble()) < Constants.MACHINE_DOUBLE_ERROR;
		return Math.abs(this.divide(in).minus(1).toDouble()) < Constants.RELATIVE_DOUBLE_ERROR;

	}

	public MDNumber floor();
}
