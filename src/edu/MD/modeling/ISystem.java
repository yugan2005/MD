package edu.MD.modeling;

import java.util.Map;

import edu.MD.utility.MDVector;

public interface ISystem {

	public double getPotentialParameters(String keyString);

	public IDistanceFinder getDistanceFinder();
	
	public void setPotentialMap();
	
	public Map<String, Double> getPotentialMap();

	public MDVector getSystemBoundary();

}
