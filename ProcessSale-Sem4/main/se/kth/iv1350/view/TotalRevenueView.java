package se.kth.iv1350.view;

import se.kth.iv1350.model.SaleObserver;

public class TotalRevenueView implements SaleObserver{

	private double totalRevenue;
	
	/**
	 * Method to record current total revenue and print to console.
	 * @param totalPrice the price to be added to total revenue.
	 */
	@Override
	public void newSale(double totalPrice) {
		totalRevenue += totalPrice;
		printCurrentRevenue();
	}

	/**
	 * Prints the total revenue to the console.
	 */
	private void printCurrentRevenue() {
		System.out.println("Total Revenue: " + totalRevenue);
	}
}
