package edu.gordon.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gordon.banking.Card;

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
