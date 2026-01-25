package tp_multithreading_sockets.multithreading.scenario2;


public class Bus {
    private int availableSeats;

    public Bus(int totalSeats) {
        this.availableSeats = totalSeats;
    }


    public boolean bookSeats(int seatsRequested) {
        if (availableSeats >= seatsRequested) {
            // Simulate some processing time (race condition window)
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            availableSeats -= seatsRequested;
            System.out.println(Thread.currentThread().getName() + 
                             " booked " + seatsRequested + 
                             " seat(s). Remaining seats: " + availableSeats);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + 
                             " cannot book " + seatsRequested + 
                             " seat(s). Only " + availableSeats + " available.");
            return false;
        }
    }


    public synchronized boolean bookSeatsSynchronized(int seatsRequested) {
        if (availableSeats >= seatsRequested) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            availableSeats -= seatsRequested;
            System.out.println(Thread.currentThread().getName() + 
                             " booked " + seatsRequested + 
                             " seat(s). Remaining seats: " + availableSeats);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + 
                             " cannot book " + seatsRequested + 
                             " seat(s). Only " + availableSeats + " available.");
            return false;
        }
    }


    public boolean bookSeatsSynchronizedBlock(int seatsRequested) {
        synchronized (this) {
            if (availableSeats >= seatsRequested) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                availableSeats -= seatsRequested;
                System.out.println(Thread.currentThread().getName() + 
                                 " booked " + seatsRequested + 
                                 " seat(s). Remaining seats: " + availableSeats);
                return true;
            } else {
                System.out.println(Thread.currentThread().getName() + 
                                 " cannot book " + seatsRequested + 
                                 " seat(s). Only " + availableSeats + " available.");
                return false;
            }
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}

