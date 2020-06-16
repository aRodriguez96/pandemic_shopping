public class customerClass implements Runnable {

  public String ID;
  private Thread thread;
  volatile public boolean isAllowedInside = false;
  public boolean isElderly;
  volatile public int yieldsLeft = 2;
  
  public customerClass(String num, boolean elderly) {
    this.ID = num;
    this.isElderly = elderly;
    this.thread = new Thread(this, num);
  }

  public void start() {
	  thread.start();
  }

  public void run() {
	  outsideLine();
	  while(this.isAllowedInside != true) {} //BW
	  enterStore();
	  goShopping();
	  goToCheckout();
	  if(this.isElderly == true) {
		  //customerClass checkoutCustomer;
		  int size = storeClass.checkoutLineSize;
		  customerClass[] checkoutTest = new customerClass[size];
		  checkoutTest = storeClass.checkoutLine.toArray(checkoutTest);
		  if(checkoutTest.length > 0) {
			  for(int i = 1; i < checkoutTest.length; i++){ 
				  if(checkoutTest[i].isElderly == true) {}
				  else {
					  if(checkoutTest[i].yieldsLeft > 0) {
						  checkoutTest[i].yieldsLeft--;
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
  }
  

  public void outsideLine() {
	  System.out.println("Customer-"+this.ID+" gets on the line outside and elderly is "+this.isElderly);
  }
  
  public void allowInside() {
	  this.isAllowedInside = true;
  }
  
  private void enterStore() {
	  System.out.println("Customer- "+this.ID+" enters the store");
	  
  }
  
  private void goShopping() {
		System.out.println("Customer-"+this.ID+" is doing their shopping");
		try {
			Thread.sleep((long) (Math.random() * (20000 - 10000)) + 10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Customer-"+this.ID+" is ready for checkout");
  }
  
  
	private void goToCheckout() {
		System.out.println("Customer-"+this.ID+" gets on the checkout line");
		storeClass.checkoutLine.addFirst(this);
	}

}