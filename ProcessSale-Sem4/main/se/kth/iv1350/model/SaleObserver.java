package se.kth.iv1350.model;

public interface SaleObserver {
	/**
	 * Invoked when a Sale has been paid for.
	 * @param totalPrice the total price paid for the sale.
	 */
	void newSale(double totalPrice);
}
