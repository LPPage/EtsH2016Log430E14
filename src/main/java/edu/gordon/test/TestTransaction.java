package edu.gordon.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.gordon.atm.ATM;
import edu.gordon.atm.physical.ICustomerConsole.Cancelled;
import edu.gordon.atm.transaction.*;
import edu.gordon.atm.*;
import edu.gordon.banking.*;

public class TestTransaction {
	private Money argentTotal;
	private Money argentDisponible;
	private Balances solde;
	private ATM atm;
	private Session session;
	private Card carte;
	private	Deposit depot;
	private Transfer transfert;
	private Inquiry verifierCompte;
	private Withdrawal retrait;
	
	
	@Before
	public void faireAvant(){
		argentTotal = new Money(9000);
		argentDisponible = new Money(9000);
		solde = new Balances();
			solde.setBalances(argentTotal, argentDisponible);
		atm = new ATM(1,"Canada", "FakeBank");
		session = new Session(atm);
		carte = new Card(123456);
		depot = new Deposit(atm, session, carte, 456);
		transfert = new Transfer(atm, session, carte, 456);
		verifierCompte = new Inquiry(atm, session, carte, 456);
		retrait = new Withdrawal(atm, session, carte, 456);

	}
	
	@Test
	public void retrait(){
		//atm.
	}
}
