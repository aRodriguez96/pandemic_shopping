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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  System.out.println("Customer-"+checkoutCustomer.ID+" is sent to register 1");
				  }
				  else if(storeClass.register3.get() == false) {
					  storeClass.register3.set(true);
					  storeClass.busyRegisters.getAndIncrement();
					  checkoutCustomer = storeClass.checkoutLine.removeLast();
					  checkoutCustomer.allowedToRegister.set(true);
					  checkoutCustomer.sentRegister.set(2);
					  System.out.println("Customer-"+checkoutCustomer.ID+" is sent to register 3");
				  }
			  }
		  }
	  }
	  closeStore();
	  storeClass.helpCustomers.set(true);
	  /*
	  customerClass customerCar;
	  for(int i = 0; i<storeClass.maxCustomers.get(); i++) {
		  customerCar = storeClass.parkingLotLine[i];
		  if(customerCar.t.isAlive()) {
			  customerCar.t.interrupt();
			  try {
				customerCar.t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  }
	  
	  employeeLeaves();
	  */
  }

  private void employeeLeaves() {
	System.out.println("Employee leaves the parking lot");
	
}

private void closeStore() {
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  System.out.println("Closing store...");
	  try {
			Thread.sleep((long) (Math.random() * (10000 - 5000)) + 5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	storeClass.isStoreOpen.set(false);
	    System.out.println("Employee has closed the store for the day and walks outside to the chaos to wake up customers");

  }
}