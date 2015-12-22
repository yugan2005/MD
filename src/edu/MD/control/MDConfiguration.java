package edu.MD.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MDConfiguration {


	private IntegerProperty filmSize, filmThickness, vaporOneSideThickness, nDensityBin;
	private DoubleProperty temperature, timeStep, cutoff;
	private StringProperty fluid;
	
	public MDConfiguration(){
		filmSize = new SimpleIntegerProperty(5);
		filmThickness = new SimpleIntegerProperty(3);
		vaporOneSideThickness = new SimpleIntegerProperty(3);
		nDensityBin = new SimpleIntegerProperty(150);
		temperature = new SimpleDoubleProperty(100);
		timeStep = new SimpleDoubleProperty(5e-14);
		cutoff = new SimpleDoubleProperty(3.0);
		fluid = new SimpleStringProperty("ARGON");
	}
	
	public void reset(){
		filmSize.set(5);
		filmThickness.set(3);
		vaporOneSideThickness.set(3);
		nDensityBin.set(150);
		temperature.set(100);
		timeStep.set(5e-14);
		cutoff.set(3.0);
		fluid.set("ARGON");
	}

	
	
	public IntegerProperty filmSize() {
		return filmSize;
	}
	
	public int getFilmSize() {
		return filmSize.get();
	}
	
	public IntegerProperty filmThickness() {
		return filmThickness;
	}

	public int getFilmThickness() {
		return filmThickness.get();
	}
	
	
	public IntegerProperty vaporOneSideThickness() {
		return vaporOneSideThickness;
	}
	
	public int getVaporOneSideThickness() {
		return vaporOneSideThickness.get();
	}

	public IntegerProperty NDensityBin() {
		return nDensityBin;
	}

	public int getNDensityBin() {
		return nDensityBin.get();
	}
	
	public DoubleProperty temperature() {
		return temperature;
	}
	
	public double getTemperature() {
		return temperature.get();
	}

	public DoubleProperty timeStep() {
		return timeStep;
	}
	
	public double getTimeStep() {
		return timeStep.get();
	}

	public DoubleProperty cutoff() {
		return cutoff;
	}
	
	public double getCutoff() {
		return cutoff.get();
	}

	public StringProperty fluid() {
		return fluid;
	}
	
	public String getFluid() {
		return fluid.get();
	}

}
