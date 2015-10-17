package edu.MD.modeling;

import java.util.HashMap;
import java.util.Map;

import edu.MD.utility.MDVector;
import edu.MD.utility.Vector3DCartesian;

public class ArgonPairwiseBaseSystem implements ISystem {
	
	// TODO all these static final should be set by the initialization procedure
	private static Map<String, Double> POTENTIAL_PARAMETERS = new HashMap<>();
	private static final IDistanceFinder DISTANCE_FINDER = PBCPairwiseDistanceFinder.INSTANCE;
	private static final IPotentialMapSetter POTENTIAL_MAP_SETTER = ArgonPotentialMapSetter.INSTANCE;
	private static MDVector SYSTEM_BOUNDARY;

	@Override
	public double getPotentialParameters(String keyString) {
		return POTENTIAL_PARAMETERS.get(keyString);
	}

	@Override
	public IDistanceFinder getDistanceFinder() {
		return DISTANCE_FINDER;
	}

	@Override
	public Map<String, Double> getPotentialMap() {
		return POTENTIAL_PARAMETERS;
	}

	@Override
	public MDVector getSystemBoundary() {
		return SYSTEM_BOUNDARY;
	}

	@Override
	public void initializeSystem() {
		// TODO not complete yet.
		
		// 1: set the system's potential parameters map
		POTENTIAL_MAP_SETTER.setPotentialMap(this);
		
		// TODO the following code will be deleted. For testing purpose only
		// The systemBoundary should be calculate by the initialization procedure.
		String sigmaString = Argon.TYPE + "_" + Argon.NAME + "_" + Argon.TYPE + "_" + Argon.NAME + "_sigma";
		double sigma = getPotentialParameters(sigmaString);
		SYSTEM_BOUNDARY = new Vector3DCartesian(new double[]{6*sigma, 6*sigma, 12*sigma});
		
	}

}
