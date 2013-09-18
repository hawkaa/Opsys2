package oving2;

import java.util.Random;

public class Door extends Thread {
	
	private boolean isOpen = false;
	private int counter = 0;
	private SushiBar sb;
	private Random rnd;
	
	public Door(SushiBar sb) {
		this.sb = sb;
		this.rnd = new Random();
	}
	
	public void run() {
		while(this.isOpen) {
			this.getCustomer().start();
			try {
				sleep(this.getRandomMillis());
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * @return If the door is open or not
	 */
	public boolean isOpen() {
		return this.isOpen;
	}
	
	public void open() {
		Main.log("Door opened.");
		this.isOpen = true;
		this.start();
	}
	
	public void close() {
		this.isOpen = false;
	}
	
	
	private Customer getCustomer() {
		Random rnd = new Random();
		// Generates random number of courses (0-9)
		int numCourses = rnd.nextInt(Main.CUSTOMER_MAX_COURSES);
		
		// Generates random number of takeaways (0-numCourses)
		int takeaway = rnd.nextInt(numCourses+1);
		
		// Creates and returns Customer
		return new Customer(this.sb, counter++, numCourses, takeaway);
	}
	
	private int getRandomMillis() {
		int min = Main.CUSTOMER_SPAWN_MINIMUM;
		int max = Main.CUSTOMER_SPAWN_MAXIMUM;
		return (this.rnd.nextInt(max - min) + min)*1000;
	}

}
