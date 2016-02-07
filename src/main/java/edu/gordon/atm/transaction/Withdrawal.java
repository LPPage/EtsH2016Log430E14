/* * ATM Example system - file Withdrawal.java     * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.atm.transaction;import edu.gordon.atm.common.Cancelled;import edu.gordon.banking.AccountInformation;import edu.gordon.banking.Card;import edu.gordon.banking.Message;import edu.gordon.banking.Money;import edu.gordon.banking.Receipt;/** Representation for a cash withdrawal transaction */public class Withdrawal extends Transaction{    /** Constructor     *     *  @param edu.gordon.atm the ATM used to communicate with customer     *  @param session the session in which the transaction is being performed     *  @param card the customer's card     *  @param pin the PIN entered by the customer     */    public Withdrawal(IATM atm, ISession session, Card card, int pin)    {        super(atm, session, card, pin);    }        /** Get specifics for the transaction from the customer     *     *  @return message to bank for initiating this transaction     *  @exception ICustomerConsole.Cancelled if customer cancelled this transaction     */    protected Message getSpecificsFromCustomer() throws Cancelled    {    	            	from = atm.getCustomerConsole().readMenuChoice(            "Account to withdraw from",            AccountInformation.ACCOUNT_NAMES);    	        String [] amountOptions = { "$20", "$40", "$60", "$100", "$200" };        Money [] amountValues = {                                   new Money(20), new Money(40), new Money(60),                                  new Money(100), new Money(200)                                };             String amountMessage = "";        boolean validAmount = false;             while (! validAmount)        {            amount = amountValues [                 atm.getCustomerConsole().readMenuChoice(                    amountMessage + "Amount of cash to withdraw", amountOptions) ];                       //TODO comment        	System.out.println("SPECIFICS");                        validAmount = atm.getCashDispenser().checkCashOnHand(amount);                       if (! validAmount)                amountMessage = "Insufficient cash available\n";        }                return new Message(Message.WITHDRAWAL,                            card, pin, serialNumber, from, -1, amount);    }        /** Complete an approved transaction     *     *  @return receipt to be printed for this transaction     */    public Receipt completeTransaction()    {    	    	//TODO comment    	    	        atm.getCashDispenser().dispenseCash(amount);System.out.println("completeTrans");        return new Receipt(atm.getBankName(), atm.getID(), atm.getPlace(), card, getSerialNumber(), balances) {            {                detailsPortion = new String[2];                detailsPortion[0] = "WITHDRAWAL FROM: " +                                     AccountInformation.ACCOUNT_ABBREVIATIONS[from];                detailsPortion[1] = "AMOUNT: " + amount.toString();            }        };    }        /** Account to withdraw from     */     private int from;        /** Amount of money to withdraw     */    private Money amount;           }