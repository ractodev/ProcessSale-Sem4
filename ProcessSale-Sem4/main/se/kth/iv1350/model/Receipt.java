/**
Represents a receipt for a sale, containing information about the sale and its items, total price, and payment information.
*/
package se.kth.iv1350.model;

import java.util.Map.Entry;

import se.kth.iv1350.utils.Amount;

public class Receipt {
	private String receiptText;

	/**
	 * Constructs a new Receipt object based on the provided Sale object.
	 * 
	 * @param sale the Sale object representing the sale for which the receipt is
	 *             being created
	 */
	public Receipt(Sale sale) {

		StringBuilder sb = new StringBuilder();

		// Header and time of sale
		sb.append("=== RECEIPT ===\n\n");
		sb.append("Time of sale: ").append(sale.getTimeOfSale()).append("\n\n");

		sb.append("Items:\n");

		// Generate a list of items and its quantities

		for (Entry<Item, Amount> entry : sale.getCart().entrySet()) {
			Item item = entry.getKey();
			Amount quantity = entry.getValue();

			double itemPrice = Math.round(item.getItemPrice() * (1 + item.getVAT()) * 100) / 100.0;

			sb.append(quantity.getValue());
			sb.append(" - ");
			sb.append(item.getItemName());
			sb.append(" - ");
			sb.append(itemPrice * quantity.getValue()).append(" SEK \n");

		}

		// Total price
		sb.append("Total price: ").append(sale.getTotalPrice().getValue()).append(" SEK\n");
		
		// Total discounts
		sb.append("Total discounts: ").append(sale.getTotalDiscounts().getValue()).append(" SEK\n");

		// Payment
		sb.append("Payment: ").append(sale.getPayment().getValue()).append(" SEK\n");
		
		// Change
		sb.append("Change: ").append(sale.calculateChange().getValue()).append(" SEK\n");

		this.receiptText = sb.toString();
	}

	// Getters
	public String getReceiptText() {
		return receiptText;
	}

}