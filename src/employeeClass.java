public class employeeClass implements Runnable {

  private Thread thread;

  public employeeClass() {
	  this.thread = new Thread(this);
  }

  public void start() {
    thread.start();
  }

  public void run() {
 
  }
}