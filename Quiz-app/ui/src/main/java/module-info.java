module quiz.ui {
    requires quiz.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ui to javafx.graphics, javafx.fxml;
    opens ui.controllers to javafx.fxml, javafx.graphics;
}
