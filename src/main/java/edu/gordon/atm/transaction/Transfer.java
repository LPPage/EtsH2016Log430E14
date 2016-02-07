/* * ATM Example system - file Transfer.java   * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.atm.transaction;import edu.gordon.atm.common.Cancelled;import edu.gordon.banking.AccountInformation;import edu.gordon.banking.Card;import edu.gordon.banking.Message;import edu.gordon.banking.Money;import edu.gordon.banking.Receipt;/** Representation for a transfer transaction */public class Transfer extends Transaction{    /** Constructor     *     *  @param edu.gordon.atm the ATM used to communicate with customer     *  @param session the session in which the transaction is being performed     *  @param card the customer's card     *  @param pin the PIN entered by the customer     */    public Transfer(IATM atm, ISession session, Card card, int pin)    {        super(atm, session, card, pin);    }        /** Get specifics for the transaction from the customer     *     *  @return message to bank for initiating this transaction     *  @exception ICustomerConsole.Cancelled if customer cancelled this transaction     */    protected Message getSpecificsFromCustomer() throws Cancelled    {        from = atm.getCustomerConsole().readMenuChoice(            "Account to transfer from",            AccountInformation.ACCOUNT_NAMES);        to = atm.getCustomerConsole().readMenuChoice(            "Account to transfer to",            AccountInformation.ACCOUNT_NAMES);        amount = atm.getCustomerConsole().readAmount("Amount to transfer");                return new Message(Message.TRANSFER,                         card, pin, serialNumber, from, to, amount);    }        /** Complete an approved transaction     *     *  @return receipt to be printed for this transaction     */    public Receipt completeTransaction()    {        return new Receipt(atm.getBankName(), atm.getID(), atm.getPlace(), card, getSerialNumber(), balances) {            {                detailsPortion = new String[2];                detailsPortion[0] = "TRANSFER FROM: " +                                     AccountInformation.ACCOUNT_ABBREVIATIONS[from] +                                    " TO: " +                                     AccountInformation.ACCOUNT_ABBREVIATIONS[to] ;                detailsPortion[1] = "AMOUNT: " + amount.toString();            }        };    }        /** Accounts to transfer from     */    private int from;        /** Account to transfer to     */    private int to;        /** Amount of money to transfer     */    private Money amount;           }