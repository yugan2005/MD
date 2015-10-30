package edu.MD.number;

import edu.MD.utility.Constants;

public class JavaDefaultNumber implements MDNumber {
	private double numDouble;
	private int numInt;
	private boolean isDouble = false;
	public static final JavaDefaultNumber ONE = new JavaDefaultNumber(1);
	public static final JavaDefaultNumber ZERO = new JavaDefaultNumber(0);
	

	public JavaDefaultNumber(double in) {
		isDouble = true;
		numDouble = in;
	}

	public JavaDefaultNumber(int in) {
		numInt = in;
	}

	public JavaDefaultNumber(JavaDefaultNumber in) {
		numDouble = in.numDouble;
		numInt = in.numInt;
		isDouble = in.isDouble;
	}

	@Override
	public MDNumber abs() {
		if (isDouble)
			return new JavaDefaultNumber(Math.abs(numDouble));
		return new JavaDefaultNumber(Math.abs(numInt));
	}

	@Override
	public MDNumber sqrt() {
		if (isDouble)
			return new JavaDefaultNumber(Math.sqrt(numDouble));
		return new JavaDefaultNumber(Math.sqrt(numInt));
	}

	@Override
	public MDNumber add(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		if (javaDefaultNumberIn.isDouble) {
			if (isDouble)
				return new JavaDefaultNumber(numDouble + javaDefaultNumberIn.numDouble);
			return new JavaDefaultNumber(numInt + javaDefaultNumberIn.numDouble);
		}
		if (isDouble)
			return new JavaDefaultNumber(numDouble + javaDefaultNumberIn.numInt);
		return new JavaDefaultNumber(numInt + javaDefaultNumberIn.numInt);
	}

	@Override
	public MDNumber minus(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		if (javaDefaultNumberIn.isDouble) {
			if (isDouble)
				return new JavaDefaultNumber(numDouble - javaDefaultNumberIn.numDouble);
			return new JavaDefaultNumber(numInt - javaDefaultNumberIn.numDouble);
		}
		if (isDouble)
			return new JavaDefaultNumber(numDouble - javaDefaultNumberIn.numInt);
		return new JavaDefaultNumber(numInt - javaDefaultNumberIn.numInt);
	}

	@Override
	public MDNumber times(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		if (javaDefaultNumberIn.isDouble) {
			if (isDouble)
				return new JavaDefaultNumber(numDouble * javaDefaultNumberIn.numDouble);
			return new JavaDefaultNumber(numInt * javaDefaultNumberIn.numDouble);
		}
		if (isDouble)
			return new JavaDefaultNumber(numDouble * javaDefaultNumberIn.numInt);
		return new JavaDefaultNumber(numInt * javaDefaultNumberIn.numInt);
	}

	@Override
	public MDNumber divide(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		if (javaDefaultNumberIn.isDouble) {
			if (isDouble)
				return new JavaDefaultNumber(numDouble / javaDefaultNumberIn.numDouble);
			return new JavaDefaultNumber(numInt / javaDefaultNumberIn.numDouble);
		}
		if (isDouble)
			return new JavaDefaultNumber(numDouble / javaDefaultNumberIn.numInt);
		return new JavaDefaultNumber(numInt / javaDefaultNumberIn.numInt);
	}

	@Override
	public MDNumber pow(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		if (javaDefaultNumberIn.isDouble) {
			if (isDouble)
				return new JavaDefaultNumber(Math.pow(numDouble, javaDefaultNumberIn.numDouble));
			return new JavaDefaultNumber(Math.pow(numInt, javaDefaultNumberIn.numDouble));
		}
		if (isDouble)
			return new JavaDefaultNumber(Math.pow(numDouble, javaDefaultNumberIn.numInt));
		return new JavaDefaultNumber(Math.pow(numInt, javaDefaultNumberIn.numInt));
	}

	@Override
	public MDNumber pow(int in) {
		if (isDouble)
			return new JavaDefaultNumber(Math.pow(numDouble, in));
		return new JavaDefaultNumber(Math.pow(numInt, in));
	}

	@Override
	public int getPrecision() {
		throw new UnsupportedOperationException("The Java Default Numbers do not support the getPrecision method");
	}

	private void checkinput(MDNumber in) {
		if (!(in instanceof JavaDefaultNumber))
			throw new IllegalArgumentException("The number type is not compatible!");
	}
	
	@Override
	public String toString(){
		if (isDouble) return ""+numDouble;
		return ""+numInt;
	}

	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (this==that) return true;
		if (!(that instanceof JavaDefaultNumber)) return false;
		JavaDefaultNumber input = (JavaDefaultNumber) that;
		if (this.isDouble) {
			if (input.isDouble) return Math.abs(numDouble-input.numDouble)<Constants.MACHINE_DOUBLE_ERROR;
			else return Math.abs(numDouble-input.numInt)<Constants.MACHINE_DOUBLE_ERROR;
		}
		
		if (input.isDouble) return Math.abs(numInt-input.numDouble)<Constants.MACHINE_DOUBLE_ERROR;
		return numInt == input.numInt;
	}

	@Override
	public double toDouble() {
		if (isDouble) return numDouble;
		return (double) numInt;
	}

	@Override
	public int toInt() {
		if (isDouble) return (int) numDouble;
		return numInt;
	}

	@Override
	public MDNumber zero() {
		return ZERO;
	}

	@Override
	public MDNumber one() {
		return ONE;
	}

}
