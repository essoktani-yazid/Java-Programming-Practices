# ☕ Input/Output in Java

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![School](https://img.shields.io/badge/School-ENSET_Mohammedia-blue?style=for-the-badge)

> **Module :** Object-Oriented Programming (Java)  
> **Supervised by :** Prof. Loubna Aminou  
> **Created by :** Yazid ESSOKTANI  
> **Program :** Master SDIA

## 📑 Table of Contents

1. [Exercise 1 : Directory Listing Simulator](#-exercise-1--directory-listing-simulator)
2. [Exercise 2 : Product Management with Serialization](#-exercise-2--product-management-with-serialization)

---

## 📁 Exercise 1 : Directory Listing Simulator

### 📝 The Problem

Create a program that simulates the Unix `ls` command. The program should accept a directory path from the user and display all files and subdirectories with their types (`<DIR>` or `<FILE>`) and permissions (read, write, hidden).

### 💡 Solution and Technical Justifications

#### 🔹 File System Access with `java.io.File`

```java
File directory = new File(path);
```

**Why `java.io.File`?**

- Standard Java class for file and directory operations
- Cross-platform compatibility (works on Windows, Linux, macOS)
- Provides methods to check existence, type, and permissions

#### 🔹 Input Validation

```java
if (directory.exists() && directory.isDirectory()) {
    // Process directory contents
} else {
    System.out.println("Error: The path provided is not a valid directory.");
}
```

**Why check both `exists()` and `isDirectory()`?**

- `exists()`: Ensures the path actually exists (not a typo)
- `isDirectory()`: Confirms it's a directory, not a file
- Prevents runtime errors when trying to list contents

#### 🔹 Directory Contents Listing

```java
File[] contents = directory.listFiles();
if (contents != null) {
    for (File file : contents) {
        // Process each file/directory
    }
}
```

**Why check `contents != null`?**

- `listFiles()` can return `null` if:
  - The directory doesn't exist (already checked, but defensive programming)
  - Access is denied (permissions issue)
- Prevents `NullPointerException` when iterating

#### 🔹 File Type Detection

```java
String type = file.isDirectory() ? "<DIR>" : "<FILE>";
```

**Why ternary operator?**

- Concise and readable one-liner
- Clearly distinguishes directories from files
- Essential for displaying meaningful information

#### 🔹 Permission Flags

```java
String r = file.canRead() ? "r" : "-";
String w = file.canWrite() ? "w" : "-";
String h = file.isHidden() ? "h" : "-";
```

**Why separate flags?**

- Mimics Unix `ls` command format
- Provides clear visual indication of permissions
- Uses `-` to indicate absence of permission (standard convention)

#### 🔹 Display Format

```java
System.out.println(file.getAbsolutePath() + " " + type + " " + r + w + h);
```

**Why `getAbsolutePath()`?**

- Shows the complete path from root
- More informative than just the filename
- Helps users identify exact file locations

### 💻 Example Execution

```
Enter the full path to the directory: /home/yazid/Documents
/home/yazid/Documents/file1.txt <FILE> rw-
/home/yazid/Documents/subfolder <DIR> rw-
/home/yazid/Documents/.hidden <FILE> r-h
```

---

## 💾 Exercise 2 : Product Management with Serialization

### 📝 The Problem

Develop a product management application that allows users to perform operations on products. The application must persist data to a binary file using Java object serialization, allowing data to survive between program executions.

### 💡 Solution and Technical Justifications

#### 🔹 Serializable Product Class

```java
public class Product implements Serializable {
    private long id;
    private String name, brand, description;
    private double price;
    private int stock;
    // ...
}
```

**Why `implements Serializable`?**

- Required interface for object serialization
- Allows objects to be converted to byte streams
- No methods to implement (marker interface)
- Enables persistence to binary files

**Why `Serializable` and not JSON/XML?**

- Binary format is more efficient (smaller file size)
- Faster read/write operations
- Native Java support (no external libraries)
- Preserves object graph relationships

#### 🔹 Business Logic Interface

```java
public interface IProduitMetier {
    void add(Product p);
    List<Product> getAll();
    Product findById(long id);
    void delete(long id);
    void saveAll();
}
```

**Why use an interface?**

- Follows **Dependency Inversion Principle** (SOLID)
- Allows multiple implementations (file-based, database, etc.)

#### 🔹 Data Loading on Access

```java
@Override
@SuppressWarnings("unchecked")
public List<Product> getAll() {
    File file = new File(FILE_NAME);
    if (file.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            products = (List<Product>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
    return products;
}
```

**Why check `file.exists()` first?**

- **Avoid Exceptions**: Trying to open a `FileInputStream` on a non-existent file throws a `FileNotFoundException`. Checking existence first is a cleaner way to handle the "first run" scenario where the data file hasn't been created yet.
- **Logic Flow**: If the file doesn't exist, we simply return the empty list (initialized in the class field), which is the correct behavior for a fresh start.

**Why `ObjectOutputStream`?**

- Writes entire object graph to binary file
- Handles nested objects automatically
- Preserves object relationships

**Why `ObjectInputStream`?**

- Reads binary data and reconstructs Java objects
- Essential for retrieving the data saved by `ObjectOutputStream`
- Converts the byte stream back into the `products` list

**Why `FileInputStream`?**

- Opens a connection to the actual file (`products.dat`)
- Reads raw bytes from the file system
- Acts as the source stream that `ObjectInputStream` decodes

**Why use `try-with-resources`?**

- **Syntax**: `try (ObjectInputStream ois = ...)`
- **Safety**: Automatically closes the stream (`ois.close()`) when the block exits, even if an exception is thrown. This prevents resource leaks (open file handles).

**Deserialization & Type Safety**:

- `ois.readObject()`: Reconstructs the object graph from the binary file. It returns a generic `Object`.
- **Casting**: `(List<Product>)` tells Java to treat this object as a List of Products.
- **The "Unchecked Cast" Warning**: You might notice a compiler warning here. This is because Java's valid type information is erased at runtime. The JVM knows it's a `List`, but doesn't know it contains `Product` objects.
  - _Best Practice_: We suppress this warning with `@SuppressWarnings("unchecked")` because we are certain of the file's content (we wrote it ourselves).

#### 🔹 Object Serialization (Saving)

```java
@Override
public void saveAll() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
        oos.writeObject(products);
    } catch (IOException e) {
        System.out.println("Error saving file: " + e.getMessage());
    }
}
```

**Why wrap in try-catch?**

- `IOException` can occur if:
  - Disk is full
  - File permissions denied
  - Disk I/O errors
- Prevents program crash, provides user feedback

#### 🔹 Object Deserialization (Loading)

```java
products = (List<Product>) ois.readObject();
```

**Why cast to `List<Product>`?**

- `readObject()` returns `Object` (generic type erasure)
- Must cast to expected type
- ⚠️ **Note**: In production, validate the cast or use generics with type tokens

#### 🔹 Scanner Buffer Management

```java
scanner.nextInt();
scanner.nextLine(); // Important!
```

**Why `nextLine()` after `nextInt()`?**

- ⚠️ **Classic Java pitfall!** `nextInt()` leaves the newline character (`\n`) in the buffer
- Without `nextLine()`, the next string input would read an empty string
- This line "consumes" the leftover newline

#### 🔹 Lambda Expression for Deletion

```java
products.removeIf(p -> p.getId() == id);
```

**Why `removeIf()` with lambda?**

- ⚡ **Modern Java 8+ syntax**
- More concise than manual loop
- Functional programming style
- Equivalent to:
  ```java
  products.removeIf(new Predicate<Product>() {
      @Override
      public boolean test(Product p) {
          return p.getId() == id;
      }
  });
  ```

#### 🔹 Method Reference for Display

```java
products.forEach(System.out::println);
```

**Why method reference (`::`)?**

- ⚡ **Java 8+ feature**
- Shorter than lambda: `products.forEach(p -> System.out.println(p))`
- More readable and idiomatic
- Leverages `Product.toString()` method

### 💻 Example Execution

```
--- PRODUCT MANAGEMENT MENU ---
1. Display the list of products
2. Search for a product by its ID
3. Add a new product to the list
4. Delete a product by ID
5. Save the products (to products.dat)
6. Exit this program
Enter your choice: 3

Enter ID: 1
Enter Name: Laptop
Enter Brand: Dell
Enter Price: 999.99
Enter Description: High-performance laptop
Enter Stock Quantity: 10
Product added successfully.

Enter your choice: 5
Products saved to products.dat

Enter your choice: 1
ID: 1 | Name: Laptop | Brand: Dell | Price: 999.99 | Description: High-performance laptop | Stock: 10
```