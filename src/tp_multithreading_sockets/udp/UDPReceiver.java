package tp_multithreading_sockets.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;


public class UDPReceiver {
    private static final int PORT = 1234;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        int port = PORT;
        
        // Allow custom port via command line argument
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("📡 UDP Receiver started");
            System.out.println("Listening on port " + port);
            System.out.println("Type 'exit' to stop the receiver\n");

            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                // Create packet to receive data
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive packet (blocking call)
                socket.receive(packet);

                // Extract message and sender info
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                String senderAddress = packet.getAddress().getHostAddress();
                int senderPort = packet.getPort();

                // Check for termination
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("\n📨 Received 'bye' from " + senderAddress + ":" + senderPort);
                    System.out.println("Terminating receiver...");
                    break;
                }

                // Display received message
                System.out.println("[" + senderAddress + ":" + senderPort + "] " + message);
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

