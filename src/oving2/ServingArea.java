package oving2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



public class ServingArea {
	private int capacity;
	ArrayList<Customer> customers;
	Queue<Customer> queue;
	SushiBar sb;
	
	private int total, takeaway, eaten = 0;
	
	/**
	 * Serving Area constructor
	 * @param sb SushiBar the Serving Area belongs to
	 * @param capacity Capacity of the Serving Area
	 */
	public ServingArea(SushiBar sb, int capacity) {
		this.capacity = capacity;
		this.customers = new ArrayList<Customer>();
		this.queue = new LinkedList<Customer>();
		this.sb = sb;
	}
	/**
	 * Checks if the Serving Area is full
	 * @return Returs if the Serving Area is full or not
	 */
	public synchronized boolean isFull() {
		return this.customers.size() >= this.capacity;
	}
	
	/**
	 * Let a customer enter the Serving Area. If the area is full, this method will lock until there is a free spot and
	 * the customer is next in line.
	 * @param c Customer that has entered the serving area
	 */
	public synchronized void enter(Customer c) {
		if(isFull()) {
			Main.log(c + " is waiting for a free seat.");
			queue.add(c);
			while(true) {
				try {
					this.wait();
				} catch (Exception e) {
				}
				if(queue.peek() == c && !isFull()) {
					break;
				}
			}
			queue.remove();
			Main.log("Now there's a free seat in the shop.");
			
			
		}
		customers.add(c);
	}
	
	/**
	 * @return total eat in orders
	 */
	public synchronized int getEaten() {
		return eaten;
	}
	/**
	 * 
	 * @return total takeaway orders
	 */
	public synchronized int getTakeaway() {
		return takeaway;
	}
	/**
	 * 
	 * @return total orders
	 */
	public synchronized int getTotal() {
		return total;
	}
	
	/**
	 * Simulates eating.
	 * @param c The customer thats about to eat
	 */
	public void eat(Customer c) {
		Main.log(c + " has a seat now.");
		order(c.getTotalOrders());
		Main.log(c + " is eating sushi.");
		eat(c.getEatIn());
		takeaway(c.getTakeaway());
		
	}
	/**
	 * Orders a given amount of courses
	 */
	public void order(int numCourses) {
		incTotal(numCourses);
		try {
			Thread.sleep(Main.ORDER_COEFFISIENT*numCourses);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Eat a given amount of courses
	 * @param numCourses Number of courses
	 */
	public void eat(int numCourses) {
		incEaten(numCourses);
		try {
			Thread.sleep(Main.EATING_COEFFISIENT*numCourses);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Take away a given number of courses
	 * @param num Number of takeaway courses
	 */
	public void takeaway(int num) {
		incTakeaway(num);
	}
	
	/*
	 * Methods for increasing counters
	 */
	private synchronized void incTotal(int i) {
		total += i;
	}
	
	private synchronized void incTakeaway(int i) {
		takeaway += i;
	}
	
	private synchronized void incEaten(int i) {
		eaten += i;
	}
	/**
	 * Simulates a customer leaving the serving area. Notifies the waiting customers.
	 * If serving area is empty, tell the Sushi Bar (it would like to know :) )
	 * @param c The leaving customer
	 */
	public synchronized void leave(Customer c) {
		Main.log(c  + " has left the shop.");
		if(this.isFull()) {
			this.notifyAll();
		}
		customers.remove(c);
		if(customers.size() < 1) {
			this.sb.queueEmpty();
		}	
	}
	
	
}
