package se.kth.iv1350.model;

import se.kth.iv1350.utils.Amount;

public class CashRegister {
	
	private Amount totalBalance;

	/**
	 * Constructs a CashRegister and sets balance to 0 as a starter value.
	 */
	public CashRegister() {
		this.totalBalance = new Amount(0);
	}

	/**
	 * Adds a cash payment to the cash register.
	 * 
	 * @param payment The cash payment to be added.
	 */
	public void addPayment(CashPayment payment) {
		this.totalBalance.setValue(totalBalance.getValue() + payment.getAmount().getValue());
	}

	// Getters
	public Amount getTotalBalance() {
		return totalBalance;
	}

}