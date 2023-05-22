package se.kth.iv1350.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Prints log messages to a file. The log file will be in the current
 * directory..
 */
public class FileLogger implements Logger {
	private PrintWriter logStream;


	/**
	 * Creates a new instance and also creates a new log file. An existing log file
	 * will be deleted.
	 * 
	 * @param file the .txt file to log to.
	 */
	public FileLogger(String file) {
		try {
			logStream = new PrintWriter(new FileWriter(file), true);
		} catch (IOException ioe) {
			System.out.println("CAN NOT LOG");
			ioe.printStackTrace();
		}
	}


	/**
	 * Writes a log entry describing a thrown exception.
	 *
	 * @param exception The exception that shall be logged.
	 */
	@Override
	public void logException(Exception exception) {
		StringBuilder logMsgBuilder = new StringBuilder();
		logMsgBuilder.append(createTime());
		logMsgBuilder.append(", Exception was thrown: ");
		logMsgBuilder.append(exception.getMessage());
		logStream.println(logMsgBuilder);
		exception.printStackTrace(logStream);
	}

	private String createTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return now.format(formatter);
	}


	@Override
	public void log(String message) {
		logStream.println(message);
	}

}
