# 🚌 Scenario 2: Seat Booking System (Shared Resource)

## 📝 The Problem

An online system allows customers to book seats on a bus. Several users may attempt to book seats at the same time. Without proper synchronization, more seats can be booked than available (overbooking problem).

**Given:**
- Total seats available: 3
- Two customers (two threads)
- Each customer tries to book 2 seats

**Expected behavior:** Only one customer should succeed (3 seats - 2 = 1 remaining)
**Actual behavior (without sync):** Both customers may succeed, leading to negative available seats (-1)

---

## 💡 Solution and Technical Justifications

### 🔹 The Overbooking Problem

#### Non-Synchronized Booking Method

```java
public boolean bookSeats(int seatsRequested) {
    if (availableSeats >= seatsRequested) {
        // ⚠️ RACE CONDITION WINDOW
        try {
            Thread.sleep(50);  // Simulates booking processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        availableSeats -= seatsRequested;  // ⚠️ NOT ATOMIC
        return true;
    }
    return false;
}
```

**Why does this cause overbooking?**

1. **Same Pattern as Bank Account:**
   - Multiple threads check `availableSeats >= seatsRequested`
   - Both may pass the check simultaneously
   - Both proceed to book, resulting in overbooking

2. **Real-World Impact:**
   ```
   Available: 3 seats
   Passenger 1: Checks (3 >= 2) ✓ → Books 2 → Remaining: 1
   Passenger 2: Checks (3 >= 2) ✓ → Books 2 → Remaining: -1  [❌ Overbooked!]
   ```

3. **Why `Thread.sleep(50)`?**
   - Simulates real booking process (database update, payment processing, etc.)
   - Longer delay (50ms vs 10ms) increases chance of race condition
   - More realistic scenario

**What happens without synchronization?**
- Both passengers may see 3 available seats
- Both pass the availability check
- Both book seats, resulting in **-1 available seats** (overbooking)
- System allows more bookings than physical capacity

---

### 🔹 Solution: Synchronized Booking

```java
public synchronized boolean bookSeatsSynchronized(int seatsRequested) {
    if (availableSeats >= seatsRequested) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        availableSeats -= seatsRequested;
        return true;
    }
    return false;
}
```

**How synchronization prevents overbooking:**

1. **Sequential Access:**
   ```
   Passenger 1: Acquires lock → Checks (3 >= 2) ✓ → Books 2 → Releases lock
   Passenger 2: Waits... → Acquires lock → Checks (1 >= 2) ✗ → Cannot book → Releases lock
   ```

2. **Atomic Check-and-Book:**
   - The entire check-and-update operation is atomic
   - No other thread can see intermediate state
   - Guarantees only one booking succeeds when seats are limited

3. **Correct Final State:**
   - Available seats: 3 - 2 = 1 (correct)
   - Second passenger correctly sees insufficient seats
   - No overbooking occurs

---

### 🔹 Passenger Thread Implementation

```java
public class Passenger extends Thread {
    private Bus bus;
    private int seatsRequested;
    private boolean useSynchronized;

    @Override
    public void run() {
        System.out.println(getName() + " wants to book " + seatsRequested + " seat(s)");
        
        if (useSynchronized) {
            bus.bookSeatsSynchronized(seatsRequested);
        } else {
            bus.bookSeats(seatsRequested);  // Race condition
        }
    }
}
```

**Why separate `Passenger` class?**

1. **Encapsulation:**
   - Each passenger has its own booking request
   - Clear separation of concerns
   - Easy to extend (add passenger name, preferences, etc.)

2. **Thread Identity:**
   - `getName()` provides unique thread identifier
   - Useful for logging and debugging
   - Shows which passenger is booking

3. **Flexible Design:**
   - Can easily add more passengers
   - Each passenger can request different number of seats
   - Supports both synchronized and non-synchronized modes

---

### 🔹 Demonstration Pattern

```java
// Part 1: Show overbooking
demonstrateOverbooking();

// Part 2: Show synchronized solution
demonstrateSynchronizedSolution();
```

**Why demonstrate both scenarios?**

1. **Problem Visibility:**
   - See how overbooking occurs in real-time
   - Understand the business impact (negative seats)
   - Recognize the need for synchronization

2. **Solution Validation:**
   - Verify synchronization prevents overbooking
   - Confirm correct final state
   - Build confidence in the solution

3. **Learning Progression:**
   - Problem → Understanding → Solution → Verification
   - Complete learning cycle

---

## 🎯 Key Takeaways

1. **Shared Resource Protection:**
   - Any shared mutable state needs protection
   - Seats, tickets, inventory are all examples
   - Same synchronization principles apply

2. **Business Logic Integrity:**
   - Overbooking violates business rules
   - Can lead to customer dissatisfaction
   - Synchronization ensures data integrity

3. **Scalability Considerations:**
   - Synchronization has performance cost
   - But correctness is more important
   - Consider connection pooling, caching for performance

---

## 💻 Example Execution

### Without Synchronization (Overbooking)
```
Total seats available: 3
Passenger-1 wants to book 2 seat(s)
Passenger-2 wants to book 2 seat(s)
Passenger-1 booked 2 seat(s). Remaining seats: 1
Passenger-2 booked 2 seat(s). Remaining seats: -1  ⚠️ OVERBOOKED!

Final available seats: -1
Expected: -1 (or 1 if one booking failed)
⚠️  OVERBOOKING: More seats booked than available!
```

### With Synchronization (Thread-Safe)
```
Total seats available: 3
Passenger-1 wants to book 2 seat(s)
Passenger-2 wants to book 2 seat(s)
Passenger-1 booked 2 seat(s). Remaining seats: 1
Passenger-2 cannot book 2 seat(s). Only 1 available.  ✅ CORRECT!

Final available seats: 1
Expected: -1 (or 1 if one booking failed)
✅ SYNCHRONIZED: Thread-safe booking!
```

---

## 🔍 Comparison with Scenario 1

| Aspect | Bank Account | Seat Booking |
|--------|-------------|--------------|
| **Shared Resource** | `balance` (int) | `availableSeats` (int) |
| **Operation** | Withdrawal | Booking |
| **Problem** | Negative balance | Overbooking |
| **Solution** | Same: `synchronized` | Same: `synchronized` |
| **Pattern** | Check-then-act | Check-then-act |

**Key Insight:** Both scenarios follow the same pattern:
1. Check condition (balance >= amount OR seats >= requested)
2. Perform operation (withdraw OR book)
3. Update state (balance -= amount OR seats -= requested)

**The solution is the same:** Synchronize the entire check-and-update operation.

---

## 🚀 Extensions

Possible enhancements:
- Add passenger names and booking history
- Implement waitlist for overbooked scenarios
- Add cancellation functionality
- Use `ReentrantLock` for more advanced control
- Implement fair booking (first-come-first-served)

---

