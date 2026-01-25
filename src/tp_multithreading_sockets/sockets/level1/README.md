# 🎮 Level 1: Magic Number Guessing Game (Single Machine)

## 📝 The Problem

Create a client-server guessing game where the server generates a random magic number (0-100). Clients attempt to guess the number, receiving "Too high" or "Too low" hints after each attempt.

**Server Requirements:**
1. Generate random number (0-100) using `Math.random()`
2. Accept client connections
3. For each guess: respond with "TOO_HIGH", "TOO_LOW", or "CORRECT"
4. Count attempts until correct guess
5. Close connection when number found

**Client Requirements:**
1. Connect to server
2. Prompt user for guesses
3. Send guesses to server
4. Display server responses
5. Show total attempts upon success

---

## 💡 Solution and Technical Justifications

### 🔹 Server Implementation

#### ServerSocket Creation

```java
ServerSocket serverSocket = new ServerSocket(PORT);
```

**Why `ServerSocket`?**

1. **TCP Server Socket:**
   - `ServerSocket` is specifically for **accepting** connections
   - Binds to a port and listens for incoming connections
   - Different from `Socket` (which is for client connections)

2. **Port Binding:**
   - `PORT = 12345` is the port number
   - Server listens on this port
   - Clients connect to `localhost:12345`

3. **Connection-Oriented:**
   - TCP provides reliable, ordered delivery
   - Connection must be established before data transfer
   - Guarantees message delivery (unlike UDP)

**Why try-with-resources?**
```java
try (ServerSocket serverSocket = new ServerSocket(PORT)) {
    // ...
}
```
- Automatically closes socket when block exits
- Prevents resource leaks
- Handles exceptions gracefully

---

#### Accepting Client Connections

```java
Socket clientSocket = serverSocket.accept();
```

**Why `accept()`?**

1. **Blocking Call:**
   - `accept()` **blocks** until a client connects
   - Server waits here until connection arrives
   - Returns a `Socket` for communication with that client

2. **One Client at a Time:**
   - This is a **single-threaded** server
   - Only handles one client at a time
   - Other clients must wait

3. **Socket Object:**
   - `clientSocket` represents the connection to one client
   - Used for bidirectional communication
   - Each client gets its own `Socket`

**Limitation:**
- If client 1 is playing, client 2 must wait
- Not suitable for multiple simultaneous players
- Solution: Multi-threaded server (Level 2)

---

#### Random Number Generation

```java
int magicNumber = (int) (Math.random() * (MAX_NUMBER - MIN_NUMBER + 1)) + MIN_NUMBER;
```

**Why this formula?**

1. **Math.random() returns:**
   - Value between `0.0` (inclusive) and `1.0` (exclusive)
   - Example: `0.0`, `0.5`, `0.999...`

2. **Scaling to Range:**
   - `Math.random() * (MAX - MIN + 1)` gives `0.0` to `101.0`
   - Casting to `int` truncates: `0` to `100`
   - Adding `MIN` shifts range: `0 + 0 = 0` to `100 + 0 = 100`

3. **Why `+ 1`?**
   - `Math.random()` never returns `1.0`
   - Without `+1`, max would be `99` (not `100`)
   - `+1` ensures `100` is included

**Example:**
```
Math.random() = 0.75
0.75 * 101 = 75.75
(int) 75.75 = 75
75 + 0 = 75  ✓
```

---

#### Input/Output Streams

```java
BufferedReader in = new BufferedReader(
    new InputStreamReader(clientSocket.getInputStream()));
PrintWriter out = new PrintWriter(
    clientSocket.getOutputStream(), true);
```

**Why wrap streams?**

1. **Raw Streams:**
   - `getInputStream()` returns raw `InputStream` (bytes)
   - `getOutputStream()` returns raw `OutputStream` (bytes)
   - Hard to work with directly

2. **Character Streams:**
   - `InputStreamReader` converts bytes → characters
   - `BufferedReader` adds buffering and `readLine()` method
   - `PrintWriter` provides `println()` for easy text output

3. **Why `true` in PrintWriter?**
   - `autoFlush = true` flushes after each `println()`
   - Ensures data is sent immediately
   - Without it, data might be buffered

**Stream Hierarchy:**
```
Socket → InputStream → InputStreamReader → BufferedReader
Socket → OutputStream → PrintWriter
```

---

#### Game Loop

```java
while (true) {
    String guessStr = in.readLine();
    
    if (guessStr == null || guessStr.equalsIgnoreCase("quit")) {
        break;
    }
    
    int guess = Integer.parseInt(guessStr);
    attempts++;
    
    if (guess < magicNumber) {
        out.println("TOO_LOW");
    } else if (guess > magicNumber) {
        out.println("TOO_HIGH");
    } else {
        out.println("CORRECT");
        break;
    }
}
```

**Why this structure?**

1. **Read-Process-Respond Pattern:**
   - Read guess from client
   - Process (compare with magic number)
   - Respond with hint or success

2. **Null Check:**
   - `readLine()` returns `null` if connection closed
   - Must check to avoid `NullPointerException`
   - Graceful handling of disconnections

3. **String Comparison:**
   - Use `equalsIgnoreCase()` for case-insensitive comparison
   - Allows "QUIT", "Quit", "quit" all to work

4. **Number Parsing:**
   - `Integer.parseInt()` converts string to int
   - Throws `NumberFormatException` for invalid input
   - Should be wrapped in try-catch (shown in code)

---

### 🔹 Client Implementation

#### Socket Connection

```java
Socket socket = new Socket(host, port);
```

**Why `Socket` (not `ServerSocket`)?**

1. **Client Socket:**
   - `Socket` is for **initiating** connections
   - Connects to server at specified host:port
   - Different from `ServerSocket` (which accepts connections)

2. **Connection Establishment:**
   - Creates TCP connection to server
   - Blocks until connection is established
   - Throws exception if server unavailable

3. **Host and Port:**
   - `host`: Server IP or hostname (e.g., "localhost", "192.168.1.100")
   - `port`: Server port number (must match server's port)

**Network vs Local:**
- `localhost` or `127.0.0.1`: Same machine
- `192.168.1.100`: Different machine on network
- Both work the same way (TCP connection)

---

#### User Input Handling

```java
Scanner scanner = new Scanner(System.in);
String userInput = scanner.nextLine().trim();
```

**Why `Scanner`?**

1. **Easy Input:**
   - `nextLine()` reads entire line
   - Handles user input from console
   - Simple and convenient

2. **Why `trim()`?**
   - Removes leading/trailing whitespace
   - User might type "  50  " (with spaces)
   - `trim()` ensures clean input

3. **Alternative:**
   - Could use `BufferedReader` with `System.in`
   - `Scanner` is more convenient for console input

---

#### Communication Protocol

**Protocol Design:**
```
Client → Server: "50"        (guess as string)
Server → Client: "TOO_LOW"   (response)

Client → Server: "75"
Server → Client: "TOO_HIGH"

Client → Server: "65"
Server → Client: "CORRECT"
Server → Client: "Congratulations! You found it in 3 attempt(s)."
```

**Why string-based protocol?**

1. **Text Protocol:**
   - Easy to debug (human-readable)
   - Simple to implement
   - Works across different systems

2. **Alternative (Binary):**
   - More efficient (smaller messages)
   - But harder to debug
   - Not necessary for this simple game

3. **Protocol Constants:**
   - "TOO_LOW", "TOO_HIGH", "CORRECT" are protocol messages
   - Client must understand these strings
   - Could use enums or constants in production code

---

## 🎯 Key Takeaways

1. **TCP Socket Programming:**
   - `ServerSocket` for servers (accept connections)
   - `Socket` for clients (connect to server)
   - Streams for bidirectional communication

2. **Single-Threaded Limitation:**
   - Only one client at a time
   - Other clients must wait
   - Solution: Multi-threaded server (Level 2)

3. **Protocol Design:**
   - Define message format (strings, JSON, binary)
   - Client and server must agree on protocol
   - Handle edge cases (null, invalid input)

---

## 💻 Example Execution

### Server Output
```
🎮 Magic Number Guessing Game Server
Server listening on port 12345
Waiting for client connection...

✅ Client connected: /127.0.0.1
🎯 Magic number generated: 42 (for this client)
Client guess: 50 -> TOO_HIGH
Client guess: 25 -> TOO_LOW
Client guess: 42 -> CORRECT
Client found the number in 3 attempt(s)!
```

### Client Output
```
🎮 Magic Number Guessing Game Client
Connected to server: localhost:12345

Welcome! I'm thinking of a number between 0 and 100. Guess it!
Enter your guess (0-100) or 'quit' to exit: 50
📈 Too high! Try a lower number.
Enter your guess (0-100) or 'quit' to exit: 25
📉 Too low! Try a higher number.
Enter your guess (0-100) or 'quit' to exit: 42
🎉 Correct! You found the magic number!
Congratulations! You found it in 3 attempt(s).

Thanks for playing! Goodbye!
```

---

## 🔍 Common Issues

1. **Port Already in Use:**
   - Another process is using port 12345
   - Solution: Change port or stop other process

2. **Connection Refused:**
   - Server not running
   - Wrong host/port
   - Firewall blocking connection

3. **NullPointerException:**
   - Client disconnected unexpectedly
   - Always check for `null` from `readLine()`

---

## 🚀 Running the Application

**Terminal 1 - Server:**
```bash
cd src/tp_multithreading_sockets/sockets/level1
javac *.java
java MagicNumberServer
```

**Terminal 2 - Client:**
```bash
java MagicNumberClient
# Or with custom host/port:
java MagicNumberClient 192.168.1.100 12345
```

---

