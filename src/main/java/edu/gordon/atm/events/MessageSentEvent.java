package edu.gordon.atm.events;

import edu.gordon.banking.Status;

public class MessageSentEvent {
	
	private Status result;

	public MessageSentEvent(Status result) {
		this.result = result;
	}

	public Status getResult() {
		return result;
	}
}
