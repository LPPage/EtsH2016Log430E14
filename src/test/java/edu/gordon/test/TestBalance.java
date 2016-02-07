package edu.gordon.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gordon.banking.Balances;
import edu.gordon.banking.Money;

public class TestBalance {
	private Balances balance;
	private Money money1;
	private Money money2;
	
	@Before
	public void faireAvant(){
		balance = new Balances();
		money1 = new Money(5000);
		money2 = new Money(999);
		
		balance.setBalances(money1, money2);
	}
	
	@Test
	public void addMoney(){
		money1.add(money2); //On ajoute $999
		assertTrue(balance.getTotal().toString().equals("$5999.00"));
	}
	
	@Test
	public void returnTotal(){
		assertTrue(balance.getTotal().toString().equals("$5000.00"));
	}
	
	@Test
	public void returnAvailable(){
		assertTrue(balance.getAvailable().toString().equals("$999.00"));
	}

}
