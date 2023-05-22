package se.kth.iv1350.integration;

public class IdentifierNotFoundException extends Exception {

	/**
	 * Constructor for IndentifierNotFoundException.
	 * @param errorMessage the error message for the cause of the exception.
	 */
	public IdentifierNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}