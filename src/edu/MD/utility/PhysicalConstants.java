package edu.MD.utility;

public enum PhysicalConstants {

	PLANCKS("plancks", 6.62606957e-34, "J*s");

	private final String name;
	private final double value;
	private final String unit;

	private PhysicalConstants(String name, double value, String unit) {
		this.value = value;
		this.name = name;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

}
