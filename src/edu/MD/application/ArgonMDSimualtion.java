package edu.MD.application;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.globalSetting.PBCBoundarySetting;
import edu.MD.initialization.MonatomicPositionInitializer;
import edu.MD.initialization.MonatomicVelocityInitializer;
import edu.MD.modeling.LJForceCalculator;
import edu.MD.modeling.PBCDistanceCalculator;
import edu.MD.modeling.VelocityCalculator;
import edu.MD.modeling.PositionCalculator;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.number.Vector3DCartesian;
import edu.MD.statThermodynamic.IDensityCalculator;
import edu.MD.statThermodynamic.MonatomicYAxialSmoothDensityCalculator;
import edu.MD.utility.MDConstants;

public class ArgonMDSimualtion implements MDSimulation {
	private int filmSize, filmThickness, vaporOneSideThickness, totalNumParticles, nDensityBin;
	private double temperature, timeStep, cutoff;
	private String name = "ARGON";
	private List<MDVector> positions, velocities, forces;
	private MDVector systemBoundary;
	private MonatomicPositionInitializer positionInitializer;
	private MonatomicVelocityInitializer velocityInitializer;
	private LJForceCalculator forceCalculator;
	private PositionCalculator positionCalculator;
	private VelocityCalculator velocityCalculator;
	private IDensityCalculator densityCalculator;

	public ArgonMDSimualtion() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		filmSize = 5;
		filmThickness = 3;
		vaporOneSideThickness = 3;
		temperature = 100;
		timeStep = 5e-14;
		cutoff = 3.0;
		nDensityBin = 20;
		initialization();
	}

	@Override
	public void initialization() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		NumberFactorySetting.set();

		String forceCalculatorType = name + "_" + name + "_" + String.valueOf(cutoff);
		String positionCalculatorType = name + "_" + String.valueOf(timeStep);
		String velocityCalculatorType = name + "_" + String.valueOf(timeStep / 2.0);
		positionInitializer = new MonatomicPositionInitializer(name, filmThickness, filmSize, vaporOneSideThickness,
				temperature);
		totalNumParticles = positionInitializer.getTotalNumberOfParticles();
		positions = positionInitializer.getPositions();
		systemBoundary = positionInitializer.getSystemBoundary();
		PBCBoundarySetting.set(systemBoundary);
		velocityInitializer = new MonatomicVelocityInitializer(name, totalNumParticles, temperature);
		velocities = velocityInitializer.getVelocities();
		forceCalculator = LJForceCalculator.getInstance(forceCalculatorType);
		positionCalculator = PositionCalculator.getInstance(positionCalculatorType);
		velocityCalculator = VelocityCalculator.getInstance(velocityCalculatorType);
		double molarVaporDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		densityCalculator = MonatomicYAxialSmoothDensityCalculator.getInstance(totalNumParticles, systemBoundary, molarVaporDensity, nDensityBin);
		
		forces = new ArrayList<>(totalNumParticles);
		for (int i = 0; i < positions.size(); i++) {
			MDVector force = new Vector3DCartesian(0, 0, 0);
			for (int j = 0; j < positions.size(); j++) {
				if (i == j)
					continue;
				MDVector distance = PBCDistanceCalculator.calculate(positions.get(i), positions.get(j));
				force = force.plus(forceCalculator.calculate(distance));
			}
			forces.add(force);
		}
	}

	@Override
	public void stepMove() {
		// This is the Verlet algorithm
		List<MDVector> newPositions = new ArrayList<>(positions.size());
		for (int i = 0; i < positions.size(); i++) {
			newPositions.add(i, positionCalculator.calculate(positions.get(i), velocities.get(i), forces.get(i)));
		}
		positions = newPositions;
		
		List<MDVector> newVelocities = new ArrayList<>(velocities.size());
		for (int i = 0; i < velocities.size(); i++) {
			newVelocities.add(i, velocityCalculator.calculate(velocities.get(i), forces.get(i)));
		}
		
		List<MDVector> newForces = new ArrayList<>(forces.size());
		for (int i = 0; i < positions.size(); i++) {
			MDVector force = new Vector3DCartesian(0, 0, 0);
			for (int j = 0; j < positions.size(); j++) {
				if (i == j)
					continue;
				MDVector distance = PBCDistanceCalculator.calculate(positions.get(i), positions.get(j));
				force = force.plus(forceCalculator.calculate(distance));
			}
			newForces.add(i, force);
		}
		forces = newForces;
		
		velocities =  new ArrayList<>(newVelocities.size());
		for (int i = 0; i < newVelocities.size(); i++) {
			velocities.add(i, velocityCalculator.calculate(newVelocities.get(i), forces.get(i)));
		}
	}

	@Override
	public List<MDVector> getPostions() {
		return positions;
	}

	@Override
	public MDVector getSystemBoundary() {
		return systemBoundary;
	}

	@Override
	public int getParticleNumber() {
		return totalNumParticles;
	}

	@Override
	public List<List<MDNumber>> getDensityProfile() {
		return densityCalculator.calculate(positions);
	}

}
