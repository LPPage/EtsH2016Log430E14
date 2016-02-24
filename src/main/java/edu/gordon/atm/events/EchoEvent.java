package edu.gordon.atm.events;

public class EchoEvent {
	
	private String echo;
	
	public EchoEvent(String echo){
		this.echo = echo;
	}

	public String getEcho() {
		return echo;
	}
}
