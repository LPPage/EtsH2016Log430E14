package edu.gordon.test;

import org.junit.Before;

import edu.gordon.atm.ATM;
import edu.gordon.atm.Session;
import edu.gordon.atm.TransactionFactory;
import edu.gordon.atm.display.Display;
import edu.gordon.atm.transaction.Deposit;
import edu.gordon.atm.transaction.Inquiry;
import edu.gordon.atm.transaction.Transaction;
import edu.gordon.atm.transaction.Transfer;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Card;
import edu.gordon.banking.Money;

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
	private Transaction transaction;
	
	private Display display;
	
	
	@Before
	public void faireAvant(){
		argentTotal = new Money(9000);
		argentDisponible = new Money(9000);
		solde = new Balances();
			solde.setBalances(argentTotal, argentDisponible);
		atm = new ATM(1,"Canada", "FakeBank");
		
		session = new Session(atm.getCardReader(), atm.getCustomerConsole(), new TransactionFactory(atm));
		carte = new Card(123456);
		depot = new Deposit(atm, session, carte, 456);
		transfert = new Transfer(atm, session, carte, 456);
		verifierCompte = new Inquiry(atm, session, carte, 456);
		retrait = new Withdrawal(atm, session, carte, 456);
		
		display = new Display(9, 45);
		//message = new Message(2, carte, 456, 123456, 11111, 22222, argentTotal);
		//infoCompte = new AccountInformation();
	}
	
	
}
