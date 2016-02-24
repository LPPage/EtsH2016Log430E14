package edu.gordon.atm.events;

import edu.gordon.banking.Money;

public class InitialCashEvent {

	private Money cash;

	public Money getCash() {
		return cash;
	}

	public void setCash(Money cash) {
		this.cash = cash;
	}
}
