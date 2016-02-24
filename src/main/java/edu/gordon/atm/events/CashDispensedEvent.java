package edu.gordon.atm.events;

import edu.gordon.banking.Money;

public class CashDispensedEvent {
	
	private Money amount;
	
	public CashDispensedEvent(Money amount){
		this.amount = amount;
	}

	public Money getAmount() {
		return amount;
	}
}
