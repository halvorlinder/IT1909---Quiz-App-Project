package ui.controllers;

import core.User;
import io.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    UserPersistence userPersistence = new UserPersistence();

    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        //TODO create logic for checking username and password
        try {
            if (userPersistence.successfulLogIn(logInUserName.getText(), logInPassword.getText()))
                logIn(actionEvent, logInUserName.getText());
            else
                System.out.println("Username or password is wrong");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        //TODO create logic for registering and checking username and password
        try {
            if (!userPersistence.userExists(registerUserName.getText())) {
                userPersistence.addUser(registerUserName.getText(), registerPassword.getText());
                logIn(actionEvent, registerUserName.getText());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void logIn(ActionEvent actionEvent, String userName) throws IOException {
        User.setUserName(userName);
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }

}
