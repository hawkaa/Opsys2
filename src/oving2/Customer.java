package oving2;
public class Customer extends Thread {
	private int id;
	private int numCourses;
	private int takeaway;
	private SushiBar sb;
	
	Customer(SushiBar sb, int id, int numCourses, int takeaway) {
		this.sb = sb;
		this.id = id;
		this.numCourses = numCourses;
		this.takeaway = takeaway;
		Main.log(this + " is now created.");
	}
	
	public void run() {
		this.sb.enter(this);
	}
	
	/**
	 * 
	 * @return Customer ID
	 */
	public int getCustomerId() {
		return id;
	}
	
	/**
	 * Returns the amount of courses the Customer is going to eat inside the restaurant.
	 * @return Eat-in number
	 */
	public int getEatIn() {
		return numCourses - takeaway;
	}
	/**
	 * Returns the amount of courses the customers is going to take home.
	 * @return Takeaway number
	 */
	public int getTakeaway() {
		return takeaway;
	}
	
	/**
	 * Get the total numbers of orders for the customer
	 * @return Number of orders
	 */
	public int getTotalOrders() {
		return numCourses;
	}
	
	/**
	 * Converts Customer object to string on the format "Customer #id"
	 */
	public String toString() {
		return "Customer #" + this.id;
	}
}
