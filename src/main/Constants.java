package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	public static DateFormat DATE_FORMAT; //Format to use for dates in this software
	public static Date TODAY; //The date today
	
	/**
	 * Instantiates the constants used in the program
	 */
	static {
		DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		try {
			TODAY = DATE_FORMAT.parse(DATE_FORMAT.format(new Date(System.currentTimeMillis())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
