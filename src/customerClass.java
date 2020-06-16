public class customerClass implements Runnable {

  public String ID;
  private Thread thread;
  volatile public boolean isAllowedInside = false;

  public customerClass(String num) {
    this.ID = num;
    this.thread = new Thread(this, num);
  }

  public void start() {
	  thread.start();
  }

  public void run() {
	  outsideLine();
	  while(this.isAllowedInside != true) {} //BW
	  enterStore();
  }
  
  public void outsideLine() {
	  System.out.println("Customer-"+this.ID+" gets on the line outside");
  }
  
  public void enterStore() {
	  System.out.println("Customer- "+this.ID+" enters the store");
	  
  }
  
  public void allowInside() {
	  this.isAllowedInside = true;
  }
}