module com.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.sudoku to javafx.fxml;
    opens com.sudoku.controller to javafx.fxml;
    exports com.sudoku;
}