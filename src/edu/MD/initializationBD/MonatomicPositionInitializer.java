package edu.MD.initializationBD;

import java.util.ArrayList;
import java.util.List;

import edu.MD.numberBD.MDNumber;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Vector3DCartesian;

public class MonatomicPositionInitializer {
	private int filmThickness, filmSize, vaporOneSideThickness, vaporSize;
	private int numOfVaporParticles, numOfLiquidParticles, totalNumberOfParticles;
	private MDNumber vaporMolarDensity, liquidMolarDensity;
	private MDNumber vaporLatticeSize, vaporLatticeLength, liquidLatticeLength;
	private MDNumber systemSize, systemLength;
	private MDVector systemBoundary;
	private Iterable<MDVector> positions;

	/**
	 * This constructs a sandwiched liquid + two sides of vapor
	 * 
	 * @param name
	 *            of particle
	 * @param filmThickness
	 *            the number of liquid lattices in the y direction (axial
	 *            direction)
	 * @param filmSize
	 *            the number of liquid lattices in the x and z direction
	 *            (lateral plane)
	 * @param vaporOneSideThickness
	 *            the number of vapor lattices in the y direction on each side
	 *            of the sandwiching vapor (axial plane)
	 * @param temperature
	 *            in SI unit K
	 */
	public MonatomicPositionInitializer(String name, int filmThickness, int filmSize, int vaporOneSideThickness,
			double temperature) {
		if (filmThickness < 1)
			throw new IllegalArgumentException("Film thickness should be at least 1");

		this.filmThickness = filmThickness;
		this.filmSize = filmSize;
		this.vaporOneSideThickness = vaporOneSideThickness;

		vaporMolarDensity = MDConstants.getMolarDensity(name, temperature, "vapor");
		liquidMolarDensity = MDConstants.getMolarDensity(name, temperature, "liquid");

		liquidFCCLattice();
		systemSize = liquidLatticeLength.times(filmSize);
		vaporFCCLattice();

		// check the thickness parameters are well defined
		// The vapor lattices should not skewed too much, 10% of difference
		// between lateral direction and axial direction is allowed
		if (vaporLatticeLength.minus(vaporLatticeSize).divide(vaporLatticeSize).abs().toDouble() > 0.1)
			throw new IllegalArgumentException("The defined parameters can not generate well defined lattices");

		totalNumberOfParticles = numOfVaporParticles + numOfLiquidParticles;

		systemLength = liquidLatticeLength.times(filmThickness)
				.add(vaporLatticeLength.times(vaporOneSideThickness * 2));

		systemBoundary = new Vector3DCartesian(systemSize, systemLength, systemSize);

		positions = sandwichedFCCPositions();

	}

	private void liquidFCCLattice() {
		// Refer to the attached notes
		// This uses the F.C.C lattice structure
		numOfLiquidParticles = (4 * filmThickness + 2) * filmSize * filmSize;
		liquidLatticeLength = liquidMolarDensity.times(MDConstants.AVOGADRO).pow(-1)
				.times((4 * filmThickness + 2) / ((double) filmThickness)).pow(1.0 / 3.0);
	}

	private void vaporFCCLattice() {
		// Refer to the attached notes
		MDNumber approximatedLatticeLen = vaporMolarDensity.times(MDConstants.AVOGADRO).pow(-1)
				.times((4 * vaporOneSideThickness - 1) / ((double) vaporOneSideThickness)).pow(1.0 / 3.0);
		vaporSize = systemSize.divide(approximatedLatticeLen).round();

		numOfVaporParticles = (8 * vaporOneSideThickness - 2) * vaporSize * vaporSize;

		vaporLatticeSize = systemSize.divide(vaporSize);
		vaporLatticeLength = vaporMolarDensity.times(MDConstants.AVOGADRO).times(vaporLatticeSize.pow(2.0)).pow(-1)
				.times((4 * vaporOneSideThickness - 1) / ((double) vaporOneSideThickness));

	}

	private Iterable<MDVector> sandwichedFCCPositions() {
		List<MDVector> positionList = new ArrayList<>(totalNumberOfParticles);

		// setting the lower y side vapor
		for (int y = 0; y < vaporOneSideThickness; y++) {
			for (int x = 0; x < vaporSize; x++) {
				for (int z = 0; z < vaporSize; z++) {
					MDVector vertex = new Vector3DCartesian(vaporLatticeSize.times(x), vaporLatticeLength.times(y),
							vaporLatticeSize.times(z));
					MDVector xzPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x + 0.5),
							vaporLatticeLength.times(y), vaporLatticeSize.times(z + 0.5));
					MDVector yzPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x),
							vaporLatticeLength.times(y + 0.5), vaporLatticeSize.times(z + 0.5));
					MDVector xyPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x + 0.5),
							vaporLatticeLength.times(y + 0.5), vaporLatticeSize.times(z));
					positionList.add(vertex);
					positionList.add(xzPlaneFCC);
					positionList.add(yzPlaneFCC);
					positionList.add(xyPlaneFCC);
				}
			}
		}

		// setting the middle liquid
		MDVector liquidStartPoint = new Vector3DCartesian(0, vaporLatticeLength.times(vaporOneSideThickness), 0);

		for (int y = 0; y <= filmThickness; y++) {
			for (int x = 0; x < filmSize; x++) {
				for (int z = 0; z < filmSize; z++) {
					MDVector vertex = new Vector3DCartesian(liquidLatticeLength.times(x), liquidLatticeLength.times(y),
							liquidLatticeLength.times(z));
					MDVector xzPlaneFCC = new Vector3DCartesian(liquidLatticeLength.times(x + 0.5),
							liquidLatticeLength.times(y), liquidLatticeLength.times(z + 0.5));
					positionList.add(vertex.add(liquidStartPoint));
					positionList.add(xzPlaneFCC.add(liquidStartPoint));

					if (y != filmThickness) {
						MDVector yzPlaneFCC = new Vector3DCartesian(liquidLatticeLength.times(x),
								liquidLatticeLength.times(y + 0.5), liquidLatticeLength.times(z + 0.5));
						MDVector xyPlaneFCC = new Vector3DCartesian(liquidLatticeLength.times(x + 0.5),
								liquidLatticeLength.times(y + 0.5), liquidLatticeLength.times(z));
						positionList.add(yzPlaneFCC.add(liquidStartPoint));
						positionList.add(xyPlaneFCC.add(liquidStartPoint));
					}
				}
			}
		}

		// setting the higher y side vapor
		MDVector highYVaporStartPoint = new Vector3DCartesian(0,
				vaporLatticeLength.times(vaporOneSideThickness).add(liquidLatticeLength.times(filmThickness)), 0);

		for (int y = 0; y < vaporOneSideThickness; y++) {
			for (int x = 0; x < vaporSize; x++) {
				for (int z = 0; z < vaporSize; z++) {
					if (y != 0) {
						MDVector vertex = new Vector3DCartesian(vaporLatticeSize.times(x), vaporLatticeLength.times(y),
								vaporLatticeSize.times(z));
						MDVector xzPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x + 0.5),
								vaporLatticeLength.times(y), vaporLatticeSize.times(z + 0.5));
						positionList.add(vertex.add(highYVaporStartPoint));
						positionList.add(xzPlaneFCC.add(highYVaporStartPoint));

					}
					MDVector yzPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x),
							vaporLatticeLength.times(y + 0.5), vaporLatticeSize.times(z + 0.5));
					MDVector xyPlaneFCC = new Vector3DCartesian(vaporLatticeSize.times(x + 0.5),
							vaporLatticeLength.times(y + 0.5), vaporLatticeSize.times(z));
					positionList.add(yzPlaneFCC.add(highYVaporStartPoint));
					positionList.add(xyPlaneFCC.add(highYVaporStartPoint));
				}
			}
		}
		
		return positionList;
	}

	public MDVector getSystemBoundary() {
		return systemBoundary;
	}

	public Iterable<MDVector> getPositions() {
		return positions;
	}

	public int getNumOfVaporParticles() {
		return numOfVaporParticles;
	}

	public int getNumOfLiquidParticles() {
		return numOfLiquidParticles;
	}

	public int getTotalNumberOfParticles() {
		return totalNumberOfParticles;
	}

}
