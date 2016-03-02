package edu.gordon.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import edu.gordon.atm.ATM;
import edu.gordon.atm.Session;
import edu.gordon.atm.TransactionFactory;
import edu.gordon.atm.common.Cancelled;
import edu.gordon.atm.display.Display;
import edu.gordon.atm.events.ReadInputEvent;
import edu.gordon.atm.physical.ICashDispenser;
import edu.gordon.atm.physical.IEnvelopeAcceptor;
import edu.gordon.atm.physical.INetworkToBank;
import edu.gordon.atm.physical.IReceiptPrinter;
import edu.gordon.atm.transaction.Deposit;
import edu.gordon.atm.transaction.Inquiry;
import edu.gordon.atm.transaction.Transaction;
import edu.gordon.atm.transaction.Transaction.CardRetained;
import edu.gordon.atm.transaction.Transfer;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Money;
import edu.gordon.banking.Receipt;
import edu.gordon.banking.Status;

public class TestOperation {
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
	private EventBus eventbus;
	private String nextInput;
	
	private Display display;
	private Message message;
	
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
		eventbus=atm.getEventBus();
		eventbus.register(this);
		display = new Display(eventbus, 9, 45);
		message = new Message(2, carte, 456, 123456, 11111, 22222, argentTotal);
			}
	
	@Test
	public void performWithdrawal() throws Cancelled, CardRetained{
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
			
			nextInput="1";
				
		display.readMenuChoice("TestPrompt", testMenu);

		
		TransactionFactory transactionFactory = new TransactionFactory(atm);
		
		transaction  = transactionFactory.makeTransaction(session, carte, 456);
    	
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

    	assertTrue(transaction.performTransaction());

	}
	
	@Test
	public void performDeposit() throws Cancelled, CardRetained{
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
			
			nextInput="2";
			
		display.readMenuChoice("TestPrompt", testMenu);
		
		
		TransactionFactory transactionFactory = new TransactionFactory(atm);
		
		transaction  = transactionFactory.makeTransaction(session, carte, 456);
		
    	
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
    	
    	atm.setEnvelopeAcceptor(new IEnvelopeAcceptor() {
			
			public void acceptEnvelope() throws Cancelled {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	nextInput="1";
    	
    	assertTrue(transaction.performTransaction());

	}
	
	@Test
	public void performTransfer() throws Cancelled, CardRetained{
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
			
			nextInput="3";
				
		display.readMenuChoice("TestPrompt", testMenu);

		
		TransactionFactory transactionFactory = new TransactionFactory(atm);
		
		transaction  = transactionFactory.makeTransaction(session, carte, 456);

    	
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
    	
    	atm.setEnvelopeAcceptor(new IEnvelopeAcceptor() {
			
			public void acceptEnvelope() throws Cancelled {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	nextInput="1";
    	
    	assertTrue(transaction.performTransaction());

	}
	
	@Test
	public void performInquiry() throws Cancelled, CardRetained{
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
			
			nextInput="4";
				
		display.readMenuChoice("TestPrompt", testMenu);

		
		TransactionFactory transactionFactory = new TransactionFactory(atm);
		
		transaction  = transactionFactory.makeTransaction(session, carte, 456);

    	
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
    	
    	atm.setEnvelopeAcceptor(new IEnvelopeAcceptor() {
			
			public void acceptEnvelope() throws Cancelled {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	nextInput="1";
    	
    	assertTrue(transaction.performTransaction());

	}
	
	@Subscribe
	public void readInput(ReadInputEvent event){
		event.setInput(nextInput);
	}
}
