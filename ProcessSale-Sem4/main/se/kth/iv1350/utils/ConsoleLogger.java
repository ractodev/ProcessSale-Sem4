package se.kth.iv1350.utils;

/**
 * Prints log messages the console.
 */
public class ConsoleLogger implements Logger{
	
	/**
	 * The specified exception will be printed to the console.
	 */
	@Override
	public void logException(Exception exc) {
		System.out.println(exc.getLocalizedMessage());
	}

	/**
	 * The specified exception will be printed to the console.
	 */
	@Override
	public void log(String message) {
		System.out.println(message);
	}
}
