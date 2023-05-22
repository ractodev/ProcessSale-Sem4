package se.kth.iv1350.startup;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.ExternalSystemHandler;
import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.model.CashRegister;
import se.kth.iv1350.utils.FileLogger;
import se.kth.iv1350.view.View;

public class Main {

	/**
	 * The main method that starts the program.
	 * 
	 * This method initializes the CashRegister, Printer, and ExternalSystemHandler
	 * objects, creates a new Controller object with these objects, and passes it to
	 * a new View object to start the user interface. The main method is the entry
	 * point for the program and is responsible for setting up the necessary objects
	 * and starting the program execution.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		CashRegister cashRegister = new CashRegister();
		Printer printer = new Printer();
		ExternalSystemHandler extSysHan = new ExternalSystemHandler();
		Controller contr = new Controller(cashRegister, printer, extSysHan);
		View view = new View(contr);

		view.start();
	}
}
