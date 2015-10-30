package edu.MD.number;

public interface MDNumber {

	public MDNumber abs();

	public MDNumber sqrt();

	public MDNumber add(MDNumber in);

	public MDNumber minus(MDNumber in);

	public MDNumber times(MDNumber in);

	public MDNumber divide(MDNumber in);

	public MDNumber pow(MDNumber in);

	public MDNumber pow(int in);
	
	public MDNumber zero();
	
	public MDNumber one();
	
	public int getPrecision();
	
	@Override
	public String toString();
	
	@Override
	public boolean equals(Object that);
	
	public double toDouble();
	
	public int toInt();
}
