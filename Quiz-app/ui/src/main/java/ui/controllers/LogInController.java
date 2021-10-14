package ui.controllers;

import core.User;
import core.UserData;
import io.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.Utilities;

import java.io.IOException;

public class LogInController {
    @FXML
    private PasswordField logInPassword;
    @FXML
    private TextField logInUserName;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerUserName;

    private UserPersistence userPersistence;
    private UserData userData;

    @FXML
    public void initialize(){
        try {
            userPersistence = new UserPersistence();
            userData = userPersistence.loadUserData();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        System.out.println(userData.getUserNames());
        try {
            if (userData.attemptLogIn(logInUserName.getText(), logInPassword.getText()))
                logIn(actionEvent, logInUserName.getText());
            else
                Utilities.alertUser("Brukernavn eller passord er feil");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        //TODO create logic for registering and checking username and password
        try {
            if (userData.attemptRegister(registerUserName.getText(), registerPassword.getText())) {
                userPersistence.saveUserData(userData);
                logIn(actionEvent, registerUserName.getText());
            }
            else{
                Utilities.alertUser("Brukernavn er tatt");
            }
        } catch (IOException ioException) {
            Utilities.alertUser("Noe gikk galt");
        }
    }

    private void logIn(ActionEvent actionEvent, String username) throws IOException {
        User.setUserName(username);
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }

}
