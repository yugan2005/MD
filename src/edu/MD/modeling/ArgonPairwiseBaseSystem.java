package edu.MD.modeling;

import java.util.HashMap;
import java.util.Map;

public class ArgonPairwiseBaseSystem implements ISystem {
	private static Map<String, Double> POTENTIAL_PARAMETERS = new HashMap<>();
	private static final IDistanceFinder DISTANCE_FINDER = PBCPairwiseDistanceFinder.INSTANCE;
	private static final IPotentialMapSetter POTENTIAL_MAP_SETTER = ArgonPotentialMapSetter.INSTANCE;

	@Override
	public double getPotentialParameters(String keyString) {
		return POTENTIAL_PARAMETERS.get(keyString);
	}

	@Override
	public IDistanceFinder getDistanceFinder() {
		return DISTANCE_FINDER;
	}

	@Override
	public void setPotentialMap() {
		POTENTIAL_MAP_SETTER.setPotentialMap(this);		
	}

	@Override
	public Map<String, Double> getPotentialMap() {
		return POTENTIAL_PARAMETERS;
	}

}
