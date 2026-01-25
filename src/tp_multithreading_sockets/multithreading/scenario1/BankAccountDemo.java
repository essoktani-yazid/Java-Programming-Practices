package tp_multithreading_sockets.multithreading.scenario1;


public class BankAccountDemo {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("SCENARIO 1: Bank Account Withdrawal");
        System.out.println("=".repeat(60));
        
        // Part 1: Demonstrate race condition (without synchronization)
        System.out.println("\n--- PART 1: WITHOUT SYNCHRONIZATION (Race Condition) ---");
        demonstrateRaceCondition();
        
        // Wait a bit before next demonstration
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Part 2: Demonstrate solution (with synchronization)
        System.out.println("\n--- PART 2: WITH SYNCHRONIZATION (Thread-Safe) ---");
        demonstrateSynchronizedSolution();
    }

    private static void demonstrateRaceCondition() {
        BankAccount account = new BankAccount(500);
        System.out.println("Initial balance: " + account.getBalance());
        
        Client client1 = new Client("Client-1", account, 450, false);
        Client client2 = new Client("Client-2", account, 100, false);
        
        // Start both threads simultaneously
        client1.start();
        client2.start();
        
        // Wait for both threads to complete
        try {
            client1.join();
            client2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal balance: " + account.getBalance());
        System.out.println("Expected: -50 (or 0 if one withdrawal failed)");
        System.out.println("⚠️  RACE CONDITION: Both withdrawals may succeed incorrectly!");
    }

    private static void demonstrateSynchronizedSolution() {
        BankAccount account = new BankAccount(500);
        System.out.println("Initial balance: " + account.getBalance());
        
        Client client1 = new Client("Client-1", account, 450, true);
        Client client2 = new Client("Client-2", account, 100, true);
        
        // Start both threads simultaneously
        client1.start();
        client2.start();
        
        // Wait for both threads to complete
        try {
            client1.join();
            client2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal balance: " + account.getBalance());
        System.out.println("Expected: -50 (or 0 if one withdrawal failed)");
        System.out.println("✅ SYNCHRONIZED: Thread-safe execution!");
    }
}

