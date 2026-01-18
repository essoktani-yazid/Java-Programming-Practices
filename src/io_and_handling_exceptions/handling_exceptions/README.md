# ☕ Exception Handling in Java

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![School](https://img.shields.io/badge/School-ENSET_Mohammedia-blue?style=for-the-badge)

> **Module :** Object-Oriented Programming (Java)  
> **Supervised by :** Prof. Loubna Aminou  
> **Created by :** Yazid ESSOKTANI  
> **Program :** Master SDIA

## 📑 Table of Contents

1. [Exercise 1 : Calculator with Exception Handling](#-exercise-1--calculator-with-exception-handling)
2. [Exercise 2 : Custom Exception - TooFastException](#-exercise-2--custom-exception--toofastexception)

---

## 🧮 Exercise 1 : Calculator with Exception Handling

### 📝 The Problem

Create a calculator class that performs basic arithmetic operations (addition, subtraction, multiplication, division) with proper exception handling. The calculator should gracefully handle division by zero and invalid number format conversions.

### 💡 Solution and Technical Justifications

#### 🔹 Division with ArithmeticException Handling

```java
public double divide(int a, int b) {
    try {
        return (double) a / b;
    } catch (ArithmeticException e) {
        System.out.println("Error: Division by zero not possible.");
        return 0;
    }
}
```

**Why catch `ArithmeticException`?**

- **Division by Zero**: In Java, dividing an integer by zero throws `ArithmeticException` (unlike floating-point division which produces `Infinity` or `NaN`)
- **User-Friendly Error Messages**: Instead of crashing, the program displays a clear error message
- **Graceful Degradation**: Returns a default value (0) to allow the program to continue

**Why cast to `double`?**

- Ensures floating-point division (e.g., `5 / 2 = 2.5` instead of `2`)
- More precise results for division operations
- Prevents integer truncation

**Why return 0 on error?**

- Provides a safe default value
- Allows the program to continue execution
- ⚠️ **Note**: In production, you might want to throw the exception or return `Double.NaN` to indicate an invalid result

#### 🔹 Number Format Conversion with NumberFormatException Handling

```java
public void convertToNumber(String text) {
    try {
        int number = Integer.parseInt(text);
        System.out.println("Result: " + number);
    } catch (NumberFormatException e) {
        System.out.println("Error: '" + text + "' is not a valid number");
    }
}
```

**Why catch `NumberFormatException`?**

- `Integer.parseInt()` throws `NumberFormatException` when the string cannot be converted to an integer
- Common scenarios: empty strings, non-numeric characters, decimal numbers (e.g., "3.14")
- Prevents program crash and provides meaningful feedback

**Why include the original text in the error message?**

- Helps users identify what input caused the error
- More informative: `"Error: 'abc' is not a valid number"` is clearer than just `"Invalid number"`

#### 🔹 Operation Selection with Switch Statement

```java
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
```

**Why use `switch` statement?**

- More readable than multiple `if-else` statements
- Efficient for multiple discrete values
- Clear separation of cases

**Why handle default case?**

- Catches unsupported operations (e.g., "%", "^")
- Provides user feedback instead of silently failing
- Defensive programming practice

**Why delegate division to `divide()` method?**

- Separation of concerns: division logic with exception handling is isolated
- Reusable: `divide()` can be called independently
- Consistent error handling across the application


### 💻 Example Execution

```
// Division by zero
divide(10, 0);
// Output: Error: Division by zero not possible.

// Valid conversion
convertToNumber("123");
// Output: Result: 123

// Invalid conversion
convertToNumber("abc");
// Output: Error: 'abc' is not a valid number

// Supported operation
calculate("+", 5, 3);
// Output: 8

// Unsupported operation
calculate("%", 10, 3);
// Output: Error: Operation '%' not supported
```

---

## 🚗 Exercise 2 : Custom Exception - TooFastException

### 📝 The Problem

Create a custom exception class `TooFastException` that is thrown when a vehicle's speed exceeds a certain limit (90 km/h). Demonstrate exception throwing, catching, and stack trace display.

### 💡 Solution and Technical Justifications

#### 🔹 Custom Exception Class

```java
public class TooFastException extends Exception {
    public TooFastException(int speed) {
        super("This is an exception of type TooFastException. Speed involved: " + speed);
    }
}
```

**Why extend `Exception`?**

- `Exception` is a checked exception (must be declared in method signature or caught)
- Forces callers to handle the exception explicitly
- Better for recoverable errors (speed can be reduced)

**Why not extend `RuntimeException`?**

- `RuntimeException` is unchecked (no compile-time requirement to handle)
- Less strict, but can lead to unhandled exceptions at runtime
- For this use case, we want to force proper handling

**Why include speed in the constructor?**

- Provides context about what caused the exception
- More informative error messages
- Helps debugging and logging

**Why call `super()` with a message?**

- Passes the message to the parent `Exception` class
- Message is accessible via `getMessage()` method
- Standard Java exception pattern

#### 🔹 Method with throws Declaration

```java
public void testSpeed(int speed) throws TooFastException {
    if (speed > 90) {
        throw new TooFastException(speed);
    }
}
```

**Why declare `throws TooFastException`?**

- Required for checked exceptions
- Documents that this method can throw an exception
- Forces callers to handle or propagate the exception

**Why check speed before throwing?**

- Validates input before throwing exception
- Clear business rule: speed > 90 is invalid
- Prevents unnecessary exception creation

**Why use `throw` keyword?**

- Explicitly throws the exception
- Transfers control to the exception handler
- Stops normal execution flow

#### 🔹 Exception Catching and Stack Trace

```java
public static void main(String[] args) {
    Vehicle myVehicle = new Vehicle();
    try {
        System.out.println("Testing speed: 80");
        myVehicle.testSpeed(80);

        System.out.println("Testing speed: 120");
        myVehicle.testSpeed(120);
    } catch (TooFastException e) {
        // Display the call stack as required
        e.printStackTrace();
    }
}
```

**Why wrap in try-catch block?**

- Required to handle checked exceptions
- Prevents program termination
- Allows graceful error handling

**Why use `printStackTrace()`?**

- Displays the complete call stack
- Shows where the exception was thrown and propagated
- Useful for debugging and understanding program flow
- Format:
  ```
  TooFastException: This is an exception of type TooFastException. Speed involved: 120
      at Vehicle.testSpeed(Vehicle.java:8)
      at Vehicle.main(Vehicle.java:19)
  ```

**Why test both valid and invalid speeds?**

- Demonstrates normal flow (80 km/h - no exception)
- Demonstrates exception flow (120 km/h - exception thrown)
- Shows the difference between successful and exceptional execution

**Why continue execution after first test?**

- Shows that exceptions don't prevent subsequent code execution
- Demonstrates that `testSpeed(80)` succeeds, then `testSpeed(120)` throws
- Illustrates exception handling doesn't stop the entire program


### 💻 Example Execution

```
Testing speed: 80
Testing speed: 120
io_and_handling_exceptions.handling_exceptions.exercise2.TooFastException: This is an exception of type TooFastException. Speed involved: 120
    at io_and_handling_exceptions.handling_exceptions.exercise2.Vehicle.testSpeed(Vehicle.java:8)
    at io_and_handling_exceptions.handling_exceptions.exercise2.Vehicle.main(Vehicle.java:19)
```