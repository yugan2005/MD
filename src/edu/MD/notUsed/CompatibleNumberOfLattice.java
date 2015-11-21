package edu.MD.initializationBD;

import java.util.ArrayList;
import java.util.List;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Vector3DCartesian;

public class CompatibleNumberOfLattice {

	public static boolean statisfyConditions(String name, int t, int s, double temperature, double x, int n) {
		double dV = MDConstants.getMolarDensity(name, temperature, "vapor").toDouble();
		double dL = MDConstants.getMolarDensity(name, temperature, "liquid").toDouble();
		double common = t * dL * (4 * n - 1) / ((4 * t + 2) * n * Math.pow(s, 3) * dV);
		double greater = Math.pow(Math.pow(dV / dL, 1.0 / 3.0) * s - 0.5, 3);
		double less = Math.pow(Math.pow(dV / dL, 1.0 / 3.0) * s + 0.5, 3);
		return greater*common >= (1 - x) && less*common <= (1 + x);
	}

	public static void main(String[] args) {
		NumberFactory.setFactorySetting("JavaDefaultNumberFactory");
		String name = "ARGON";
		double x = 0.5;
		int s = 10;
		int n = 5;
		int t = 1;
		double temperature = 110;

		System.out.println(statisfyConditions(name, t, s, temperature, x, n));
	}

}
