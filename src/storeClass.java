import java.util.LinkedList; 
import java.util.Queue;


public class storeClass {
 
  volatile public static Queue<customerClass> storeLine = new LinkedList<>();
  volatile public static LinkedList<customerClass> checkoutLine = new LinkedList<customerClass>();
  volatile public static int checkoutLineSize = checkoutLine.size();
  volatile public static boolean checkingLine = false;
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
	  customerClass customer;
	  
	  while(currentCustomers < maxCustomers) {
		  //customers arrive every 1-10 seconds and join the line outside 
		  Thread.sleep((int) (Math.random() * (10000 - 1000)) + 1000); //(high - low) + low in ms
		  if((int) (Math.random() * (4 - 1)) + 1 == 1){
			  customer = new customerClass(Integer.toString(ID), true);
		  }
		  else {
			  customer = new customerClass(Integer.toString(ID), false);
		  }
		  storeLine.add(customer);
		  customer.start();
		  ID++;
		  currentCustomers++;
	  }
	 

    
  }
}