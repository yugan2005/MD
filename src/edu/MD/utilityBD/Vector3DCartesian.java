package edu.MD.utilityBD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector3DCartesian implements MDVector {
	private BigDecimal[] cartesianCoordinates = new BigDecimal[3];
	private static final List<Class<?>> supportedClass = new ArrayList<>(Arrays.asList(Vector3DCartesian.class));

	public Vector3DCartesian(BigDecimal x, BigDecimal y, BigDecimal z) {
		cartesianCoordinates[0] = x;
		cartesianCoordinates[1] = y;
		cartesianCoordinates[2] = z;
	}

	public Vector3DCartesian(BigDecimal[] corrdinates) {
		if (corrdinates.length != 3)
			throw new IllegalArgumentException("The corrdinates is of wrong dimensions");
		cartesianCoordinates[0] = corrdinates[0];
		cartesianCoordinates[1] = corrdinates[1];
		cartesianCoordinates[2] = corrdinates[2];
	}

	@Override
	public BigDecimal[] getCartesianComponent() {
		return cartesianCoordinates;
	}

	@Override
	public MDVector addition(MDVector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return addVector3DCartesian((Vector3DCartesian) vector);
		}
		return null;
	}

	@Override
	public BigDecimal getCartesianDistance(MDVector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return getDistanceOfTwo3DCartesian((Vector3DCartesian) vector);
		}
		return new BigDecimal(0);
	}

	@Override
	public MDVector substraction(MDVector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return subtractVector3DCartesian((Vector3DCartesian) vector);
		}

		return null;
	}

	@Override
	public int getDimension() {
		return 3;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof MDVector))
			return false;

		MDVector vector = (MDVector) obj;

		if (getDimension() != vector.getDimension())
			return false;
		if (!supportedClass.contains(vector.getClass()))
			return false;
		if (vector.getClass() == Vector3DCartesian.class) {
			return equals((Vector3DCartesian) vector);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(cartesianCoordinates);
	}

	private Vector3DCartesian addVector3DCartesian(Vector3DCartesian vector) {
		BigDecimal x = cartesianCoordinates[0].add(vector.getCartesianComponent()[0]);
		BigDecimal y = cartesianCoordinates[1].add(vector.getCartesianComponent()[1]);
		BigDecimal z = cartesianCoordinates[2].add(vector.getCartesianComponent()[2]);
		return new Vector3DCartesian(x, y, z);
	}

	private MDVector subtractVector3DCartesian(Vector3DCartesian vector) {
		BigDecimal x = cartesianCoordinates[0].subtract(vector.getCartesianComponent()[0]);
		BigDecimal y = cartesianCoordinates[1].subtract(vector.getCartesianComponent()[1]);
		BigDecimal z = cartesianCoordinates[2].subtract(vector.getCartesianComponent()[2]);
		return new Vector3DCartesian(x, y, z);
	}

	private BigDecimal getDistanceOfTwo3DCartesian(Vector3DCartesian vector) {
		BigDecimal result = new BigDecimal(0);
		BigDecimal[] coord1 = getCart esianComponent();
		BigDecimal[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			result = result.add(coord1[i].subtract(coord2[i])).multiply(coord1[i].subtract(coord2[i]));
		}
		result = result.p;
		return result;
	}

	private boolean equals(Vector3DCartesian vector) {
		BigDecimal[] coord1 = getCartesianComponent();
		BigDecimal[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			if (Math.abs(coord1[i] - coord2[i]) > Constants.MACHINE_DOUBLE_ERROR)
				return false;
		}
		return true;

	}

	private void checkDimension(MDVector vector) {
		if (vector.getDimension() != this.getDimension())
			throw new IllegalArgumentException(
					"The two vectors have different dimensions, cannot have vector operation");
	}

	private void checkClass(MDVector vector) {
		if (!supportedClass.contains(vector.getClass()))
			throw new IllegalArgumentException(vector.getClass().getName() + " is not supported for vector operation.");
	}

	@Override
	public BigDecimal norm() {
		BigDecimal norm = 0;
		for (int i = 0; i < 3; i++)
			norm += cartesianCoordinates[i] * cartesianCoordinates[i];
		return Math.sqrt(norm);
	}

	@Override
	public MDVector multiply(BigDecimal c) {
		BigDecimal x = cartesianCoordinates[0] * c;
		BigDecimal y = cartesianCoordinates[1] * c;
		BigDecimal z = cartesianCoordinates[2] * c;
		return new Vector3DCartesian(x, y, z);
	}

}
