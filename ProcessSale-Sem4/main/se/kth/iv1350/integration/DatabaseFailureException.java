package se.kth.iv1350.integration;

public class DatabaseFailureException extends RuntimeException {
	
	/**
	 * Constructor for the DatabaseFailureException.
	 * @param errorMessage the error message for the cause of the exception.
	 */
    public DatabaseFailureException(String errorMessage) {
        super(errorMessage);
    }
}
