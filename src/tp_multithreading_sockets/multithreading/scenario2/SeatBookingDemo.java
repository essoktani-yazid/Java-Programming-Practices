package tp_multithreading_sockets.multithreading.scenario2;


public class SeatBookingDemo {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("SCENARIO 2: Seat Booking System");
        System.out.println("=".repeat(60));
        
        // Part 1: Demonstrate overbooking (without synchronization)
        System.out.println("\n--- PART 1: WITHOUT SYNCHRONIZATION (Overbooking) ---");
        demonstrateOverbooking();
        
        // Wait a bit before next demonstration
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Part 2: Demonstrate solution (with synchronization)
        System.out.println("\n--- PART 2: WITH SYNCHRONIZATION (Thread-Safe) ---");
        demonstrateSynchronizedSolution();
    }

    private static void demonstrateOverbooking() {
        Bus bus = new Bus(3);
        System.out.println("Total seats available: " + bus.getAvailableSeats());
        
        Passenger passenger1 = new Passenger("Passenger-1", bus, 2, false);
        Passenger passenger2 = new Passenger("Passenger-2", bus, 2, false);
        
        // Start both threads simultaneously
        passenger1.start();
        passenger2.start();
        
        // Wait for both threads to complete
        try {
            passenger1.join();
            passenger2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal available seats: " + bus.getAvailableSeats());
        System.out.println("Expected: -1 (or 1 if one booking failed)");
        System.out.println("⚠️  OVERBOOKING: More seats booked than available!");
    }

    private static void demonstrateSynchronizedSolution() {
        Bus bus = new Bus(3);
        System.out.println("Total seats available: " + bus.getAvailableSeats());
        
        Passenger passenger1 = new Passenger("Passenger-1", bus, 2, true);
        Passenger passenger2 = new Passenger("Passenger-2", bus, 2, true);
        
        // Start both threads simultaneously
        passenger1.start();
        passenger2.start();
        
        // Wait for both threads to complete
        try {
            passenger1.join();
            passenger2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal available seats: " + bus.getAvailableSeats());
        System.out.println("Expected: -1 (or 1 if one booking failed)");
        System.out.println("✅ SYNCHRONIZED: Thread-safe booking!");
    }
}

