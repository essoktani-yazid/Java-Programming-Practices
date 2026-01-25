# 🎮 Level 2: Multi-threaded Network Version with Leaderboard

## 📝 The Problem

Extend the Magic Number Guessing Game to support:
1. **Multi-threaded Server:** Handle multiple simultaneous players
2. **Network Play:** Server on one machine, clients on others
3. **Score Tracking:** Leaderboard showing fewest attempts
4. **Player Ranking:** System to rank players

---

## 💡 Solution and Technical Justifications

### 🔹 Multi-Threaded Server Architecture

#### Thread-Per-Client Model

```java
while (true) {
    Socket clientSocket = serverSocket.accept();
    System.out.println("✅ New client connected: " + clientSocket.getInetAddress());
    
    // Create a new thread for each client
    ClientHandler clientHandler = new ClientHandler(clientSocket, leaderboard);
    Thread clientThread = new Thread(clientHandler);
    clientThread.start();
}
```

**Why thread-per-client?**

1. **Concurrent Handling:**
   - Each client runs in its **own thread**
   - Multiple clients can play **simultaneously**
   - No client blocks another

2. **Scalability:**
   - Can handle many clients (limited by system resources)
   - Each thread is independent
   - One slow client doesn't affect others

3. **Alternative Models:**
   - **Thread Pool:** Reuse threads (more efficient)
   - **NIO (Non-blocking I/O):** Single thread handles many clients
   - Thread-per-client is simplest to understand

**How it works:**
```
Main Thread: Accepts connections → Creates thread → Continues accepting
Client Thread 1: Handles client 1's game
Client Thread 2: Handles client 2's game
Client Thread 3: Handles client 3's game
All run concurrently!
```

---

#### ClientHandler Implementation

```java
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Leaderboard leaderboard;
    
    @Override
    public void run() {
        // Handle this client's game
        // Each client gets its own magic number
        // Updates leaderboard when game ends
    }
}
```

**Why `implements Runnable`?**

1. **Separation of Concerns:**
   - `ClientHandler` defines **what** to do
   - `Thread` defines **how** to execute
   - More flexible than extending `Thread`

2. **Reusability:**
   - Can pass to `ExecutorService` (thread pool)
   - Can run in different contexts
   - Better design pattern

3. **Thread Creation:**
   ```java
   Thread clientThread = new Thread(clientHandler);
   clientThread.start();
   ```
   - Creates new thread
   - Executes `run()` method in that thread
   - Main thread continues immediately

---

#### Independent Game State

```java
// Each client gets its own magic number
int magicNumber = (int) (Math.random() * (MAX_NUMBER - MIN_NUMBER + 1)) + MIN_NUMBER;
```

**Why separate magic number per client?**

1. **Isolation:**
   - Each client plays **independent** game
   - One client's number doesn't affect others
   - Fair competition

2. **Thread Safety:**
   - Each thread has its **own stack**
   - Local variables are **thread-local**
   - No shared state (except leaderboard)

3. **Game Logic:**
   - Each client tries to find **their own** number
   - Attempts are counted **per client**
   - Winner is determined by **fewest attempts**

---

### 🔹 Leaderboard Implementation

#### PlayerScore Class

```java
public class PlayerScore implements Comparable<PlayerScore> {
    private String playerName;
    private int attempts;
    private String ipAddress;
    
    @Override
    public int compareTo(PlayerScore other) {
        int attemptsComparison = Integer.compare(this.attempts, other.attempts);
        if (attemptsComparison != 0) {
            return attemptsComparison;
        }
        return this.playerName.compareToIgnoreCase(other.playerName);
    }
}
```

**Why `implements Comparable`?**

1. **Natural Ordering:**
   - Defines how to sort `PlayerScore` objects
   - Used by `Collections.sort()`
   - Enables automatic sorting

2. **Sorting Logic:**
   - Primary: Fewest attempts (ascending)
   - Secondary: Alphabetical name (tie-breaker)
   - Ensures consistent ordering

3. **Why `Integer.compare()`?**
   - Handles edge cases (negative numbers, overflow)
   - More robust than `this.attempts - other.attempts`
   - Recommended Java practice

---

#### Thread-Safe Leaderboard

```java
public class Leaderboard {
    private final List<PlayerScore> scores;
    
    public synchronized void addScore(String playerName, int attempts, String ipAddress) {
        scores.add(new PlayerScore(playerName, attempts, ipAddress));
        Collections.sort(scores);
        
        if (scores.size() > maxEntries) {
            scores.remove(scores.size() - 1);
        }
    }
    
    public synchronized List<PlayerScore> getLeaderboard() {
        return new ArrayList<>(scores);  // Defensive copy
    }
}
```

**Why `synchronized` methods?**

1. **Shared Resource:**
   - `scores` list is **shared** by all threads
   - Multiple clients may add scores **simultaneously**
   - Must protect against race conditions

2. **Atomic Operations:**
   - `addScore()`: Add → Sort → Trim (all atomic)
   - `getLeaderboard()`: Read (atomic)
   - Prevents corruption during concurrent access

3. **Defensive Copy:**
   ```java
   return new ArrayList<>(scores);
   ```
   - Returns **copy** of list, not original
   - Prevents external modification
   - Thread-safe even after return

**What happens without synchronization?**
- Multiple threads may add scores simultaneously
- `Collections.sort()` may see inconsistent state
- List may become corrupted
- Leaderboard may show incorrect data

---

#### Leaderboard Display

```java
public synchronized String getLeaderboardString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n🏆 LEADERBOARD 🏆\n");
    sb.append(String.format("%-5s %-20s %-10s %s%n", "Rank", "Player", "Attempts", "IP Address"));
    
    for (int i = 0; i < scores.size(); i++) {
        sb.append(String.format("%-5d %s%n", i + 1, scores.get(i)));
    }
    
    return sb.toString();
}
```

**Why `StringBuilder`?**

1. **Efficiency:**
   - `String` concatenation creates new objects
   - `StringBuilder` modifies internal buffer
   - Much faster for multiple concatenations

2. **Memory:**
   - Avoids creating many temporary `String` objects
   - Reduces garbage collection pressure
   - Better performance

3. **Formatting:**
   - `String.format()` for consistent column widths
   - `%-20s`: Left-aligned, 20 characters
   - Professional-looking output

---

#### Player Ranking

```java
public synchronized int getRank(int attempts) {
    int rank = 1;
    for (PlayerScore score : scores) {
        if (score.getAttempts() < attempts) {
            rank++;
        } else {
            break;
        }
    }
    return rank;
}
```

**Why calculate rank separately?**

1. **Dynamic Ranking:**
   - Rank changes as new scores are added
   - Must recalculate each time
   - Shows current position

2. **Efficient Algorithm:**
   - List is already sorted (by attempts)
   - Count how many scores are better
   - Rank = count + 1

3. **Thread Safety:**
   - `synchronized` ensures consistent view
   - No race conditions during calculation
   - Accurate ranking

---

### 🔹 Enhanced Client Features

#### Player Name Collection

```java
out.println("Enter your name:");
String playerName = in.readLine();
if (playerName == null || playerName.trim().isEmpty()) {
    playerName = "Anonymous";
}
```

**Why collect name?**

1. **Personalization:**
   - Leaderboard shows player names
   - More engaging experience
   - Competitive element

2. **Default Handling:**
   - Empty input → "Anonymous"
   - Null input → "Anonymous"
   - Always has a name for leaderboard

3. **User Experience:**
   - First interaction with server
   - Sets up game context
   - Friendly greeting

---

#### Leaderboard Display to Client

```java
out.println("CORRECT");
out.println("Congratulations, " + playerName + "! You found it in " + attempts + " attempt(s).");

// Add to leaderboard
leaderboard.addScore(playerName, attempts, clientIP);

// Send leaderboard
out.println(leaderboard.getLeaderboardString());

// Send player's rank
int rank = leaderboard.getRank(attempts);
out.println("Your rank: #" + rank);
```

**Why send leaderboard after game?**

1. **Motivation:**
   - Shows how player compares to others
   - Encourages replay to improve rank
   - Competitive element

2. **Transparency:**
   - Players can see top scores
   - Understand ranking system
   - Fair competition

3. **Feedback:**
   - Immediate ranking after game
   - Shows progress
   - Engaging user experience

---

## 🎯 Key Takeaways

1. **Multi-Threading:**
   - Thread-per-client enables concurrent handling
   - Each client runs independently
   - Shared resources must be synchronized

2. **Thread Safety:**
   - Leaderboard is shared by all threads
   - `synchronized` methods ensure correctness
   - Defensive copies prevent external modification

3. **Network Play:**
   - Server can run on any machine
   - Clients connect via IP address
   - Same code works locally or remotely

---

## 💻 Example Execution

### Server Output
```
🎮 Multi-Threaded Magic Number Guessing Game Server
Server listening on port 12345
Waiting for client connections...

✅ New client connected: /192.168.1.100
✅ New client connected: /192.168.1.101
🎯 [Alice] Magic number: 42
🎯 [Bob] Magic number: 73
[Alice] Guess: 50 -> TOO_HIGH
[Bob] Guess: 50 -> TOO_LOW
[Alice] Guess: 42 -> CORRECT
[Alice] Found the number in 2 attempt(s)!
[Bob] Guess: 73 -> CORRECT
[Bob] Found the number in 2 attempt(s)!

🏆 LEADERBOARD 🏆
Rank  Player                Attempts  IP Address
------------------------------------------------------------
1     Alice                 2         192.168.1.100
2     Bob                   2         192.168.1.101
```

### Client Output (Alice)
```
🎮 Multi-Threaded Magic Number Guessing Game Client
Connected to server: 192.168.1.50:12345

Enter your name:
> Alice
Welcome, Alice! I'm thinking of a number between 0 and 100. Guess it!
Enter your guess (0-100) or 'quit' to exit: 50
📈 Too high! Try a lower number.
Enter your guess (0-100) or 'quit' to exit: 42
🎉 Correct! You found the magic number!
Congratulations, Alice! You found it in 2 attempt(s).

🏆 LEADERBOARD 🏆
Rank  Player                Attempts  IP Address
------------------------------------------------------------
1     Alice                 2         192.168.1.100
Your rank: #1

Thanks for playing! Goodbye!
```

---

## 🚀 Running the Application

### Server (Machine 1)
```bash
cd src/tp_multithreading_sockets/sockets/level2
javac *.java
java MultiThreadedMagicNumberServer
```

### Client 1 (Machine 2)
```bash
java MultiThreadedMagicNumberClient <server-ip> 12345
```

### Client 2 (Machine 3)
```bash
java MultiThreadedMagicNumberClient <server-ip> 12345
```

**Note:** Replace `<server-ip>` with actual server IP address (e.g., `192.168.1.50`)

---

## 🔍 Advanced Considerations

1. **Thread Pool:**
   - Use `ExecutorService` instead of creating threads directly
   - Limits number of concurrent threads
   - Better resource management

2. **Persistence:**
   - Save leaderboard to file
   - Load on server startup
   - Survive server restarts

3. **Security:**
   - Validate player names (prevent injection)
   - Rate limiting (prevent spam)
   - Authentication (optional)

---

