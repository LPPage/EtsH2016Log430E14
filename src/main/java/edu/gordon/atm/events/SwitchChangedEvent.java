package edu.gordon.atm.events;

public class SwitchChangedEvent {
	
	private boolean on;
	
	public SwitchChangedEvent(boolean on){
		this.on = on;
	}

	public boolean isOn() {
		return on;
	}
}
