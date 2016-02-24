package edu.gordon.atm.events;

public class PrintLogEvent {

	private String message;
	
	
	public PrintLogEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


}
