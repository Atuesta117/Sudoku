# 🧩 Sudoku Game

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-17-blue)
![License](https://img.shields.io/badge/License-MIT-green)

An interactive Sudoku game developed in Java with JavaFX and Scene Builder.

</div>

## 📋 Description

Sudoku Game is a desktop application that allows you to play the classic Sudoku game with a modern graphical interface and additional features like help system, automatic validation, and move tracking.

## ✨ Features

- ✅ **Modern graphical interface** with JavaFX
- ✅ **6x6 board** with 2x3 sections
- ✅ **Intelligent help system** that reveals correct moves
- ✅ **Real-time validation** of moves
- ✅ **Automatic victory detection**
- ✅ **Keyboard navigation** between cells
- ✅ **Move history** - last 6 entered values
- ✅ **Visual highlighting** of helped cells
- ✅ **Advanced data structures**: N-ary Tree, ArrayList and Queue

## 🛠️ Technologies Used

- **Java 17+**
- **JavaFX 17** - Graphical interface
- **Scene Builder** - Interface design
- **Maven** - Dependency management

## 🎮 How to Play

1. **Start the application** - Run the `SudokuMain` class
2. **Complete the board** - Fill empty cells with numbers from 1 to 6
3. **Sudoku 6x6 Rules**:
    - Each row must contain numbers 1-6 without repetition
    - Each column must contain numbers 1-6 without repetition
    - Each 2x3 section must contain numbers 1-6 without repetition
4. **Use the help system** - Press the "Help" button to get suggestions
5. **Track moves** - Observe the last 6 entered numbers at the top
6. **Win** - Complete the entire board correctly to win

## 🏗️ Architecture and Data Structures

### Implemented Structures
- **N-ary Tree**: Models the hierarchical structure of the Sudoku board (sections → cells)
- **ArrayList**: Manages empty cells available for the help system
- **Queue (LinkedList)**: Implements a circular buffer to show the last 6 entered values

### Patterns and Algorithms
- **Observer Pattern**: Listeners for real-time validation
- **FIFO (First-In-First-Out)**: Move history management with Queue
- **Random Sampling**: Random cell selection using Collections.shuffle()
- **Recursive Navigation**: Cell movement with jumps over non-editable fields

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/com/sudoku/
│   │   ├── controller/          # View controllers
│   │   │   ├── GameWindowController.java
│   │   │   ├── SudokuMainMenuController.java
│   │   │   └── VictoryWindowController.java
│   │   ├── model/              # Game logic and data structures
│   │   │   ├── Board.java
│   │   │   ├── Helper.java
│   │   │   ├── Node.java
│   │   │   └── RandomInitialValues.java
│   │   └── view/               # Window management
│   │       ├── GameWindow.java
│   │       ├── SudokuMainMenu.java
│   │       └── VictoryWindow.java
│   └── resources/com/sudoku/
│       ├── *.fxml              # Graphical interface files
│       ├── style.css           # CSS styles
│       └── *.png               # Graphic assets

```


## 🚀 Installation and Execution

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+

### Execution Steps
```bash
# Clone the repository
git clone https://github.com/Atuesta117/Sudoku
cd sudoku-game

# Compile with Maven
mvn clean compile

# Run the application
mvn javafx:run
```

## 👥 Authors

- **Juan Jose Atuesta** - Main development
- **David Alejandro Garcia** - Development and collaboration

## 📄 License

This project is under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Have fun playing and improving your Sudoku skills!** 🎮

</div>