package edu.MD.number;

public class JavaDefaultNumber implements MDNumber {
	private double num;
	public static final JavaDefaultNumber ONE = new JavaDefaultNumber(1);
	public static final JavaDefaultNumber ZERO = new JavaDefaultNumber(0);
	

	public JavaDefaultNumber(double in) {
		num = in;
	}

	public JavaDefaultNumber(JavaDefaultNumber in) {
		num = in.num;
	}

	@Override
	public MDNumber abs() {
		return new JavaDefaultNumber(Math.abs(num));
	}

	@Override
	public MDNumber sqrt() {
		return new JavaDefaultNumber(Math.sqrt(num));
	}

	@Override
	public MDNumber add(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		return new JavaDefaultNumber(num + javaDefaultNumberIn.num);
	}

	@Override
	public MDNumber minus(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		return new JavaDefaultNumber(num - javaDefaultNumberIn.num);
	}

	@Override
	public MDNumber times(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		return new JavaDefaultNumber(num * javaDefaultNumberIn.num);
	}

	@Override
	public MDNumber divide(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		return new JavaDefaultNumber(num / javaDefaultNumberIn.num);
	}

	@Override
	public MDNumber pow(MDNumber in) {
		checkinput(in);
		JavaDefaultNumber javaDefaultNumberIn = (JavaDefaultNumber) in;
		return new JavaDefaultNumber(Math.pow(num, javaDefaultNumberIn.num));
	}

	@Override
	public MDNumber pow(int in) {
		return new JavaDefaultNumber(Math.pow(num, in));
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
		return ""+num;
	}

	@Override
	public int hashCode(){
		return ((Double) num).hashCode();
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (this==that) return true;
		if (!(that instanceof JavaDefaultNumber)) return false;
		JavaDefaultNumber input = (JavaDefaultNumber) that;
		return ((Double) num).equals(input.num);
	}

	@Override
	public double toDouble() {
		return num;
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
