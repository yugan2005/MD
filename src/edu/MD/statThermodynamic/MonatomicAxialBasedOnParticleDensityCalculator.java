package edu.MD.statThermodynamic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.utility.IterableCounter;
import edu.MD.utility.MDConstants;

public class MonatomicAxialBasedOnParticleDensityCalculator {

	private static class YComparator implements Comparator<MDVector> {

		@Override
		public int compare(MDVector vector1, MDVector vector2) {
			MDNumber y1 = vector1.getCartesianComponent()[1];
			MDNumber y2 = vector2.getCartesianComponent()[1];

			return y1.compareTo(y2);
		}

	}

	/**
	 * @param positions
	 *            Iterable<MDVector>
	 * @param systemBoundary
	 * @return DensityProfiles in List<List<MDNumber>> format. Two lists, the
	 *         1st list is the y location, the 2nd list is the corresponding
	 *         local density value.
	 */
	public static List<List<MDNumber>> calculate(Iterable<MDVector> positions, MDVector systemBoundary) {

		int totalNumOfParticles = IterableCounter.count(positions);

		List<MDVector> listOfPositions;
		if (positions instanceof Collection<?>) {
			Collection<MDVector> collectionOfPositions = (Collection<MDVector>) positions;
			listOfPositions = new ArrayList<MDVector>(collectionOfPositions);
		} else {
			listOfPositions = new ArrayList<>(totalNumOfParticles);
			for (MDVector position : positions) {
				listOfPositions.add(position);
			}
		}

		listOfPositions.sort(new YComparator());

package edu.MD.statThermodynamic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.utility.IterableCounter;
import edu.MD.utility.MDConstants;

public class MonatomicAxialBasedOnParticleDensityCalculator {

	private static class YComparator implements Comparator<MDVector> {

		@Override
		public int compare(MDVector vector1, MDVector vector2) {
			MDNumber y1 = vector1.getCartesianComponent()[1];
			MDNumber y2 = vector2.getCartesianComponent()[1];

			return y1.compareTo(y2);
		}

	}

	/**
	 * @param positions
	 *            Iterable<MDVector>
	 * @param systemBoundary
	 * @return DensityProfiles in List<List<MDNumber>> format. Two lists, the
	 *         1st list is the y location, the 2nd list is the corresponding
	 *         local density value.
	 */
	public static List<List<MDNumber>> calculate(Iterable<MDVector> positions, MDVector systemBoundary) {

		int totalNumOfParticles = IterableCounter.count(positions);

		List<MDVector> listOfPositions;
		if (positions instanceof Collection<?>) {
			Collection<MDVector> collectionOfPositions = (Collection<MDVector>) positions;
			listOfPositions = new ArrayList<MDVector>(collectionOfPositions);
		} else {
			listOfPositions = new ArrayList<>(totalNumOfParticles);
			for (MDVector position : positions) {
				listOfPositions.add(position);
			}
		}

		listOfPositions.sort(new YComparator());

		List<List<MDNumber>> density = new ArrayList<>(2);
		List<MDNumber> yPositions = new ArrayList<>();
		List<MDNumber> localDensity = new ArrayList<>();
		density.add(yPositions);
		density.add(localDensity);

		MDNumber crossSectionArea = systemBoundary.getCartesianComponent()[0]
				.times(systemBoundary.getCartesianComponent()[2]);
		
		
		MDNumber yPrevious = systemBoundary.getCartesianComponent()[1].minus(listOfPositions.get(totalNumOfParticles-1).getCartesianComponent()[1]).times(-1);
		MDNumber yCurrent = listOfPositions.get(0).getCartesianComponent()[1];
		int idx = 1;
		int localCounter = 1;
		
		while (idx<totalNumOfParticles) {
			MDNumber yNext = listOfPositions.get(idx).getCartesianComponent()[1];
			if (yNext.equals(yCurrent)){
				localCounter++;
			}
			else{
				// calculate local density
				MDNumber yMean = yNext.minus(yPrevious);
				MDNumber localVolume = yMean.times(crossSectionArea);
				MDNumber molarDensity = localVolume.pow(-1)
						.times(((double) localCounter)/ MDConstants.AVOGADRO);
				yPositions.add(yCurrent);
				localDensity.add(molarDensity);
				// reset
				yPrevious = yCurrent;
				yCurrent = yNext;
				localCounter = 1;
			}
			idx++;
		}
		
		MDNumber yNext = listOfPositions.get(0).getCartesianComponent()[1].plus(systemBoundary.getCartesianComponent()[1]);
		MDNumber yMean = yNext.minus(yPrevious);
		MDNumber localVolume = yMean.times(crossSectionArea);
		MDNumber molarDensity = localVolume.pow(-1)
				.times(((double) localCounter)/ MDConstants.AVOGADRO);
		yPositions.add(yCurrent);
		localDensity.add(molarDensity);

		return density;
	}

}
