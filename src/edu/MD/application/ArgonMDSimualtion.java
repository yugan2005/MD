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
import edu.MD.number.MDVector;
import edu.MD.number.Vector3DCartesian;

public class ArgonMDSimualtion implements MDSimulation {
	private static int filmSize, filmThickness, vaporOneSideThickness, totalNumParticles;
	private static double temperature, timeStep, cutoff;
	private static String name;
	private static List<MDVector> positions, velocities, forces;
	private static MDVector systemBoundary;
	private static MonatomicPositionInitializer positionInitializer;
	private static MonatomicVelocityInitializer velocityInitializer;
	private static LJForceCalculator forceCalculator;
	private static PositionCalculator positionCalculator;
	private static VelocityCalculator velocityCalculator;
	
	private static void initialization() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		NumberFactorySetting.set();
		filmSize = 10;
		filmThickness = 6;
		vaporOneSideThickness = 3;
		name = "ARGON";
		temperature = 100;
		timeStep = 1e-15;
		cutoff = 3.0;
		String forceCalculatorType = name+"_"+name+"_"+String.valueOf(cutoff);
		String positionCalculatorType = name+"_"+String.valueOf(timeStep);
		String velocityCalculatorType = name+"_"+String.valueOf(timeStep/2.0);
		positionInitializer = new MonatomicPositionInitializer(name, filmThickness, filmSize, vaporOneSideThickness, temperature);
		totalNumParticles = positionInitializer.getTotalNumberOfParticles();
		positions = positionInitializer.getPositions();
		systemBoundary = positionInitializer.getSystemBoundary();
		PBCBoundarySetting.set(systemBoundary);
		velocityInitializer = new MonatomicVelocityInitializer(name, totalNumParticles, temperature);
		velocities = velocityInitializer.getVelocities();
		forceCalculator = LJForceCalculator.getInstance(forceCalculatorType);
		positionCalculator = PositionCalculator.getInstance(positionCalculatorType);
		velocityCalculator = VelocityCalculator.getInstance(velocityCalculatorType);
		forces = new ArrayList<>(totalNumParticles);
		for (int i=0; i<positions.size(); i++){
			MDVector force = new Vector3DCartesian(0, 0, 0);
			for (int j=0; j<positions.size(); j++){
				if (i==j) continue;
				MDVector distance = PBCDistanceCalculator.calculate(positions.get(i), positions.get(j));
				force = force.plus(forceCalculator.calculate(distance));
			}
			forces.add(force);
		}
	}
	
	private static void stepMove(){
		// This is the Verlet algorithm
		for (int i=0; i<positions.size(); i++){
			positions.add(i, positionCalculator.calculate(positions.get(i), velocities.get(i), forces.get(i)));
		}
		for (int i=0; i<velocities.size(); i++){
			velocities.add(i, velocityCalculator.calculate(velocities.get(i), forces.get(i)));
		}
		for (int i=0; i<positions.size(); i++){
			MDVector force = new Vector3DCartesian(0, 0, 0);
			for (int j=0; j<positions.size(); j++){
				if (i==j) continue;
				MDVector distance = PBCDistanceCalculator.calculate(positions.get(i), positions.get(j));
				force = force.plus(forceCalculator.calculate(distance));
			}
			forces.add(i, force);
		}
		for (int i=0; i<velocities.size(); i++){
			velocities.add(i, velocityCalculator.calculate(velocities.get(i), forces.get(i)));
		}		
		
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		initialization();
		stepMove();
		
	}
	

}
