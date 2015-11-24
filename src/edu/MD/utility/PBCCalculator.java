package edu.MD.utility;

import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;

public class PBCCalculator {
	private static PBCCalculator instance;
	private MDVector systemBoundary;

	private PBCCalculator(MDVector systemBoundary) {
		this.systemBoundary = systemBoundary;
	}

	/**
	 * Factory method to get the singleton PBCCalculator
	 * 
	 * @return PBCCalculator.instance
	 */
	public static PBCCalculator getInstance() {
		if (instance == null)
			throw new UnsupportedOperationException(
					"Need set the PBCCalculator first by calling setPBCCalculator() method");
		return instance;
	}

	/**
	 * Factory method set the singleton PBCCalculator and return it
	 * 
	 * @param systemBoundary
	 * @return PBCCalculator.instance
	 */
	public static PBCCalculator getInstance(MDVector systemBoundary) {
		if (instance != null) {
			if (!instance.systemBoundary.approximateEqual(systemBoundary))
				throw new UnsupportedOperationException(
						"The PBCCalculator has been set and the setting is incompatible with request setting");
			return instance;
		}
		setPBCCalculator(systemBoundary);
		return instance;
	}

	/**
	 * Set the singleton PBCCalculator
	 * 
	 * @param systemBoundary
	 */
	public static void setPBCCalculator(MDVector systemBoundary) {
		instance = new PBCCalculator(systemBoundary);
	}

	/**
	 * Applies the minimum image convention to adjust the distance between two MDVectors
	 * @param distanceVector The unadjusted distanceVector
	 * @return void. The input distanceVector will be adjusted
	 */
	public void applyMinimumImageConvention(MDVector distanceVector) {		

		MDNumber[] dist = distanceVector.getCartesianComponent();
		MDNumber[] bound = systemBoundary.getCartesianComponent();
		for (int i = 0; i < distanceVector.getDimension(); i++) {
			//The following guard should not be here to save time
			//if (dist[i].abs().compareTo(bound[i])>0) throw new IllegalArgumentException("The input MDVector is larger than the system's boundary");
			
			// corner case, when the distance is right at the middle of the System
			// Boundary
			if (!dist[i].approximateEqual(bound[i].times(0.5)))
				dist[i] = dist[i].minus(bound[i].times(dist[i].divide(bound[i]).add(0.5).floor()));
		}
	}

	/**
	 * Applies the Periodic Boundary Condition to the position vector
	 * @param position The unadjusted position vector
	 * @return void. The input position vector will be adjusted
	 */
	public void applyPBC(MDVector position) {
		MDNumber[] components = position.getCartesianComponent();
		MDNumber[] bound = systemBoundary.getCartesianComponent();
		for (int i = 0; i < position.getDimension(); i++) {
			components[i]=components[i].mod(bound[i]);
		}
	}

}
