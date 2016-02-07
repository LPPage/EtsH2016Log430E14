package edu.gordon.atm.common;

public class Cancelled extends Exception{
    /** Constructor
     */
    public Cancelled()
    {
        super("Cancelled by customer");
    }
}
