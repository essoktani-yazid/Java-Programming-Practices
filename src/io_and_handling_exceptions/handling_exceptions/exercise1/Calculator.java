package io_and_handling_exceptions.handling_exceptions.exercise1;

public class Calculator {
    public double divide(int a, int b) {
        try {
            return (double) a / b;
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero not possible.");
            return 0;
        }
    }

    public void convertToNumber(String text) {
        try {
            int number = Integer.parseInt(text);
            System.out.println("Result: " + number);
        } catch (NumberFormatException e) {
            System.out.println("Error: '" + text + "' is not a valid number");
        }
    }

    public void calculate(String operation, int a, int b) {
        switch (operation) {
            case "+": System.out.println(a + b); break;
            case "-": System.out.println(a - b); break;
            case "*": System.out.println(a * b); break;
            case "/": System.out.println(divide(a, b)); break;
            default:
                System.out.println("Error: Operation '" + operation + "' not supported");
        }
    }
}