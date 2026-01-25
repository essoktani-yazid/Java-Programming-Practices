package tp_multithreading_sockets.sockets.level1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class MagicNumberClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        String host = SERVER_HOST;
        int port = SERVER_PORT;

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

            System.out.println("🎮 Magic Number Guessing Game Client");
            System.out.println("Connected to server: " + host + ":" + port);
            System.out.println();

            // Read welcome message
            String welcomeMessage = in.readLine();
            if (welcomeMessage != null) {
                System.out.println(welcomeMessage);
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

                // Read server response
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
                    // Read the congratulations message
                    String congrats = in.readLine();
                    if (congrats != null) {
                        System.out.println(congrats);
                    }
                    break;
                } else if (response.startsWith("INVALID")) {
                    System.out.println("❌ " + response);
                } else {
                    System.out.println("Server: " + response);
                }
            }

            System.out.println("\nThanks for playing! Goodbye!");
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

