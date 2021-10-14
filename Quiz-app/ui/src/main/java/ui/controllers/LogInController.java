package ui.controllers;

import core.User;
import core.UserData;
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

    private UserPersistence userPersistence;
    private UserData userData;

    @FXML
    public void initialize(){
        try {
            userPersistence = new UserPersistence();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        //TODO create logic for checking username and password
        try {
            if (true)
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
            if (true) {
//                userPersistence.addUser(registerUserName.getText(), registerPassword.getText());
                logIn(actionEvent, registerUserName.getText());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void logIn(ActionEvent actionEvent, String username) throws IOException {
        User.setUserName(username);
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }

}
