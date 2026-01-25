# 🏦 Scenario 1: Bank Account Withdrawal (Race Condition)

## 📝 The Problem

A bank account is shared between multiple clients. Each client can withdraw money from the same account at the same time. Without proper synchronization, race conditions occur, leading to incorrect balance calculations.

**Given:**
- Initial balance: 500
- Two clients (two threads)
- Client 1 withdraws 450
- Client 2 withdraws 100

**Expected behavior:** Only one withdrawal should succeed (balance becomes 50 or 0)
**Actual behavior (without sync):** Both withdrawals may succeed, leading to negative balance (-50)

---

## 💡 Solution and Technical Justifications

### 🔹 The Race Condition Problem

#### Non-Synchronized Withdrawal Method

```java
public boolean withdraw(int amount) {
    if (balance >= amount) {
        // ⚠️ RACE CONDITION WINDOW
        try {
            Thread.sleep(10);  // Simulates processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        balance -= amount;  // ⚠️ NOT ATOMIC OPERATION
        return true;
    }
    return false;
}
```

**Why does this cause a race condition?**

1. **Non-Atomic Operations:**
   - `balance >= amount` (read)
   - `balance -= amount` (read-modify-write)
   - These are **three separate operations**, not atomic

2. **Interleaving Problem:**
   ```
   Thread 1: Reads balance (500) >= 450 ✓
   Thread 2: Reads balance (500) >= 100 ✓  [Both pass the check!]
   Thread 1: balance = 500 - 450 = 50
   Thread 2: balance = 50 - 100 = -50  [❌ Negative balance!]
   ```

3. **Why `Thread.sleep(10)`?**
   - Increases the window for race conditions to occur
   - Simulates real-world processing time (database access, validation, etc.)
   - Makes the problem more visible and reproducible

**What happens without synchronization?**
- Both threads may read the same initial balance (500)
- Both pass the `if (balance >= amount)` check
- Both proceed to withdraw, resulting in incorrect final balance
- Final balance can be negative or incorrect

---

### 🔹 Solution 1: Synchronized Method

```java
public synchronized boolean withdrawSynchronized(int amount) {
    if (balance >= amount) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        balance -= amount;
        return true;
    }
    return false;
}
```

**Why `synchronized` keyword?**

1. **Mutual Exclusion:**
   - Only **one thread** can execute this method at a time
   - Other threads must **wait** until the current thread finishes
   - Prevents interleaving of operations

2. **Intrinsic Lock (Monitor):**
   - Each object has an **intrinsic lock** (monitor)
   - `synchronized` method acquires the lock on `this` object
   - Lock is automatically released when method exits (even if exception occurs)

3. **Thread Safety:**
   - The entire method becomes **atomic** from the perspective of other threads
   - No other thread can see intermediate states
   - Guarantees **happens-before** relationship

**How it works:**
```
Thread 1: Acquires lock → Reads balance (500) → Withdraws 450 → Releases lock
Thread 2: Waits... → Acquires lock → Reads balance (50) → Cannot withdraw 100 → Releases lock
```

**Result:** Only one withdrawal succeeds, balance remains correct (50)

---

### 🔹 Solution 2: Synchronized Block

```java
public boolean withdrawSynchronizedBlock(int amount) {
    synchronized (this) {
        if (balance >= amount) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

**Why use synchronized block instead of synchronized method?**

1. **Fine-Grained Control:**
   - Can synchronize only **part** of the method
   - Reduces lock contention (smaller critical section)
   - Better performance when method has non-critical code

2. **Flexible Lock Object:**
   - Can synchronize on **any object**, not just `this`
   - Example: `synchronized (lockObject)` for more control
   - Useful for multiple locks in complex scenarios

3. **Same Effect:**
   - `synchronized (this)` is equivalent to `synchronized` method
   - Both acquire the same intrinsic lock
   - Choose based on code organization preference

**When to use which?**
- **Synchronized method:** Simple, clear, entire method needs protection
- **Synchronized block:** Need to protect only part of method, or need custom lock object

---

### 🔹 Client Thread Implementation

```java
public class Client extends Thread {
    private BankAccount account;
    private int withdrawalAmount;
    private boolean useSynchronized;

    @Override
    public void run() {
        if (useSynchronized) {
            account.withdrawSynchronized(withdrawalAmount);
        } else {
            account.withdraw(withdrawalAmount);  // Race condition
        }
    }
}
```

**Why extend `Thread`?**

1. **Simple Thread Creation:**
   - Each `Client` object **is** a thread
   - Call `client.start()` to begin execution
   - `run()` method contains the thread's logic

2. **Alternative: Implement `Runnable`:**
   ```java
   public class Client implements Runnable {
       // ...
   }
   // Usage: new Thread(new Client(...)).start();
   ```
   - More flexible (can extend another class)
   - Better separation of concerns
   - Preferred in modern Java

**Why `start()` not `run()`?**
- `start()`: Creates new thread, calls `run()` in that thread
- `run()`: Executes in **current thread** (no concurrency!)
- Always use `start()` for multithreading

---

### 🔹 Demonstration Pattern

```java
// Part 1: Show race condition
demonstrateRaceCondition();

// Part 2: Show synchronized solution
demonstrateSynchronizedSolution();
```

**Why demonstrate both?**

1. **Learning Objective:**
   - See the **problem** first (race condition)
   - Then see the **solution** (synchronization)
   - Understand the difference

2. **Observable Behavior:**
   - Without sync: Final balance may be **-50** (incorrect)
   - With sync: Final balance is **50** or **0** (correct)
   - Visual proof of the problem and solution

3. **Why `join()`?**
   ```java
   client1.start();
   client2.start();
   client1.join();  // Wait for thread 1 to finish
   client2.join();  // Wait for thread 2 to finish
   ```
   - Ensures both threads complete before printing final balance
   - Without `join()`, main thread might print balance before threads finish
   - Critical for accurate demonstration

---

## 🎯 Key Takeaways

1. **Race Conditions:**
   - Occur when multiple threads access shared data without synchronization
   - Non-atomic operations (read-modify-write) are vulnerable
   - Can lead to data corruption and incorrect results

2. **Synchronization:**
   - `synchronized` keyword provides mutual exclusion
   - Only one thread can execute synchronized code at a time
   - Ensures thread-safe access to shared resources

3. **Best Practices:**
   - Always synchronize access to shared mutable state
   - Use smallest possible critical section (synchronized block vs method)
   - Consider using `java.util.concurrent` classes for advanced scenarios

---

## 💻 Example Execution

### Without Synchronization (Race Condition)
```
Initial balance: 500
Client-1 is attempting to withdraw 450
Client-2 is attempting to withdraw 100
Client-1 withdrew 450. Remaining balance: 50
Client-2 withdrew 100. Remaining balance: -50  ⚠️ INCORRECT!

Final balance: -50
Expected: -50 (or 0 if one withdrawal failed)
⚠️  RACE CONDITION: Both withdrawals may succeed incorrectly!
```

### With Synchronization (Thread-Safe)
```
Initial balance: 500
Client-1 is attempting to withdraw 450
Client-2 is attempting to withdraw 100
Client-1 withdrew 450. Remaining balance: 50
Client-2 cannot withdraw 100. Insufficient balance: 50  ✅ CORRECT!

Final balance: 50
Expected: -50 (or 0 if one withdrawal failed)
✅ SYNCHRONIZED: Thread-safe execution!
```

---

## 🔍 Additional Notes

- **Performance Impact:** Synchronization has overhead (locking/unlocking)
- **Deadlocks:** Be careful with multiple locks to avoid deadlocks
- **Alternatives:** Consider `java.util.concurrent.locks.ReentrantLock` for more control
- **Atomic Operations:** For simple operations, consider `AtomicInteger` instead of `synchronized`

---

