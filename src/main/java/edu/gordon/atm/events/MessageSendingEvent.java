package edu.gordon.atm.events;

import edu.gordon.banking.Message;

public class MessageSendingEvent {
	
	private Message message;
	
	public MessageSendingEvent(Message message){
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}
}
