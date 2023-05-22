package se.kth.iv1350.model;

import se.kth.iv1350.utils.Amount;

public class CashPayment {

	private Amount amount;
	
	/**
	 * Represents a cash payment.
	 * 
	 * @param amount the amount of cash paid.
	 */
	public CashPayment(Amount amount) {
		this.amount = amount;
	}

	
	// Getters
	public Amount getAmount() {
		return amount;
	}

}
