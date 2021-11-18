package ui.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public interface InitializableController {
    /**
     * initializes the controller
     */
    @FXML
    void initialize() throws IOException;
}
