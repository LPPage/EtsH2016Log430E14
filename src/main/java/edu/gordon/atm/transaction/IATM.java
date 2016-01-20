package edu.gordon.atm.transaction;

import edu.gordon.atm.physical.ICardReader;
import edu.gordon.atm.physical.ICashDispenser;
import edu.gordon.atm.physical.ICustomerConsole;
import edu.gordon.atm.physical.IEnvelopeAcceptor;
import edu.gordon.atm.physical.INetworkToBank;
import edu.gordon.atm.physical.IReceiptPrinter;

public interface IATM {
    public ICustomerConsole getCustomerConsole();
    public INetworkToBank getNetworkToBank();
    public IReceiptPrinter getReceiptPrinter();
    public ICardReader getCardReader();
    public IEnvelopeAcceptor getEnvelopeAcceptor();
    public String getBankName();
    public int getID();
    public String getPlace();
    public ICashDispenser getCashDispenser();
}
