package edu.gordon.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.gordon.atm.ATM;
import edu.gordon.atm.display.Display;
import edu.gordon.atm.display.Display.ReadInputCallback;
import edu.gordon.atm.physical.ICashDispenser;
import edu.gordon.atm.physical.ICustomerConsole;
import edu.gordon.atm.physical.INetworkToBank;
import edu.gordon.atm.physical.IReceiptPrinter;
import edu.gordon.atm.physical.ICustomerConsole.Cancelled;
import edu.gordon.atm.transaction.*;
import edu.gordon.atm.transaction.Transaction.CardRetained;
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
	private Transaction transaction;
	
	private Display display;
	private Message message;
	
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
		
		display = new Display(9, 45);
		message = new Message(2, carte, 456, 123456, 11111, 22222, argentTotal);
		//infoCompte = new AccountInformation();
	}
	
	@Test
	public void performWithdrawal() throws Cancelled, CardRetained{
	/*	transaction.getState(0);
		transaction.performTransaction();*/
		atm.setCustomerConsole(display);
		atm.setCashDispenser(new ICashDispenser() {
			
			public void setInitialCash(Money initialCash) {}
			
			public void dispenseCash(Money amount) {}
			
			public boolean checkCashOnHand(Money amount) {
				
				return true;
			}
		});
		
		String[] testMenu = new String[4];
			testMenu[0]="Withdrawal";
			testMenu[1]="Deposit";
			testMenu[2]="Transfer";
			testMenu[3]="Balance Inquiry";
			
		display.setReadInputCallback(new ReadInputCallback(){
			public String readInputCallbackReceived(int mode, int maxValue) {
				return "1";
			}
		});
				
		display.readMenuChoice("TestPrompt", testMenu);
		
		//TODO comment
		System.out.println("makeTransaction WILL BE called");
		
		transaction  = transaction.makeTransaction(atm, session, carte, 456);System.out.println("yep yep");
		
		//TODO comment
    	System.out.println("B4 assertTrue");
    	
    	atm.setNetworkToBank(new INetworkToBank() {
			
			public Status sendMessage(Message message, Balances balances) {
				return new Status(){

					@Override
					public boolean isSuccess() {
						return true;
					}

					@Override
					public boolean isInvalidPIN() {
						return false;
					}

					@Override
					public String getMessage() {
						return "Success!";
					}
				};
			}
			
			public void openConnection() {
				// TODO Auto-generated method stub
				
			}
			
			public void closeConnection() {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	atm.setReceiptPrinter(new IReceiptPrinter() {
			
			public void printReceipt(Receipt receipt) {
				
			}
		});
    	
    	/*transaction.performTransaction();
		System.out.println("TEST END");*/
    	assertTrue(transaction.performTransaction());
		
		
		
	}
}
