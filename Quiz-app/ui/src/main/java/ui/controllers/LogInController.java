package ui.controllers;

import core.User;
import core.UserData;
import io.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.Utilities;

import java.io.IOException;

public final class LogInController {
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
    private final String fileName;

    /**
     * fetches userData
     */
    @FXML
    public void initialize() {
        try {
            userPersistence = new UserPersistence(fileName);
            userData = userPersistence.loadUserData();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     *
     */
    public LogInController() {
        fileName = "users.json";
    }

    /**
     * @param fileName the file where users are saved
     */
    public LogInController(String fileName) {
        this.fileName = fileName;
    }

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
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

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        //TODO create logic for registering and checking username and password
        try {
            if (userData.attemptRegister(registerUserName.getText(), registerPassword.getText())) {
                userPersistence.saveUserData(userData);
                logIn(actionEvent, registerUserName.getText());
            } else {
                Utilities.alertUser("Brukernavn er tatt");
            }
        } catch (IOException ioException) {
            Utilities.alertUser("Noe gikk galt");
        }
    }

    /**
     * logs into the applicatio
     *
     * @param actionEvent
     * @param username
     * @throws IOException
     */
    private void logIn(ActionEvent actionEvent, String username) throws IOException {
        User.setUserName(username);
        final FXMLLoader loader = Utilities.getFXMLLoader("HomePage.fxml");
        // HomePageController controller = new HomePageController();
        // loader.setController(controller);
        final Parent root = loader.load();
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
