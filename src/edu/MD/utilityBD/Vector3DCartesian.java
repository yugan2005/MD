package edu.MD.utilityBD;

import edu.MD.utility.MDNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector3DCartesian implements MDVector {
	private MDNumber[] cartesianCoordinates = new MDNumber[3];
	private static final List<Class<?>> supportedClass = new ArrayList<>(Arrays.asList(Vector3DCartesian.class));

	public Vector3DCartesian(MDNumber x, MDNumber y, MDNumber z) {
		cartesianCoordinates[0] = x;
		cartesianCoordinates[1] = y;
		cartesianCoordinates[2] = z;
	}

	public Vector3DCartesian(MDNumber[] corrdinates) {
		if (corrdinates.length != 3)
			throw new IllegalArgumentException("The corrdinates is of wrong dimensions");
		cartesianCoordinates[0] = corrdinates[0];
		cartesianCoordinates[1] = corrdinates[1];
		cartesianCoordinates[2] = corrdinates[2];
	}

	@Override
	public MDNumber[] getCartesianComponent() {
		return cartesianCoordinates;
	}

	@Override
	public MDVector add(MDVector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return addVector3DCartesian((Vector3DCartesian) vector);
		}
		return null;
	}

	@Override
	public MDNumber getCartesianDistance(MDVector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return getDistanceOfTwo3DCartesian((Vector3DCartesian) vector);
		}
		return null;
	}

	@Override
	public MDVector minus(MDVector vector) {
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
		MDNumber x = cartesianCoordinates[0].add(vector.getCartesianComponent()[0]);
		MDNumber y = cartesianCoordinates[1].add(vector.getCartesianComponent()[1]);
		MDNumber z = cartesianCoordinates[2].add(vector.getCartesianComponent()[2]);
		return new Vector3DCartesian(x, y, z);
	}

	private MDVector subtractVector3DCartesian(Vector3DCartesian vector) {
		MDNumber x = cartesianCoordinates[0].minus(vector.getCartesianComponent()[0]);
		MDNumber y = cartesianCoordinates[1].minus(vector.getCartesianComponent()[1]);
		MDNumber z = cartesianCoordinates[2].minus(vector.getCartesianComponent()[2]);
		return new Vector3DCartesian(x, y, z);
	}

	private MDNumber getDistanceOfTwo3DCartesian(Vector3DCartesian vector) {
		MDNumber result = cartesianCoordinates[0].zero();
		MDNumber[] coord1 = vector.getCartesianComponent();
		MDNumber[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			result = result.add(coord1[i].minus(coord2[i])).times(coord1[i].minus(coord2[i]));
		}
		return result;
	}

	private boolean equals(Vector3DCartesian vector) {
		MDNumber[] coord1 = getCartesianComponent();
		MDNumber[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			if (!coord1[i].equals(coord2[i]))
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
	public MDNumber norm() {
		MDNumber norm = cartesianCoordinates[0].zero();
		for (int i = 0; i < 3; i++)
			norm = norm.add(cartesianCoordinates[i].times(cartesianCoordinates[i]));
		return norm.sqrt();
	}

	@Override
	public MDVector times(MDNumber c) {
		MDNumber x = cartesianCoordinates[0].times(c);
		MDNumber y = cartesianCoordinates[1].times(c);
		MDNumber z = cartesianCoordinates[2].times(c);
		return new Vector3DCartesian(x, y, z);
	}

}
