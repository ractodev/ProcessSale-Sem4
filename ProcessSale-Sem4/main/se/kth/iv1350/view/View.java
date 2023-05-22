package se.kth.iv1350.view;

import java.util.Scanner;
import java.util.Map.Entry;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.DatabaseFailureException;
import se.kth.iv1350.integration.IdentifierNotFoundException;
import se.kth.iv1350.model.Item;
import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.ConsoleLogger;
import se.kth.iv1350.utils.FileLogger;
import se.kth.iv1350.utils.Identifier;
import se.kth.iv1350.utils.TotalRevenueFileOutput;
import se.kth.iv1350.utils.Logger;

/**
 * View is a class that represents a user interface and contains hard coded
 * controller calls + printouts.
 * 
 * 
 */

public class View {
	private Controller controller;
	private Logger logger;

	/**
	 * Constructs a new View object with the specified Controller.
	 * 
	 * @param controller the Controller object to use for handling user input and
	 *                   processing sales.
	 */
	public View(Controller controller) {
		this.controller = controller;
		this.logger = new ConsoleLogger();
		controller.addSaleObserver(new TotalRevenueView());
		controller.addSaleObserver(new TotalRevenueFileOutput(new FileLogger("TotalRevenueLog.txt")));
	}

	/**
	 * Starts a new sale by invoking the appropriate methods on the Controller
	 * object.
	 */

	public void start(){
		controller.startSale();

		Scanner scanner = new Scanner(System.in);
		Identifier itemId;

		do {
			System.out.print("Enter item ID (or 0 to finish): ");
			itemId = new Identifier(scanner.nextInt());
			if (itemId.getIdentifier() != 0) {
				try {
					controller.enterIdentifier(itemId);
				} catch (DatabaseFailureException | IdentifierNotFoundException exc) {
		            setLogger(new FileLogger("ExceptionLog.txt"));
		            logger.logException(exc);
		            setLogger(new ConsoleLogger());
		            logger.log(exc.getLocalizedMessage());
				}
			}
			System.out.println("\nRunning total is: " + controller.getSaleInformation().getTotalPrice().getValue() + " SEK\n\n_______");
			System.out.println("Current cart:\n");
			for (Entry<Item, Amount> cartEntry : controller.getCart().entrySet()) {
			    Item key = cartEntry.getKey();
			    Amount value = cartEntry.getValue();
				System.out.println(Math.round(value.getValue()) + " " + key.getItemName() + ": " + (value.getValue() * key.getItemPrice()+ " SEK\n"));
			}
			System.out.println("_______\n");
		} while (itemId.getIdentifier() != 0);
		
		System.out.print("Enter customer ID for discount: ");
		Identifier customerId = new Identifier(scanner.nextInt());
		controller.applyDiscount(customerId);
		Amount totalPrice = controller.endSale();
		System.out.println("Discount has been added. The new total is: " + totalPrice.getValue());

		Amount payment;
		do {
			System.out.print("Enter payment amount: ");
			payment = new Amount(scanner.nextDouble());
			if (payment.getValue() < controller.getSaleInformation().getTotalPrice().getValue()) {
				System.out.println("Payment must exceed the sale total!");
			}
		} while (payment.getValue() < controller.getSaleInformation().getTotalPrice().getValue());

		controller.pay(payment);
		controller.change();
		controller.printReceipt();
		scanner.close();
		}
	
	/**
	 * Setter for what logger to use.
	 * @param logger the logger to use.
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}

