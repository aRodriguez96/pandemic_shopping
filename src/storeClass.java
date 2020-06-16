import java.util.LinkedList; 
import java.util.Queue;

public class storeClass {
	
  volatile public static Queue<customerClass> customerLine = new LinkedList<>();
  volatile public static boolean isStoreOpen = false; 
  volatile public static  int inStore = 0;
  volatile public static int currentCustomers = 0;
  volatile public static int maxCustomers = 20;
  volatile public static int storeCap = 6;
  volatile public static int customersServed = 0;
  private static int ID = 1;
  
  public static void main(String[] args) throws InterruptedException {
	  //starts the manager and employee thread
	  managerClass manager = new managerClass();
	  manager.start();
	  employeeClass employee = new employeeClass();
	  employee.start();
	  
	  while(currentCustomers < maxCustomers) {
		  //customers arrive every 1-10 seconds and join the line outside 
		  Thread.sleep((int) (Math.random() * (10000 - 1000)) + 1000); //(high - low) + low in ms
		  customerClass customer = new customerClass(Integer.toString(ID));
		  customerLine.add(customer);
		  customer.start();
		  ID++;
		  currentCustomers++;
	  }
	 

    
  }
}