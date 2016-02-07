package edu.gordon.atm.exception;

public class Cancelled extends Exception{
    /** Constructor
     */
    public Cancelled()
    {
        super("Cancelled by customer");
    }
}
