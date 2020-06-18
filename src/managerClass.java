public class managerClass implements Runnable {

  private Thread thread;


  public managerClass() {
    
    this.thread = new Thread(this);

  }

  public void start() {
    thread.start();
  }

  public void run() {
	  	int onLine;
	  	int toOpenStore = (int) (Math.random() * (10 - 6)) + 6; //randomly decides to open the store between 6-10 customers get on the line
	  	
	  	while(storeClass.currentCustomers.get() != toOpenStore) {}//BW
	  	openStore();
	  	while(storeClass.customersServed.get() < storeClass.maxCustomers.get()) { 
	  		onLine = storeClass.storeLine.size();
	  		if(storeClass.inStore.get() == 0) {
	  			if(onLine < storeClass.storeCap) {
	  				if(onLine == 0) continue;
	  				msg("Manager lets in "+onLine);
		  			for(int i = 1; i <= onLine; i++) {
		  				storeClass.storeLine.poll().isAllowedInside.set(true);
		  				storeClass.inStore.getAndIncrement();
		  				try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		  			}
		  		}
		  		else {
		  			msg("Manager lets in 6");
		  			for(int i = 1; i <= 6; i++) {
		  				storeClass.storeLine.poll().isAllowedInside.set(true);
		  				storeClass.inStore.getAndIncrement();
		  				try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		  			}
		  		}
	  		}
	  	}
	  	msg("Manager is done for the day");
  }
  
  public void openStore() {
	  storeClass.isStoreOpen.set(true);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  msg("The store has been opened!");
  }
  
  public void closeStore() {
	  storeClass.isStoreOpen.set(false);
	 msg("The store has been closed!");
	  
  }
  
  public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-storeClass.time)+"] "+thread.getName()+": "+m);
  }
}