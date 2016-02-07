package edu.gordon.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.gordon.atm.ATM;
import edu.gordon.atm.transaction.*;
import edu.gordon.atm.*;
import edu.gordon.banking.*;

public class TestWithdrawal {
	private Withdrawal retrait;
	private Session session;
	private ATM atm;
	private Card carte;
	
	@Before
	public void faireAvant(){
		atm = new ATM(1, "Canada", "FakeBank");
		session =  new Session(atm);
		carte = new Card(123456);
		retrait = new Withdrawal(atm, session, carte, 430);
	}
	
	

}
