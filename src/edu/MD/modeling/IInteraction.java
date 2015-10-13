package edu.MD.modeling;

public interface IInteraction {
	
	// TODO  bad design desicison dependency on higher level object
	public IUpdater interaction(ISystem particleSystem);

}
