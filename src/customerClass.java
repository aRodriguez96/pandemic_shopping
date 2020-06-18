import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class customerClass implements Runnable {


public String ID;
  private Thread thread;
  volatile public AtomicBoolean isAllowedInside = new AtomicBoolean(false);
  volatile public AtomicBoolean allowedToRegister = new AtomicBoolean(false);
  public AtomicBoolean isElderly = new AtomicBoolean(false);
  volatile public AtomicInteger yieldsLeft = new AtomicInteger(2);
  volatile public AtomicInteger sentRegister = new AtomicInteger(0);
  public Thread t = Thread.currentThread();
  
  public customerClass(String num, boolean elderly) {
    this.ID = num;
    this.isElderly.set(elderly);
    this.thread = new Thread(this, num);
  }

  public void start() {
	  thread.start();
  }

  public void run() {
	  outsideLine();
	  while(this.isAllowedInside.get() != true) {} //BW
	  enterStore();
	  int oldPriority = Thread.currentThread().getPriority(); 
	  int newPriority = oldPriority+1;
	  Thread.currentThread().setPriority(newPriority); 
	  System.out.println(" and has increased priority from "+oldPriority+" to "+newPriority+" and is rushing to the checkout Line");
	  try {
		Thread.sleep((long) (Math.random() * (10000 - 5000)) + 5000);
	  } catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	  }
	  Thread.currentThread().setPriority(oldPriority); 
	  getOnCheckoutLine();
	  if(this.isElderly.get() == true) {
		  int size = storeClass.checkoutLineSize.get();
		  customerClass[] checkoutTest = new customerClass[size];
		  checkoutTest = storeClass.checkoutLine.toArray(checkoutTest);
		  if(checkoutTest.length > 0) {
			  for(int i = 1; i < checkoutTest.length; i++){ 
				  if(checkoutTest[i].isElderly.get() == true) {}
				  else {
					  if(checkoutTest[i].yieldsLeft.get() > 0) {
						  checkoutTest[i].yieldToElderly();
						  checkoutTest[i].yieldsLeft.getAndDecrement();
						  System.out.println("Customer-"+checkoutTest[i].ID+" yields to elderly customer-"+this.ID+". Now customer-"+checkoutTest[i].ID+" has "+checkoutTest[i].yieldsLeft+" yield left");
						  try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  }
					  else {
						  break;
					  }
				  }
		      }
		  } 
	  }
	  while(this.allowedToRegister.get() == false) {} //BW on checkout line
	  try {
		Thread.sleep((long) (Math.random() * (10000 - 5000)) + 5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  if(this.sentRegister.get() == 0) {
		  storeClass.register1.set(false);
	  }
	  else {
		  storeClass.register3.set(false);
	  }
	 storeClass.busyRegisters.getAndDecrement();
	 storeClass.inStore.getAndDecrement();
	 storeClass.customersServed.getAndIncrement();
	 System.out.println("Customer-"+this.ID+" leaves the store and goes to their car");
	 storeClass.parkingLotLine[Integer.parseInt(this.ID)-1] = this;
	 try {
			Thread.sleep(1000000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Customer-"+this.ID+" is woken up and leaves the parking lot");
		}
	    
	 
  }
  

  public void outsideLine() {
	  System.out.println("Customer-"+this.ID+" gets on the line outside and elderly is "+this.isElderly.get());
  }
  
  public void allowInside() {
	  this.isAllowedInside.set(true);
  }
  
  private void enterStore() {
	  System.out.println("Customer- "+this.ID+" enters the store and starts shopping");
	  try {
			Thread.sleep((long) (Math.random() * (20000 - 10000)) + 10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Customer-"+this.ID+" is done shopping");
  }
  
	private void getOnCheckoutLine() {
		System.out.println("Customer-"+this.ID+" gets on the checkout line and has priority set back to normal");
		storeClass.checkoutLine.addFirst(this);
	}
	
	public void yieldToElderly() {
		Thread.yield();
	}
/*
	public boolean checkIfAlive() {
		if(t.isAlive()) {
			return true;
		}
		else {
			return false;
		}
	}
	*/
	public void interruptCar() {
		this.t.interrupt();
		
	}
	/*
	public void joinThread() {
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/

}