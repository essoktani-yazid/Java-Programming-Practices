package tp_multithreading_sockets.multithreading.scenario1;


public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public boolean withdraw(int amount) {
        if (balance >= amount) {
            // Simulate some processing time (race condition window)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + 
                             " withdrew " + amount + 
                             ". Remaining balance: " + balance);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + 
                             " cannot withdraw " + amount + 
                             ". Insufficient balance: " + balance);
            return false;
        }
    }


    public synchronized boolean withdrawSynchronized(int amount) {
        if (balance >= amount) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + 
                             " withdrew " + amount + 
                             ". Remaining balance: " + balance);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + 
                             " cannot withdraw " + amount + 
                             ". Insufficient balance: " + balance);
            return false;
        }
    }


    public boolean withdrawSynchronizedBlock(int amount) {
        synchronized (this) {
            if (balance >= amount) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + 
                                 " withdrew " + amount + 
                                 ". Remaining balance: " + balance);
                return true;
            } else {
                System.out.println(Thread.currentThread().getName() + 
                                 " cannot withdraw " + amount + 
                                 ". Insufficient balance: " + balance);
                return false;
            }
        }
    }

    public int getBalance() {
        return balance;
    }
}

