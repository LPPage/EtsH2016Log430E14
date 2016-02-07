package edu.gordon.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.gordon.atm.ATM;
import edu.gordon.banking.*;

public class TestCard {
	private Card carte;
	
	@Before
	public void faireAvant(){
		carte = new Card(9370707);
	}
	
	@Test
	public void numeroCarte(){
		assertTrue(carte.getNumber()==9370707);
	}
}
