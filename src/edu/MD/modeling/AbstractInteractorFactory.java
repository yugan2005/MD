package edu.MD.modeling;

public abstract class AbstractInteractorFactory {
	abstract IFinder getFinder();
	abstract IInteractionCalculator getInteractionCalculator();
}
