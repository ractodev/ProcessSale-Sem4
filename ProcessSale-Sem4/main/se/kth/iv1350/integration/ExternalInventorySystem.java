package se.kth.iv1350.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import se.kth.iv1350.model.Item;
import se.kth.iv1350.model.Sale;
import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.Identifier;

/**
 * The ExternalInventorySystem class represents an external inventory system
 * that keeps track of items and their information.
 */
public class ExternalInventorySystem {

	private Map<Item, Amount> inventory;

	/**
	 * Constructs a new ExternalInventorySystem object and initializes the inventory
	 * with some default items with 10 in stock.
	 */
	public ExternalInventorySystem() {
		inventory = new HashMap<>();
		inventory.put(new Item(new Identifier (1), new Amount(14), new Amount(0), "Milk"), new Amount(10));
		inventory.put(new Item(new Identifier (2), new Amount(10), new Amount(0.2), "Tomato"), new Amount(10));
		inventory.put(new Item(new Identifier (3), new Amount(3.5), new Amount(0.12), "Orange Juice"), new Amount(10));
		inventory.put(new Item(new Identifier (4), new Amount(2.5), new Amount(0.1), "Apple Juice"), new Amount(10));
		inventory.put(new Item(new Identifier (5), new Amount(8), new Amount(0.4), "Bananas"), new Amount(10));
		inventory.put(new Item(new Identifier (6), new Amount(8), new Amount(0.1), "Carrots"), new Amount(10));
		inventory.put(new Item(new Identifier (7), new Amount(12), new Amount(0.33), "Eggs"), new Amount(10));
		inventory.put(new Item(new Identifier (8), new Amount(4), new Amount(0.12), "Bread"), new Amount(10));
		inventory.put(new Item(new Identifier (9), new Amount(6), new Amount(0.04), "Cheese"), new Amount(10));
		inventory.put(new Item(new Identifier (10), new Amount(20), new Amount(0.05), "Steak"), new Amount(10));
		inventory.put(new Item(new Identifier (11), new Amount(20), new Amount(0.05), "DatabaseUnavailableItem"), new Amount(10));
	}
	

	/**
	 * Returns information about an item with a specific item ID.
	 * 
	 * @param itemId the item ID to search for.
	 * @return the item with the matching item ID, or null if no item is found.
	 * @throws DatabaseFailureException if the database could not be reached.
	 * @throws IdentifierNotfoundException if the identifier could not be found.
	 */
	public Item fetchItemInformation(Identifier itemID) throws DatabaseFailureException, IdentifierNotFoundException {
		if(itemID.getIdentifier() == 11) {
			throw new DatabaseFailureException("ERROR: Database could not be reached");
		}
		Item inventoryKey = null;
		Amount stock = null;
		for (Entry<Item, Amount> inventoryEntry : inventory.entrySet()) {
			inventoryKey = inventoryEntry.getKey();
			stock = new Amount(inventoryEntry.getValue().getValue());
			if(inventoryKey.getItemID().getIdentifier() == itemID.getIdentifier()) {
				break;
			}
		}
			if(inventoryKey.getItemID().getIdentifier() == itemID.getIdentifier() && stock.getValue() > 0) {
				return inventoryKey;
			} else {
				throw new IdentifierNotFoundException("ERROR: Item identifier " + itemID.getIdentifier() + " not found in inventory.");
			}
		}
		
	/**
	 * Checks which items and how many are in the cart and updates inventory accordingly.
	 * 
	 * @param sale information about the sale.
	 */
	public void updateInventory(Sale sale) {
		for (Entry<Item, Amount> inventoryEntry : inventory.entrySet()) {
		    Item inventoryKey = inventoryEntry.getKey();
		    for (Entry<Item, Amount> cartEntry : sale.getCart().entrySet()) {
		        Item cartKey = cartEntry.getKey();
		        Amount cartValue = cartEntry.getValue();
		        if (inventoryKey == cartKey) {
		        	inventory.put(inventoryKey, new Amount(inventory.get(inventoryKey).getValue() - cartValue.getValue()));
		        }
		    }
		}
	}











}

