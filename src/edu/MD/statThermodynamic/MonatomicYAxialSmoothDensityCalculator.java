package edu.MD.statThermodynamic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.utility.IterableCounter;
import edu.MD.utility.MDConstants;

public class MonatomicYAxialSmoothDensityCalculator {
	// TODO need have a traditional density profile calculator to compare
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

		ArrayDeque<MDNumber> yOfParticles = new ArrayDeque<>();
		MDNumber yCurrent = listOfPositions.get(0).getCartesianComponent()[1];
		MDNumber ySum = yCurrent;
		yOfParticles.addLast(yCurrent);
		int idx = 1;

		while (idx < totalNumOfParticles) {
			yCurrent = listOfPositions.get(idx).getCartesianComponent()[1];
			yOfParticles.addLast(yCurrent);
			ySum = ySum.plus(yCurrent);
			if (yOfParticles.size() >= numParticles && !yOfParticles.peekLast().equals(yOfParticles.peekFirst())) {
				// calculate local density and add to the lists
				MDNumber yMean = ySum.divide(yOfParticles.size());
				yPositions.add(yMean);
				MDNumber localVolume = yOfParticles.peekLast().minus(yOfParticles.peekFirst()).times(crossSectionArea);
				MDNumber molarDensity = localVolume.pow(-1)
						.times(((double) yOfParticles.size()) / MDConstants.AVOGADRO);
				localDensity.add(molarDensity);
				// reset counter
				MDNumber yDiscard = yOfParticles.removeFirst();
				ySum = ySum.minus(yDiscard);
				while (yOfParticles.size() >= numParticles || yOfParticles.peekFirst().equals(yDiscard)) {
					yDiscard = yOfParticles.removeFirst();
					ySum = ySum.minus(yDiscard);
				}
			}
			idx++;
		}
		// handle the last local density if it is not handled
		if (yOfParticles.peekLast().equals(yOfParticles.peekFirst())) {
			// extend backward
			int sizeLastGroup = yOfParticles.size();
			int i = totalNumOfParticles - 1 - sizeLastGroup;
			MDNumber yAdded = listOfPositions.get(i).getCartesianComponent()[1];
			while (yAdded.equals(yCurrent)) {
				ySum = ySum.plus(yAdded);
				sizeLastGroup++;
				i--;
				yAdded = listOfPositions.get(i).getCartesianComponent()[1];
			}
			ySum = ySum.plus(yAdded);
			sizeLastGroup++;
			MDNumber yMean = ySum.divide(sizeLastGroup);
			yPositions.add(yMean);
			MDNumber localVolume = yCurrent.minus(yAdded).times(crossSectionArea);
			MDNumber molarDensity = localVolume.pow(-1).times(((double) sizeLastGroup) / MDConstants.AVOGADRO);
			localDensity.add(molarDensity);
		} else if (yOfParticles.size() < numParticles) {
			// just use the smaller group
			MDNumber yMean = ySum.divide(yOfParticles.size());
			yPositions.add(yMean);
			MDNumber localVolume = yOfParticles.peekLast().minus(yOfParticles.peekFirst()).times(crossSectionArea);
			MDNumber molarDensity = localVolume.pow(-1).times(((double) yOfParticles.size()) / MDConstants.AVOGADRO);
			localDensity.add(molarDensity);
		}

		return density;

	}

	public static void setDensityCalculator(double molarVaporDensity, MDVector systemBoundary, int nBins) {
		MDNumber crossSectionArea = systemBoundary.getCartesianComponent()[0]
				.times(systemBoundary.getCartesianComponent()[2]);
		MDNumber axialLength = systemBoundary.getCartesianComponent()[1];
		MDNumber binLength = axialLength.divide(nBins);
		MDNumber binVolume = binLength.times(crossSectionArea);
		MonatomicYAxialSmoothDensityCalculator.numParticles = binVolume
				.times(molarVaporDensity * MDConstants.AVOGADRO).round();
	}

}
