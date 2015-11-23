package edu.MD.initializationBD;

import java.util.ArrayList;
import java.util.List;

import edu.MD.numberBD.MDNumber;
import edu.MD.statThermodynamicBD.MonatomicSysTemperatureCalculator;
import edu.MD.utilityBD.MDConstants;
import edu.MD.utilityBD.MDVector;
import edu.MD.utilityBD.Vector3DCartesian;

public class MonatomicVelocityInitializer {
	private List<MDVector> velocities;
	private MDNumber mass;
	private MDNumber characteristicVelocity;
	
	
	public MonatomicVelocityInitializer(String name, int totalNumParticles, double temperature){
		this.mass = MDConstants.getMass(name);
		characteristicVelocity = mass.pow(-1).times(MDConstants.KB*temperature).pow(0.5);
		velocities = new ArrayList<>(totalNumParticles);
		
		for (int i=0; i<totalNumParticles; i++){
			double c1 = Math.sqrt(-2*Math.log(Math.random())); // Thesis (3.13)
			double c2 = 2*Math.PI*Math.random();
			MDNumber vX = characteristicVelocity.times(c1*Math.cos(c2));
			MDNumber vY = characteristicVelocity.times(c1*Math.sin(c2));
			c1 = Math.sqrt(-2*Math.log(Math.random()));
			c2 = 2*Math.PI*Math.random();
			MDNumber vZ = characteristicVelocity.times(c1*Math.cos(c2));
			velocities.add(new Vector3DCartesian(vX, vY, vZ));
		}
		
		// due to the statistical distribution, this need be rescaled to really match the temperature specified.
		MDNumber calculatedTemperature = MonatomicSysTemperatureCalculator.calculate(velocities, name);
		MDNumber rescaleFactor = calculatedTemperature.pow(-1).times(temperature).sqrt();
		List<MDVector> newVelocities = new ArrayList<>(totalNumParticles);

		for (MDVector velocity:velocities){
			newVelocities.add(velocity.times(rescaleFactor));
		}
		velocities = newVelocities;
	}
	
	public List<MDVector> getVelocities(){
		return velocities;
	}

}
