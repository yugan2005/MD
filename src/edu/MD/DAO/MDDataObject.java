package edu.MD.DAO;

import edu.MD.application.ArgonMDSimualtion;
import edu.MD.application.MDSimulation;
import edu.MD.globalSetting.NumberFactorySetting;
import edu.MD.number.MDNumber;
import edu.MD.number.MDVector;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class MDDataObject {
	
	private MDSimulation simulation;
	private ScheduledService<double[][]> worker;
	private double[][] positions;
	private int particleNumber;
	private double[] systemBoundary;


	public MDDataObject(MDSimulation model) {
		NumberFactorySetting.set();

		this.simulation = model;
		this.particleNumber = simulation.getParticleNumber();
		this.positions = simulation.getPostions();
		this.systemBoundary = simulation.getSystemBoundary();
		worker = new ScheduledService<double[][]>(){
			@Override
			protected Task<double[][]> createTask() {
				return new Task<double[][]>() {

					@Override
					protected double[][] call() throws Exception {
						double[][] newPosition = new double[3][particleNumber];
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < particleNumber; j++) {
								newPosition[i][j] = positions[i][j] + 10*(Math.random()-0.5);
							}
						}
						positions = newPosition;
						return newPosition;
					}

				};
			}
			
		};
		worker.setPeriod(Duration.millis(40));
	}
	
	public MDDataObject(){
		this(new ArgonMDSimualtion());
	}

	public double[] getSystemBoundary() {
		MDNumber[] boundVector = simulation.getSystemBoundary().getCartesianComponent();
		double[] bound = new double[boundVector.length];
		for (int i=0; i<bound.length; i++){
			bound[i]=boundVector[i].toDouble();
		}
		return bound;
	}

	public double[][] getPositions() {
		return positions;
	}

	public int getParticleNumber() {
		return particleNumber;
	}

	public Service<double[][]> getWorker() {
		return worker;
	}

}
