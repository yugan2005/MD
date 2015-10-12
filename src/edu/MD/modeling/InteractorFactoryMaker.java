package edu.MD.modeling;

public class InteractorFactoryMaker {
	private static AbstractInteractorFactory factory = null;
	private static String argumentList = "PAIRWISE, ";

	/**
	 * @param choice
	 *            Must be one of the type strings inside the argumentList field.
	 * @return The concrete Factory corresponding to the type String.
	 */
	static AbstractInteractorFactory getFactory(String choice) {
		switch (choice) {
		case "PAIRWISE":
			factory = new PairwiseInteractorFactory();
			break;
		default:
			throw new IllegalArgumentException("Invalid Interactor Factory Type. Available Type strings are: "
					+ System.lineSeparator() + argumentList);
		}

		return factory;

	}

}
