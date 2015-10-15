package edu.MD.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector3DCartesian implements Vector {
	private double[] cartesianCoordinates = new double[3];
	private static final List<Class<?>> supportedClass = new ArrayList<>(Arrays.asList(Vector3DCartesian.class));

	public Vector3DCartesian(double x, double y, double z) {
		cartesianCoordinates[0] = x;
		cartesianCoordinates[1] = y;
		cartesianCoordinates[2] = z;
	}

	public Vector3DCartesian(double[] corrdinates) {
		if (corrdinates.length != 3)
			throw new IllegalArgumentException("The corrdinates is of wrong dimensions");
		cartesianCoordinates[0] = corrdinates[0];
		cartesianCoordinates[1] = corrdinates[1];
		cartesianCoordinates[2] = corrdinates[2];
	}

	@Override
	public double[] getCartesianComponent() {
		return cartesianCoordinates;
	}

	@Override
	public Vector addition(Vector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return addVector3DCartesian((Vector3DCartesian) vector);
		}
		return null;
	}

	@Override
	public double getCartesianDistance(Vector vector) {
		checkDimension(vector);
		checkClass(vector);
		if (vector.getClass() == Vector3DCartesian.class) {
			return getDistanceOfTwo3DCartesian((Vector3DCartesian) vector);
		}
		return 0;
	}

	@Override
	public Vector substraction(Vector vector) {
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
		if (!(obj instanceof Vector))
			return false;

		Vector vector = (Vector) obj;

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
		return Arrays.hashCode(cartesianCoordinates);
	}

	private Vector3DCartesian addVector3DCartesian(Vector3DCartesian vector) {
		double x = cartesianCoordinates[0] + vector.getCartesianComponent()[0];
		double y = cartesianCoordinates[1] + vector.getCartesianComponent()[1];
		double z = cartesianCoordinates[2] + vector.getCartesianComponent()[2];
		return new Vector3DCartesian(x, y, z);
	}

	private Vector subtractVector3DCartesian(Vector3DCartesian vector) {
		double x = cartesianCoordinates[0] - vector.getCartesianComponent()[0];
		double y = cartesianCoordinates[1] - vector.getCartesianComponent()[1];
		double z = cartesianCoordinates[2] - vector.getCartesianComponent()[2];
		return new Vector3DCartesian(x, y, z);
	}

	private double getDistanceOfTwo3DCartesian(Vector3DCartesian vector) {
		double result = 0;
		double[] coord1 = getCartesianComponent();
		double[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			result += (coord1[i] - coord2[i]) * (coord1[i] - coord2[i]);
		}
		result = Math.sqrt(result);
		return result;
	}

	private boolean equals(Vector3DCartesian vector) {
		double[] coord1 = getCartesianComponent();
		double[] coord2 = vector.getCartesianComponent();
		for (int i = 0; i < coord1.length; i++) {
			if (Math.abs(coord1[i] - coord2[i]) > Constants.MACHINE_DOUBLE_ERROR)
				return false;
		}
		return true;

	}

	private void checkDimension(Vector vector) {
		if (vector.getDimension() != this.getDimension())
			throw new IllegalArgumentException(
					"The two vectors have different dimensions, cannot have vector operation");
	}

	private void checkClass(Vector vector) {
		if (!supportedClass.contains(vector.getClass()))
			throw new IllegalArgumentException(
					vector.getClass().getName()+" is not supported for vector operation.");
	}

}
