# ☕ Strings and Arrays in Java

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
2. [Exercise 1 : Grade Management](#-exercise-1--grade-management)
3. [Exercise 2 : Verb Conjugation](#-exercise-2--verb-conjugation)
4. [Exercise 3 : String Operations](#-exercise-3--string-operations)
5. [Exercise 4 : Frequency Analysis](#-exercise-4--frequency-analysis)

---

## 🔍 Project Overview
This package contains the solutions for the **"Strings and Arrays in Java"** exercise series. The objective is to master array manipulation, character strings (`String`, `StringBuilder`), and basic algorithmic logic in Java.

---

## 📊 Exercise 1 : Grade Management
### 📝 The Problem
Create a program that allows the user to input student grades into an array, then perform statistical analyses: sorting, average, min/max, and searching for specific grades.

### 💡 Solution and Technical Justifications

#### 🔹 Data Structure Choice
```java
double[] grades = new double[size];
```
**Why `double[]`?** 
- Grades can be decimals (e.g., 15.5, 12.75)
- `double` offers more precision than `float` for statistical calculations
- Primitive array = direct memory access, no object overhead

#### 🔹 Dynamic Data Input
```java
System.out.print("Enter the number of students: ");
int size = scanner.nextInt();

for(int i = 0; i < size; i++){
    System.out.print("Enter grade for student N" + (i + 1) + ": " );
    grades[i] = scanner.nextDouble();
}
```
**Why this approach?**
- Dynamic allocation based on user input
- Classic `for` loop to precisely control the index and display personalized messages
- `nextDouble()` directly reads decimal numbers

#### 🔹 Sorting and Display Formatting
```java
Arrays.sort(grades);
String str_grade = Arrays.toString(grades).replace("[", "").replace("]", "");
System.out.println("Grades: " + str_grade);
```
**Why `Arrays.sort()`?**
- Modifies the array in place (no need for a copy)

**Why `.replace("[", "").replace("]", "")`?**
- `Arrays.toString()` returns `"[15.0, 12.5, 18.0]"`
- We remove the brackets for cleaner display: `"15.0, 12.5, 18.0"`

#### 🔹 Average Calculation with for-each
```java
double somme = 0;
for (double grade: grades){
    somme += grade;
}
System.out.printf("Average: %.2f%n", somme / size);
```
**Why a for-each loop?**
- More readable and less prone to index errors
- No need for the index in this operation
- Modern and concise syntax

**Why `printf()` with `%.2f`?**
- Formats the result with exactly 2 decimals (e.g., `15.67` instead of `15.666666...`)
- `%n` is platform-independent for line breaks

#### 🔹 Min/Max Optimization After Sorting
```java
// Optimized version (after sorting) :
double min = grades[0], max = grades[size-1];

// Alternative version (without sorting) :
// double min = grades[0], max = grades[0];
// for (double grade: grades){
//     min = Math.min(min, grade);
//     max = Math.max(max, grade);
// }
```
**Why access indices directly?**
- ⚡ **Performance:** O(1) instead of O(n) with a loop
- After sorting, the minimum is necessarily at index 0 and the maximum at `size-1`
- Saves a complete iteration through the array

#### 🔹 Occurrence Search
```java
System.out.print("What grade are you looking for? : ");
double target = scanner.nextDouble();
int count = 0;
for (double grade : grades) {
    if (grade == target) count++;
}
System.out.println("Number of students with " + target + " : " + count);
```
**Why `==` to compare doubles?**
- For this exercise, we compare exact values
- ⚠️ **Note:** In production, we would use `Math.abs(grade - target) < EPSILON` to handle floating-point precision errors

### 💻 Demonstration
*Example execution with grade input, sorted display and average.*

```
Entrez le nombre d'étudiants: 3
Entrez la note de l'étudiants N1: 12.5
Entrez la note de l'étudiants N2: 18
Entrez la note de l'étudiants N3: 14.75

Notes: 12.5, 14.75, 18.0
Moyenne: 15.08
La note minimale est: 12.5
La note maximale est: 18.0
Quelle note recherchez-vous ? : 18
Nombre d'étudiants ayant 18.0 : 1
```
---

## 🇫🇷 Exercise 2 : Verb Conjugation

### 📝 The Problem
Develop a program that conjugates French first-group verbs (ending in -er) in the present tense. The program should handle special cases like verbs ending in -ger (e.g., "manger" → "nous mangeons").

### 💡 Solution and Technical Justifications

#### 🔹 User Input Normalization
```java
System.out.print("Enter a first-group verb : ");
String verb = scanner.nextLine().trim().toLowerCase();
```
**Why `.trim().toLowerCase()`?**
- `.trim()`: removes leading/trailing spaces (robustness if user adds spaces)
- `.toLowerCase()`: normalizes case to treat "MANGER", "Manger", "manger" the same way
- `.nextLine()` instead of `.next()`: allows entering verbs with spaces (unlikely case but good practice)

#### 🔹 Input Validation
```java
if (!verb.endsWith("er")) {
    System.out.println("This is not a verb ending in 'er'.");
} else {
    // Conjugation...
}
```
**Why `endsWith("er")`?**
- Simple and efficient O(1) check on suffix length
- Validates that the input is indeed a first-group verb

#### 🔹 Stem Extraction
```java
String radical = verb.substring(0, verb.length() - 2);
```
**Why `substring()` with `length() - 2`?**
- Removes the last 2 characters ("er") to get the stem
- Example: `"manger"` → `"mang"` (the stem)
- More readable than manually manipulating indices

#### 🔹 Standard Conjugation
```java
System.out.println("je " + radical + "e");
System.out.println("tu " + radical + "es");
System.out.println("il/elle " + radical + "e");
System.out.println("vous " + radical + "ez");
System.out.println("ils/elles " + radical + "ent");
```
#### 🔹 Special Case Handling for -ger Verbs
```java
if (radical.endsWith("g")) {
    System.out.println("nous " + radical + "eons");
} else {
    System.out.println("nous " + radical + "ons");
}
```
**Why this condition?**
- In French, verbs ending in -ger (manger, voyager) take an 'e' before "ons" in the first person plural
- Rule: "nous mangeons" (not "nous mangons")
- `endsWith("g")` detects if the stem ends with 'g' before adding the ending

**Example:**
- `"manger"` → stem `"mang"` → ends with 'g' → `"nous mangeons"` ✅
- `"parler"` → stem `"parl"` → doesn't end with 'g' → `"nous parlons"` ✅

### 🎯 Features
- ✅ Input validation (checks "er" ending)
- ✅ Complete present indicative conjugation
- ✅ Intelligent special case handling (-ger)

### 💻 Example Execution
```
Enter a first-group verb : manger
je mange
tu manges
il/elle mange
nous mangeons    ← Special case handled!
vous mangez
ils/elles mangent
```

---

## 🔤 Exercise 3 : String Operations

### 📝 The Problem
Create an interactive menu-driven program that performs various string operations: input, display, reverse, and word count. The program should maintain state and provide a user-friendly interface.

### 💡 Solution and Technical Justifications

#### 🔹 Menu Structure with do-while
```java
String currentString = "";
int choice;

do {
    // Menu display
    System.out.println("\n--- MENU ---");
    System.out.println("1. Enter string");
    System.out.println("2. Display string");
    System.out.println("3. Reverse string");
    System.out.println("4. Count words");
    System.out.println("0. Quit");
    System.out.print("Your choice : ");

    choice = scanner.nextInt();
    scanner.nextLine(); // Important!
    
    // Processing...
    
} while (choice != 0);
```
**Why `do-while`?**
- Menu displays at least once (no check before first iteration)
- More natural for an interactive interface
- User immediately sees available options

**Why `scanner.nextLine()` after `nextInt()`?**
- ⚠️ **Classic Java pitfall!** `nextInt()` leaves the `\n` (newline) character in the buffer
- Without `nextLine()`, the next `nextLine()` to read the string would read an empty string
- This line "cleans" the scanner buffer

#### 🔹 State Storage
```java
String currentString = "";
```
**Why a variable outside the loop?**
- Allows preserving the string between different menu operations
- User can modify the string once, then reuse it for multiple operations

#### 🔹 String Input
```java
case 1:
    System.out.print("Enter a string : ");
    currentString = scanner.nextLine();
    break;
```
**Why `nextLine()`?**
- Allows entering complete sentences with spaces
- Unlike `next()` which stops at the first space

#### 🔹 Reversal with StringBuilder
```java
case 3:
    String reversed = new StringBuilder(currentString).reverse().toString();
    System.out.println("Reversed string : " + reversed);
    break;
```
**Why `StringBuilder` and not a manual loop?**
- ⚡ **Performance:** `StringBuilder.reverse()` is optimized O(n)
- ✅ **Simplicity:** One line instead of a complete loop
- ✅ **Readability:** Shorter and more expressive code
- **Manual alternative (less efficient):**
  ```java
  String reversed = "";
  for (int i = currentString.length() - 1; i >= 0; i--) {
      reversed += currentString.charAt(i); // Creates a new String at each iteration!
  }
  ```
- `.toString()` converts the `StringBuilder` to an immutable `String`

#### 🔹 Word Count with Regex
```java
case 4:
    if (currentString.trim().isEmpty()) {
        System.out.println("Number of words : 0");
    } else {
        String[] words = currentString.trim().split("\\s+");
        System.out.println("Number of words : " + words.length);
    }
    break;
```
**Why check `isEmpty()` first?**
- Avoids creating an array with an empty element if the string only contains spaces
- More robust: `"   "` gives 0 words (expected behavior)

**Why `.trim()` before `split()`?**
- Removes leading/trailing spaces to avoid empty elements in the array
- Example: `"  bonjour  "` → without trim: `["", "bonjour", ""]` (3 elements) ❌
- With trim: `["bonjour"]` (1 element) ✅

**Why `split("\\s+")` and not `split(" ")`?**
- `\\s+` is a regular expression meaning "one or more spaces"
- Handles multiple spaces: `"bonjour    le    monde"` → 3 words ✅
- `split(" ")` would give empty elements: `["bonjour", "", "", "", "le", ...]` ❌
- `\\` (double backslash) is necessary because Java escapes backslashes in strings

#### 🔹 UX Management
```java
if (choice != 0) {
    System.out.println("Press Enter to return to menu...");
    scanner.nextLine();
}
```
**Why this pause?**
- Gives the user time to read the result before the menu reappears
- Improves user experience (otherwise the result disappears too quickly)

### 🎯 Features
- ✅ String input and storage
- ✅ Display current string
- ✅ String reversal with `StringBuilder`
- ✅ Intelligent word counting (handles multiple spaces)
- ✅ Intuitive and robust menu interface

### 💻 Example Execution
```
--- MENU ---
1. Enter string
2. Display string
3. Reverse string
4. Count words
0. Quit
Your choice : 1
Enter a string : Hello world

Your choice : 3
Reversed string : dlrow olleH

Your choice : 4
Number of words : 2
```
---

## 📈 Exercise 4 : Frequency Analysis

### 📝 The Problem
Write a program that analyzes a text string and counts the frequency of each letter (A-Z). The program ignores case sensitivity and non-alphabetic characters, displaying only the letters that appear at least once.

### 💡 Solution and Technical Justifications

#### 🔹 1. The Array Trick (The 26 Boxes)
Instead of creating 26 separate variables (`countA`, `countB`, `countC`...), we use an array of 26 integers called `nb_occurrences`.

* **Index 0** counts the **A**.
* **Index 1** counts the **B**.
* **Index 2** counts the **C**.
* ...
* **Index 25** counts the **Z**.

Initially, all cases (boxes) are set to 0 by Java upon array instantiation.

#### 🔹 2. The Magic Formula (ASCII Arithmetic)
The computer doesn't naturally know that box 0 corresponds to 'A'. We use a mathematical trick to transform a letter into its corresponding box number. In Java, a `char` is internally handled as a numeric value (ASCII/Unicode).

* **'A'** is 65
* **'B'** is 66
* **'C'** is 67

The trick is to perform a simple subtraction to find the relative position:
$$\text{Index} = \text{Letter} - 'A'$$

**Example:**
If the letter is **'C'** (67) and **'A'** is (65):
$$67 - 65 = 2$$
The program then increments the value at **index 2** of the array: `nb_occurrences[c - 'A']++`.

#### 🔹 3. Implementation
```java
int[] nb_occurrences = new int[26];

// Traversal with character normalization
for (int i = 0; i < upperInput.length(); i++) {
char c = upperInput.charAt(i);
    if (c >= 'A' && c <= 'Z') {
        nb_occurrences[c - 'A']++; // The magic formula in action
    }
}
```

#### 🔹 Selective Display
```java
System.out.println("The string \"" + input + "\" contains :");
for (int i = 0; i < nb_occurrences.length; i++) {
    if (nb_occurrences[i] > 0) {
        char letter = (char) ('A' + i);
        System.out.println(nb_occurrences[i] + " times the letter '" + letter + "'");
    }
}
```
**Why display only if `nb_occurrences[i] > 0`?**
- Avoids displaying "0 times the letter 'X'" for all absent letters
- Cleaner and more readable display
- We show only relevant information

**Why `(char) ('A' + i)`?**
- Inverse conversion: index → letter
- `i = 0` → `'A'`, `i = 1` → `'B'`, ..., `i = 25` → `'Z'`
- The cast `(char)` is necessary because `'A' + i` gives an `int`

### 🎯 Features
- ✅ Case-insensitive counting
- ✅ Ignores non-alphabetic characters
- ✅ Formatted display with only found letters
- ✅ Efficient memory management (fixed array)

### 💻 Example Execution
```
Enter a line of text (max. 100 characters) : Hello World!
The string "Hello World!" contains :
1 times the letter 'D'
1 times the letter 'E'
3 times the letter 'L'
2 times the letter 'O'
1 times the letter 'R'
1 times the letter 'W'
1 times the letter 'H'
