package tp_multithreading_sockets.sockets.level2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class MultiThreadedMagicNumberClient {
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 12345;

    public static void main(String[] args) {
        String host = DEFAULT_SERVER_HOST;
        int port = DEFAULT_SERVER_PORT;

        // Allow custom host and port via command line arguments
        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("🎮 Multi-Threaded Magic Number Guessing Game Client");
            System.out.println("Connected to server: " + host + ":" + port);
            System.out.println();

            // Read and display server messages
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                
                // If server asks for name, read from user
                if (message.contains("Enter your name")) {
                    System.out.print("> ");
                    String name = scanner.nextLine().trim();
                    out.println(name.isEmpty() ? "Anonymous" : name);
                }
                // If server sends welcome message, start game loop
                else if (message.contains("Welcome") || message.contains("Guess it")) {
                    break;
                }
            }

            // Game loop
            while (true) {
                System.out.print("Enter your guess (0-100) or 'quit' to exit: ");
                String userInput = scanner.nextLine().trim();

                if (userInput.equalsIgnoreCase("quit")) {
                    out.println("quit");
                    break;
                }

                // Send guess to server
                out.println(userInput);

                // Read and display server responses
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                // Display response
                if (response.equals("TOO_LOW")) {
                    System.out.println("📉 Too low! Try a higher number.");
                } else if (response.equals("TOO_HIGH")) {
                    System.out.println("📈 Too high! Try a lower number.");
                } else if (response.equals("CORRECT")) {
                    System.out.println("🎉 Correct! You found the magic number!");
                    
                    // Read congratulations and leaderboard
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.startsWith("Your rank:")) {
                            System.out.println(line);
                            break;
                        }
                        System.out.println(line);
                    }
                    break;
                } else if (response.startsWith("INVALID")) {
                    System.out.println("❌ " + response);
                } else {
                    System.out.println(response);
                }
            }

            System.out.println("\nThanks for playing! Goodbye!");
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            System.err.println("Make sure the server is running on " + host + ":" + port);
            e.printStackTrace();
        }
    }
}

