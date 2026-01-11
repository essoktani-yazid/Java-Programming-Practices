# ☕ Java Generics & Collections

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![School](https://img.shields.io/badge/School-ENSET_Mohammedia-blue?style=for-the-badge)

> **Module:** Object-Oriented Programming (Java)
> **Supervised by:** Prof. Loubna Aminou
> **Created by:** Yazid ESSOKTANI
> **Program:** Master SDIA

---

## 📑 Table of Contents
1. [Project Overview](#-project-overview)
2. [Part 1: Basic Generic Storage](#-part-1-basic-generic-storage)
3. [Part 2: Generic Interface & Product Management](#-part-2-generic-interface--product-management)

---

## 🔍 Project Overview

This project demonstrates the implementation of **Java Generics** to create reusable and type-safe components. It covers a universal storage class for various data types and a specialized business logic interface (`IMetier`) for managing `Product` objects.

---

## 🛠️ Part 1: Basic Generic Storage

### 📝 The Problem
Designing a container capable of storing any object type (Integer, String, etc.) while ensuring type safety at compile-time and avoiding code duplication.

### 💡 Solution and Technical Justifications

#### 🔹 The `GenericStorage<T>` Class
```java
public class GenericStorage<T> {
    ArrayList<T> elements = new ArrayList<>();

    public void addElement(T o){ elements.add(o); }
    public T getElement(int index){ 
        return elements.get(index); 
    }
    public int getSize(){ return elements.size(); }
}
```

**Why use Generics (`<T>`)?**
* **Type Safety**: Errors are caught at **compile-time**. For example, a `GenericStorage<Integer>` will not accept a `String`.
* **Reusability**: One class definition works for `Integer`, `String`, or `Double` without rewriting the logic.
* **Elimination of Casting**: The `getElement` method returns type `T`, so no manual casting is required when retrieving data.

---

## 🏗️ Part 2: Generic Interface & Product Management

### 📝 The Problem
Developing a standardized architecture to manage complex objects (`Product`) using a generic interface that supports operations like searching and deleting.

### 💡 Solution and Technical Justifications

#### 🔹 The `IMetier<T>` Interface
```java
public interface IMetier<T> {
    void add(T o);
    List<T> getAll();
    T findById(long id);
    void delete(long id);
}
```

**Why a Generic Interface?**
* It establishes a standard contract for all service classes, ensuring consistent methods across different data types.

#### 🔹 Business Logic (MetierProduitImpl)
```java
@Override
public Product findById(long id) {
    for(Product product: products){
        if (product.getId() == id) return product;
    }
    return null;
}

@Override
public void delete(long id) {
    products.removeIf(p -> p.getId() == id);
}
```

**Why use `removeIf`?**
* It provides a clean, functional way to remove elements based on a condition (the ID).
* It prevents `ConcurrentModificationException` which can occur when trying to remove elements while iterating through a list.

### 💻 Demonstration
The console application features a menu to interact with the system:
```text
--- MENU ---
1. Display product list
2. Search product by ID
3. Add a new product
4. Delete a product by ID
5. Exit

Choice: 3
ID: 1
Name: Keyboard
Brand: Logitech
Price: 45.0
```