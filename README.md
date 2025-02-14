# Bisaya++ Interpreter

## Introduction

Bisaya++ is a strongly-typed, high-level interpreted Cebuano-based programming language developed to help Cebuanos learn the fundamentals of programming. With its simple syntax and native keywords, Bisaya++ makes coding more accessible, especially for beginners who are more comfortable with the Cebuano language.

The language is designed to be intuitive, using Cebuano words for variable declarations, data types, and control structures. It removes unnecessary complexity while retaining essential programming concepts such as variables, conditionals, loops, and functions. This makes it an excellent choice for educational purposes, particularly in Cebuano-speaking regions.

This project is an interpreter for Bisaya++, implemented in Java. The interpreter reads and executes Bisaya++ code, allowing users to write programs in their native language and immediately see the results.

## Team Members

- **Sajulga, Adrian**
- **Segundo, Julia Laine**
- **Tolentino, Tristan James**

## Features

- Parses and executes Bisaya++ code
- Supports variable declaration, assignment, and data types
- Implements control structures like loops and conditionals
- Provides string manipulation and mathematical operations
- Displays error handling for syntax and runtime issues

## Installation

1. Clone this repository:

   ```sh
   git clone https://github.com/your-repo/bisaya-plusplus-interpreter.git
   ```

2. Navigate to the project directory:

   ```sh
   cd bisaya-plusplus-interpreter
   ```

3. Compile the Java files:

   ```sh
   javac -d bin src/*.java
   ```

4. Run the interpreter:

   ```sh
   java -cp bin Main
   ```

## Example Usage

A sample Bisaya++ script:

```plaintext
-- this is a sample program in Bisaya++
SUGOD
MUGNA NUMERO x, y, z=5
MUGNA LETRA a_1=’n’
MUGNA TINUOD t=”OO”
x=y=4
a_1=’c’
-- this is a comment
IPAKITA: x & t & z & $ & a_1 & [#] & “last”
KATAPUSAN
```

### Expected Output

```plaintext
4OO5
c#last
```

## Contributing

Feel free to contribute by submitting pull requests or reporting issues.

## License

This project is open-source under the MIT License.
