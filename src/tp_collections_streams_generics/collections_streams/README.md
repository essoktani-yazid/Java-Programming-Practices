# ☕ Collections & Streams in Java

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![School](https://img.shields.io/badge/School-ENSET_Mohammedia-blue?style=for-the-badge)

> **Module :** Object-Oriented Programming (Java)  
> **Supervised by :** Prof. Loubna Aminou  
> **Created by :** Yazid ESSOKTANI  
> **Program :** Master SDIA
---

## 📑 Table of Contents
1. [Project Overview](#-project-overview)
2. [Part 1 : Lists & Products](#-part-1--lists--products)
3. [Part 2 : Maps & Grades](#-part-2--maps--grades)
4. [Part 3 : Sets & Groups](#-part-3--sets--groups)

---

## 🔍 Package Overview
This package explores the **Java Collections Framework**. The objective is to understand how to efficiently store, organize, and manipulate groups of objects using **Lists**, **Maps**, and **Sets**, while introducing modern functional programming concepts like **Lambda expressions**.

---

## 📦 Part 1 : Lists & Products
### 📝 The Problem
Create a product management application that allows storing products (ID, name, price) in a dynamic list. The program must support adding, removing by index, modifying, and searching for products by name.

### 💡 Solution and Technical Justifications

#### 🔹 Data Structure Choice
```java
ArrayList<Product> products = new ArrayList<>();
````

**Why `ArrayList`?** Unlike arrays (`Product[]`), `ArrayList` creates a **dynamic** collection that grows automatically.
* Provides built-in methods for manipulation such as `add`, `remove`, `get`, and `set`.
* Ideal for sequential access and iteration.

#### 🔹 Dynamic Operations
```java
// Adding products
products.add(new Product(1, "Clavier", 25.0));

// Modifying index 0
products.set(0, new Product(1, "Clavier Gamer", 45.0));

// Removing index 1
products.remove(1);
```

**Why use `set()` and `remove()`?**
* `set(index, element)` replaces the element at a specific position efficiently (O(1)).
* `remove(index)` shifts subsequent elements to the left, maintaining a gap-free list.

#### 🔹 Modern Iteration
```java
products.forEach(System.out::println);
```

**Why Method Reference (`::`)?**
* Shorthand for `p -> System.out.println(p)`.
* Makes the code cleaner and more readable than a traditional `for` loop.

#### 🔹 Search Logic
```java
for (Product p : products) {
    if (p.getName().equalsIgnoreCase(searchName)) {
        System.out.println("Produit trouvé : " + p);
        trouve = true;
        break;
    }
}
```

**Why `equalsIgnoreCase`?**
* Ensures user-friendly search (e.g., "clavier" finds "Clavier").
* Robust string comparison preventing case-sensitivity issues during user input.

### 💻 Demonstration
*Execution showing list manipulation and search.*
```text
Liste des produits :
ID: 1 | Nom: Clavier Gamer | Prix: 45.0
ID: 3 | Nom: Ecran | Prix: 150.0

Entrez le nom du produit à rechercher : ecran
Produit trouvé : ID: 3 | Nom: Ecran | Prix: 150.0
```
---

## 🗺️ Part 2 : Maps & Grades

### 📝 The Problem
Manage student grades using a Key-Value association (Name → Grade). he system should allow inserting, updating, and deleting grades, as well as calculating statistics (Average, Min, Max) and verifying specific values.

### 💡 Solution and Technical Justifications

#### 🔹 Choosing the Map Interface
```java
HashMap<String, Double> grades = new HashMap<>();
grades.put("Student 1", 15.5);
```

**Why `HashMap`?**
* Allows retrieving a grade by student name in **constant time O(1)** (on average).
* Keys (`String`) must be unique: adding "Student 1" again updates their grade automatically.

#### 🔹 Statistical Analysis
```java
double sum = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;

for(double grade: grades.values()){
    sum += grade;
    max = Math.max(max, grade);
    min = Math.min(min, grade);
}
```

**Why iterate over `values()`?**
* We only need the grades for statistics, ignoring the student names (keys).
* `Double.MIN_VALUE` and `MAX_VALUE` ensure correct initialization for finding the maximum and minimum values respectively during comparisons.

#### 🔹 Existence Check
```java
boolean has20 = grades.containsValue(20.0);
```

**Why `containsValue`?**
* Instantly checks if any student has a specific grade (e.g., 20.0) without the need to write a manual loop.

#### 🔹 Lambda Display
```java
grades.forEach((name, grade) -> System.out.println(name + ": " + grade));
```
**Why BiConsumer Lambda?**
* The `forEach` method on a `Map` takes both a key and a value as arguments (k, v).
* It eliminates the need for verbose `Map.Entry` iterators or manual loops, making the code much more concise.

### 💻 Demonstration
```text
Nombre d'étudiants : 2
Moyenne: 14.75, Max: 17.5, Min: 12.0
Note 20 présente ? false
Student 1: 17.5
Student 3: 12.0
```

---

## 🔢 Part 3 : Sets & Groups

### 📝 The Problem
Create a program to manage groups of students using Sets. The goal is to perform mathematical set operations: Intersection (students in both groups) and Union (all unique students).

### 💡 Solution and Technical Justifications

#### 🔹 Uniqueness with HashSet
```java
HashSet<String> groupA = new HashSet<>();
groupA.add("Student 1");
groupA.add("Student 1"); // Duplicate ignored!
```

**Why `HashSet`?**
* **Automatically prevents duplicates**: A `HashSet` internally ensures that each element is unique; adding an existing element again will have no effect.
* **Order is not guaranteed**: Unlike Lists, the order of elements is not preserved, which is acceptable and expected for mathematical sets.

#### 🔹 Intersection (Common Elements)
```java
HashSet<String> intersect = new HashSet<>(groupA);
intersect.retainAll(groupB);
```

**How `retainAll` works?**
* This method modifies the `intersect` set to keep **only** the elements that are also present in `groupB`.
* It is mathematically equivalent to the intersection: $A \cap B$.

#### 🔹 Union (All Elements)
```java
HashSet<String> union = new HashSet<>(groupA);
union.addAll(groupB);
```

**How `addAll` works?**
* Adds all elements from `groupB` to the `union` set.
* Since it's a `Set`, duplicates (students present in both groups) are added only once.
* Mathematically equivalent to: $A \cup B$.

### 💻 Demonstration
```text
Group A : [Student 1, Student 2]
Group B : [Student 2, Student 3]
Intersection : [Student 2]
Union : [Student 1, Student 2, Student 3]
```