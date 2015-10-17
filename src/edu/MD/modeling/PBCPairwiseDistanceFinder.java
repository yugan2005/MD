package edu.MD.modeling;

import edu.MD.utility.MDVector;

public class PBCPairwiseDistanceFinder implements IDistanceFinder {
	public static final PBCPairwiseDistanceFinder INSTANCE = new PBCPairwiseDistanceFinder();
	
	private PBCPairwiseDistanceFinder(){};
	
	/* (non-Javadoc)
	 * @see edu.MD.modeling.IDistanceFinder#findDistance(edu.MD.modeling.Particle, edu.MD.modeling.Particle, edu.MD.modeling.ISystem)
	 */
	@Override
	public MDVector findDistance(Particle p1, Particle p2, ISystem system){
		double[] systemBoundary = system.getSystemBoundary().getCartesianComponent();
		double[] p1Position = p1.getPosition().getCartesianComponent();
		double[] p2Position = p2.getPosition().getCartesianComponent();
		
		double[] distanceVector = new double[p1Position.length];
		for (int i=0; i<p1Position.length; i++){
			if (p2Position[i]>p1Position[i]) 
				// TODO Still need figure this out.
				distanceVector[i]= (p1Position[i]+systemBoundary[i]-p2Position[i]>p2Position[i]-p1Position[i])?
			
		}
		double xLim = systemBoundary.getCartesianComponent()[0];
		double yLim = systemBoundary[1];
		double zLim = systemBoundary[2];
		
		
		return null;
	}

}
