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
	private static int numParticles;

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
		if (numParticles == 0)
			throw new UnsupportedOperationException("Need set the number of bins first. call setNumParticles()");

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
		List<MDNumber> yPositions = new ArrayList<>(totalNumOfParticles - numParticles + 1);
		List<MDNumber> localDensity = new ArrayList<>(totalNumOfParticles - numParticles + 1);
		density.add(yPositions);
		density.add(localDensity);

		MDNumber crossSectionArea = systemBoundary.getCartesianComponent()[0]
				.times(systemBoundary.getCartesianComponent()[2]);

		MDNumber ySum = listOfPositions.get(0).getCartesianComponent()[1];
		for (int i = 1; i < numParticles - 1; i++) {
			ySum = ySum.plus(listOfPositions.get(i).getCartesianComponent()[1]);
		}

		for (int i = 0; i < totalNumOfParticles - numParticles + 1; i++) {
			ySum = ySum.plus(listOfPositions.get(i + numParticles - 1).getCartesianComponent()[1]);
			MDNumber yMean = ySum.divide(numParticles);
			yPositions.add(yMean);
			MDNumber locaVolume = listOfPositions.get(i + numParticles - 1).getCartesianComponent()[1]
					.minus(listOfPositions.get(i).getCartesianComponent()[1]).times(crossSectionArea);
			MDNumber molarDensity = locaVolume.pow(-1).times(((double) numParticles) / MDConstants.AVOGADRO);
			localDensity.add(molarDensity);
			ySum = ySum.minus(listOfPositions.get(i).getCartesianComponent()[1]);
		}
		return density;

	}

	public static void setDensityCalculator(double molarVaporDensity, MDVector systemBoundary, int nBins) {
		MDNumber crossSectionArea = systemBoundary.getCartesianComponent()[0]
				.times(systemBoundary.getCartesianComponent()[2]);
		MDNumber axialLength = systemBoundary.getCartesianComponent()[1];
		MDNumber binLength = axialLength.divide(nBins);
		MDNumber binVolume = binLength.times(crossSectionArea);
		MonatomicAxialBasedOnParticleDensityCalculator.numParticles = binVolume
				.times(molarVaporDensity * MDConstants.AVOGADRO).round();
	}

}
