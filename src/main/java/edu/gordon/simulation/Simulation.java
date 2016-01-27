/* * ATM Example system - file Simulation.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.simulation;import edu.gordon.atm.ATM;import edu.gordon.atm.display.Display;import edu.gordon.atm.display.Display.ReadInputCallback;import edu.gordon.banking.Card;import edu.gordon.banking.Money;import edu.gordon.simulation.SimKeyboard.EchoListener;/** Simulation of the physical components of the ATM, including its network  *  connection to the bank.  An instance is created at startup by either the *  application's main() program or the applet's init() method. * *  The individual components are displayed in a panel belonging to class GUI. *  The bank is simulated by an object belonging to class SimulatedBank.  The *  constructor for this class creates one instance of each. * *  The static method getInstance() allows components of the ATM to access the one *  and only instance of this class in order to simulate various operations.  The *  remaining methods simulate specific operations of the ATM, and are forwarded *  to either the GUI panel or the simulated bank to actually carry them out. */public class Simulation{    public Simulation(ATM atm)    {        this.atm = atm;                // Create the simulated individual components of the ATM's GUI        log = new SimLog(this);        operatorPanel = new SimOperatorPanel(this);        cardReader = new SimCardReader(this);        cashDispenser = new SimCashDispenser(log);        envelopeAcceptor = new SimEnvelopeAcceptor(log);        display = new Display(ATMPanel.DISPLAYABLE_LINES, ATMPanel.DISPLAYABLE_COLUMNS);        keyboard = new SimKeyboard(envelopeAcceptor);        simulatedBank = new SimulatedBank(log);        receiptPrinter = new SimReceiptPrinter();                  // Listen to the keyboard's echo event        keyboard.addEchoListener(new EchoListener(){			public void echoEventReceived(String echo) {				display.setEcho(echo);			}        });                // Provide an input source callback to the display        display.setReadInputCallback(new ReadInputCallback(){			public String readInputCallbackReceived(int mode, int maxValue) {				return keyboard.readInput(mode, maxValue);			}        });                // Assign the concrete component implementations to the ATM        atm.setLog(log);        atm.setOperatorPanel(operatorPanel);        atm.setCardReader(cardReader);        atm.setCashDispenser(cashDispenser);        atm.setEnvelopeAcceptor(envelopeAcceptor);        atm.setCustomerConsole(display);        atm.setNetworkToBank(simulatedBank);         atm.setReceiptPrinter(receiptPrinter);                // Create the GUI containing the above                gui = new GUI(operatorPanel, cardReader, display, keyboard,                      cashDispenser, envelopeAcceptor, receiptPrinter);    }        /** Simulated getting initial amount of cash from operator     *     *  @return value of initial cash entered     */    public Money getInitialCash()    {        return gui.getInitialCash();    }        /** Simulate reading of a card     *     *     *  @return Card object representing information on the card if read     *          successfully, null if not read successfully     */    public Card readCard()    {        // Machine can't be turned off while there is a card in it        operatorPanel.setEnabled(false);        cardReader.animateInsertion();                // Since we don't have a magnetic stripe reader, we'll simulate by        // having customer type the card number in                return gui.readCard();    }        /** Simulate ejecting a card      */    public void ejectCard()    {        cardReader.animateEjection();        // Re-enable on-off switch        operatorPanel.setEnabled(true);    }        /** Simulate retaining a card     */    public void retainCard()    {        cardReader.animateRetention();        // Re-enable on-off switch        operatorPanel.setEnabled(true);    }         /** Simulate reading input from the keyboard     *     *  @param mode the input mode to use - one of the constants defined below.     *  @param maxValue the maximum acceptable value (used in MENU_MODE only)     *  @return the line that was entered - null if user pressed CANCEL.     */    public String readInput(int mode, int maxValue)    {        return keyboard.readInput(mode, maxValue);    }        /** Simulate printing a line to the log     *     *  @param text the line to print     */    public void printLogLine(String text)    {        gui.printLogLine(text);    }    /** Notify the ATM that the state of the on-off switch has been changed     *     *  @param on true if state is now "on", false if it is "off"     */    void switchChanged(boolean on)    {        // The card reader is only enabled when the switch is on                cardReader.setVisible(on);                if (on)            atm.switchOn();        else            atm.switchOff();    }        /** Notify ATM that a card has been inserted     */    void cardInserted()    {        atm.cardInserted();    }        /** Accessor for GUI Panel that simulates the ATM     *     *  @return the GUI Panel     */    public GUI getGUI()    {        return gui;    }        /** Accessor for simulated bank     *     *  @return simulated bank     */    public SimulatedBank getSimulatedBank()    {        return simulatedBank;    }        /** The ATM object for the ATM being simulated     */    private ATM atm;        /** The simulated log     */    private static SimLog log;        /** The simulated operator panel     */    private SimOperatorPanel operatorPanel;        /** The simulated card reader     */    private SimCardReader cardReader;        /** The simulated display     */    private Display display;        /** The simulated keyboard     */    private SimKeyboard keyboard;        /** The simulated cash dispenser     */    private SimCashDispenser cashDispenser;        /** The simulated envelope acceptor     */    private SimEnvelopeAcceptor envelopeAcceptor;        /** The simulated receipt printer     */    private SimReceiptPrinter receiptPrinter;        /** Panel containing the GUI that simulates the ATM     */    private GUI gui;        /** Simulated bank     */    private SimulatedBank simulatedBank;}    