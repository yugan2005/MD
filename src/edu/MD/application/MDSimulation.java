package edu.MD.application;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;

public interface MDSimulation {
	
	public void initialization() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	public void stepMove();
	
	public List<MDVector> getPostions();

	public MDVector getSystemBoundary();

	public int getParticleNumber();

	public List<List<MDNumber>> getDensityProfile();
	
}
