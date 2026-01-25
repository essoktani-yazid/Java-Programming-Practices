package tp_multithreading_sockets.multithreading.scenario1;


public class Client extends Thread {
    private BankAccount account;
    private int withdrawalAmount;
    private boolean useSynchronized;

    public Client(String name, BankAccount account, int withdrawalAmount, boolean useSynchronized) {
        super(name);
        this.account = account;
        this.withdrawalAmount = withdrawalAmount;
        this.useSynchronized = useSynchronized;
    }

    @Override
    public void run() {
        System.out.println(getName() + " is attempting to withdraw " + withdrawalAmount);
        
        if (useSynchronized) {
            // Use synchronized method
            account.withdrawSynchronized(withdrawalAmount);
        } else {
            // Use non-synchronized method (demonstrates race condition)
            account.withdraw(withdrawalAmount);
        }
    }
}

