package edu.MD.utility;

public abstract class MDNumbers {
	
	public abstract MDNumbers abs(MDNumbers in);
	public abstract MDNumbers sqrt(MDNumbers in);
	public abstract MDNumbers add(MDNumbers in);
	public abstract MDNumbers minus(MDNumbers in);
	public abstract MDNumbers times(MDNumbers in);
	public abstract MDNumbers divide(MDNumbers in);
	public abstract MDNumbers pow(MDNumbers in);
	public abstract MDNumbers pow(int in);
	
	public static MDNumbers getNum(int in){
		return valueOf(in);
	}
	public abstract MDNumbers valueOf(double in);
	
	public abstract MDNumbers valueOf(int in);

}
