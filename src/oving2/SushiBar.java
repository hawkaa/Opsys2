package oving2;

import java.util.Timer;
import java.util.TimerTask;

public class SushiBar {
	
	private Door door;
	private Timer timer;
	private ServingArea sa;
	
	public SushiBar() {
		this.door = new Door(this);		
		this.timer = new Timer();
		this.sa = new ServingArea(this, Main.SERVING_AREA_CAPACITY);
	}
	
	public synchronized void open() {
		Main.log("***** RESTAURANT OPEN, SIMULATION STARTED. *****");
		this.timer.schedule(new CloseTask(), Main.RESTAURANT_OPEN*1000);
		this.door.open();
		try {
			this.wait();
		} catch (InterruptedException e) {
		}
		Main.log("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");
	}
	
	private void close() {
		this.door.close();
	}
	
	public void enter(Customer c) {
		Main.log(c + " arrived at restaurant.");
		sa.enter(c);
		sa.eat(c);
		sa.leave(c);
	}
	
	public void printStatistics() {
		Main.log("Total orders: " + sa.getTotal());
		Main.log("Eaten orders: " + sa.getEaten());
		Main.log("Takeaway orders" + sa.getTakeaway());
	}
	
	/**
	 * If queue at Serving Area is empty, this event is called.
	 * If the restaurant is closed, invoke the waiting Close thread 
	 */
	public synchronized void queueEmpty() {
		if(!this.door.isOpen()) {
			this.notify();
		}
	}
	
	private class CloseTask extends TimerTask {
		public void run() {
			close();
			timer.cancel();
		}
	}

}
