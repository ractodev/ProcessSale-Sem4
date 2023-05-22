package se.kth.iv1350.utils;

public class Amount {
	
	private double value;
	private String currency;
	
	/**
	 * Constructor for the Amount class.
	 * @param value the value of the amount.
	 * @param currency the currency of the amount.
	 */
	public Amount(Double value, String currency) {
		this.value = value;
		this.currency = currency;
	}
	/**
	 * Constructor for the Amount class without currency.
	 * @param value
	 */
	public Amount(double value) {
		this.value = value;
	}

	//Getters
	public Double getValue() {
		return value;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	//Setters
	public void setValue(double value) {
		this.value = value;
	}

}
