package io_and_handling_exceptions.handling_exceptions.exercise2;

public class Vehicle {
    public Vehicle() {}

    public void testSpeed(int speed) throws TooFastException {
        if (speed > 90) {
            throw new TooFastException(speed);
        }
    }

    public static void main(String[] args) {
        Vehicle myVehicle = new Vehicle();
        try {
            System.out.println("Testing speed: 80");
            myVehicle.testSpeed(80);

            System.out.println("Testing speed: 120");
            myVehicle.testSpeed(120);
        } catch (TooFastException e) {
            e.printStackTrace();
        }
    }
}