package edu.gordon.atm.common;

public abstract class Modes {
    /** Not currently reading input - ignore keys (except CANCEL)
     */
    public static final int IDLE_MODE = 0;
    
    /** Read input in PIN mode - allow user to enter several characters,
     *  and to clear the line if the user wishes; echo as asterisks
     */
    public static final int PIN_MODE = 1;
    
    /** Read input in amount mode - allow user to enter several characters,
     *  and to clear the line if the user wishes; echo what use types
     */
    public static final int AMOUNT_MODE = 2;
    
    /** Read input in menu choice mode - wait for one digit key to be pressed,
     *  and return value immediately.
     */
    public static final int MENU_MODE = 3;
}
