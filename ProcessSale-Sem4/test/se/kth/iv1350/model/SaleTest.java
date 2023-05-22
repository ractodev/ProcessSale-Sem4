package se.kth.iv1350.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.Identifier;

class SaleTest {
	private Sale sale;
	private Printer printer;

	@BeforeEach
	void setUp() throws Exception {
		printer = new Printer();
		sale = new Sale(printer);
	}

	@AfterEach
	void tearDown() throws Exception {
		printer = null;
		sale = null;
	}

	@Test
	void testAddItem() {

		Item item = new Item(new Identifier(1), new Amount(10), new Amount(0.25), "Test Item");
		sale.addItem(item);
		
		Map<Item, Amount> cart = sale.getCart();
		assertEquals(1, cart.size(), "Item should have been added to the sale");
		assertEquals(1, sale.getCart().get(item).getValue(), "Item should have the same ID as the item we're trying to add");

		double expectedTotalPrice = item.getItemPrice() * (1 + item.getVAT());
		assertEquals(expectedTotalPrice, sale.getTotalPrice().getValue(),
				"Total price should be updated correctly after calling addItem()");
	}

	@Test
	void testApplyDiscount() {

		Item item = new Item(new Identifier(1), new Amount(10), new Amount(0.25), "Test Item");

		sale.addItem(item);

		// Apply a discount to the sale
		Amount discount = new Amount(0.1); // 10% discount
		sale.applyDiscount(discount);

		double expectedTotalPrice = item.getItemPrice() * (1 + item.getVAT()) * (1 - discount.getValue());
		assertEquals(expectedTotalPrice, sale.getTotalPrice().getValue(),
				"Total price should be updated correctly after applying a discount");
	}

	@Test
	void testSaleConstructor() {

		// Check that the time of sale is not null
		assertNotNull(sale.getTimeOfSale(), "Time of sale should not be null");

		// check that the cart is empty
		assertTrue(sale.getCart().isEmpty(), "Item quantity map should be empty");

		// check that the printer is the same as the one passed to the constructor
		assertEquals(printer, sale.getPrinter(), "Printer should be the same as the one passed to the constructor");
	}

	@Test
	public void testPay() {
		CashPayment cashPayment = new CashPayment(new Amount(100));
		sale.pay(cashPayment);
		assertEquals(100.0, sale.getPayment().getValue());
	}

	@Test
	public void testChange() {
		Item item = new Item(new Identifier(1), new Amount(10), new Amount(0.25), "Test Item");
		sale.addItem(item);
		CashPayment cashPayment = new CashPayment(new Amount(100));
		sale.pay(cashPayment);
		assertEquals(sale.calculateChange().getValue(), (cashPayment.getAmount().getValue() - sale.getTotalPrice().getValue()), "Change not calculated correctly");

	}
}
