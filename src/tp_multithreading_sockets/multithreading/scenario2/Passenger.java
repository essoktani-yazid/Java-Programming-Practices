package tp_multithreading_sockets.multithreading.scenario2;


public class Passenger extends Thread {
    private Bus bus;
    private int seatsRequested;
    private boolean useSynchronized;

    public Passenger(String name, Bus bus, int seatsRequested, boolean useSynchronized) {
        super(name);
        this.bus = bus;
        this.seatsRequested = seatsRequested;
        this.useSynchronized = useSynchronized;
    }

    @Override
    public void run() {
        System.out.println(getName() + " wants to book " + seatsRequested + " seat(s)");
        
        if (useSynchronized) {
            // Use synchronized method
            bus.bookSeatsSynchronized(seatsRequested);
        } else {
            // Use non-synchronized method (demonstrates race condition)
            bus.bookSeats(seatsRequested);
        }
    }
}

