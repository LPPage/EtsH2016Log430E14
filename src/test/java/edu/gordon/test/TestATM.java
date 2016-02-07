package edu.gordon.test;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import edu.gordon.atm.*;

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
