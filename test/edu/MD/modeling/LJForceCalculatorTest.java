package edu.MD.modeling;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.MD.utility.MDVector;
import edu.MD.utility.Vector3DCartesian;

public class LJForceCalculatorTest {
	Particle p1, p2;
	ISystem system;
	IPairWiseForceCalculator forceCalculator;
	
	@Before
	public void init(){
		p1 = new Argon(new Vector3DCartesian(new double[]{0,0,0}));
		p2 = new Argon(new Vector3DCartesian(new double[]{0,0,0}));
		system = new ArgonPairwiseBaseSystem();
		forceCalculator = LJForceCalculator.INSTANCE;
	}

	@Test
	public void p1ForceOnP2IsOppositeOfP2ForceOnP1() {
		MDVector p1ForceOnP2 = forceCalculator.calculate(p2, p1, system);
		MDVector p2ForceOnP1 = forceCalculator.calculate(p1, p2, system);
		assertThat(p1ForceOnP2.multiply(-1), equalTo(p2ForceOnP1));		
	}

}
