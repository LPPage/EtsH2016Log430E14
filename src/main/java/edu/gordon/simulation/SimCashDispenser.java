/* * ATM Example system - file SimCashDispenser.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.simulation;import java.awt.Color;import java.awt.Font;import java.awt.GridLayout;import java.awt.Label;import java.awt.Panel;import edu.gordon.atm.physical.ICashDispenser;import edu.gordon.atm.physical.ILog;import edu.gordon.banking.Money;/** Simulate the cash dispenser */class SimCashDispenser extends Panel implements ICashDispenser{    /** Constructor     */    SimCashDispenser(SimLog log)    {    	this.log = log;    	        setLayout(new GridLayout(1,1));        label = new Label("$XXX.XX", Label.CENTER);        label.setFont(new Font("SansSerif", Font.PLAIN, 24));        label.setForeground(new Color(0, 64, 0));        add(label);        label.setVisible(false);    }        /** Animate dispensing cash to a customer     *     *  @param amount the amount of cash to dispense     *     *  Precondition: amount is <= cash on hand     */    public void animateDispensingCash(Money amount)    {        label.setText(amount.toString());        for (int size = 3; size <= 24; size += 1)        {             label.setFont(new Font("SansSerif", Font.PLAIN, size));            label.setVisible(true);            try            {                 Thread.sleep(250);            }            catch (InterruptedException e)            { }            label.setVisible(false);        }    }        /** Set the amount of cash initially on hand     *    *  @param initialCash the amount of money in the dispenser    */   public void setInitialCash(Money initialCash)   {       cashOnHand = initialCash;   }      /** See if there is enough cash on hand to satisfy a request    *    *  @param amount the amount of cash the customer wants    *  @return true if at least this amount of money is available    */   public boolean checkCashOnHand(Money amount)   {       return amount.lessEqual(cashOnHand);   }      /** Dispense cash to a customer    *    *  @param amount the amount of cash to dispense    *    *  Precondition: amount is <= cash on hand    */   public void dispenseCash(Money amount)   {       cashOnHand.subtract(amount);              this.animateDispensingCash(amount);              // Log cash having been dispensed              log.logCashDispensed(amount);   }   /** Log into which cash amounts dispensed will be recorded    */    private ILog log;      /** Current cash on hand    */   private Money cashOnHand;        /** Label that shows the amount of money being dispensed - starts out     *  small and grows to simulate cash coming out of the machine     */    private Label label;}                                       