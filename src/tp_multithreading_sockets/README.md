# ☕ Multithreading & Sockets Programming in Java

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![School](https://img.shields.io/badge/School-ENSET_Mohammedia-blue?style=for-the-badge)

> **Module :** Object-Oriented Programming (Java)  
> **Supervised by :** Prof. Loubna Aminou  
> **Created by :** Yazid ESSOKTANI  
> **Program :** Master SDIA
---

## 📑 Table of Contents
1. [Project Overview](#-project-overview)
2. [Multithreading in Java](#-multithreading-in-java)
3. [Sockets Programming](#-sockets-programming)
4. [UDP Sockets Exercise](#-udp-sockets-exercise)

---

## 🔍 Project Overview

This package contains solutions for **Multithreading and Sockets Programming** exercises. The objective is to master:
- **Concurrency**: Understanding race conditions, thread synchronization, and thread-safe programming
- **Network Programming**: Building client-server applications using TCP and UDP sockets
- **Multi-threaded Servers**: Handling multiple clients simultaneously

---

## 🧵 Multithreading in Java

This section demonstrates race conditions and their solutions using synchronization mechanisms.

### [Scenario 1: Bank Account Withdrawal](./multithreading/scenario1/README.md)
Demonstrates race conditions when multiple threads access shared bank account data.

**Key Concepts:**
- Race conditions in shared resources
- `synchronized` methods
- `synchronized` blocks
- Thread safety

### [Scenario 2: Seat Booking System](./multithreading/scenario2/README.md)
Demonstrates overbooking problems when multiple threads book seats simultaneously.

**Key Concepts:**
- Shared resource management
- Preventing data corruption
- Synchronization strategies

---

## 🌐 Sockets Programming

This section covers TCP socket programming for network applications.

### [Level 1: Single Machine Version](./sockets/level1/README.md)
A basic client-server guessing game where the server generates a random number and clients attempt to guess it.

**Key Concepts:**
- TCP sockets (`ServerSocket`, `Socket`)
- Client-server architecture
- Input/Output streams
- Single-threaded server

### [Level 2: Multi-threaded Network Version](./sockets/level2/README.md)
An enhanced version supporting multiple simultaneous players with score tracking and leaderboard.

**Key Concepts:**
- Multi-threaded servers
- Thread-per-client model
- Shared data structures (leaderboard)
- Network play across machines

---

## 📡 UDP Sockets Exercise

### [Chat Application](./udp/README.md)
A peer-to-peer chat application using UDP sockets for real-time messaging.

**Key Concepts:**
- UDP sockets (`DatagramSocket`, `DatagramPacket`)
- Connectionless communication
- Peer-to-peer architecture
- Message broadcasting

---