# 📡 UDP Sockets Exercise: Chat Application

## 📝 The Problem

Create a simple peer-to-peer chat application using UDP sockets. The application consists of two components:
1. **Receiver (Listener):** Listens on port 1234 and displays received messages
2. **Sender:** Sends messages to a specified IP address and port

**Requirements:**
- Receiver displays messages with sender's address
- Receiver runs until user types "exit"
- Sender reads user input and sends as UDP packets
- Sender sends "bye" to terminate

---

## 💡 Solution and Technical Justifications

### 🔹 UDP vs TCP

**Key Differences:**

| Aspect | TCP | UDP |
|--------|-----|-----|
| **Connection** | Connection-oriented | Connectionless |
| **Reliability** | Guaranteed delivery | Best-effort delivery |
| **Ordering** | Guaranteed order | No ordering guarantee |
| **Speed** | Slower (overhead) | Faster (less overhead) |
| **Use Case** | File transfer, web | Video streaming, chat |

**Why UDP for Chat?**

1. **Real-Time Communication:**
   - Chat messages are small and frequent
   - Speed is more important than reliability
   - Lost messages are acceptable (user can resend)

2. **Connectionless:**
   - No connection setup overhead
   - Can send to any IP:port immediately
   - Simpler than TCP

3. **Broadcasting:**
   - Can send to multiple recipients easily
   - Useful for group chat (future enhancement)

**Trade-offs:**
- Messages may be lost (network congestion)
- Messages may arrive out of order
- No automatic retransmission
- Must handle errors in application

---

### 🔹 UDP Receiver Implementation

#### DatagramSocket Creation

```java
DatagramSocket socket = new DatagramSocket(port);
```

**Why `DatagramSocket`?**

1. **UDP Socket:**
   - `DatagramSocket` is for UDP communication
   - Different from TCP's `Socket` and `ServerSocket`
   - Binds to a port to receive packets

2. **Port Binding:**
   - `port = 1234` is the listening port
   - Receiver listens on this port
   - Senders send to this port

3. **Connectionless:**
   - No `accept()` call (unlike TCP)
   - Can receive from **any** sender
   - No connection establishment

**Why try-with-resources?**
```java
try (DatagramSocket socket = new DatagramSocket(port)) {
    // ...
}
```
- Automatically closes socket
- Prevents resource leaks
- Handles exceptions

---

#### Receiving Packets

```java
byte[] buffer = new byte[BUFFER_SIZE];
DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

socket.receive(packet);  // Blocking call
```

**Why this pattern?**

1. **Buffer Allocation:**
   - `buffer` holds received data
   - `BUFFER_SIZE = 1024` bytes
   - Must be large enough for messages

2. **Packet Object:**
   - `DatagramPacket` wraps data and metadata
   - Contains: data, length, sender address, sender port
   - Reused for each receive operation

3. **Blocking Receive:**
   - `receive()` **blocks** until packet arrives
   - Thread waits here until data received
   - Similar to TCP's `accept()` or `readLine()`

**How it works:**
```
1. Create buffer and packet
2. Call receive() → blocks until packet arrives
3. Packet is filled with data and sender info
4. Extract data and sender address
5. Repeat
```

---

#### Extracting Message and Sender Info

```java
String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
String senderAddress = packet.getAddress().getHostAddress();
int senderPort = packet.getPort();
```

**Why this extraction?**

1. **Data Extraction:**
   - `getData()` returns byte array
   - `getLength()` is actual data length (may be less than buffer size)
   - Convert bytes to string using UTF-8 encoding

2. **Sender Information:**
   - `getAddress()` returns `InetAddress` of sender
   - `getHostAddress()` converts to string (e.g., "192.168.1.100")
   - `getPort()` returns sender's port number

3. **Why `StandardCharsets.UTF_8`?**
   - Ensures correct character encoding
   - Handles international characters
   - Explicit encoding prevents platform issues

**Display Format:**
```
[192.168.1.100:54321] Hello, world!
```
- Shows sender's IP and port
- Useful for identifying different senders
- Helps with debugging

---

#### Termination Handling

```java
if (message.equalsIgnoreCase("bye")) {
    System.out.println("\n📨 Received 'bye' from " + senderAddress + ":" + senderPort);
    System.out.println("Terminating receiver...");
    break;
}
```

**Why check for "bye"?**

1. **Graceful Shutdown:**
   - Allows sender to signal termination
   - Receiver exits cleanly
   - Better than force-killing process

2. **Protocol Design:**
   - "bye" is a special message
   - Both sender and receiver understand it
   - Part of application protocol

3. **Alternative:**
   - Could use "exit" command in receiver's own input
   - But "bye" from sender is more natural
   - Matches requirement specification

---

### 🔹 UDP Sender Implementation

#### DatagramSocket for Sending

```java
DatagramSocket socket = new DatagramSocket();
```

**Why no port specified?**

1. **Ephemeral Port:**
   - System assigns random available port
   - Sender doesn't need fixed port
   - Only receiver needs known port

2. **Bidirectional:**
   - Socket can both send and receive
   - But sender only sends in this example
   - Could receive replies if needed

3. **Connectionless:**
   - No connection to establish
   - Can send to any IP:port immediately
   - Different from TCP

---

#### Creating and Sending Packets

```java
String message = scanner.nextLine().trim();
byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

DatagramPacket packet = new DatagramPacket(
    messageBytes,
    messageBytes.length,
    targetAddress,
    targetPort
);

socket.send(packet);
```

**Why this process?**

1. **Message to Bytes:**
   - UDP sends **bytes**, not strings
   - Must convert string to byte array
   - Use UTF-8 encoding for consistency

2. **Packet Creation:**
   - `DatagramPacket` contains:
     - Data (message bytes)
     - Length (bytes.length)
     - Target address (where to send)
     - Target port (which port on target)

3. **Send Operation:**
   - `send()` transmits packet
   - **Non-blocking** (usually)
   - Packet is queued and sent by OS
   - No guarantee of delivery

**Packet Structure:**
```
DatagramPacket {
    data: [72, 101, 108, 108, 111]  // "Hello" in bytes
    length: 5
    address: 192.168.1.100
    port: 1234
}
```

---

#### Target Address Resolution

```java
InetAddress targetAddress = InetAddress.getByName(targetIP);
```

**Why `getByName()`?**

1. **Hostname Resolution:**
   - Converts hostname/IP string to `InetAddress`
   - Handles both "localhost" and "192.168.1.100"
   - Performs DNS lookup if needed

2. **Exception Handling:**
   - Throws `UnknownHostException` if hostname invalid
   - Should be caught and handled
   - Provides user-friendly error message

3. **Network Independence:**
   - Works with IP addresses or hostnames
   - Flexible for different network configurations
   - Supports both IPv4 and IPv6

---

#### Command-Line Arguments

```java
if (args.length < 1) {
    System.out.println("Usage: java UDPSender <targetIP> [targetPort]");
    System.exit(1);
}

String targetIP = args[0];
int targetPort = DEFAULT_PORT;

if (args.length >= 2) {
    targetPort = Integer.parseInt(args[1]);
}
```

**Why command-line arguments?**

1. **Flexibility:**
   - Can send to different IPs without code changes
   - Supports different ports
   - Easy to test with different configurations

2. **User-Friendly:**
   - Clear usage message
   - Default port (1234) if not specified
   - Validates input

3. **Network Play:**
   - Essential for sending to remote machines
   - Can't hardcode IP addresses
   - Must be configurable

---

## 🎯 Key Takeaways

1. **UDP Characteristics:**
   - Connectionless (no connection setup)
   - Fast but unreliable
   - Suitable for real-time applications

2. **Packet-Based:**
   - Data sent in discrete packets
   - Each packet is independent
   - Must handle packet boundaries

3. **Peer-to-Peer:**
   - Both sides can send and receive
   - No client-server distinction
   - Symmetric communication

---

## 💻 Example Execution

### Terminal 1 - Receiver
```bash
cd src/tp_multithreading_sockets/udp
javac *.java
java UDPReceiver
```

**Output:**
```
📡 UDP Receiver started
Listening on port 1234
Type 'exit' to stop the receiver

[192.168.1.100:54321] Hello!
[192.168.1.100:54321] How are you?
[192.168.1.100:54321] bye

📨 Received 'bye' from 192.168.1.100:54321
Terminating receiver...
```

### Terminal 2 - Sender
```bash
java UDPSender localhost 1234
# Or with remote IP:
java UDPSender 192.168.1.50 1234
```

**Output:**
```
📤 UDP Sender started
Target: localhost:1234
Type your messages (type 'bye' to terminate)

You: Hello!
You: How are you?
You: bye
Sent 'bye'. Terminating sender...
```

---

## 🔍 Comparison: TCP vs UDP

### TCP Chat (Hypothetical)
```java
// Connection setup required
Socket socket = new Socket(host, port);
// Reliable delivery
out.println(message);  // Guaranteed to arrive
// Connection-oriented
socket.close();  // Clean shutdown
```

### UDP Chat (This Exercise)
```java
// No connection setup
DatagramSocket socket = new DatagramSocket();
// Best-effort delivery
socket.send(packet);  // May be lost
// Connectionless
socket.close();  // Just closes socket
```

**When to use which?**
- **TCP:** When reliability is critical (file transfer, banking)
- **UDP:** When speed is critical (gaming, video, chat)

---

## 🚀 Running the Application

### Setup for Network Play

**Machine 1 (Receiver):**
```bash
java UDPReceiver 1234
# Note your IP address (e.g., 192.168.1.50)
```

**Machine 2 (Sender):**
```bash
java UDPSender 192.168.1.50 1234
```

**Machine 3 (Another Sender):**
```bash
java UDPSender 192.168.1.50 1234
```

**Note:** Receiver can receive from multiple senders simultaneously!

---

## 🔍 Common Issues

1. **Firewall Blocking:**
   - UDP port 1234 may be blocked
   - Solution: Configure firewall or use different port

2. **Messages Not Received:**
   - UDP packets can be lost
   - Network congestion
   - Firewall blocking

3. **Wrong IP Address:**
   - Verify IP with `ipconfig` (Windows) or `ifconfig` (Linux/Mac)
   - Use correct network interface IP
   - `localhost` only works on same machine

---

## 🚀 Extensions

Possible enhancements:
- **Group Chat:** Broadcast to multiple receivers
- **Message Acknowledgment:** Add reliability layer
- **Encryption:** Secure messages
- **File Transfer:** Send files via UDP
- **Voice Chat:** Real-time audio streaming

---

