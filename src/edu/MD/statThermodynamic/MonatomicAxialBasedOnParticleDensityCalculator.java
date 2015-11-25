package edu.MD.statThermodynamic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import edu.MD.number.NumberFactory;
import edu.MD.utility.IterableCounter;
import edu.MD.utility.MDConstants;

public class MonatomicAxialBasedOnParticleDensityCalculator {
	private static int numParticles = 3;

	private static class YComparator implements Comparator<MDVector> {

		@Override
		public int compare(MDVector vector1, MDVector vector2) {
			MDNumber y1 = vector1.getCartesianComponent()[1];
			MDNumber y2 = vector2.getCartesianComponent()[1];

			return y1.compareTo(y2);
		}

	}

	public static List<List<MDNumber>> calculate(Iterable<MDVector> positions, MDVector systemBoundary) {
		// TODO need be able to set the numParticles dynamically.
		TreeSet<MDVector> densityAlongYAxis = new TreeSet<>(new YComparator());
		for (MDVector position : positions) {
			densityAlongYAxis.add(position);
		}
		int totalNumOfParticles = IterableCounter.count(positions);
		List<List<MDNumber>> density = new ArrayList<>(2);
		List<MDNumber> yPositions = new ArrayList<>(totalNumOfParticles);
		List<MDNumber> localDensity = new ArrayList<>(totalNumOfParticles);
		density.add(yPositions);
		density.add(localDensity);
		
		MDNumber crossSectionArea = systemBoundary.getCartesianComponent()[0].times(systemBoundary.getCartesianComponent()[2]);
		
		Iterator<MDVector> positionIterator = densityAlongYAxis.iterator();
		
		Deque<MDNumber> yLocalPositions = new ArrayDeque<MDNumber>(numParticles);
		MDNumber ySum = NumberFactory.getInstance().valueOf(0);
		for (int i = 0; i < numParticles; i++) {
			MDNumber yLocalPosition = positionIterator.next().getCartesianComponent()[1];
			ySum = ySum.plus(yLocalPosition);
			yLocalPositions.add(yLocalPosition);
		}
		while (positionIterator.hasNext()){
			MDNumber yMean = ySum.divide(numParticles);
			yPositions.add(yMean);
			MDNumber locaVolume = yLocalPositions.peekLast().minus(yLocalPositions.peekFirst()).times(crossSectionArea);
			MDNumber molarDensity = locaVolume.pow(-1).times(((double) numParticles)/MDConstants.AVOGADRO);
			localDensity.add(molarDensity);
			ySum = ySum.minus(yLocalPositions.peekFirst());
			yLocalPositions.poll();
			MDNumber yNext = positionIterator.next().getCartesianComponent()[1];
			yLocalPositions.add(yNext);
			ySum = ySum.plus(yNext);
		}
		return density;

	}

	public static void setNumParticles(int numParticles) {
		MonatomicAxialBasedOnParticleDensityCalculator.numParticles = numParticles;
	}

}
