package edu.MD.initializationBD;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.MD.modelingBD.Particle;
import edu.MD.modelingBD.ParticleFactory;
import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;
import edu.MD.statThermodynamicBD.MonatomicSysTemperatureCalculator;
import edu.MD.utilityBD.MDVector;

public class MonatomicVelocityInitializerTest {
	private MonatomicVelocityInitializer initializer;
	private String name;
	private double temperature;
	private int numOfParticles;
	private List<Particle> particles;
	
	@BeforeClass
	public static void globalInit() {
		try {
			NumberFactory.getInstance();
		} catch (UnsupportedOperationException ex) {
			NumberFactory.setFactorySetting("JavaDefaultNumberFactory");
		}
	}
	
	@Before
	public void init() {
		name = "ARGON";
		int filmThickness = 5;
		int filmSize = 10;
		int vaporOneSideThickness = 8;
		temperature = 110;
		numOfParticles = new MonatomicPositionInitializer(name, filmThickness, filmSize, vaporOneSideThickness, temperature).getTotalNumberOfParticles();
		initializer = new MonatomicVelocityInitializer(name, numOfParticles, temperature);
		List<MDVector> velocities = initializer.getVelocities();
		particles = new ArrayList<>(numOfParticles);

		for (MDVector velocity:velocities){
			Particle particle = ParticleFactory.getParticle(name);
			particle.setInitialVelocity(velocity);
			particles.add(particle);
		}
	}

	@Test
	public void initalVelocityIsConsistantWithTemperature() {
		MDNumber calculatedTemperature = MonatomicSysTemperatureCalculator.calculate(particles);
		assertTrue(calculatedTemperature.approximateEqual(temperature));
	}

}
