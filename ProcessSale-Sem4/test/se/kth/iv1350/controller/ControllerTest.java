package se.kth.iv1350.controller;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map.Entry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.integration.DatabaseFailureException;
import se.kth.iv1350.integration.ExternalSystemHandler;
import se.kth.iv1350.integration.IdentifierNotFoundException;
import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.model.CashRegister;
import se.kth.iv1350.model.Item;
import se.kth.iv1350.model.Sale;
import se.kth.iv1350.utils.Amount;
import se.kth.iv1350.utils.ConsoleLogger;
import se.kth.iv1350.utils.FileLogger;
import se.kth.iv1350.utils.Identifier;
import se.kth.iv1350.utils.Logger;

class ControllerTest {
    private CashRegister cashRegister;
    private Printer printer;
    private ExternalSystemHandler extSysHan;
    private Controller controller;
    private Logger logger;

    @BeforeEach
    void setUp() throws Exception {
        cashRegister = new CashRegister();
        printer = new Printer();
        extSysHan = new ExternalSystemHandler();
        controller = new Controller(cashRegister, printer, extSysHan);
    }

    @AfterEach
    void tearDown() throws Exception {
    	cashRegister=null;
    	printer=null;
    	extSysHan=null;
    	controller=null;
    }

    @Test
    void testStartSale() {
        controller.startSale();
        assertNotNull(controller.getSaleInformation(), "Sale information should not be null");
        assertTrue(controller.getSaleInformation() instanceof Sale, "Sale information should be an instance of Sale");
    }

    @Test
    void testEnterIdentifierValidItem(){
    	try {
    	    Item expectedResult = new Item(new Identifier (1), new Amount(14), new Amount(0), "Milk");
    	    Item result = null;
    	    controller.startSale();
    	    controller.enterIdentifier(new Identifier(1)); //1 is a valid item ID
    	    for (Entry<Item, Amount> inventoryEntry : controller.getCart().entrySet()) {
		result = inventoryEntry.getKey();
    	    }
    	    assertEquals(controller.getCart().size(), 1, "Cart should have one item after adding a valid ID.");
    	    assertTrue(result.compare(expectedResult), "The item in the cart should match the one added.");
    	} catch (DatabaseFailureException | IdentifierNotFoundException exc){
    	    fail("Should not have thrown an exception");
	    setLogger(new FileLogger("ExceptionTestLog.txt"));
	    logger.log(exc.toString());
	    setLogger(new ConsoleLogger());
	    logger.log(exc.getLocalizedMessage());
    	}
    }

    @Test
    void testEnterIdentifierInvalidItem() throws DatabaseFailureException {
         controller.startSale();
         try {
             controller.enterIdentifier(new Identifier(12)); //12 is an invalid itemID (Does not exist in inventory)
             fail("Expected IdentifierNotFoundException has not been thrown"); // UPDATED: Fail if exception is not thrown.
         } catch (IdentifierNotFoundException exc) {
             assertTrue(exc.getLocalizedMessage().contains("not found in inventory."));
         }
    }
    
    @Test
    void testDatabaseConnection() throws IdentifierNotFoundException {
    	controller.startSale();
        try {
            controller.enterIdentifier(new Identifier(11));
	    fail("DatabaseFailureException was expected but not thrown"); // UPDATED: Fail if exception is not thrown.
	} catch (DatabaseFailureException exc) {
    	    assertTrue(exc.getLocalizedMessage().contains("Database could not be reached"));
	}
    }

    @Test
    void testApplyDiscountValidCustomerId() throws DatabaseFailureException, IdentifierNotFoundException {
        controller.startSale();
	controller.enterIdentifier(new Identifier(1));
	    
        Amount priceBeforeDiscount = controller.getSaleInformation().getTotalPrice();
        double whatPriceShouldBe = priceBeforeDiscount.getValue() * (1 - controller.GetExternalSystemHandler().getDiscountDatabase().fetchDiscount(new Identifier (1)).getValue());
        
	controller.applyDiscount(new Identifier(1));
        Amount priceAfterDiscount = controller.getSaleInformation().getTotalPrice();
        assertEquals(priceAfterDiscount.getValue(), whatPriceShouldBe,0.001, "Discount should be applied for customer ID 1");
    }
    
    @Test
    void testApplyDiscountInvalidCustomerId() throws DatabaseFailureException, IdentifierNotFoundException {
    	  controller.startSale();
    	  controller.enterIdentifier(new Identifier(1));
          
          Amount priceBefore = controller.getSaleInformation().getTotalPrice();
          controller.applyDiscount(new Identifier (40)); // CutomerID of 40 is not eligible for discount
          Amount priceAfter = controller.getSaleInformation().getTotalPrice();
         
          assertEquals(priceBefore.getValue(), priceAfter.getValue(), "Price should not change for an invalid customer ID");
    }

    @Test
    void testPay() throws DatabaseFailureException, IdentifierNotFoundException {
        controller.startSale();
	controller.enterIdentifier(new Identifier(1));
        controller.pay(new Amount(100));
        
        assertEquals(100, controller.getSaleInformation().getPayment().getValue(), "Payment should be recorded correctly after calling pay()");
        assertEquals(100, cashRegister.getTotalBalance().getValue(), "Cash register balance should be updated after calling pay()");
    }
    
    @Test
    public void testEndSale() throws DatabaseFailureException, IdentifierNotFoundException {
        controller.startSale();
	controller.enterIdentifier(new Identifier(1));
        
        double expectedTotalPrice = 14; // the expected total price with VAT and discounts applied
        assertEquals(expectedTotalPrice, controller.endSale().getValue(), 0.01); // delta of 0.01 to account for floating point errors
    }
    
    public void setLogger(Logger logger) {
	this.logger = logger;
    }
}
