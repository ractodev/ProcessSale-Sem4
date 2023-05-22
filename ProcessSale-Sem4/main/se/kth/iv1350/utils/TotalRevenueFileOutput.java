package se.kth.iv1350.utils;

import se.kth.iv1350.model.SaleObserver;

public class TotalRevenueFileOutput implements SaleObserver{
	
	private double totalRevenue;
	private Logger logger;
	
	/**
	 * Constructor for SaleObserver that prints log messages to a .txt file.
	 * @param logger the FileLogger instance containing what .txt file to write to.
	 */
	public TotalRevenueFileOutput(FileLogger logger){
		this.logger = logger;
	}
	
	/**
	 * Method to record current total revenue and log it.
	 * @param totalPrice the price to be added to total revenue.
	 */
	@Override
	public void newSale(double totalPrice) {
		totalRevenue += totalPrice;
		logSale();
	}
	
	/**
	 * Method to log total revenue to file.
	 */
	private void logSale() {
		logger.log("Total Revenue: " + totalRevenue);
	}
}
