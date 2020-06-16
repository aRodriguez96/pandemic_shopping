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
	  	
	  	while(storeClass.currentCustomers != toOpenStore) {}//BW
	  	openStore();
	  	onLine = storeClass.storeLine.size();
	  	while(storeClass.customersServed < storeClass.maxCustomers) { 
	  		if(storeClass.inStore == 0) {
	  			if(onLine < storeClass.storeCap) {
	  				System.out.println("Manager lets in "+onLine);
		  			for(int i = 1; i <= onLine; i++) {
		  				storeClass.storeLine.poll().isAllowedInside = true;
		  				storeClass.inStore++;
		  				try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		  			}
		  		}
		  		else {
		  			System.out.println("Manager lets in 6");
		  			for(int i = 1; i <= 6; i++) {
		  				storeClass.storeLine.poll().isAllowedInside = true;
		  				storeClass.inStore++;
		  				try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		  			}
		  		}
	  		}
	  	}
  }
  
  public void openStore() {
	  storeClass.isStoreOpen = true;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  System.out.println("The store has been opened!");
  }
  
  public void closeStore() {
	  storeClass.isStoreOpen = false;
	  System.out.println("The store has been closed!");
	  
  }
}