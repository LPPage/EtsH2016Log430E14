package edu.gordon.test;

import org.junit.Before;

import edu.gordon.atm.ATM;
import edu.gordon.atm.Session;
import edu.gordon.atm.TransactionFactory;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Card;

public class TestWithdrawal {
	private Withdrawal retrait;
	private Session session;
	private ATM atm;
	private Card carte;
	
	@Before
	public void faireAvant(){
		atm = new ATM(1, "Canada", "FakeBank");
		session = new Session(atm.getCardReader(), atm.getCustomerConsole(), new TransactionFactory(atm));
		carte = new Card(123456);
		retrait = new Withdrawal(atm, session, carte, 430);
	}
	
	

}
