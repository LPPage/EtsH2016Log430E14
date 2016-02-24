/* * ATM Example system - file SimOperatorPanel.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.simulation;import java.awt.BorderLayout;import java.awt.Button;import java.awt.Color;import java.awt.Label;import java.awt.Panel;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import com.google.common.eventbus.EventBus;import com.google.common.eventbus.Subscribe;import edu.gordon.atm.events.CardEjectedEvent;import edu.gordon.atm.events.CardRetainedEvent;import edu.gordon.atm.physical.IOperatorPanel;import edu.gordon.banking.Money;/** Simulate the operator panel */class SimOperatorPanel extends Panel implements IOperatorPanel{    /** Constructor     *     *  @param edu.gordon.simulation the overall edu.gordon.simulation object     */    SimOperatorPanel(final EventBus eventBus)    {    	        this.eventBus = eventBus;    	eventBus.register(this)    	;                setLayout(new BorderLayout(10, 0));        setBackground(new Color(128,128,255));        add(new Label("     Operator Panel"), BorderLayout.WEST);        final Label message = new Label("Click button to turn ATM on", Label.CENTER);        add(message, BorderLayout.CENTER);        final Button button = new Button(" ON ");        button.addActionListener(new ActionListener() {            public void actionPerformed(ActionEvent e)            {                if (button.getLabel().equals("OFF"))    // ATM is currently on                {                    message.setText("Click button to turn ATM on  ");                    button.setLabel(" ON ");                                        simulation.switchChanged(false);                }                else                                    // ATM is currently off                {                    message.setText("Click button to turn ATM off");                    button.setLabel("OFF");                                        simulation.switchChanged(true);                }            }        });        Panel buttonPanel = new Panel();        buttonPanel.add(button);        add(buttonPanel, BorderLayout.EAST);                // Use a thread to blink the "Click button to turn ATM on" message when        // the ATM is off.  This will also make the message invisible when the        // button is not enabled.                new Thread() {            public void run()            {                while(true)                {                    try                    {                        sleep(1000);                    }                    catch(InterruptedException e)                    { }                                        if (message.isVisible() && ! button.getLabel().equals("OFF")                            || ! SimOperatorPanel.this.isEnabled() )                        message.setVisible(false);                    else                        message.setVisible(true);                }            }        }.start();    }        @Subscribe    public void setEnabled(CardRetainedEvent event){    	super.setEnabled(true);    }        @Subscribe    public void setEnabled(CardEjectedEvent event){    	super.setEnabled(true);    }    public Money getInitialCash()    {        return simulation.getInitialCash();    }    private final EventBus eventBus;    }                                       