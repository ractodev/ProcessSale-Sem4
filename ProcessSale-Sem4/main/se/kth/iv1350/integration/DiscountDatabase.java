/**
This class represents a database of discounts, stored as a map with customer IDs as keys and
discount percentages as values.
*/
package se.kth.iv1350.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.Identifier;

public class DiscountDatabase {
	private Map<Identifier, Amount> discounts;

	/**
	 * Constructs a new DiscountDatabase and initializes it with some sample
	 * discounts for customer IDs 1, 2, and 3.
	 */
	public DiscountDatabase() {
		discounts = new HashMap<Identifier, Amount>();
		discounts.put(new Identifier(1), new Amount(0.2));
		discounts.put(new Identifier(2), new Amount(0.4));
		discounts.put(new Identifier(3), new Amount(0.15));
	}

	/**
	 * Looks up the discount percentage for the given customer ID.
	 *
	 * @param customerID the ID of the customer for which to fetch the discount percentage.
	 * @return the discount percentage as an Amount with value between 0 and 1. 0 if no discount is found.
	 */
	public Amount fetchDiscount(Identifier customerID) {
		for (Entry<Identifier, Amount> inventoryEntry : discounts.entrySet()) {
			if(inventoryEntry.getKey().getIdentifier() == customerID.getIdentifier()) {
				Amount discountPercentage = inventoryEntry.getValue();
				return discountPercentage;
			}
		}
		return new Amount(0);
	}
}
