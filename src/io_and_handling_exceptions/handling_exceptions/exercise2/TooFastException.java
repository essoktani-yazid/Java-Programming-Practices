package io_and_handling_exceptions.handling_exceptions.exercise2;

public class TooFastException extends Exception {
    public TooFastException(int speed) {
        super("This is an exception of type TooFastException. Speed involved: " + speed);
    }
}

