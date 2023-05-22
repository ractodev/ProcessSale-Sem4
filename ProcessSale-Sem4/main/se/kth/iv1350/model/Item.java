package se.kth.iv1350.model;

import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.Identifier;

public class Item {
	
	private Identifier itemID;
	private Amount itemPrice;
	private Amount VAT;
	private String itemName;
	
	/**
	 * Constructs an Item object with the given parameters.
	 *
	 * @param itemID    the ID of the item
	 * @param itemPrice the price of the item
	 * @param VATrate       the VAT of the item
	 * @param itemName  the name of the item
	 */
	public Item(Identifier itemID, Amount itemPrice, Amount VATrate, String itemName) {
		this.itemPrice = itemPrice;
		this.VAT = VATrate;
		this.itemID = itemID;
		this.itemName = itemName;
	}
	
	/**
	 * Compares this item with another item.
	 * @param item the item to compare with.
	 * @return true if item is equal.
	 */
	public boolean compare(Item item) {
		if(item.getItemID().getIdentifier() == itemID.getIdentifier() && item.getItemName() == itemName && item.getItemPrice() == itemPrice.getValue() && item.getVAT() == VAT.getValue()) {
			return true;
		}
		//System.out.println(item.getItemID().getIdentifier() + " " + item.getItemName() + " " + item.getItemPrice() + " " + item.getVAT() + "        " + itemID.getIdentifier() + " " + itemName + " " + itemPrice.getValue() + " " + VAT.getValue());
		return false;
	}

	// Getters
	public double getItemPrice() {
		return itemPrice.getValue();
	}

	public double getVAT() {
		return VAT.getValue();
	}

	public Identifier getItemID() {
		return itemID;
	}

	public String getItemName() {
		return itemName;
	}
}
