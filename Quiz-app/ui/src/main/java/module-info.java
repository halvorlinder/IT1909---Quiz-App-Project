module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens ui to javafx.graphics, javafx.fxml;
    opens ui.controllers to javafx.fxml, javafx.graphics;
    opens ui.constants to javafx.fxml, javafx.graphics;
}
