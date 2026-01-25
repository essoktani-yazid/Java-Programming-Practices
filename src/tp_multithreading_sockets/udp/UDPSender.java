package tp_multithreading_sockets.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class UDPSender {
    private static final int DEFAULT_PORT = 1234;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java UDPSender <targetIP> [targetPort]");
            System.out.println("Example: java UDPSender 192.168.1.100 1234");
            System.exit(1);
        }

        String targetIP = args[0];
        int targetPort = DEFAULT_PORT;

        if (args.length >= 2) {
            targetPort = Integer.parseInt(args[1]);
        }

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("📤 UDP Sender started");
            System.out.println("Target: " + targetIP + ":" + targetPort);
            System.out.println("Type your messages (type 'bye' to terminate)\n");

            InetAddress targetAddress = InetAddress.getByName(targetIP);

            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine().trim();

                if (message.isEmpty()) {
                    continue;
                }

                // Convert message to bytes
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

                // Create packet
                DatagramPacket packet = new DatagramPacket(
                    messageBytes,
                    messageBytes.length,
                    targetAddress,
                    targetPort
                );

                // Send packet
                socket.send(packet);

                // Check for termination
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Sent 'bye'. Terminating sender...");
                    break;
                }
            }
        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
            e.printStackTrace();
        } catch (java.io.IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

