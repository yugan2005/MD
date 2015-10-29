package edu.MD.utility;

public interface MDNumber {

	public MDNumber abs();

	public MDNumber sqrt();

	public MDNumber add(MDNumber in);

	public MDNumber minus(MDNumber in);

	public MDNumber times(MDNumber in);

	public MDNumber divide(MDNumber in);

	public MDNumber pow(MDNumber in);

	public MDNumber pow(int in);
	
	public int getPrecision();
	
	@Override
	public String toString();
	
	public boolean equals(MDNumber that);
	
	public double toDouble();
	
	public int toInt();
}
