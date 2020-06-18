public class employeeClass implements Runnable {

  private Thread thread;


  public employeeClass() {
	  this.thread = new Thread(this);
	
  }

  public void start() {
    thread.start();
  }

  public void run() {
	  customerClass checkoutCustomer;
	  while(storeClass.customersServed.get() < storeClass.maxCustomers.get()) {
		  if(storeClass.checkoutLine.size() > 0) {
			  if(storeClass.busyRegisters.get() < 2) {
				  if(storeClass.register1.get() == false) {
					  storeClass.register1.set(true);
					  storeClass.busyRegisters.getAndIncrement();
					  checkoutCustomer = storeClass.checkoutLine.removeLast();
					  checkoutCustomer.allowedToRegister.set(true);
					  checkoutCustomer.sentRegister.set(0);
					  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					  msg("Customer-"+checkoutCustomer.ID+" is sent to register 1");
				  }
				  else if(storeClass.register3.get() == false) {
					  storeClass.register3.set(true);
					  storeClass.busyRegisters.getAndIncrement();
					  checkoutCustomer = storeClass.checkoutLine.removeLast();
					  checkoutCustomer.allowedToRegister.set(true);
					  checkoutCustomer.sentRegister.set(2);
					  msg("Customer-"+checkoutCustomer.ID+" is sent to register 3");
				  }
			  }
		  }
	  }
	  closeStore();
	  for(int i = 0; i<storeClass.maxCustomers.get(); i++) {
		  if(storeClass.parkingLotLine[i].thread.isAlive()) {
			  storeClass.parkingLotLine[i].thread.interrupt();
			  try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
		  }
	  }
	  for(int i = 0; i<storeClass.maxCustomers.get(); i++) {
		  try {
			  storeClass.parkingLotLine[i].canLeave.set(true);
			  storeClass.parkingLotLine[i].thread.join();
		  } catch (InterruptedException e) {
			e.printStackTrace();
		  }
		  try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	  }
	  employeeLeaves();
	  
  }

  private void employeeLeaves() {
	msg("Employee leaves the parking lot");
	
}

  private void closeStore() {
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	 msg("Closing store...");
	  try {
			Thread.sleep((long) (Math.random() * (15000 - 10000)) + 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  	storeClass.isStoreOpen.set(false);
	   msg("Employee has closed the store for the day and walks outside to the chaos to wake up customers");
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

  }
  
  public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-storeClass.time)+"] "+thread.getName()+": "+m);
	}
}