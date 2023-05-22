/**
The Printer class is responsible for printing the receipt to the console.
*/
package se.kth.iv1350.integration;

import se.kth.iv1350.model.Receipt;

public class Printer {

	/**
	 * Prints the receipt to the console.
	 * 
	 * @param receipt The Receipt object to print
	 */
	public void print(Receipt receipt) {
		System.out.println(receipt.getReceiptText());
	}
}
