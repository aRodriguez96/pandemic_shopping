public class managerClass implements Runnable {

  private Thread thread;

  public managerClass() {
    
    this.thread = new Thread(this);
  }

  public void start() {
    thread.start();
  }

  public void run() {
	  	int toOpenStore = 8;
	  	int onLine;
	  //	toOpenStore = (int) (Math.random() * (10 - 6)) + 6;
	  	/*
	  	while(storeClass.currentCustomers != toOpenStore) {}//BW
	  	openStore();
	  	onLine = storeClass.customerLine.size();
	  	while(storeClass.customersServed < storeClass.maxCustomers && storeClass.inStore == 0) {
	  		if(onLine < storeClass.storeCap) {
	  			for(int i = 1; i <= onLine; i++) {
	  				storeClass.customerLine.poll().isAllowedInside = true;
	  			}
	  		}
	  		else {
	  			for(int i = 1; i <= 6; i++) {
	  				storeClass.customerLine.poll().isAllowedInside = true;
	  			}
	  		}
	  	}
	  	*/
	  	while(storeClass.currentCustomers != toOpenStore) {}//BW
	  	openStore();
	  		System.out.println("manager lets in 6");
		  	for(int i = 1; i <= 6; i++) {
					storeClass.customerLine.poll().allowInside();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		  //	System.out.println(storeClass.customerLine.size());
	  	//closeStore();
  
  
  
  
  }
  
  public void openStore() {
	  storeClass.isStoreOpen = true;
	  System.out.println("The store has been opened!");
	  
  }
  
  public void closeStore() {
	  storeClass.isStoreOpen = false;
	  System.out.println("The store has been closed!");
	  
  }
}