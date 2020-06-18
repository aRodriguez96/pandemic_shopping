import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class storeClass {
    volatile public static AtomicInteger maxCustomers;
    volatile public static int storeCap = 6;
    volatile public static Queue < customerClass > storeLine = new LinkedList < > ();
    volatile public static LinkedList < customerClass > checkoutLine = new LinkedList < customerClass > ();
    volatile public static customerClass[] parkingLotLine;
    volatile public static AtomicInteger checkoutLineSize = new AtomicInteger(checkoutLine.size());
    volatile public static AtomicBoolean isStoreOpen = new AtomicBoolean(false);
    volatile public static AtomicInteger inStore = new AtomicInteger(0);
    volatile public static AtomicInteger currentCustomers = new AtomicInteger(0);
    volatile public static AtomicInteger customersServed = new AtomicInteger(0);
    volatile public static AtomicBoolean register1 = new AtomicBoolean(false);
    volatile public static AtomicBoolean register3 = new AtomicBoolean(false);
    volatile public static AtomicInteger busyRegisters = new AtomicInteger(0);
    private static int ID = 1;
    public static long time;

    public static void main(String[] args) throws InterruptedException {
        maxCustomers = new AtomicInteger(Integer.parseInt(args[0]));
        parkingLotLine = new customerClass[maxCustomers.get()];
        time = System.currentTimeMillis();
        //starts the manager and employee thread
        managerClass manager = new managerClass();
        manager.start();
        employeeClass employee = new employeeClass();
        employee.start();
        customerClass customer;

        while (currentCustomers.get() < maxCustomers.get()) {
            //customers arrive every 1-10 seconds and join the line outside 
            Thread.sleep((int)(Math.random() * (10000 - 1000)) + 1000); //(high - low) + low in ms
            if ((int)(Math.random() * (4 - 1)) + 1 == 1) {
                customer = new customerClass(Integer.toString(ID), true);
            } else {
                customer = new customerClass(Integer.toString(ID), false);
            }
            storeLine.add(customer);
            customer.start();
            ID++;
            currentCustomers.getAndIncrement();
        }
    }
}