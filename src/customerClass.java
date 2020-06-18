import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class customerClass implements Runnable {


  public String ID;
  public Thread thread = Thread.currentThread();;
  volatile public AtomicBoolean isAllowedInside = new AtomicBoolean(false);
  volatile public AtomicBoolean allowedToRegister = new AtomicBoolean(false);
  public AtomicBoolean isElderly = new AtomicBoolean(false);
  public AtomicBoolean canLeave = new AtomicBoolean(false);
  volatile public AtomicInteger yieldsLeft = new AtomicInteger(2);
  volatile public AtomicInteger sentRegister = new AtomicInteger(0);

  
  public customerClass(String num, boolean elderly) {
    this.ID = num;
    this.isElderly.set(elderly);
    this.thread = new Thread(this);
   
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
	  msg("Customer-"+this.ID+" is done shopping and has increased priority from "+oldPriority+" to "+newPriority+" and is rushing to the checkout Line");
	  try {
		Thread.sleep((long) (Math.random() * (10000 - 5000)) + 5000);
	  } catch (InterruptedException e1) {
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
						  checkoutTest[i].thread.yield();
						  checkoutTest[i].yieldsLeft.getAndDecrement();
						  msg("Customer-"+checkoutTest[i].ID+" yields to elderly customer-"+this.ID+". Now customer-"+checkoutTest[i].ID+" has "+checkoutTest[i].yieldsLeft+" yield left");
						  try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
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
	 msg("Customer-"+this.ID+" leaves the store and goes to their car");
	 storeClass.parkingLotLine[Integer.parseInt(this.ID)-1] = this;
	 try {
			Thread.sleep(1000000000);
		} catch (InterruptedException e) {
			msg("Customer-"+this.ID+" is woken up!");
		}
	 while(canLeave.get()==false) {}
	 msg("Customer-"+this.ID+" leaves the parking lot");  
	 
  }
  

  public void outsideLine() {
	  msg("Customer-"+this.ID+" gets on the line outside and elderly is "+isElderly.get());
  }
  
  private void enterStore() {
	 msg("Customer- "+this.ID+" enters the store and starts shopping");
	  try {
			Thread.sleep((long) (Math.random() * (20000 - 10000)) + 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  }
  
	private void getOnCheckoutLine() {
		msg("Customer-"+this.ID+" gets on the checkout line and has priority set back to normal");
		storeClass.checkoutLine.addFirst(this);
	}
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-storeClass.time)+"] "+thread.getName()+": "+m);
	}
}