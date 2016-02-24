package edu.gordon.atm.events;

import edu.gordon.banking.Card;

public class CardReadEvent {
	
	private Card card;

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
