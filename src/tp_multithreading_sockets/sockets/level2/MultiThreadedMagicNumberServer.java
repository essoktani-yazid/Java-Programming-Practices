package tp_multithreading_sockets.sockets.level2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadedMagicNumberServer {
    private static final int PORT = 12345;
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_LEADERBOARD_ENTRIES = 10;

    private static Leaderboard leaderboard = new Leaderboard(MAX_LEADERBOARD_ENTRIES);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🎮 Multi-Threaded Magic Number Guessing Game Server");
            System.out.println("Server listening on port " + PORT);
            System.out.println("Waiting for client connections...");
            System.out.println("(Press Ctrl+C to stop the server)\n");

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("✅ New client connected: " + clientSocket.getInetAddress());

                // Create a new thread for each client
                ClientHandler clientHandler = new ClientHandler(clientSocket, leaderboard);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Leaderboard leaderboard;
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 100;

    public ClientHandler(Socket clientSocket, Leaderboard leaderboard) {
        this.clientSocket = clientSocket;
        this.leaderboard = leaderboard;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true)) {

            // Get player name
            out.println("Enter your name:");
            String playerName = in.readLine();
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Anonymous";
            }

            // Generate random magic number for this client
            int magicNumber = (int) (Math.random() * (MAX_NUMBER - MIN_NUMBER + 1)) + MIN_NUMBER;
            System.out.println("🎯 [" + playerName + "] Magic number: " + magicNumber);

            int attempts = 0;
            String clientIP = clientSocket.getInetAddress().getHostAddress();

            out.println("Welcome, " + playerName + "! I'm thinking of a number between " + 
                       MIN_NUMBER + " and " + MAX_NUMBER + ". Guess it!");

            // Game loop
            while (true) {
                String guessStr = in.readLine();
                
                if (guessStr == null || guessStr.equalsIgnoreCase("quit")) {
                    System.out.println("[" + playerName + "] Disconnected.");
                    break;
                }

                try {
                    int guess = Integer.parseInt(guessStr);
                    attempts++;

                    if (guess < magicNumber) {
                        out.println("TOO_LOW");
                        System.out.println("[" + playerName + "] Guess: " + guess + " -> TOO_LOW");
                    } else if (guess > magicNumber) {
                        out.println("TOO_HIGH");
                        System.out.println("[" + playerName + "] Guess: " + guess + " -> TOO_HIGH");
                    } else {
                        out.println("CORRECT");
                        out.println("Congratulations, " + playerName + "! You found it in " + attempts + " attempt(s).");
                        
                        // Add to leaderboard
                        leaderboard.addScore(playerName, attempts, clientIP);
                        
                        // Send leaderboard
                        out.println(leaderboard.getLeaderboardString());
                        
                        // Send player's rank
                        int rank = leaderboard.getRank(attempts);
                        out.println("Your rank: #" + rank);
                        
                        System.out.println("[" + playerName + "] Found the number in " + attempts + " attempt(s)!");
                        System.out.println(leaderboard.getLeaderboardString());
                        break;
                    }
                } catch (NumberFormatException e) {
                    out.println("INVALID: Please enter a valid number.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

