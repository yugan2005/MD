package edu.MD.utility;

public class Utility {

	public static double[] forceCombiner(double[] f1, double[] f2) {
		if (f1.length != f2.length)
			throw new IllegalArgumentException("The two force dimension must be the same!");
		double[] resultantForce = new double[f1.length];
		for (int i = 0; i < f1.length; i++) {
			resultantForce[i] = f1[i] + f2[i];
		}
		return resultantForce;
	}

}
