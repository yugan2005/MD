package edu.MD.DAO;

import edu.MD.application.ArgonMDSimualtion;
import edu.MD.application.MDSimulation;
import javafx.concurrent.Service;

public class MDDataObject {
	
	private final MDSimulation simulation = new ArgonMDSimualtion();
	private final static MDDataObject instance = new MDDataObject();

	public static MDDataObject getInstance() {
		return instance;
	}

	public double[] getSystemBoundary() {
		// TODO Auto-generated method stub
		return null;
	}

	public double[][] getPositions() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getParticleNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Service<double[][]> getWorker() {
		// TODO Auto-generated method stub
		return null;
	}

}
