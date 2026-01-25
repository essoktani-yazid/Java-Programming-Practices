package tp_multithreading_sockets.sockets.level1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MagicNumberServer {
    private static final int PORT = 12345;
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 100;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🎮 Magic Number Guessing Game Server");
            System.out.println("Server listening on port " + PORT);
            System.out.println("Waiting for client connection...");

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("\n✅ Client connected: " + clientSocket.getInetAddress());

                // Handle client in the same thread (single-threaded)
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true)) {

            // Generate random magic number
            int magicNumber = (int) (Math.random() * (MAX_NUMBER - MIN_NUMBER + 1)) + MIN_NUMBER;
            System.out.println("🎯 Magic number generated: " + magicNumber + " (for this client)");

            int attempts = 0;
            String response;

            out.println("Welcome! I'm thinking of a number between " + 
                       MIN_NUMBER + " and " + MAX_NUMBER + ". Guess it!");

            // Game loop
            while (true) {
                String guessStr = in.readLine();
                
                if (guessStr == null || guessStr.equalsIgnoreCase("quit")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                try {
                    int guess = Integer.parseInt(guessStr);
                    attempts++;

                    if (guess < magicNumber) {
                        response = "TOO_LOW";
                        out.println(response);
                        System.out.println("Client guess: " + guess + " -> " + response);
                    } else if (guess > magicNumber) {
                        response = "TOO_HIGH";
                        out.println(response);
                        System.out.println("Client guess: " + guess + " -> " + response);
                    } else {
                        response = "CORRECT";
                        out.println(response);
                        out.println("Congratulations! You found it in " + attempts + " attempt(s).");
                        System.out.println("Client found the number in " + attempts + " attempt(s)!");
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

