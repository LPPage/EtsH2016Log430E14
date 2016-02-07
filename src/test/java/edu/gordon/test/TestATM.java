package edu.gordon.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gordon.atm.ATM;

public class TestATM {
	private ATM atm; 

	
	@Before
	public void faireAvant(){
		atm = new ATM(555, "testPlace", "testBank");
		
	}
	
	@Test
	public void switchOn(){
		atm.switchOn();
		
		assertTrue(atm.isSwitchOn());
	}

}
