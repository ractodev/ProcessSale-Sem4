package se.kth.iv1350.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import se.kth.iv1350.integration.DatabaseFailureException;
import se.kth.iv1350.integration.ExternalSystemHandler;
import se.kth.iv1350.integration.IdentifierNotFoundException;
import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.model.CashRegister;
import se.kth.iv1350.model.Item;
import se.kth.iv1350.model.Sale;
import se.kth.iv1350.model.SaleObserver;
import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.Identifier;
import se.kth.iv1350.utils.Logger;
import se.kth.iv1350.model.CashPayment;

public class Controller {
	private ExternalSystemHandler extSysHan;
	private CashRegister cashRegister;
	private Printer printer;
	private Sale saleInformation;
	
	private List<SaleObserver> saleObservers = new ArrayList<>();

	/**
	 * Constructs a new Controller instance and initializes the cash
	 * register,printer and external system handler.
	 */
	public Controller(CashRegister cashRegister, Printer printer, ExternalSystemHandler extSysHan) {
		this.cashRegister = cashRegister;
		this.extSysHan = extSysHan;
		this.printer = printer;
	}

	/**
	 * Starts a new sale by creating a new Sale object.
	 */
	public void startSale() {
		saleInformation = new Sale(printer);
	}

	/**
	 * Looks up an item in the external inventory system based on its ID and adds it
	 * to the current sale if the ID is found in the database.
	 * 
	 * @param itemId the ID of the item to look up
	 */
	public void enterIdentifier(Identifier itemID) throws DatabaseFailureException, IdentifierNotFoundException {
			Item foundItem = extSysHan.getExternalInventorySystem().fetchItemInformation(itemID);
			saleInformation.addItem(foundItem);
	}

	/**
	 * Ends the current sale.
	 * 
	 * @return the total price of the sale.
	 */
	public Amount endSale() {
		return saleInformation.getTotalPrice();
	}

	/**
	 * Fetches discount from DiscountDatabase and applies it to sale.
	 * 
	 * @param customerID the ID of the customer
	 */
	public void applyDiscount(Identifier customerID) {
		Amount discountToApply = extSysHan.fetchDiscount(customerID);
		saleInformation.applyDiscount(discountToApply);
	}

	/**
	 * Processes a cash payment for the current sale, updates external systems,
	 * creates a Cashpayment and adds it to cashregisters.
	 * 
	 * @param amount the amount of cash paid by the customer
	 */
	public void pay(Amount amount) {
		CashPayment cashPayment = new CashPayment(amount);
		cashRegister.addPayment(cashPayment);
		saleInformation.pay(cashPayment);
		extSysHan.updateExternalSystems(saleInformation);
	}

	/**
	 * Calculates the change for the payment.
	 * 
	 * @return change the amount of change owed to the customer.
	 */
	public Amount change() {
		Amount change = saleInformation.calculateChange();
		return change;
	}

	/**
	 * Prints receipt for the current Sale and notifies observers.
	 */
	public void printReceipt() {
		saleInformation.printReceipt();
		notifyObservers();
	}
	
	/**
	 * Notifies all observers that a new sale has been made.
	 */
	private void notifyObservers() {
		for(SaleObserver obs : saleObservers) {
			obs.newSale(getSaleInformation().getTotalPrice().getValue());
		}
	}
	
	/**
	 * Adds a new observer to the saleObservers list.
	 * @param obs the observer to be added.
	 */
	public void addSaleObserver(SaleObserver obs) {
		saleObservers.add(obs);
	}
	
	public Sale getSaleInformation() {
		return saleInformation;
	}
	
	public ExternalSystemHandler GetExternalSystemHandler() {
		return extSysHan;
	}
	
	public Map<Item, Amount> getCart() {
		return saleInformation.getCart();
	}
}


