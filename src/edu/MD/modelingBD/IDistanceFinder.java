package edu.MD.modelingBD;

import edu.MD.utilityBD.MDVector;

public interface IDistanceFinder {

	MDVector findDistance(MDVector p1Position, MDVector p2Position, MDVector systemBoundary);

}
