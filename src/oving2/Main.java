package oving2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
	/*
	 * Settings.
	 */
	public static final int CUSTOMER_MAX_COURSES = 10;
	public static final int SERVING_AREA_CAPACITY = 2;
	
	// Customer spawn interval
	public static final int CUSTOMER_SPAWN_MINIMUM = 1;
	public static final int CUSTOMER_SPAWN_MAXIMUM = 5;
	
	public static final int RESTAURANT_OPEN = 20;
	
	// How many milliseconds to prepare an order
	public static final int ORDER_COEFFISIENT = 1000;
	
	// How many milliseconds to eat a course
	public static final int EATING_COEFFISIENT = 1000;
	
	/*
	 * For the log file
	 */
	private static File logfile;
	private static String path = "./";
	
	/*
	 * The sushi bar itself
	 */
	public static SushiBar sushibar;
        
	public static void main(String[] args) {
		// Creating the log file
		logfile = new File(path + "log.txt"); 
		log("Starting Sushi Bar application.");
		
		// Creating the Sushi Bar and opens it
		sushibar = new SushiBar();
		sushibar.open();
		sushibar.printStatistics();
		
		
	}
	/**
	 * Logs the given string to console and log.txt in root folder.
	 * @param str Line to log
	 */
	public static void log(String str){
		try {
			FileWriter fw = new FileWriter(logfile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			String logline = getTime() + ", " + Thread.currentThread().getName() + ": " + str;
			bw.write(logline+"\n");
			bw.close();
			System.out.println(logline);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Time utility method
	 * @return Current time in HH:mm:ss format.
	 */
	public static String getTime(){
		// get current time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime()) ;
	}
}
