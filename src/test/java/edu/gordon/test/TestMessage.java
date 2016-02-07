package edu.gordon.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Money;

public class TestMessage {
	private Message message;
	private Card carte;
	private Money argent;
	
	@Before
	public void faireAvant(){
		carte = new Card(123456);
		argent = new Money(7777);
		message = new Message(0, carte, 123, 123456, 789456, 456123, argent);
	}
	
	@Test
	public void returnMessageCode(){
		assertTrue(message.getMessageCode()==0);
	}
	
	@Test
	public void returnMessagePin(){
		assertTrue(message.getPIN()==123);
	}
	
	@Test
	public void returnSerialNumber(){
		assertTrue(message.getSerialNumber()==123456);
	}
	
	@Test
	public void returnFromAccount(){
		assertTrue(message.getFromAccount()==789456);
	}
	
	@Test
	public void returnToAccount(){
		assertTrue(message.getToAccount()==456123);
	}

}
