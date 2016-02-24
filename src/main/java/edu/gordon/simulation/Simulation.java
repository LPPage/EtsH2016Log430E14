/* * ATM Example system - file Simulation.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.simulation;import com.google.common.eventbus.EventBus;import com.google.common.eventbus.Subscribe;import edu.gordon.atm.ATM;import edu.gordon.atm.display.Display;import edu.gordon.atm.display.Display.ReadInputCallback;import edu.gordon.banking.Card;import edu.gordon.banking.Money;import edu.gordon.simulation.SimKeyboard.EchoListener;/** Simulation of the physical components of the ATM, including its network  *  connection to the bank.  An instance is created at startup by either the *  application's main() program or the applet's init() method. * *  The individual components are displayed in a panel belonging to class GUI. *  The bank is simulated by an object belonging to class SimulatedBank.  The *  constructor for this class creates one instance of each. * *  The static method getInstance() allows components of the ATM to access the one *  and only instance of this class in order to simulate various operations.  The *  remaining methods simulate specific operations of the ATM, and are forwarded *  to either the GUI panel or the simulated bank to actually carry them out. */public class Simulation{    public Simulation()    {        this.atm = new ATM(42, "Gordon College", "First National Bank of Podunk");;                EventBus eventBus = atm.getEventBus();                // Create the simulated individual components of the ATM's GUI        log = new SimLog(eventBus);        operatorPanel = new SimOperatorPanel(eventBus);        cardReader = new SimCardReader(eventBus);        cashDispenser = new SimCashDispenser(eventBus);        envelopeAcceptor = new SimEnvelopeAcceptor(eventBus);        display = new Display(eventBus, ATMPanel.DISPLAYABLE_LINES, ATMPanel.DISPLAYABLE_COLUMNS);        keyboard = new SimKeyboard(eventBus);        simulatedBank = new SimulatedBank(eventBus);        receiptPrinter = new SimReceiptPrinter(eventBus);                  // Listen to the keyboard's echo event        keyboard.addEchoListener(new EchoListener(){			public void echoEventReceived(String echo) {				display.setEcho(echo);			}        });                // Provide an input source callback to the display        display.setReadInputCallback(new ReadInputCallback(){			public String readInputCallbackReceived(int mode, int maxValue) {				return keyboard.readInput(mode, maxValue);			}        });                // Assign the concrete component implementations to the ATM        atm.setLog(log);        atm.setOperatorPanel(operatorPanel);        atm.setCardReader(cardReader);        atm.setCashDispenser(cashDispenser);        atm.setEnvelopeAcceptor(envelopeAcceptor);        atm.setCustomerConsole(display);        atm.setNetworkToBank(simulatedBank);         atm.setReceiptPrinter(receiptPrinter);                // Create the GUI containing the above                gui = new GUI(eventBus, operatorPanel, cardReader, display, keyboard,                      cashDispenser, envelopeAcceptor, receiptPrinter);    }        public void start()    {        new Thread(atm).start();    }        /** Accessor for GUI Panel that simulates the ATM     *     *  @return the GUI Panel     */    public GUI getGUI()    {        return gui;    }        /** Accessor for simulated bank     *     *  @return simulated bank     */    public SimulatedBank getSimulatedBank()    {        return simulatedBank;    }        /** The ATM object for the ATM being simulated     */    private ATM atm;        /** The simulated log     */    private static SimLog log;        /** The simulated operator panel     */    private SimOperatorPanel operatorPanel;        /** The simulated card reader     */    private SimCardReader cardReader;        /** The simulated display     */    private Display display;        /** The simulated keyboard     */    private SimKeyboard keyboard;        /** The simulated cash dispenser     */    private SimCashDispenser cashDispenser;        /** The simulated envelope acceptor     */    private SimEnvelopeAcceptor envelopeAcceptor;        /** The simulated receipt printer     */    private SimReceiptPrinter receiptPrinter;        /** Panel containing the GUI that simulates the ATM     */    private GUI gui;        /** Simulated bank     */    private SimulatedBank simulatedBank;}    