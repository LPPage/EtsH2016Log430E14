/* * ATM Example system - file ATM.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.atm;import edu.gordon.atm.physical.ICardReader;import edu.gordon.atm.physical.ICashDispenser;import edu.gordon.atm.physical.ICustomerConsole;import edu.gordon.atm.physical.IEnvelopeAcceptor;import edu.gordon.atm.physical.ILog;import edu.gordon.atm.physical.INetworkToBank;import edu.gordon.atm.physical.IOperatorPanel;import edu.gordon.atm.physical.IReceiptPrinter;import edu.gordon.atm.transaction.IATM;import edu.gordon.banking.Money;/** Representation for the ATM itself.  An object of this class "owns" *  the objects representing the component parts of the ATM, and the *  communications network, and is responsible for creating customer  *  sessions which then use it to gain access to the component parts. *  This is an active class - when an instance of the class is created, *  a thread is executed that actually runs the system. */ public class ATM implements Runnable, IATM{    /** Constructor     *     *  @param id the unique ID for this ATM     *  @param place the physical location of this ATM     *  @param bankName the name of the bank owning this ATM     *  @param bankAddress the Internet address of the bank     */    public ATM(int id, String place, String bankName)    {        this.id = id;        this.place = place;        this.bankName = bankName;                // Set up initial conditions when ATM first created                state = OFF_STATE;        switchOn = false;        cardInserted = false;           }        // Methods corresponding to major responsibilities of the ATM        public void setCardReader(ICardReader cardReader) {		this.cardReader = cardReader;	}	public void setCashDispenser(ICashDispenser cashDispenser) {		this.cashDispenser = cashDispenser;	}	public void setCustomerConsole(ICustomerConsole customerConsole) {		this.customerConsole = customerConsole;	}	public void setEnvelopeAcceptor(IEnvelopeAcceptor envelopeAcceptor) {		this.envelopeAcceptor = envelopeAcceptor;	}	public void setNetworkToBank(INetworkToBank networkToBank) {		this.networkToBank = networkToBank;	}	public void setOperatorPanel(IOperatorPanel operatorPanel) {		this.operatorPanel = operatorPanel;	}	public void setReceiptPrinter(IReceiptPrinter receiptPrinter) {		this.receiptPrinter = receiptPrinter;	}	/** The main program/applet will create a Thread that executes     *  this code.     */    public void run()    {        Session currentSession = null;                while (true)        {        	customerConsole.clearDisplay();        	            switch(state)            {                case OFF_STATE:                                    customerConsole.display("Not currently available");                    synchronized(this)                    {                        try                        {                             wait();                        }                        catch(InterruptedException e)                        { }                    }                                        if (switchOn)                    {                        performStartup();                        state = IDLE_STATE;                    }                                                                break;                                    case IDLE_STATE:                                    customerConsole.display("Please insert your card");                    cardInserted = false;                                                            synchronized(this)                    {                        try                        {                             wait();                        }                        catch(InterruptedException e)                        { }                    }                                               if (cardInserted)                    {                        currentSession = new Session(cardReader, customerConsole, new TransactionFactory(this));                        state = SERVING_CUSTOMER_STATE;                    }                    else if (! switchOn)                    {                        performShutdown();                        state = OFF_STATE;                    }                                        break;                            case SERVING_CUSTOMER_STATE:                                                        // The following will not return until the session has                    // completed                                        currentSession.performSession();                                        state = IDLE_STATE;                                        break;                            }        }    }                    public void setLog(ILog log) {		this.log = log;	}	/** Inform the ATM that the switch on the operator console has been moved     *  to the "on" position.     */    public synchronized void switchOn()    {        switchOn = true;        notify();    }        /** Inform the ATM that the switch on the operator console has been moved     *  to the "off" position.     */    public synchronized void switchOff()    {        switchOn = false;        notify();    }        /** Inform the ATM that a card has been inserted into the card reader.     */    public synchronized void cardInserted()    {        cardInserted = true;        notify();    }        // The following methods allow objects of other classes to access component    // parts of the ATM        /** Accessor for id     *     *  @return unique id of this ATM     */    public int getID()    {        return id;    }        /** Accessor for place     *     *  @return physical location of this ATM     */    public String getPlace()    {        return place;    }        /** Accessor for bank name     *     *  @return name of bank owning this ATM     */    public String getBankName()    {        return bankName;    }        /** Accessor for card reader     *     *  @return card reader component of this ATM     */    public ICardReader getCardReader()    {        return cardReader;    }        /** Accessor for cash dispenser     *     *  @return cash dispenser component of this ATM     */    public ICashDispenser getCashDispenser()    {        return cashDispenser;    }        /** Accessor for customer console      *     *  @return customer console component of this ATM     */    public ICustomerConsole getCustomerConsole()    {        return customerConsole;    }        /** Accessor for envelope acceptor     *     *  @return envelope acceptor component of this ATM     */    public IEnvelopeAcceptor getEnvelopeAcceptor()    {        return envelopeAcceptor;    }        /** Accessor for log     *     *  @return log component of this ATM     */    public ILog getLog()    {        return log;    }        /** Accessor for network to bank     *     *  @return network connection to bank of this ATM     */    public INetworkToBank getNetworkToBank()    {        return networkToBank;    }        /** Accessor for operator panel     *     *  @return operator panel component of this ATM     */    public IOperatorPanel getOperatorPanel()    {        return operatorPanel;    }        /** Accessor for receipt printer     *     *  @return receipt printer component of this ATM     */    public IReceiptPrinter getReceiptPrinter()    {        return receiptPrinter;    }            // Private methods    public boolean isSwitchOn() {		return switchOn;	}	/** Perform the System Startup use case when switch is turned on     */    private void performStartup()    {        Money initialCash = operatorPanel.getInitialCash();        cashDispenser.setInitialCash(initialCash);        networkToBank.openConnection();         }        /** Perform the System Shutdown use case when switch is turned off     */    private void performShutdown()    {        networkToBank.closeConnection();    }            // Instance variables recording information about the ATM            /** Unique ID for this ATM     */    private int id;        /** Physical location of this ATM     */    private String place;        /** Name of the bank owning this ATM     */    private String bankName;         // Instance variables referring to the omponent parts of the ATM        /** The ATM's card reader     */    private ICardReader cardReader;        /** The ATM's cash dispenser     */    private ICashDispenser cashDispenser;        /** The ATM's customer console     */    private ICustomerConsole customerConsole;        /** The ATM's envelope acceptor     */    private IEnvelopeAcceptor envelopeAcceptor;        /** The ATM's log     */    private ILog log;        /** The ATM's network connection to the bank     */    private INetworkToBank networkToBank;        /** The ATM's operator panel     */    private IOperatorPanel operatorPanel;        /** The ATM's receipt printer     */    private IReceiptPrinter receiptPrinter;        // State information        /** The current state of the ATM - one of the possible values listed below     */    private int state;        /** Becomes true when the operator panel informs the ATM that the switch has     *  been turned on - becomes false when the operator panel informs the ATM     *  that the switch has been turned off.     */    private boolean switchOn;        /** Becomes true when the card reader informs the ATM that a card has been     *  inserted - the ATM will make this false when it has tried to read the     *  card     */    private boolean cardInserted;     // Possible values for state            /** The ATM is off.  The switch must be turned on before it can operate     */    private static final int OFF_STATE = 0;        /** The ATM is on, but idle.  It can service a customer, or it can be shut down     */    private static final int IDLE_STATE = 1;        /** The ATM is servicing a customer.     */    private static final int SERVING_CUSTOMER_STATE = 2;}