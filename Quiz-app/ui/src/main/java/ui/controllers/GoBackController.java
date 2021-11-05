package ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ui.Utilities;

import java.io.IOException;

public class GoBackController {
    private InitializableController previousController;
    private Button button;
    private Parent previousRoot;


    public GoBackController(){
    }

    public void setBackButton(Button backButton){
        button = backButton;
        backButton.setOnAction((ae)->goBack());
    }

    public void setPreviousPageInfo(InitializableController previousController, Parent previousRoot){
        this.previousRoot = previousRoot;
        this.previousController = previousController;
    }

    private void goBack(){
        previousController.initialize();
        button.getScene().setRoot(previousRoot);
    }

}
