package edu.gordon.atm.events;

public class ReadInputEvent {
	
	private int maxValue;
	private int mode;
	private String input;

	public ReadInputEvent(int mode, int maxValue) {
		this.mode = mode;
		this.maxValue = maxValue;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public int getMode() {
		return mode;
	}
}
