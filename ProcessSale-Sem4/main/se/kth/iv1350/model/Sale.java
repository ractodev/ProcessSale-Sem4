/**
The Sale class represents a sale in the point of sale system.
It keeps track of the items purchased, the total price, the payment received, and the time of the sale.
It also provides methods for adding items to the sale, calculating the total price, applying a discount,
and printing a receipt.
*/
package se.kth.iv1350.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.utils.Amount;

public class Sale {
	private LocalTime timeOfSale; // The time the sale was made
	private Map<Item, Amount> cart; // A map that keeps track of customers cart
	private Printer printer; // The printer used to print the receipt
	
	private Amount totalPrice; // The total price of the sale
	private Amount totalDiscounts; // The total discounts for the sale
	private Amount payment; // The amount of payment received for the sale
	private Amount change; // The amount of change owed to the customer
	


	/**
	 * Creates a new Sale object. initializes variables and sets time of sale.
	 *
	 * @param printer The printer to be used to print the receipt.
	 */
	public Sale(Printer printer) {
		timeOfSale = LocalTime.now().withNano(0);
		cart = new HashMap<>();
		this.printer = printer;
		
		totalPrice = new Amount(0);
		totalDiscounts = new Amount(0);
		payment = new Amount(0);
		change = new Amount(0);
	}

	/**
	 * Adds an item to the sale. If the item is already in the sale, its quantity is
	 * increased. Otherwise, it is added to the sale with a quantity of 1. The total
	 * price of the sale is updated accordingly.
	 *
	 * @param item The item to be added to the sale.
	 */
	public void addItem(Item item) {
		if (cart.containsKey(item)) {
			double quantity = cart.get(item).getValue() + 1;
			cart.put(item, new Amount(quantity));
		} else {
			cart.put(item, new Amount(1));
		}
		totalPrice.setValue(totalPrice.getValue() + (item.getItemPrice() * (1 + item.getVAT())));
	}

	/**
	 * Applies a discount to the total price of the sale. The discount is given as an amount with a
	 * decimal value and must be between 0 and 1. Updates total price based on discount
	 *
	 * @param discountToApply The discount to be applied to the sale.
	 */
	public void applyDiscount(Amount discountToApply) {
		double discountPercentage = (1 - discountToApply.getValue());
		totalDiscounts.setValue(totalPrice.getValue() * (1 - discountPercentage));
		totalPrice.setValue(totalPrice.getValue() - totalDiscounts.getValue());
	}
	
	/**
	 * Sets the payment amount for the sale.
	 *
	 * @param paidAmount The amount paid for the sale.
	 */
	public void pay(CashPayment cashPayment) {
		payment = cashPayment.getAmount();
	}

	/**
	 * Prints a receipt for the sale using the printer.
	 */
	public void printReceipt() {
		Receipt receipt = new Receipt(this);
		printer.print(receipt);
	}

	
	//Getters
	public Amount getTotalPrice() {
		return totalPrice;
	}
	
	public Amount getTotalDiscounts() {
		return totalDiscounts;
	}

	public Amount getPayment() {
		return payment;
	}

	public Amount calculateChange() {
		change.setValue(payment.getValue() - totalPrice.getValue());
		return change;
	}

	public LocalTime getTimeOfSale() {
		return timeOfSale;
	}

	public Map<Item, Amount> getCart() {
		return cart;

	}

	public Printer getPrinter() {
		return printer;
	}

	
}
