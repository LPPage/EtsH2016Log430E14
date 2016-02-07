package edu.gordon.atm;

import edu.gordon.atm.common.Cancelled;
import edu.gordon.atm.physical.ICustomerConsole;
import edu.gordon.atm.transaction.Deposit;
import edu.gordon.atm.transaction.ISession;
import edu.gordon.atm.transaction.Inquiry;
import edu.gordon.atm.transaction.Transaction;
import edu.gordon.atm.transaction.Transfer;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Card;

public class TransactionFactory {
	public TransactionFactory(ATM atm)
	{
		this.atm = atm;
	}
	
    /** Create a transaction of an appropriate type by asking the customer
     *  what type of transaction is desired and then returning a newly-created
     *  member of the appropriate subclass
     *
     *  @param edu.gordon.atm the ATM used to communicate with customer
     *  @param session the session in which this transaction is being performed
     *  @param card the customer's card
     *  @param pin the PIN entered by the customer
     *  @return a newly created Transaction object of the appropriate type
     *  @exception ICustomerConsole.Cancelled if the customer presses cancel instead
     *         of choosing a transaction type
     */
    public Transaction makeTransaction( ISession session,
                                              Card card, int pin)
                                throws Cancelled              
    {
        int choice = atm.getCustomerConsole().readMenuChoice(
                "Please choose transaction type", TRANSACTION_TYPES_MENU);
                
        switch(choice)
        {
            case 0:
            
                return new Withdrawal(atm, session, card, pin);
                
            case 1:

            	return new Deposit(atm, session, card, pin);
                
            case 2:
            
                return new Transfer(atm, session, card, pin);
                
            case 3:
            
                return new Inquiry(atm, session, card, pin);
                
            default:
            
                return null;    // To keep compiler happy - should not happen!
        }
    }
    
    /** List of available transaction types to display as a menu
     */
    private static final String [] TRANSACTION_TYPES_MENU = 
        { "Withdrawal", "Deposit", "Transfer", "Balance Inquiry" };
    
    private ATM atm;
}
