package ui.controllers;

import core.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.APIClientService;
import ui.User;
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

    private APIClientService apiClientService;

    /**
     * fetches userData
     */
    @FXML
    public void initialize() {
        apiClientService = new APIClientService();
    }


    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        try {
            UserRecord userRecord = new UserRecord(logInUserName.getText(), logInPassword.getText());
            apiClientService.loginUser(userRecord);
            logIn(actionEvent, logInUserName.getText());
        } catch (IOException | InterruptedException ioException) {
            Utilities.alertUser("Brukernavn eller passord er feil");
        }

    }

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        try {
            UserRecord userRecord = new UserRecord(registerUserName.getText(), registerPassword.getText());
            apiClientService.registerUser(userRecord);
            logIn(actionEvent, registerUserName.getText());
        } catch (IOException | InterruptedException ioException) {
            Utilities.alertUser("Brukernavn er tatt");
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
        final FXMLLoader loader = Utilities.getFXMLLoader("HomePage.fxml");
        HomePageController controller = new HomePageController(new User(username));
        loader.setController(controller);
        final Parent root = loader.load();
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
