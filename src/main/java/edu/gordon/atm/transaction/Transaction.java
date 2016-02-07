/*
 * ATM Example system - file Transaction.java   
 *
 * copyright (c) 2001 - Russell C. Bjork
 *
 */
 
package edu.gordon.atm.transaction;

import edu.gordon.atm.common.Cancelled;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Receipt;
import edu.gordon.banking.Status;

/** Abstract base class for classes representing the various kinds of
 *  transaction the ATM can perform
 */
public abstract class Transaction
{
    /** Constructor
     *
     *  @param edu.gordon.atm the ATM used to communicate with customer
     *  @param session the session in which this transaction is being performed
     *  @param card the customer's card
     *  @param pin the PIN entered by the customer
     */
     
    protected Transaction(IATM atm, ISession session, Card card, int pin)
    {
        this.atm = atm;
        this.session = session;
        this.card = card;
        this.pin = pin;
        this.serialNumber = nextSerialNumber ++;
        this.balances = new Balances();
        
        state = GETTING_SPECIFICS_STATE;
    }
    
    /** Peform a transaction.  This method depends on the three abstract methods
     *  that follow to perform the operations unique to each type of transaction
     *  in the appropriate way.
     *
     *  @return true if customer indicates a desire to do another transaction;
     *          false if customer does not desire to do another transaction
     *  @exception CardRetained if card was retained due to too many invalid PIN's
     */
    public boolean performTransaction() throws CardRetained
    {
        String doAnotherMessage = "";
        Status status = null;
        Receipt receipt = null;
        
       
        
        while (true)    // Terminates by return in ASKING_DO_ANOTHER_STATE or exception
        {
            switch(state)
            {
                case GETTING_SPECIFICS_STATE:
                	 //TODO comment
                    System.out.println("performTransac***");
                    try
                    {   
                    	//TODO comment
                    	System.out.println("B4 message");
                        message = getSpecificsFromCustomer();
                        
                        //TODO comment
                        System.out.println("After message");
                        atm.getCustomerConsole().display("");
                        state = SENDING_TO_BANK_STATE;
                        
                        //TOOD
                        System.out.println("getSpecState FINISH");
                    }
                    catch(Cancelled e)
                    {
                        doAnotherMessage = "Last transaction was cancelled";
                        state = ASKING_DO_ANOTHER_STATE;
                    }
                    
                    break;
                    
                case SENDING_TO_BANK_STATE:
                                
                    status = atm.getNetworkToBank().sendMessage(message, balances);
                
                    if (status.isInvalidPIN())
                        state = INVALID_PIN_STATE;
                    else if (status.isSuccess())
                        state = COMPLETING_TRANSACTION_STATE;
                    else
                    {
                        doAnotherMessage = status.getMessage();
                        state = ASKING_DO_ANOTHER_STATE;
                    }
                    
                    break;
                
                case INVALID_PIN_STATE:
                
                    try
                    {
                        status = performInvalidPINExtension();
                    
                        // If customer repeatedly enters invalid PIN's, a
                        // CardRetained exception is thrown, and this method
                        // terminates
                        
                        if (status.isSuccess())
                            state = COMPLETING_TRANSACTION_STATE;
                        else
                        {
                            doAnotherMessage = status.getMessage();
                            state = ASKING_DO_ANOTHER_STATE;
                        }
                    }
                    catch(Cancelled e)
                    {
                        doAnotherMessage = "Last transaction was cancelled";
                        state = ASKING_DO_ANOTHER_STATE;
                    }

                    break;
                        
                case COMPLETING_TRANSACTION_STATE:

                    try
                    {
                        receipt = completeTransaction();
                        state = PRINTING_RECEIPT_STATE;
                    }
                    catch(Cancelled e)
                    {
                        doAnotherMessage = "Last transaction was cancelled";
                        state = ASKING_DO_ANOTHER_STATE;
                    }
                    
                    break;
                    
                case PRINTING_RECEIPT_STATE:
                System.out.println("REDO");
                    atm.getReceiptPrinter().printReceipt(receipt);
                    state = ASKING_DO_ANOTHER_STATE;
                    
                    break;
                    
                case ASKING_DO_ANOTHER_STATE:
                
                    if (doAnotherMessage.length() > 0)
                        doAnotherMessage += "\n";
                        
                    try
                    {
                        String [] yesNoMenu = { "Yes", "No" };

                        boolean doAgain = atm.getCustomerConsole().readMenuChoice(
                            doAnotherMessage + 
                            "Would you like to do another transaction?",
                            yesNoMenu) == 0;
                        return doAgain;
                    }
                    catch(Cancelled e)
                    {
                        return false;
                    }
            }
        }
    }
        
    
    /** Perform the Invalid PIN Extension - reset session pin to new value if successful
     *
     *  @return status code returned by bank from most recent re-submission
     *          of transaction
     *  @exception ICustomerConsole.Cancelled if customer presses the CANCEL key
     *             instead of re-entering PIN
     *  @exception CardRetained if card was retained due to too many invalid PIN's
     */
    public Status performInvalidPINExtension() throws Cancelled,
                                                      CardRetained
    {
        Status status = null;
        for (int i = 0; i < 3; i ++)
        {
            pin = atm.getCustomerConsole().readPIN(
                "PIN was incorrect\nPlease re-enter your PIN\n" +
                "Then press ENTER");
            atm.getCustomerConsole().display("");
            
            message.setPIN(pin);
            status = atm.getNetworkToBank().sendMessage(message, balances);
            if (! status.isInvalidPIN())
            {
                session.setPIN(pin);
                return status;
            }
        }
        
        atm.getCardReader().retainCard();
        atm.getCustomerConsole().display(
            "Your card has been retained\nPlease contact the bank.");
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        { }
        atm.getCustomerConsole().display("");
                
        throw new CardRetained();
    }
    

    /** Get serial number of this transaction
     *
     *  @return serial number
     */
    public int getSerialNumber()
    {
        return serialNumber;
    }
    
    /** Get specifics for the transaction from the customer - each
     *  subclass must implement this appropriately.
     *
     *  @return message to bank for initiating this transaction
     *  @exception ICustomerConsole.Cancelled if customer cancelled this transaction
     */
    protected abstract Message getSpecificsFromCustomer() throws Cancelled;
    
    /** Complete an approved transaction  - each subclass must implement
     *  this appropriately.
     *
     *  @return receipt to be printed for this transaction
     *  @exception ICustomerConsole.Cancelled if customer cancelled this transaction
     */
    public abstract Receipt completeTransaction() throws Cancelled;
    
    
    // Local class representing card retained exception
   
    
    /** Exception that is thrown when the customer's card is retained due to too
     *  many invalid PIN entries
     */
    public static class CardRetained extends Exception
    {
        /** Constructor
         */
        public CardRetained()
        {
            super("Card retained due to too many invalid PINs");
        }
    }
    
    
    
    // Instance variables


    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/** ATM to use for communication with the customer
     */
    protected IATM atm;
    
    /** Session in which this transaction is being performed
     */
    protected ISession session;
    
    /** Customer card for the session this transaction is part of
     */
    protected Card card;
    
    /** PIN entered or re-entered by customer
     */
    protected int pin;
    
    /** Serial number of this transaction
     */
    protected int serialNumber;
    
    /** Message to bank describing this transaction
     */
    protected Message message;
    
    /** Used to return account balances from the bank
     */
    protected Balances balances;
        
    /** Next serial number - used to assign a unique serial number to
     *  each transaction
     */
    private static int nextSerialNumber = 1;
    
    /** The current state of the transaction
     */
    private int state;
    
    // Possible values for state
    
    /** Getting specifics of the transaction from customer
     */
    private static final int GETTING_SPECIFICS_STATE = 1;
    
    /** Sending transaction to bank
     */
    private static final int SENDING_TO_BANK_STATE = 2;
    
    /** Performing invalid PIN extension
     */
    private static final int INVALID_PIN_STATE = 3;
    
    /** Completing transaction
     */
    private static final int COMPLETING_TRANSACTION_STATE = 4;
    
    /** Printing receipt
     */
    private static final int PRINTING_RECEIPT_STATE = 5;
    
    /** Asking if customer wants to do another transaction
     */
    private static final int ASKING_DO_ANOTHER_STATE = 6;
}
